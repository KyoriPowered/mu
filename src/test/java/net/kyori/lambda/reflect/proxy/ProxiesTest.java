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
package net.kyori.lambda.reflect.proxy;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProxiesTest {
  private static final InvocationHandler X_RETURNER = (proxy, method, args) -> "x";

  @Test
  void testCreateSingleAuto() {
    final Runnable runnable = Proxies.create(Runnable.class, X_RETURNER);
    assertEquals("x", runnable.toString());
  }

  @Test
  void testCreateSingleManual() {
    final Runnable runnable = Proxies.create(Runnable.class.getClassLoader(), Runnable.class, X_RETURNER);
    assertEquals("x", runnable.toString());
  }

  @SuppressWarnings("RedundantClassCall")
  @Test
  void testCreateSingleWithListFriends() {
    final Runnable runnable = Proxies.create(this.getClass().getClassLoader(), Runnable.class, Lists.newArrayList(A.class, B.class), X_RETURNER);
    assertEquals("x", runnable.toString());
    assertTrue(A.class.isInstance(runnable));
    assertTrue(B.class.isInstance(runnable));
  }

  @SuppressWarnings("RedundantClassCall")
  @Test
  void testCreateSingleWithArrayFriends() {
    final Runnable runnable = Proxies.create(this.getClass().getClassLoader(), Runnable.class, new Class<?>[]{A.class, B.class}, X_RETURNER);
    assertEquals("x", runnable.toString());
    assertTrue(A.class.isInstance(runnable));
    assertTrue(B.class.isInstance(runnable));
  }

  @SuppressWarnings("RedundantClassCall")
  @Test
  void testCreateListFriends() {
    final Object runnable = Proxies.create(this.getClass().getClassLoader(), Lists.newArrayList(Runnable.class, A.class), X_RETURNER);
    assertEquals("x", runnable.toString());
    assertTrue(A.class.isInstance(runnable));
  }

  @SuppressWarnings("RedundantClassCall")
  @Test
  void testCreateArrayFriends() {
    final Object runnable = Proxies.create(this.getClass().getClassLoader(), new Class<?>[]{Runnable.class, A.class}, X_RETURNER);
    assertEquals("x", runnable.toString());
    assertTrue(A.class.isInstance(runnable));
  }

  interface A {}
  interface B {}
}
