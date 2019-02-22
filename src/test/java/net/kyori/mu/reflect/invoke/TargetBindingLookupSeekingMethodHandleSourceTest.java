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
package net.kyori.mu.reflect.invoke;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TargetBindingLookupSeekingMethodHandleSourceTest extends AbstractMethodHandleSourceTest {
  @Test
  void test() {
    final AtomicInteger targetSelects = new AtomicInteger();
    final A a = this.create(b -> new TargetBindingLookupSeekingMethodHandleSource() {
      @Override
      public @NonNull Object target(final Method method) {
        targetSelects.incrementAndGet();
        return b;
      }

      @Override
      public MethodHandles.@NonNull Lookup lookup(final @NonNull Class<?> klass) {
        return MethodHandles.lookup();
      }
    });

    assertEquals("foo", a.foo());
    assertEquals("foo", a.foo());
    assertEquals("bar", a.bar("bar"));
    assertEquals("bar", a.bar("bar"));
    assertEquals(4, targetSelects.get());
  }
}
