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

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TripleTest {
  @Test
  void testOfLeft() {
    final Triple<String, ?, ?> triple = Triple.left("left");
    assertThat(triple.left()).isEqualTo("left");
    assertThat(triple.middle()).isNull();
    assertThat(triple.right()).isNull();
  }

  @Test
  void testOfMiddle() {
    final Triple<?, String, ?> triple = Triple.middle("middle");
    assertThat(triple.left()).isNull();
    assertThat(triple.middle()).isEqualTo("middle");
    assertThat(triple.right()).isNull();
  }

  @Test
  void testOfRight() {
    final Triple<?, ?, String> triple = Triple.right("right");
    assertThat(triple.left()).isNull();
    assertThat(triple.middle()).isNull();
    assertThat(triple.right()).isEqualTo("right");
  }

  @Test
  void testOf() {
    final Triple<String, String, String> triple = Triple.of("left", "middle", "right");
    assertThat(triple.left()).isEqualTo("left");
    assertThat(triple.middle()).isEqualTo("middle");
    assertThat(triple.right()).isEqualTo("right");
  }

  @Test
  void testTriple() {
    final Triple<String, String, String> triple = Triple.triple("left", "middle", "right");
    assertThat(triple.left()).isEqualTo("left");
    assertThat(triple.middle()).isEqualTo("middle");
    assertThat(triple.right()).isEqualTo("right");
  }

  @Test
  void testMap() {
    Triple<String, String, String> triple = Triple.of("left", "middle", "right");
    assertEquals("left", triple.left());
    assertEquals("middle", triple.middle());
    assertEquals("right", triple.right());
    triple = triple.map(left -> "tfel", middle -> "elddim", right -> "thgir");
    assertEquals("tfel", triple.left());
    assertEquals("elddim", triple.middle());
    assertEquals("thgir", triple.right());
  }

  @Test
  void testLMap() {
    Triple<String, String, String> triple = Triple.of("left", "middle", "right");
    assertEquals("left", triple.left());
    assertEquals("middle", triple.middle());
    assertEquals("right", triple.right());
    triple = triple.lmap(left -> "tfel");
    assertEquals("tfel", triple.left());
    assertEquals("middle", triple.middle());
    assertEquals("right", triple.right());
  }

  @Test
  void testMMap() {
    Triple<String, String, String> triple = Triple.of("left", "middle", "right");
    assertEquals("left", triple.left());
    assertEquals("middle", triple.middle());
    assertEquals("right", triple.right());
    triple = triple.mmap(middle -> "elddim");
    assertEquals("left", triple.left());
    assertEquals("elddim", triple.middle());
    assertEquals("right", triple.right());
  }

  @Test
  void testRMap() {
    Triple<String, String, String> triple = Triple.of("left", "middle", "right");
    assertEquals("left", triple.left());
    assertEquals("middle", triple.middle());
    assertEquals("right", triple.right());
    triple = triple.rmap(left -> "thgir");
    assertEquals("left", triple.left());
    assertEquals("middle", triple.middle());
    assertEquals("thgir", triple.right());
  }

  @Test
  void testEquals() {
    assertThat(Triple.of("left", "middle", "right")).isEqualTo(Triple.of("left", "middle", "right"));
    assertThat(Triple.of("tfel", "middle", "right")).isNotEqualTo(Triple.of("left", "middle", "right"));
    assertThat(Triple.of("left", "elddim", "right")).isNotEqualTo(Triple.of("left", "middle", "right"));
    assertThat(Triple.of("left", "middle", "thgir")).isNotEqualTo(Triple.of("left", "middle", "right"));
  }

  @Test
  void testHashCode() {
    assertThat(Triple.of("left", "middle", "right").hashCode()).isEqualTo(Triple.of("left", "middle", "right").hashCode());
    assertThat(Triple.of("tfel", "middle", "right").hashCode()).isNotEqualTo(Triple.of("left", "middle", "right").hashCode());
    assertThat(Triple.of("left", "elddim", "right").hashCode()).isNotEqualTo(Triple.of("left", "middle", "right").hashCode());
    assertThat(Triple.of("left", "middle", "thgir").hashCode()).isNotEqualTo(Triple.of("left", "middle", "right").hashCode());
  }
}
