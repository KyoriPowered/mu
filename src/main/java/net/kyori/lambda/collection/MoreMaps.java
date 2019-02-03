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
package net.kyori.lambda.collection;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/*
 * Name is prefixed with 'More' to avoid conflict with com.google.common.collect.Maps
 */

/**
 * A collection of utilities for working with maps.
 */
public interface MoreMaps {
  /**
   * Fills a map with some entries made from some keys and values.
   *
   * @param map the map
   * @param keys the keys
   * @param values the values
   * @param <K> the key type
   * @param <V> the value type
   * @return the map
   * @throws NoSuchElementException if the length of {@code keys} and {@code values} differs
   */
  static <K, V> @NonNull Map<K, V> from(final @NonNull Map<K, V> map, final @NonNull Iterable<K> keys, final @NonNull Iterable<V> values) {
    final Iterator<V> valuesIt = values.iterator();

    for(final K key : keys) {
      map.put(key, valuesIt.next()); // 'next' will throw if too many keys and not enough values
    }

    // throw if too many values and not enough keys
    if(valuesIt.hasNext()) {
      throw new NoSuchElementException();
    }

    return map;
  }
}
