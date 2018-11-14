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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringReaderTest {
  @Test
  void testString() {
    final String string = "foo bar!";
    Assertions.assertEquals(string, StringReader.create(string).string());
  }

  @Test
  void testLength() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals(7, reader.length());
  }

  @Test
  void testIndex() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals(0, reader.index());
    reader.skip(); // skipping should increase the index by 1
    assertEquals(1, reader.index());
    reader.peek(); // peeking should not increase the index
    assertEquals(1, reader.index());
    reader.next(); // reading should increase the index by 1
    assertEquals(2, reader.index());
  }

  @Test
  void testReadable() {
    final String string = "foo bar";
    final int length = string.length();
    final StringReader reader = StringReader.create(string);
    assertTrue(reader.readable());
    assertTrue(reader.readable(length)); // we should be able to read the entire string...
    assertFalse(reader.readable(length + 1)); // ...but not more
    for(int i = 0; i < length; i++) {
      reader.skip();
    }
    assertFalse(reader.readable()); // we've read everything available
  }

  @Test
  void testSkip() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals('f', reader.peek());
    reader.skip();
    assertEquals('o', reader.peek());
  }

  @Test
  void testMarkAndReset() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals('f', reader.next());
    assertEquals(1, reader.mark());
    for(int i = 0; i < 5; i++) {
      reader.skip();
    }
    assertEquals('r', reader.next());
    assertEquals(7, reader.reset());
    assertEquals('o', reader.next());
    assertEquals('o', reader.next());
  }

  @Test
  void testPeek() {
    final StringReader reader = StringReader.create("foo");
    assertEquals('f', reader.peek());
    assertEquals('f', reader.peek()); // twice to ensure that peeking does not increment
    reader.skip();
    assertEquals('o', reader.peek()); // and once more after skipping
  }

  @Test
  void testPeekTooFar() {
    assertThrows(IndexOutOfBoundsException.class, StringReader.create("")::peek); // String index out of range: 0
  }

  @Test
  void testPeekWhile() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals("foo", reader.peekWhile(character -> !Character.isWhitespace(character)));
    assertEquals('f', reader.peek()); // we peeked above, we should be at the beginning
  }

  @Test
  void testNext() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals('f', reader.next());
    assertEquals('o', reader.next());
    assertEquals('o', reader.next());
  }

  @Test
  void testNextTooFar() {
    final StringReader reader = StringReader.create("a");
    assertEquals(0, reader.index());
    reader.next();
    assertEquals(1, reader.index());
    assertThrows(IndexOutOfBoundsException.class, reader::next);
    assertEquals(1, reader.index());
  }

  @Test
  void testNextWhile() {
    final StringReader reader = StringReader.create("foo bar");
    assertEquals("foo", reader.nextWhile(character -> !Character.isWhitespace(character)));
    assertEquals(' ', reader.next());
    assertEquals('b', reader.next());
    reader.next(); reader.next();
  }
}