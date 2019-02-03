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
package net.kyori.lambda.examine;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringExaminer implements Examiner<String> {
  private static final Function<String, String> DEFAULT_ESCAPER = string -> string
    .replace("\"", "\\\"")
    .replace("\\", "\\\\")
    .replace("\b", "\\b")
    .replace("\f", "\\f")
    .replace("\n", "\\n")
    .replace("\r", "\\r")
    .replace("\t", "\\t");
  private static final Collector<CharSequence, ?, String> COMMA_CURLY = Collectors.joining(", ", "{", "}");
  private static final Collector<CharSequence, ?, String> COMMA_SQUARE = Collectors.joining(", ", "[", "]");
  private final Function<String, String> escaper;

  public StringExaminer() {
    this(DEFAULT_ESCAPER);
  }

  public StringExaminer(final @NonNull Function<String, String> escaper) {
    this.escaper = escaper;
  }

  @Override
  public <E> @NonNull String array(final @NonNull E[] array, final @NonNull Stream<String> elements) {
    return elements.collect(COMMA_SQUARE);
  }

  @Override
  public <E> @NonNull String collection(final @NonNull Collection<E> collection, final @NonNull Stream<String> elements) {
    return elements.collect(COMMA_SQUARE);
  }

  @Override
  public @NonNull String examinable(final @NonNull Examinable examinable, final @NonNull Stream<Map.Entry<String, String>> properties) {
    return examinable.examinableName() + properties.map(property -> property.getKey() + '=' + property.getValue()).collect(COMMA_CURLY);
  }

  @Override
  public <K, V> @NonNull String map(final @NonNull Map<K, V> map, final @NonNull Stream<Map.Entry<String, String>> entries) {
    return entries.map(entry -> entry.getKey() + '=' + entry.getValue()).collect(COMMA_CURLY);
  }

  @Override
  public @NonNull String nil() {
    return "null";
  }

  @Override
  public @NonNull String scalar(final @NonNull Object value) {
    if(value instanceof Character) {
      return '\'' + this.escaper.apply(String.valueOf(value)) + '\'';
    } else if(value instanceof String) {
      return '"' + this.escaper.apply((String) value) + '"';
    }
    return String.valueOf(value);
  }

  @Override
  public <T> @NonNull String stream(final @NonNull Stream<T> stream) {
    return stream.map(this::examine).collect(COMMA_SQUARE);
  }
}
