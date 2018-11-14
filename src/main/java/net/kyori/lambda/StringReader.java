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
package net.kyori.lambda;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.IntPredicate;

public interface StringReader extends StringRepresentable {
  /**
   * Creates a new string reader.
   *
   * @param string the string
   * @return a string reader
   */
  static @NonNull StringReader create(final @NonNull String string) {
    return new StringReaderImpl(string);
  }

  /**
   * Gets the string.
   *
   * @return the string
   */
  @NonNull String string();

  @Override
  default @NonNull String asString() {
    return this.string();
  }

  /**
   * Gets the length.
   *
   * @return length
   */
  int length();

  /**
   * Gets the current index.
   *
   * @return the current index
   */
  int index();

  /**
   * Checks if a single character can be read.
   *
   * @return if a single character can be read
   */
  default boolean readable() {
    return this.readable(1);
  }

  /**
   * Checks if {@code length} characters can be read.
   *
   * @param length the number of characters
   * @return if {@code length} characters can be read
   */
  boolean readable(final int length);

  /**
   * Skips a single character.
   */
  void skip();

  /**
   * Marks the current index.
   *
   * @return the current index
   */
  int mark();

  /**
   * Resets the index to the {@link #mark() marked} position.
   *
   * @return the index before reset
   */
  int reset();

  /**
   * Peeks at the next character.
   *
   * @return the next character
   * @throws IndexOutOfBoundsException if there is no character available
   */
  char peek();

  /**
   * Peeks at the character at {@code index + offset}.
   *
   * @param offset the offset
   * @return the next character
   * @throws IndexOutOfBoundsException if there is no character available
   */
  char peek(final int offset);

  /**
   * Peeks at the next characters while {@code predicate} is satisfied.
   *
   * @param predicate the character predicate
   * @return a string
   */
  @NonNull String peekWhile(final IntPredicate predicate);

  /**
   * Gets the next character.
   *
   * @return the next character
   * @throws IndexOutOfBoundsException if there is no character available
   */
  char next();

  /**
   * Gets the next characters while {@code predicate} is satisfied.
   *
   * @param predicate the character predicate
   * @return a string
   */
  @NonNull String nextWhile(final IntPredicate predicate);
}
