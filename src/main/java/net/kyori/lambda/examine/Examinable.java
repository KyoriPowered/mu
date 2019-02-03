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

import java.util.stream.Stream;

/**
 * Something that can be examined.
 */
public interface Examinable {
  /**
   * Examines.
   *
   * @param examiner the examiner
   * @param <R> the result type
   * @return the examination result
   */
  default <R> @NonNull R examine(final @NonNull Examiner<R> examiner) {
    return examiner.examine(this);
  }

  /**
   * Gets the examinable name.
   *
   * @return the examinable name
   */
  default @NonNull String examinableName() {
    return this.getClass().getSimpleName();
  }

  /**
   * Gets a stream of examinable properties.
   *
   * @return a stream of examinable properties
   */
  @NonNull Stream<? extends ExaminableProperty> examinableProperties();
}
