/*
 * This file is part of mu, licensed under the MIT License.
 *
 * Copyright (c) 2018-2020 KyoriPowered
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
package net.kyori.mu.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MembersTest {
  @Test
  void testIsFinal_field() {
    for(final Field field : A.class.getDeclaredFields()) {
      assertEquals(Modifier.isFinal(field.getModifiers()), Members.isFinal(field));
    }
  }

  @Test
  void testIsFinal_method() {
    for(final Method method : A.class.getDeclaredMethods()) {
      assertEquals(Modifier.isFinal(method.getModifiers()), Members.isFinal(method));
    }
  }

  @Test
  void testIsPackagePrivate_constructor() throws NoSuchMethodException {
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredConstructor(int.class)));
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredConstructor(int.class, int.class)));
    assertTrue(Members.isPackagePrivate(A.class.getDeclaredConstructor(int.class, int.class, int.class)));
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredConstructor(int.class, int.class, int.class, int.class)));
  }

  @Test
  void testIsPackagePrivate_field() throws NoSuchFieldException {
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredField("a")));
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredField("b")));
    assertTrue(Members.isPackagePrivate(A.class.getDeclaredField("c")));
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredField("d")));
  }

  @Test
  void testIsPackagePrivate_method() throws NoSuchMethodException {
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredMethod("a")));
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredMethod("b")));
    assertTrue(Members.isPackagePrivate(A.class.getDeclaredMethod("c")));
    assertFalse(Members.isPackagePrivate(A.class.getDeclaredMethod("d")));
  }

  @Test
  void testIsPrivate_constructor() {
    for(final Constructor<?> constructor : A.class.getDeclaredConstructors()) {
      assertEquals(Modifier.isPrivate(constructor.getModifiers()), Members.isPrivate(constructor));
    }
  }

  @Test
  void testIsPrivate_field() {
    for(final Field field : A.class.getDeclaredFields()) {
      assertEquals(Modifier.isPrivate(field.getModifiers()), Members.isPrivate(field));
    }
  }

  @Test
  void testIsPrivate_method() {
    for(final Method method : A.class.getDeclaredMethods()) {
      assertEquals(Modifier.isPrivate(method.getModifiers()), Members.isPrivate(method));
    }
  }

  @Test
  void testIsStatic_constructor() {
    for(final Constructor<?> constructor : A.class.getDeclaredConstructors()) {
      assertEquals(Modifier.isStatic(constructor.getModifiers()), Members.isStatic(constructor));
    }
  }

  @Test
  void testIsStatic_field() {
    for(final Field field : A.class.getDeclaredFields()) {
      assertEquals(Modifier.isStatic(field.getModifiers()), Members.isStatic(field));
    }
  }

  @Test
  void testIsStatic_method() {
    for(final Method method : A.class.getDeclaredMethods()) {
      assertEquals(Modifier.isStatic(method.getModifiers()), Members.isStatic(method));
    }
  }

  @SuppressWarnings("unused")
  private static class A {
    public int a;
    protected int b;
    int c;
    private int d;
    private static int e;
    private static final int F = 0;

    public A(final int a) {}
    protected A(final int a, final int b) {}
    A(final int a, final int b, final int c) {}
    private A(final int a, final int b, final int c, final int d) {}

    public void a() {}
    protected void b() {}
    void c() {}
    private void d() {}
    private static void e() {}
    public final void f() {}
  }
}
