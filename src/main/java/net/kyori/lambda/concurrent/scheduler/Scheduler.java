/*
 * This file is part of lambda, licensed under the MIT License.
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
package net.kyori.lambda.concurrent.scheduler;

import net.kyori.lambda.exception.ExceptionHandler;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A scheduler.
 */
public interface Scheduler {
  /**
   * Creates a scheduler, wrapping a scheduled executor service.
   *
   * @param es the scheduled executor service
   * @param eh the exception handler
   * @return the scheduler
   */
  static @NonNull Scheduler forSES(final @NonNull ScheduledExecutorService es, final @NonNull ExceptionHandler eh) {
    return new ScheduledExecutorServiceSchedulerImpl(es, eh);
  }

  /**
   * Schedules an immediate task.
   *
   * @param runnable the runnable
   * @return the task
   */
  @NonNull Task immediate(final @NonNull Runnable runnable);

  /**
   * Schedules an immediate task.
   *
   * @param supplier the supplier
   * @param <T> the type
   * @return the task
   */
  <T> @NonNull FutureTask<T> immediate(final @NonNull Supplier<T> supplier);

  /**
   * Schedules a delayed task.
   *
   * @param runnable the runnable
   * @param delay the delay
   * @return the task
   */
  @NonNull Task delayed(final @NonNull Runnable runnable, final @NonNull Duration delay);

  /**
   * Schedules a delayed task.
   *
   * @param supplier the supplier
   * @param delay the delay
   * @param <T> the type
   * @return the task
   */
  <T> @NonNull FutureTask<T> delayed(final @NonNull Supplier<T> supplier, final @NonNull Duration delay);

  /**
   * Schedules a repeating task with no delay.
   *
   * @param runnable the runnable
   * @param period the repeat period
   * @return the task
   */
  @NonNull Task repeating(final @NonNull Runnable runnable, final @NonNull Duration period);

  /**
   * Schedules a repeating task with no delay.
   *
   * @param consumer the consumer
   * @param period the repeat period
   * @return the task
   */
  @NonNull Task repeating(final @NonNull Consumer<Task> consumer, final @NonNull Duration period);

  /**
   * Schedules a repeating task with a delay.
   *
   * @param runnable the runnable
   * @param delay the delay
   * @param period the repeat period
   * @return the task
   */
  @NonNull Task delayedRepeating(final @NonNull Runnable runnable, final @NonNull Duration delay, final @NonNull Duration period);

  /**
   * Schedules a repeating task with a delay.
   *
   * @param consumer the consumer
   * @param delay the delay
   * @param period the repeat period
   * @return the task
   */
  @NonNull Task delayedRepeating(final @NonNull Consumer<Task> consumer, final @NonNull Duration delay, final @NonNull Duration period);

  /**
   * Cancels all tasks.
   */
  void cancel();
}
