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
package net.kyori.mu.examine;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An examiner.
 *
 * @param <R> the result type
 */
public interface Examiner<R> {
  /**
   * Examines.
   *
   * @param value the value to examine
   * @return the result
   */
  default @NonNull R examine(final @Nullable Object value) {
    if(value == null) {
      return this.nil();
    } else if(value.getClass().isArray()) {
      return this.array((Object[]) value);
    } else if(value instanceof Collection<?>) {
      return this.collection((Collection<?>) value);
    } else if(value instanceof Examinable) {
      return this.examinable((Examinable) value);
    } else if(value instanceof Map<?, ?>) {
      return this.map((Map<?, ?>) value);
    } else if(value instanceof Stream<?>) {
      return this.stream((Stream<?>) value);
    } else {
      return this.scalar(value);
    }
  }

  /**
   * Examines an array.
   *
   * @param array the array
   * @param <E> the element type
   * @return the result from examining an array
   */
  default <E> @NonNull R array(final @NonNull E[] array) {
    return this.array(array, Arrays.stream(array).map(this::examine));
  }

  /**
   * Examines an array.
   *
   * @param array the array
   * @param elements the array elements
   * @param <E> the element type
   * @return the result from examining an array
   */
  <E> @NonNull R array(final @NonNull E[] array, final @NonNull Stream<R> elements);

  /**
   * Examines a collection.
   *
   * @param collection the collection
   * @param <E> the element type
   * @return the result from examining a collection
   */
  default <E> @NonNull R collection(final @NonNull Collection<E> collection) {
    return this.collection(collection, collection.stream().map(this::examine));
  }

  /**
   * Examines a collection.
   *
   * @param collection the collection
   * @param elements the collection elements
   * @param <E> the element type
   * @return the result from examining a collection
   */
  <E> @NonNull R collection(final @NonNull Collection<E> collection, final @NonNull Stream<R> elements);

  /**
   * Examines an examinable.
   *
   * @param examinable the examinable
   * @return the result from examining an examinable
   */
  default @NonNull R examinable(final @NonNull Examinable examinable) {
    return this.examinable(examinable, examinable.examinableProperties().map(property -> new AbstractMap.SimpleImmutableEntry<>(property.name(), this.examine(property.value()))));
  }

  /**
   * Examines an examinable.
   *
   * @param examinable the examinable
   * @param properties the examinable properties
   * @return the result from examining an examinable
   */
  @NonNull R examinable(final @NonNull Examinable examinable, final @NonNull Stream<Map.Entry<String, R>> properties);

  /**
   * Examines a map.
   *
   * @param map the map
   * @param <K> the key type
   * @param <V> the value type
   * @return the result from examining a map
   */
  default <K, V> @NonNull R map(final @NonNull Map<K, V> map) {
    return this.map(map, map.entrySet().stream().map(entry -> new AbstractMap.SimpleImmutableEntry<>(this.examine(entry.getKey()), this.examine(entry.getValue()))));
  }

  /**
   * Examines a map.
   *
   * @param map the map
   * @param entries the map entries
   * @param <K> the key type
   * @param <V> the value type
   * @return the result from examining a map
   */
  <K, V> @NonNull R map(final @NonNull Map<K, V> map, final @NonNull Stream<Map.Entry<R, R>> entries);

  /**
   * Examines {@code null}.
   *
   * @return the result from examining {@code null}
   */
  @NonNull R nil();

  /**
   * Examines a scalar value.
   *
   * @param value the scalar value
   * @return the result from examining a scalar
   */
  @NonNull R scalar(final @NonNull Object value);

  /**
   * Examines a stream.
   *
   * @param stream the stream
   * @param <T> the type
   * @return the result from examining a stream
   */
  <T> @NonNull R stream(final @NonNull Stream<T> stream);
}
