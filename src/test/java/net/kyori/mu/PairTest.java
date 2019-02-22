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

import java.util.AbstractMap;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PairTest {
  @Test
  void testOfLeft() {
    final Pair<String, ?> pair = Pair.left("left");
    assertThat(pair.left()).isEqualTo("left");
    assertThat(pair.right()).isNull();
  }

  @Test
  void testOfRight() {
    final Pair<?, String> pair = Pair.right("right");
    assertThat(pair.left()).isNull();
    assertThat(pair.right()).isEqualTo("right");
  }

  @Test
  void testFromMapEntry() {
    final Map.Entry<String, String> entry = new AbstractMap.SimpleImmutableEntry<>("left", "right");
    final Pair<String, String> pair = Pair.from(entry);
    assertThat(pair.left()).isEqualTo("left");
    assertThat(pair.right()).isEqualTo("right");
  }

  @Test
  void testOf() {
    final Pair<String, String> pair = Pair.of("left", "right");
    assertThat(pair.left()).isEqualTo("left");
    assertThat(pair.right()).isEqualTo("right");
  }

  @Test
  void testPair() {
    final Pair<String, String> pair = Pair.pair("left", "right");
    assertThat(pair.left()).isEqualTo("left");
    assertThat(pair.right()).isEqualTo("right");
  }

  @Test
  void testMap() {
    Pair<String, String> pair = Pair.of("left", "right");
    assertEquals("left", pair.left());
    assertEquals("right", pair.right());
    pair = pair.map(left -> "tfel", right -> "thgir");
    assertEquals("tfel", pair.left());
    assertEquals("thgir", pair.right());
  }

  @Test
  void testLMap() {
    Pair<String, String> pair = Pair.of("left", "right");
    assertEquals("left", pair.left());
    assertEquals("right", pair.right());
    pair = pair.lmap(left -> "tfel");
    assertEquals("tfel", pair.left());
    assertEquals("right", pair.right());
  }

  @Test
  void testRMap() {
    Pair<String, String> pair = Pair.of("left", "right");
    assertEquals("left", pair.left());
    assertEquals("right", pair.right());
    pair = pair.rmap(left -> "thgir");
    assertEquals("left", pair.left());
    assertEquals("thgir", pair.right());
  }

  @Test
  void testEquals() {
    assertThat(Pair.of("left", "right")).isEqualTo(Pair.of("left", "right"));
    assertThat(Pair.of("tfel", "right")).isNotEqualTo(Pair.of("left", "right"));
    assertThat(Pair.of("left", "thgir")).isNotEqualTo(Pair.of("left", "right"));
  }

  @Test
  void testHashCode() {
    assertThat(Pair.of("left", "right").hashCode()).isEqualTo(Pair.of("left", "right").hashCode());
    assertThat(Pair.of("tfel", "right").hashCode()).isNotEqualTo(Pair.of("left", "right").hashCode());
    assertThat(Pair.of("left", "thgir").hashCode()).isNotEqualTo(Pair.of("left", "right").hashCode());
  }
}
