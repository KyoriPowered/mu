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
package net.kyori.lambda.function;

import net.kyori.lambda.exception.Exceptions;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.BiPredicate;

/**
 * A {@link BiPredicate} that allows for throwing checked exceptions.
 *
 * @param <T> the input type
 * @param <E> the exception type
 */
@FunctionalInterface
public interface ThrowingBiPredicate<T, U, E extends Throwable> extends BiPredicate<T, U> {
  /**
   * Returns the same throwing bi-predicate.
   *
   * @param predicate the predicate
   * @param <T> the first input type
   * @param <U> the second input type
   * @param <E> the exception type
   * @return the predicate
   */
  static <T, U, E extends Throwable> @NonNull ThrowingBiPredicate<T, U, E> of(final @NonNull ThrowingBiPredicate<T, U, E> predicate) {
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
  static <T, U, E extends Throwable> @NonNull ThrowingBiPredicate<T, U, E> unwrapping(final @NonNull ThrowingBiPredicate<T, U, E> predicate) {
    return (first, second) -> {
      try {
        return predicate.throwingTest(first, second);
      } catch(final Throwable t) {
        throw Exceptions.rethrow(Exceptions.unwrap(t));
      }
    };
  }

  /**
   * Evaluates this predicate on the given arguments.
   *
   * @param first the first input argument
   * @param second the second input argument
   * @return {@code true} if the input arguments matches the predicate, {@code false} otherwise
   * @throws E potential exception
   */
  boolean throwingTest(final T first, final U second) throws E;

  @Override
  default boolean test(final T first, final U second) {
    try {
      return this.throwingTest(first, second);
    } catch(final Throwable t) {
      throw Exceptions.rethrow(t);
    }
  }
}
