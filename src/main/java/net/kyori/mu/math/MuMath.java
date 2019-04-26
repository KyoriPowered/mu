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
package net.kyori.mu.math;

/**
 * 2 plus 2 equals 5... right?
 */
public final class MuMath {
  private MuMath() {
  }

  /**
   * Clamps {@code value} between {@code min} and {@code max}.
   *
   * @param value the value
   * @param min the min value
   * @param max the max value
   * @return the clamped value
   */
  public static double clamp(final double value, final double min, final double max) {
    if(value < min) return min;
    if(value > max) return max;
    return value;
  }

  /**
   * Clamps {@code value} between {@code min} and {@code max}.
   *
   * @param value the value
   * @param min the min value
   * @param max the max value
   * @return the clamped value
   */
  public static float clamp(final float value, final float min, final float max) {
    if(value < min) return min;
    if(value > max) return max;
    return value;
  }

  /**
   * Clamps {@code value} between {@code min} and {@code max}.
   *
   * @param value the value
   * @param min the min value
   * @param max the max value
   * @return the clamped value
   */
  public static int clamp(final int value, final int min, final int max) {
    if(value < min) return min;
    if(value > max) return max;
    return value;
  }

  /**
   * Clamps {@code value} between {@code min} and {@code max}.
   *
   * @param value the value
   * @param min the min value
   * @param max the max value
   * @return the clamped value
   */
  public static long clamp(final long value, final long min, final long max) {
    if(value < min) return min;
    if(value > max) return max;
    return value;
  }
}
