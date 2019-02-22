/*
 * This file is part of mu, licensed under the MIT License.
 *
 * Copyright (c) 2018-2019 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.mu.concurrent.scheduler;

import net.kyori.mu.exception.ExceptionHandler;
import net.kyori.mu.function.ThrowingConsumer;
import net.kyori.mu.function.ThrowingRunnable;
import net.kyori.mu.function.ThrowingSupplier;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class ScheduledExecutorServiceSchedulerImpl implements Scheduler {
  private final Set<AbstractTask<?>> tasks = Collections.newSetFromMap(new WeakHashMap<>());
  private final ScheduledExecutorService es;
  private final ExceptionHandler eh;

  ScheduledExecutorServiceSchedulerImpl(final ScheduledExecutorService es, final ExceptionHandler eh) {
    this.es = es;
    this.eh = eh;
  }

  @Override
  public @NonNull Task immediate(final @NonNull Runnable runnable) {
    return this.schedule(runnable, null, null);
  }

  @Override
  public <T> @NonNull FutureTask<T> immediate(final @NonNull Supplier<T> supplier) {
    return this.schedule(supplier, null);
  }

  @Override
  public @NonNull Task delayed(final @NonNull Runnable runnable, final @NonNull Duration delay) {
    return this.schedule(runnable, delay, null);
  }

  @Override
  public <T> @NonNull FutureTask<T> delayed(final @NonNull Supplier<T> supplier, final @NonNull Duration delay) {
    return this.schedule(supplier, delay);
  }

  @Override
  public @NonNull Task repeating(final @NonNull Runnable runnable, final @NonNull Duration period) {
    return this.schedule(runnable, null, period);
  }

  @Override
  public @NonNull Task repeating(final @NonNull Consumer<Task> consumer, final @NonNull Duration period) {
    return this.schedule(consumer, null, period);
  }

  @Override
  public @NonNull Task delayedRepeating(final @NonNull Runnable runnable, final @NonNull Duration delay, final @NonNull Duration period) {
    return this.schedule(runnable, delay, period);
  }

  @Override
  public @NonNull Task delayedRepeating(final @NonNull Consumer<Task> consumer, final @NonNull Duration delay, final @NonNull Duration period) {
    return this.schedule(consumer, delay, period);
  }

  private @NonNull Task schedule(final @NonNull Runnable runnable, final @Nullable Duration delay, final @Nullable Duration period) {
    return this.schedule(new RunnableTask(runnable), delay, period);
  }

  private @NonNull Task schedule(final @NonNull Consumer<Task> consumer, final @Nullable Duration delay, final @Nullable Duration period) {
    return this.schedule(new ConsumerTask(consumer), delay, period);
  }

  private <T> @NonNull FutureTask<T> schedule(final @NonNull Supplier<T> supplier, final @Nullable Duration delay) {
    return this.schedule(new SupplierTask<>(supplier), delay, null);
  }

  private <T extends AbstractTask<?>> @NonNull T schedule(final @NonNull T task, final @Nullable Duration delay, final @Nullable Duration period) {
    task.schedule(delay, period);

    synchronized(this) {
      this.tasks.add(task);
    }

    return task;
  }

  @Override
  public void cancel() {
    synchronized(this) {
      for(final AbstractTask<?> task : this.tasks) {
        task.cancel0();
      }
      this.tasks.clear();
    }
  }

  private abstract class AbstractTask<U> implements Runnable, Task {
    final U executable;
    @MonotonicNonNull ScheduledFuture<?> scheduled;

    AbstractTask(final @NonNull U executable) {
      this.executable = executable;
    }

    final void schedule(final @Nullable Duration delay, final @Nullable Duration period) {
      if(period == null) {
        this.scheduled = ScheduledExecutorServiceSchedulerImpl.this.es.schedule(this, delay == null ? 0 : delay.toNanos(), TimeUnit.NANOSECONDS);
      } else {
        this.scheduled = ScheduledExecutorServiceSchedulerImpl.this.es.scheduleAtFixedRate(this, delay == null ? 0 : delay.toNanos(), period.toNanos(), TimeUnit.NANOSECONDS);
      }
    }

    @Override
    public final void run() {
      try {
        this.run0();
      } catch(final Throwable t) {
        this.exceptionCaught(t);
      }
    }

    abstract void run0() throws Throwable;

    void exceptionCaught(final Throwable throwable) {
      ScheduledExecutorServiceSchedulerImpl.this.eh.handleException(throwable, this.executable);
    }

    @Override
    public final void cancel() {
      synchronized(ScheduledExecutorServiceSchedulerImpl.this) {
        this.cancel0();
        ScheduledExecutorServiceSchedulerImpl.this.tasks.remove(this);
      }
    }

    void cancel0() {
      this.scheduled.cancel(false);
    }
  }

  private class RunnableTask extends AbstractTask<Runnable> {
    RunnableTask(final @NonNull Runnable runnable) {
      super(runnable);
    }

    @Override
    void run0() throws Throwable {
      if(this.executable instanceof ThrowingRunnable<?>) {
        ((ThrowingRunnable) this.executable).throwingRun();
      } else {
        this.executable.run();
      }
    }
  }

  private class ConsumerTask extends AbstractTask<Consumer<Task>> {
    ConsumerTask(final @NonNull Consumer<Task> consumer) {
      super(consumer);
    }

    @Override
    void run0() throws Throwable {
      if(this.executable instanceof ThrowingConsumer<?, ?>) {
        ((ThrowingConsumer<Task, ?>) this.executable).throwingAccept(this);
      } else {
        this.executable.accept(this);
      }
    }
  }

  private class SupplierTask<T> extends AbstractTask<Supplier<T>> implements FutureTask<T> {
    private final CompletableFuture<T> completable = new CompletableFuture<>();

    SupplierTask(final @NonNull Supplier<T> supplier) {
      super(supplier);
    }

    @Override
    void run0() throws Throwable {
      final T value;
      if(this.executable instanceof ThrowingSupplier<?, ?>) {
        value = ((ThrowingSupplier<T, ?>) this.executable).throwingGet();
      } else {
        value = this.executable.get();
      }
      this.completable.complete(value);
    }

    @Override
    void exceptionCaught(final Throwable throwable) {
      this.completable.completeExceptionally(throwable);
      super.exceptionCaught(throwable);
    }

    @Override
    void cancel0() {
      this.completable.cancel(false);
      super.cancel0();
    }

    @Override
    public @NonNull CompletableFuture<T> future() {
      return this.completable;
    }
  }
}
