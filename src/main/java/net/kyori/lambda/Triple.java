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

import net.kyori.lambda.examine.Examinable;
import net.kyori.lambda.examine.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A triple.
 *
 * @param <L> the left type
 * @param <M> the middle type
 * @param <R> the right type
 */
public class Triple<L, M, R> implements Examinable {
  private final L left;
  private final M middle;
  private final R right;

  /**
   * Creates a new triple with a left value.
   *
   * @param left the left value
   * @param <L> the left type
   * @param <M> the middle type
   * @param <R> the right type
   * @return the triple
   */
  public static <L, M, R> @NonNull Triple<L, M, R> left(final @Nullable L left) {
    return new Triple<>(left, null, null);
  }

  /**
   * Creates a new triple with a middle value.
   *
   * @param middle the middle value
   * @param <L> the left type
   * @param <M> the middle type
   * @param <R> the right type
   * @return the triple
   */
  public static <L, M, R> @NonNull Triple<L, M, R> middle(final @Nullable M middle) {
    return new Triple<>(null, middle, null);
  }

  /**
   * Creates a new triple with a right value.
   *
   * @param right the right value
   * @param <L> the left type
   * @param <M> the middle type
   * @param <R> the right type
   * @return the triple
   */
  public static <L, M, R> @NonNull Triple<L, M, R> right(final @Nullable R right) {
    return new Triple<>(null, null, right);
  }

  /**
   * Creates a new triple.
   *
   * @param left the left value
   * @param middle the middle value
   * @param right the right value
   * @param <L> the left type
   * @param <M> the middle type
   * @param <R> the right type
   * @return the triple
   */
  public static <L, M, R> @NonNull Triple<L, M, R> of(final @Nullable L left, final @Nullable M middle, final @Nullable R right) {
    return new Triple<>(left, middle, right);
  }

  /**
   * Creates a new triple.
   *
   * @param left the left value
   * @param middle the middle value
   * @param right the right value
   * @param <L> the left type
   * @param <M> the middle type
   * @param <R> the right type
   * @return the triple
   */
  public static <L, M, R> @NonNull Triple<L, M, R> triple(final @Nullable L left, final @Nullable M middle, final @Nullable R right) {
    return new Triple<>(left, middle, right);
  }

  public Triple(final @Nullable L left, final @Nullable M middle, final @Nullable R right) {
    this.left = left;
    this.middle = middle;
    this.right = right;
  }

  /**
   * Gets the left value.
   *
   * @return the left value
   */
  public /* @Nullable */ L left() {
    return this.left;
  }

  /**
   * Gets the middle value.
   *
   * @return the middle value
   */
  public /* @Nullable */ M middle() {
    return this.middle;
  }

  /**
   * Gets the right value.
   *
   * @return the right value
   */
  public /* @Nullable */ R right() {
    return this.right;
  }

  /**
   * Map the left, middle, and right values of this triple.
   *
   * @param left the left value function
   * @param middle the middle value function
   * @param right the right value function
   * @param <NL> the left type
   * @param <NM> the middle type
   * @param <NR> the right type
   * @return a new triple
   */
  public <NL, NM, NR> @NonNull Triple<NL, NM, NR> map(final @NonNull Function<? super L, ? extends NL> left, final @NonNull Function<? super M, ? extends NM> middle, final @NonNull Function<? super R, ? extends NR> right) {
    return new Triple<>(left.apply(this.left), middle.apply(this.middle), right.apply(this.right));
  }

  /**
   * Map the left value of this triple.
   *
   * @param function the function
   * @param <NL> the left type
   * @return a new triple
   */
  public <NL> @NonNull Triple<NL, M, R> lmap(final @NonNull Function<? super L, ? extends NL> function) {
    return new Triple<>(function.apply(this.left), this.middle, this.right);
  }

  /**
   * Map the middle value of this triple.
   *
   * @param function the function
   * @param <NM> the middle type
   * @return a new triple
   */
  public <NM> @NonNull Triple<L, NM, R> mmap(final @NonNull Function<? super M, ? extends NM> function) {
    return new Triple<>(this.left, function.apply(this.middle), this.right);
  }

  /**
   * Map the right value of this triple.
   *
   * @param function the function
   * @param <NR> the right type
   * @return a new triple
   */
  public <NR> @NonNull Triple<L, M, NR> rmap(final @NonNull Function<? super R, ? extends NR> function) {
    return new Triple<>(this.left, this.middle, function.apply(this.right));
  }

  @Override
  public @NonNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
      ExaminableProperty.of("left", this.left),
      ExaminableProperty.of("right", this.right)
    );
  }

  @Override
  public String toString() {
    return "Triple{left=" + this.left + ", middle=" + this.middle + ", right=" + this.right + '}';
  }

  @Override
  public boolean equals(final Object other) {
    if(this == other) {
      return true;
    }
    if(other == null || this.getClass() != other.getClass()) {
      return false;
    }
    final Triple<?, ?, ?> that = (Triple<?, ?, ?>) other;
    return Objects.equals(this.left, that.left) && Objects.equals(this.middle, that.middle) && Objects.equals(this.right, that.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.left, this.middle, this.right);
  }
}
