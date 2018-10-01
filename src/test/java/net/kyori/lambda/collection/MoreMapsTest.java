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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoreMapsTest {
  @Test
  void testFrom() {
    assertThrows(NoSuchElementException.class, () -> MoreMaps.from(new HashMap<>(), Collections.singletonList("abc"), Arrays.asList("def", "jkl")));
    assertThrows(NoSuchElementException.class, () -> MoreMaps.from(new HashMap<>(), Arrays.asList("abc", "ghi"), Collections.singletonList("def")));
    assertTrue(MoreMaps.from(new HashMap<>(), Collections.emptyList(), Collections.emptyList()).isEmpty());

    final Map<String, String> map = MoreMaps.from(new HashMap<>(), Arrays.asList("abc", "ghi"), Arrays.asList("def", "jkl"));
    assertEquals(2, map.size());
    assertEquals("def", map.get("abc"));
    assertEquals("jkl", map.get("ghi"));
  }
}
