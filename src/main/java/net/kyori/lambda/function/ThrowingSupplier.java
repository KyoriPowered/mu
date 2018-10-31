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
package net.kyori.lambda.function;

import net.kyori.lambda.exception.Exceptions;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Supplier;

/**
 * A {@link Supplier} that allows for throwing checked exceptions.
 *
 * @param <T> the result type
 * @param <E> the exception type
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Throwable> extends Supplier<T> {
  /**
   * Returns the same throwing supplier.
   *
   * @param supplier the supplier
   * @param <T> the output type
   * @param <E> the exception type
   * @return the supplier
   */
  static <T, E extends Throwable> @NonNull ThrowingSupplier<T, E> of(final @NonNull ThrowingSupplier<T, E> supplier) {
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
  static <T, E extends Throwable> @NonNull ThrowingSupplier<T, E> unwrapping(final @NonNull ThrowingSupplier<T, E> supplier) {
    return () -> {
      try {
        return supplier.throwingGet();
      } catch(final Throwable t) {
        throw Exceptions.rethrow(Exceptions.unwrap(t));
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
  static <T, E extends Throwable> @NonNull T get(final @NonNull ThrowingSupplier<T, E> supplier) {
    return supplier.get(); // get() rethrows for us
  }

  /**
   * Gets a result.
   *
   * @return a result
   * @throws E potential exception
   */
  T throwingGet() throws E;

  @Override
  default T get() {
    try {
      return this.throwingGet();
    } catch(final Throwable t) {
      throw Exceptions.rethrow(t);
    }
  }
}
