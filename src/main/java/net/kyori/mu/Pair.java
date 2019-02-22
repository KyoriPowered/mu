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
package net.kyori.mu;

import net.kyori.mu.examine.Examinable;
import net.kyori.mu.examine.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A pair.
 *
 * @param <L> the left type
 * @param <R> the right type
 */
public class Pair<L, R> implements Examinable {
  private final L left;
  private final R right;

  /**
   * Creates a new pair with a left value.
   *
   * @param left the left value
   * @param <L> the left type
   * @param <R> the right type
   * @return the pair
   */
  public static <L, R> @NonNull Pair<L, R> left(final @Nullable L left) {
    return new Pair<>(left, null);
  }

  /**
   * Creates a new pair with a right value.
   *
   * @param right the right value
   * @param <L> the left type
   * @param <R> the right type
   * @return the pair
   */
  public static <L, R> @NonNull Pair<L, R> right(final @Nullable R right) {
    return new Pair<>(null, right);
  }

  /**
   * Creates a new pair from a map entry.
   *
   * @param entry the entry
   * @param <L> the left type
   * @param <R> the right type
   * @return the pair
   */
  public static <L, R> @NonNull Pair<L, R> from(final Map.Entry<L, R> entry) {
    return new Pair<>(entry.getKey(), entry.getValue());
  }

  /**
   * Creates a new pair.
   *
   * @param left the left value
   * @param right the right value
   * @param <L> the left type
   * @param <R> the right type
   * @return the pair
   */
  public static <L, R> @NonNull Pair<L, R> of(final @Nullable L left, final @Nullable R right) {
    return new Pair<>(left, right);
  }

  /**
   * Creates a new pair.
   *
   * @param left the left value
   * @param right the right value
   * @param <L> the left type
   * @param <R> the right type
   * @return the pair
   */
  public static <L, R> @NonNull Pair<L, R> pair(final @Nullable L left, final @Nullable R right) {
    return new Pair<>(left, right);
  }

  public Pair(final @Nullable L left, final @Nullable R right) {
    this.left = left;
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
   * Gets the right value.
   *
   * @return the right value
   */
  public /* @Nullable */ R right() {
    return this.right;
  }

  /**
   * Map the left and right value of this pair.
   *
   * @param left the left value function
   * @param right the right value function
   * @param <NL> the left type
   * @param <NR> the right type
   * @return a new pair
   */
  public <NL, NR> @NonNull Pair<NL, NR> map(final @NonNull Function<? super L, ? extends NL> left, final @NonNull Function<? super R, ? extends NR> right) {
    return new Pair<>(left.apply(this.left), right.apply(this.right));
  }

  /**
   * Map the left value of this pair.
   *
   * @param function the function
   * @param <NL> the left type
   * @return a new pair
   */
  public <NL> @NonNull Pair<NL, R> lmap(final @NonNull Function<? super L, ? extends NL> function) {
    return new Pair<>(function.apply(this.left), this.right);
  }

  /**
   * Map the right value of this pair.
   *
   * @param function the function
   * @param <NR> the right type
   * @return a new pair
   */
  public <NR> @NonNull Pair<L, NR> rmap(final @NonNull Function<? super R, ? extends NR> function) {
    return new Pair<>(this.left, function.apply(this.right));
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
    return "Pair{left=" + this.left + ", right=" + this.right + '}';
  }

  @Override
  public boolean equals(final Object other) {
    if(this == other) {
      return true;
    }
    if(other == null || this.getClass() != other.getClass()) {
      return false;
    }
    final Pair<?, ?> that = (Pair<?, ?>) other;
    return Objects.equals(this.left, that.left) && Objects.equals(this.right, that.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.left, this.right);
  }
}
