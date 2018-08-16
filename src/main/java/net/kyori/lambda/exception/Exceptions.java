/*
 * This file is part of lambda, licensed under the MIT License.
 *
 * Copyright (c) 2018 KyoriPowered
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
package net.kyori.lambda.exception;

import net.kyori.lambda.function.ThrowingBiConsumer;
import net.kyori.lambda.function.ThrowingBiFunction;
import net.kyori.lambda.function.ThrowingBiPredicate;
import net.kyori.lambda.function.ThrowingBinaryOperator;
import net.kyori.lambda.function.ThrowingConsumer;
import net.kyori.lambda.function.ThrowingFunction;
import net.kyori.lambda.function.ThrowingPredicate;
import net.kyori.lambda.function.ThrowingRunnable;
import net.kyori.lambda.function.ThrowingSupplier;
import net.kyori.lambda.function.ThrowingUnaryOperator;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * A collection of methods for working with exceptions.
 */
public interface Exceptions {
  /**
   * Returns the same throwing bi-consumer.
   *
   * @param consumer the bi-consumer
   * @param <T> the first input type
   * @param <U> the second input type
   * @param <E> the exception type
   * @return the bi-consumer
   */
  static <T, U, E extends Throwable> @NonNull BiConsumer<T, U> rethrowBiConsumer(final @NonNull ThrowingBiConsumer<T, U, E> consumer) {
    return consumer;
  }

  /**
   * Returns a consumer which will unwrap and rethrow any throwables caught in {@code consumer}.
   *
   * @param consumer the consumer
   * @param <T> the first input type
   * @param <U> the second input type
   * @param <E> the exception type
   * @return a consumer
   */
  static <T, U, E extends Throwable> @NonNull BiConsumer<T, U> unwrappingRethrowBiConsumer(final @NonNull ThrowingBiConsumer<T, U, E> consumer) {
    return (first, second) -> {
      try {
        consumer.throwingAccept(first, second);
      } catch(final Throwable th) {
        throw rethrow(unwrap(th));
      }
    };
  }

  /**
   * Returns the same throwing bi-function.
   *
   * @param function the bi-function
   * @param <T> the first input type
   * @param <U> the second input type
   * @param <R> the output type
   * @param <E> the exception type
   * @return the bi-function
   */
  static <T, U, R, E extends Throwable> @NonNull BiFunction<T, U, R> rethrowBiFunction(final @NonNull ThrowingBiFunction<T, U, R, E> function) {
    return function;
  }

  /**
   * Returns a function which will unwrap and rethrow any throwables caught in {@code function}.
   *
   * @param function the function
   * @param <T> the first input type
   * @param <U> the second input type
   * @param <R> the output type
   * @param <E> the exception type
   * @return a bi-function
   */
  static <T, U, R, E extends Throwable> @NonNull BiFunction<T, U, R> unwrappingRethrowBiFunction(final @NonNull ThrowingBiFunction<T, U, R, E> function) {
    return (first, second) -> {
      try {
        return function.throwingApply(first, second);
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing binary operator.
   *
   * @param operator the binary operator
   * @param <T> the input type
   * @param <E> the exception type
   * @return the binary operator
   */
  static <T, E extends Throwable> @NonNull BinaryOperator<T> rethrowBinaryOperator(final @NonNull ThrowingBinaryOperator<T, E> operator) {
    return operator;
  }

  /**
   * Returns a binary operator which will unwrap and rethrow any throwables caught in {@code operator}.
   *
   * @param operator the binary operator
   * @param <T> the input type
   * @param <E> the exception type
   * @return the binary operator
   */
  static <T, E extends Throwable> @NonNull BinaryOperator<T> unwrappingRethrowBinaryOperator(final @NonNull ThrowingBinaryOperator<T, E> operator) {
    return (first, second) -> {
      try {
        return operator.throwingApply(first, second);
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing bi-predicate.
   *
   * @param predicate the predicate
   * @param <T> the first input type
   * @param <U> the second input type
   * @param <E> the exception type
   * @return the predicate
   */
  static <T, U, E extends Throwable> @NonNull BiPredicate<T, U> rethrowBiPredicate(final @NonNull ThrowingBiPredicate<T, U, E> predicate) {
    return predicate;
  }

  /**
   * Returns a bi-predicate which will unwrap and rethrow any throwables caught in {@code predicate}.
   *
   * @param predicate the predicate
   * @param <T> the first input type
   * @param <U> the second input type
   * @param <E> the exception type
   * @return a predicate
   */
  static <T, U, E extends Throwable> @NonNull BiPredicate<T, U> unwrappingRethrowBiPredicate(final @NonNull ThrowingBiPredicate<T, U, E> predicate) {
    return (first, second) -> {
      try {
        return predicate.throwingTest(first, second);
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing consumer.
   *
   * @param consumer the consumer
   * @param <T> the input type
   * @param <E> the exception type
   * @return the consumer
   */
  static <T, E extends Throwable> @NonNull Consumer<T> rethrowConsumer(final @NonNull ThrowingConsumer<T, E> consumer) {
    return consumer;
  }

  /**
   * Returns a consumer which will unwrap and rethrow any throwables caught in {@code consumer}.
   *
   * @param consumer the consumer
   * @param <T> the input type
   * @param <E> the exception type
   * @return a consumer
   */
  static <T, E extends Throwable> @NonNull Consumer<T> unwrappingRethrowConsumer(final @NonNull ThrowingConsumer<T, E> consumer) {
    return input -> {
      try {
        consumer.throwingAccept(input);
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing function.
   *
   * @param function the function
   * @param <T> the input type
   * @param <R> the output type
   * @param <E> the exception type
   * @return the function
   */
  static <T, R, E extends Throwable> @NonNull Function<T, R> rethrowFunction(final @NonNull ThrowingFunction<T, R, E> function) {
    return function;
  }

  /**
   * Returns a function which will unwrap and rethrow any throwables caught in {@code function}.
   *
   * @param function the function
   * @param <T> the input type
   * @param <R> the output type
   * @param <E> the exception type
   * @return a function
   */
  static <T, R, E extends Throwable> @NonNull Function<T, R> unwrappingRethrowFunction(final @NonNull ThrowingFunction<T, R, E> function) {
    return input -> {
      try {
        return function.throwingApply(input);
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing predicate.
   *
   * @param predicate the predicate
   * @param <T> the input type
   * @param <E> the exception type
   * @return the predicate
   */
  static <T, E extends Throwable> @NonNull Predicate<T> rethrowPredicate(final @NonNull ThrowingPredicate<T, E> predicate) {
    return predicate;
  }

  /**
   * Returns a predicate which will unwrap and rethrow any throwables caught in {@code predicate}.
   *
   * @param predicate the predicate
   * @param <T> the input type
   * @param <E> the exception type
   * @return a predicate
   */
  static <T, E extends Throwable> @NonNull Predicate<T> unwrappingRethrowPredicate(final @NonNull ThrowingPredicate<T, E> predicate) {
    return input -> {
      try {
        return predicate.throwingTest(input);
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing runnable.
   *
   * @param runnable the runnable
   * @param <E> the exception type
   * @return the runnable
   */
  static <E extends Throwable> @NonNull Runnable rethrowRunnable(final @NonNull ThrowingRunnable<E> runnable) {
    return runnable;
  }

  /**
   * Returns a runnable which will unwrap and rethrow any throwables caught in {@code runnable}.
   *
   * @param runnable the runnable
   * @param <E> the exception type
   * @return the runnable
   */
  static <E extends Throwable> @NonNull Runnable unwrappingRethrowRunnable(final @NonNull ThrowingRunnable<E> runnable) {
    return () -> {
      try {
        runnable.throwingRun();
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing supplier.
   *
   * @param supplier the supplier
   * @param <T> the output type
   * @param <E> the exception type
   * @return the supplier
   */
  static <T, E extends Throwable> @NonNull Supplier<T> rethrowSupplier(final @NonNull ThrowingSupplier<T, E> supplier) {
    return supplier;
  }

  /**
   * Returns a supplier which will unwrap and rethrow any throwables caught in {@code supplier}.
   *
   * @param supplier the supplier
   * @param <T> the output type
   * @param <E> the exception type
   * @return a supplier
   */
  static <T, E extends Throwable> @NonNull Supplier<T> unwrappingRethrowSupplier(final @NonNull ThrowingSupplier<T, E> supplier) {
    return () -> {
      try {
        return supplier.throwingGet();
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Returns the same throwing unary operator.
   *
   * @param operator the unary operator
   * @param <T> the input type
   * @param <E> the exception type
   * @return the unary operator
   */
  static <T, E extends Throwable> @NonNull UnaryOperator<T> rethrowUnaryOperator(final @NonNull ThrowingUnaryOperator<T, E> operator) {
    return operator;
  }

  /**
   * Returns a unary operator which will unwrap and rethrow any throwables caught in {@code operator}.
   *
   * @param operator the unary operator
   * @param <T> the input type
   * @param <E> the exception type
   * @return the unary operator
   */
  static <T, E extends Throwable> @NonNull UnaryOperator<T> unwrappingRethrowUnaryOperator(final @NonNull ThrowingUnaryOperator<T, E> operator) {
    return input -> {
      try {
        return operator.throwingApply(input);
      } catch(final Throwable t) {
        throw rethrow(unwrap(t));
      }
    };
  }

  /**
   * Gets the result of {@code supplier}, or re-throws an exception, sneakily.
   *
   * @param supplier the supplier
   * @param <T> the result type
   * @param <E> the exception type
   * @return the result
   */
  static <T, E extends Throwable> @NonNull T getOrRethrow(final @NonNull ThrowingSupplier<T, E> supplier) {
    return supplier.get(); // get() rethrows for us
  }

  /**
   * Re-throws an exception, sneakily.
   *
   * @param exception the exception
   * @param <E> the exception type
   * @return nothing
   * @throws E the exception
   */
  @SuppressWarnings("unchecked")
  static <E extends Throwable> @NonNull RuntimeException rethrow(final @NonNull Throwable exception) throws E {
    throw (E) exception;
  }

  /**
   * Propagates {@code throwable} as-is if it is an instance of {@link RuntimeException} or
   * {@link Error}, otherwise wraps it in a {@code RuntimeException} and then
   * propagates.
   *
   * @param throwable the throwable
   * @return nothing
   */
  static RuntimeException propagate(final @NonNull Throwable throwable) {
    throwIfUnchecked(throwable);
    throw new RuntimeException(throwable);
  }

  /**
   * Throws {@code throwable} if it is a {@link RuntimeException} or {@link Error}.
   *
   * @param throwable the throwable
   */
  static void throwIfUnchecked(final @NonNull Throwable throwable) {
    if(throwable instanceof RuntimeException) {
      throw (RuntimeException) throwable;
    }
    if(throwable instanceof Error) {
      throw (Error) throwable;
    }
  }

  /**
   * Unwraps a throwable.
   *
   * @param throwable the throwable
   * @return the unwrapped throwable, or the original throwable
   */
  static @NonNull Throwable unwrap(final @NonNull Throwable throwable) {
    if(throwable instanceof InvocationTargetException) {
      final /* @Nullable */ Throwable cause = throwable.getCause();
      if(cause != null) {
        return cause;
      }
    }
    return throwable;
  }
}
