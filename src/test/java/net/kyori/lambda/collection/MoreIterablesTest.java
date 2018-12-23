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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;

class MoreIterablesTest {
  @Test
  void testLFold() {
    assertThat(MoreIterables.lfold(Arrays.asList(1, 2, 3, 4, 5), 0, (a, b) -> a + b)).isEqualTo(15);
    assertThat(MoreIterables.lfold(Arrays.asList(1, 2, 3, 4, 5), 1, (a, b) -> a + b)).isEqualTo(16);
  }

  @Test
  void testReduce() {
    assertThat(
      MoreIterables.reduce(
        Arrays.asList("abc", "def", "ghi"),
        strings -> String.join("-", strings)
      )
    ).isEqualTo("abc-def-ghi");
  }

  @Test
  void testStream() {
    final Iterable<String> iterable = Arrays.asList("abc", "def");
    final Stream<String> stream = MoreIterables.stream(iterable);
    assertThat(stream).containsExactly("abc", "def").inOrder();
  }
}
