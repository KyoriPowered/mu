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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MuMathTest {
  @Test
  void testClamp_double() {
    assertEquals(3d, MuMath.clamp(3d, 0d, 5d));
    assertEquals(-3.1d, MuMath.clamp(-7d, -3.1d, 5d));
    assertEquals(5d, MuMath.clamp(7d, -3.1d, 5d));
    assertEquals(0.1d, MuMath.clamp(-10d, 0.1d, 100d));
    assertEquals(100d, MuMath.clamp(101d, 0.1d, 100d));
  }

  @Test
  void testClamp_float() {
    assertEquals(3f, MuMath.clamp(3f, 0f, 5f));
    assertEquals(-3.1f, MuMath.clamp(-7f, -3.1f, 5f));
    assertEquals(5f, MuMath.clamp(7f, -3.1f, 5f));
    assertEquals(0.1f, MuMath.clamp(-10f, 0.1f, 100f));
    assertEquals(100f, MuMath.clamp(101f, 0.1f, 100f));
  }

  @Test
  void testClamp_int() {
    assertEquals(3, MuMath.clamp(3, 0, 5));
    assertEquals(-3, MuMath.clamp(-7, -3, 5));
    assertEquals(5, MuMath.clamp(7, -3, 5));
    assertEquals(0, MuMath.clamp(-10, 0, 100));
    assertEquals(100, MuMath.clamp(101, 0, 100));
  }

  @Test
  void testClamp_long() {
    assertEquals(3L, MuMath.clamp(3L, 0L, 5L));
    assertEquals(-3L, MuMath.clamp(-7L, -3L, 5L));
    assertEquals(5L, MuMath.clamp(7L, -3L, 5L));
    assertEquals(0L, MuMath.clamp(-10L, 0L, 100L));
    assertEquals(100L, MuMath.clamp(101L, 0L, 100L));
  }
}
