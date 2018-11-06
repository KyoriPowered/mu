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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaybeTest {
  @Test
  void testNothing() {
    assertEquals(Maybe.nothing(), Maybe.nothing());
  }

  @Test
  void testJust() {
    assertEquals(Maybe.just("foo"), Maybe.just("foo"));
  }

  @Test
  void testMaybe() {
    assertEquals(Maybe.nothing(), Maybe.maybe(null));
    assertEquals(Maybe.just("foo"), Maybe.maybe("foo"));
  }

  @Test
  void testFrom() {
    assertEquals(Maybe.nothing(), Maybe.from(Optional.empty()));
    assertEquals(Maybe.just("foo"), Maybe.from(Optional.of("foo")));
  }

  @Test
  void testIsEmpty() {
    assertTrue(Maybe.nothing().isEmpty());
    assertFalse(Maybe.just("foo").isEmpty());
  }

  @Test
  void testIsPopulated() {
    assertFalse(Maybe.nothing().isPopulated());
    assertTrue(Maybe.just("foo").isPopulated());
  }

  @Test
  void testGet() {
    assertThrows(NoSuchElementException.class, () -> Maybe.nothing().get());
    assertEquals("foo", Maybe.just("foo").get());
  }

  @Test
  void testGetOrDefault() {
    assertEquals("bar", Maybe.nothing().getOrDefault("bar"));
    assertEquals("foo", Maybe.just("foo").getOrDefault("bar"));
  }

  @Test
  void testGetOrGet() {
    assertEquals("bar", Maybe.nothing().getOrGet(() -> "bar"));
    assertEquals("foo", Maybe.just("foo").getOrGet(() -> "bar"));
  }

  @Test
  void testGetOrThrow() {
    assertThrows(Expected.class, () -> Maybe.nothing().getOrThrow(Expected::new));
    assertEquals("foo", Maybe.just("foo").getOrThrow(Expected::new));
  }

  @Test
  void testOr() {
    assertEquals(Maybe.nothing(), Maybe.nothing().or(Maybe.nothing()));
    assertEquals(Maybe.just("foo"), Maybe.just("foo").or(Maybe.just("bar")));
    assertEquals(Maybe.just("foo"), Maybe.nothing().or(Maybe.just("foo")));

    assertEquals(Maybe.nothing(), Maybe.nothing().or(Maybe::nothing));
    assertEquals(Maybe.just("foo"), Maybe.just("foo").or(() -> Maybe.just("bar")));
    assertEquals(Maybe.just("foo"), Maybe.nothing().or(() -> Maybe.just("foo")));
  }

  @Test
  void testFilter() {
    assertTrue(Maybe.<String>nothing().filter(String::isEmpty).isEmpty());
    assertTrue(Maybe.just("foo").filter(String::isEmpty).isEmpty());
  }

  @Test
  void testMap() {
    assertTrue(Maybe.nothing().map(nothing -> "foo").isEmpty());
    assertEquals("!foo!", Maybe.just("foo").map(string -> '!' + string + '!').get());
  }

  @Test
  void testFlatMap() {
    assertTrue(Maybe.nothing().flatMap(nothing -> Maybe.just("foo")).isEmpty());
    assertEquals("!foo!", Maybe.just("foo").flatMap(string -> Maybe.just('!' + string + '!')).get());
  }

  @Test
  void testWith() {
    final AtomicInteger nothing = new AtomicInteger();
    Maybe.nothing().with(v -> nothing.incrementAndGet());
    assertEquals(0, nothing.get());
    final AtomicInteger just = new AtomicInteger();
    Maybe.just("foo").with(v -> just.incrementAndGet());
    assertEquals(1, just.get());
  }

  @Test
  void testWithOrElse() {
    final AtomicInteger just = new AtomicInteger();
    final AtomicInteger nothing = new AtomicInteger();
    Maybe.nothing().withOrElse(v -> just.incrementAndGet(), nothing::incrementAndGet);
    assertEquals(0, just.get());
    assertEquals(1, nothing.get());
    Maybe.just("foo").withOrElse(v -> just.incrementAndGet(), nothing::incrementAndGet);
    assertEquals(1, just.get());
    assertEquals(1, nothing.get());
  }

  @Test
  void testForEach() {
    final AtomicInteger nothing = new AtomicInteger();
    Maybe.nothing().forEach(v -> nothing.incrementAndGet());
    assertEquals(0, nothing.get());
    final AtomicInteger just = new AtomicInteger();
    Maybe.just("foo").forEach(v -> just.incrementAndGet());
    assertEquals(1, just.get());
  }

  @Test
  void testStream() {
    assertThat(Maybe.nothing().stream()).isEmpty();
    assertThat(Maybe.just("foo").stream()).containsExactly("foo");
  }

  @Test
  void testOptional() {
    assertEquals(Maybe.nothing().optional(), Maybe.nothing().optional());
    assertEquals(Maybe.just("foo").optional(), Maybe.just("foo").optional());
  }

  @Test
  void testIterator() {
    assertThat(Maybe.nothing()).isEmpty();
    assertThat(Maybe.just("foo")).containsExactly("foo");
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Test
  void testCollector() {
    assertEquals(Maybe.nothing(), Stream.empty().collect(Maybe.collector()));
    assertEquals(Maybe.just("foo"), Stream.of("foo").collect(Maybe.collector()));
    assertThrows(AmbiguousElementException.class, () -> Stream.of("foo", "bar").collect(Maybe.collector()));
  }

  @Test
  void testFirst() {
    assertEquals(Maybe.nothing(), Maybe.first(Maybe.nothing(), Maybe.nothing()));
    assertEquals(Maybe.just("foo"), Maybe.first(Maybe.just("foo"), Maybe.just("bar")));
    assertEquals(Maybe.nothing(), Maybe.first(Arrays.asList(Maybe.nothing(), Maybe.nothing())));
    assertEquals(Maybe.just("foo"), Maybe.first(Arrays.asList(Maybe.just("foo"), Maybe.just("bar"))));
  }

  private static class Expected extends RuntimeException {}
}
