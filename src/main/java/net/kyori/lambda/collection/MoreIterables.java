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
package net.kyori.lambda.collection;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.BiFunction;
import java.util.function.Function;

/*
 * Name is prefixed with 'More' to avoid conflict with com.google.common.collect.Iterables
 */

/**
 * A collection of utilities for working with iterables.
 */
public interface MoreIterables {
  /**
   * Folds {@code elements} from the left, starting with {@code seed} and successively calling {@code combiner}.
   *
   * @param elements the elements to fold
   * @param seed the starting element
   * @param combiner a function to combine elements
   * @param <E> the element type
   * @param <T> the type to fold over
   * @return a folded value
   */
  static <E, T> /* @Nullable */ T lfold(final @NonNull Iterable<? extends E> elements, final /* @Nullable */ T seed, final @NonNull BiFunction<? super T, E, T> combiner) {
    T result = seed;
    for(final E element : elements) {
      result = combiner.apply(result, element);
    }
    return result;
  }

  /**
   * Reduces {@code iterable} into a single element.
   *
   * @param iterable the iterable
   * @param function the flattener
   * @param <E> the element type
   * @return a single element
   */
  static <E> /* @Nullable */ E reduce(final /* @Nullable */ Iterable<? extends E> iterable, final @NonNull Function<Iterable<? extends E>, ? extends E> function) {
    return function.apply(iterable);
  }
}
