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
package net.kyori.lambda.reflect;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

class AnnotationsTest {
  @Test
  void testFind() throws NoSuchMethodException {
    assertAnnotationValue("class=a", Annotations.find((AnnotatedElement) A.class, Foo.class));
    assertAnnotationValue("class=b", Annotations.find((AnnotatedElement) B.class, Foo.class));
    assertAnnotationValue("class=b,method=a", Annotations.find((AnnotatedElement) B.class.getDeclaredMethod("a"), Foo.class));
  }

  @Test
  void testFindClass() {
    assertAnnotationValue("class=b", Annotations.find(B.class, Foo.class));
    assertAnnotationValue("class=b", Annotations.find(C.class, Foo.class));
    assertAnnotationValue("class=d", Annotations.find(D.class, Foo.class));
  }

  @Test
  void testFindField() throws NoSuchFieldException {
    assertNull(Annotations.find(B.class.getDeclaredField("y"), Foo.class));
    assertAnnotationValue("class=b,field=z", Annotations.find(B.class.getDeclaredField("z"), Foo.class));
  }

  @Test
  void testFindMethod() throws NoSuchMethodException {
    assertAnnotationValue("class=b,method=a", Annotations.find(B.class.getDeclaredMethod("a"), Foo.class));
    assertAnnotationValue("class=c,method=a", Annotations.find(C.class.getDeclaredMethod("a"), Foo.class));
    assertAnnotationValue("class=c,method=a", Annotations.find(D.class.getDeclaredMethod("a"), Foo.class));
    assertNull(Annotations.find(D.class.getDeclaredMethod("b"), Foo.class));
  }

  private static void assertAnnotationValue(final String string, final @Nullable Foo annotation) {
    if(annotation == null) {
      fail("could not find annotation");
    } else {
      assertEquals(string, annotation.value());
    }
  }

  @Foo("class=a")
  public interface A {
    @Foo("class=a,method=a") void a();
  }

  @Foo("class=b")
  private static class B implements A {
    private String y;
    private @Foo("class=b,field=z") String z;

    @Override
    @Foo("class=b,method=a") public void a() {}
  }

  private static class C extends B {
    @Foo("class=c,method=a") @Override public void a() {}
  }

  @Foo("class=d")
  private static class D extends C {
    @Override public void a() {}
    public void b() {}
  }

  @Retention(RetentionPolicy.RUNTIME) private @interface Foo { String value(); }
}
