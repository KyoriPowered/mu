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
package net.kyori.lambda.function;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MorePredicatesTest {
  @Test
  void testAlwaysFalse() {
    assertFalse(MorePredicates.alwaysFalse().test(null));
    assertFalse(MorePredicates.alwaysFalse().test("foo"));
  }

  @Test
  void testAlwaysFalse_and() {
    final Predicate<String> predicate = string -> string.contains("meow");
    assertSame(MorePredicates.alwaysFalse(), MorePredicates.<String>alwaysFalse().and(predicate));
  }

  @Test
  void testAlwaysFalse_negate() {
    assertTrue(MorePredicates.alwaysFalse().negate().test(null));
    assertTrue(MorePredicates.alwaysFalse().negate().test("foo"));
  }

  @Test
  void testAlwaysFalse_or() {
    final Predicate<String> predicate = string -> string.contains("meow");
    assertSame(predicate, MorePredicates.<String>alwaysFalse().or(predicate));
  }

  @Test
  void testAlwaysTrue() {
    assertTrue(MorePredicates.alwaysTrue().test(null));
    assertTrue(MorePredicates.alwaysTrue().test("foo"));
  }

  @Test
  void testAlwaysTrue_and() {
    final Predicate<String> predicate = string -> string.contains("meow");
    assertSame(predicate, MorePredicates.<String>alwaysTrue().and(predicate));
  }

  @Test
  void testAlwaysTrue_negate() {
    assertFalse(MorePredicates.alwaysTrue().negate().test(null));
    assertFalse(MorePredicates.alwaysTrue().negate().test("foo"));
  }

  @Test
  void testAlwaysTrue_or() {
    final Predicate<String> predicate = string -> string.contains("meow");
    assertNotSame(predicate, MorePredicates.<String>alwaysTrue().or(predicate));
  }

  @Test
  void testIsNull() {
    assertTrue(MorePredicates.isNull().test(null));
    assertFalse(MorePredicates.isNull().test("foo"));
  }

  @Test
  void testIsNull_negate() {
    assertFalse(MorePredicates.isNull().negate().test(null));
    assertTrue(MorePredicates.isNull().negate().test("foo"));
  }

  @Test
  void testNonNull() {
    assertFalse(MorePredicates.nonNull().test(null));
    assertTrue(MorePredicates.nonNull().test("foo"));
  }

  @Test
  void testNonNull_negate() {
    assertTrue(MorePredicates.nonNull().negate().test(null));
    assertFalse(MorePredicates.nonNull().negate().test("foo"));
  }

  @Test
  void testInstanceOf() {
    assertTrue(MorePredicates.instanceOf(String.class).test("foo"));
    assertFalse(MorePredicates.instanceOf(String.class).test(1));
    assertTrue(MorePredicates.instanceOf(Integer.class).test(1));
  }
}
