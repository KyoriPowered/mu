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
package net.kyori.lambda.exception;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionsTest {
  @Test
  void testRethrowBiConsumer() {
    assertThrows(TestException.class, () -> this.consumer(Exceptions.rethrowBiConsumer((a, b) -> { throw new TestException(); })));
  }

  @Test
  void testUnwrappingRethrowBiConsumer() {
    assertThrows(TestException.class, () -> this.consumer(Exceptions.unwrappingRethrowBiConsumer((a, b) -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowBiFunction() {
    assertThrows(TestException.class, () -> this.function(Exceptions.rethrowBiFunction((a, b) -> { throw new TestException(); })));
  }

  @Test
  void testUnwrappingRethrowBiFunction() {
    assertThrows(TestException.class, () -> this.function(Exceptions.unwrappingRethrowBiFunction((a, b) -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowBinaryOperator() {
    assertThrows(TestException.class, () -> this.function(Exceptions.rethrowBinaryOperator((a, b) -> { throw new TestException(); })));
  }

  @Test
  void testUnwrappingRethrowBinaryOperator() {
    assertThrows(TestException.class, () -> this.function(Exceptions.unwrappingRethrowBinaryOperator((a, b) -> { throw new TestException(); })));
  }

  @Test
  void testRethrowBiPredicate() {
    assertThrows(TestException.class, () -> this.predicate(Exceptions.rethrowBiPredicate((a, b) -> { throw new TestException(); })));
  }

  @Test
  void testUnwrappingRethrowBiPredicate() {
    assertThrows(TestException.class, () -> this.predicate(Exceptions.unwrappingRethrowBiPredicate((a, b) -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowConsumer() {
    assertThrows(TestException.class, () -> this.consumer(Exceptions.rethrowConsumer(a -> { throw new TestException(); })));
  }

  @Test
  void testUnwrappingRethrowConsumer() {
    assertThrows(TestException.class, () -> this.consumer(Exceptions.unwrappingRethrowConsumer(a -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowFunction() {
    assertThrows(TestException.class, () -> this.function(Exceptions.rethrowFunction(a -> { throw new TestException(); })));
  }

  @Test
  void testUnwrappingRethrowFunction() {
    assertThrows(TestException.class, () -> this.function(Exceptions.unwrappingRethrowFunction(a -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowPredicate() {
    assertThrows(TestException.class, () -> this.predicate(Exceptions.rethrowPredicate(a -> { throw new TestException(); } )));
  }

  @Test
  void testUnwrappingRethrowPredicate() {
    assertThrows(TestException.class, () -> this.predicate(Exceptions.unwrappingRethrowPredicate(a -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowRunnable() {
    assertThrows(TestException.class, () -> this.runnable(Exceptions.rethrowRunnable(() -> { throw new TestException(); } )));
  }

  @Test
  void testUnwrappingRethrowRunnable() {
    assertThrows(TestException.class, () -> this.runnable(Exceptions.unwrappingRethrowRunnable(() -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowSupplier() {
    assertThrows(TestException.class, () -> this.supplier(Exceptions.rethrowSupplier(() -> { throw new TestException(); } )));
  }

  @Test
  void testUnwrappingRethrowSupplier() {
    assertThrows(TestException.class, () -> this.supplier(Exceptions.unwrappingRethrowSupplier(() -> { throw new InvocationTargetException(new TestException()); })));
  }

  @Test
  void testRethrowUnaryOperator() {
    assertThrows(TestException.class, () -> this.function(Exceptions.rethrowUnaryOperator(a -> { throw new TestException(); })));
  }

  @Test
  void testUnwrappingRethrowUnaryOperator() {
    assertThrows(TestException.class, () -> this.function(Exceptions.unwrappingRethrowUnaryOperator(a -> { throw new TestException(); })));
  }

  @Test
  void testGetOrRethrow() {
    assertEquals("kitten", Exceptions.getOrRethrow(() -> "kitten"));
    assertThrows(TestException.class, () -> Exceptions.getOrRethrow(() -> { throw new TestException(); } ));
  }

  @Test
  void testPropagate() {
    final TestException te = new TestException();
    try {
      throw Exceptions.propagate(te);
    } catch(final RuntimeException e) {
      assertSame(te, e.getCause());
    }
  }

  @Test
  void testThrowIfUnchecked() {
    assertDoesNotThrow(() -> Exceptions.throwIfUnchecked(new Exception("should not be thrown")));
    final RuntimeException re = new RuntimeException("should be thrown");
    assertSame(re, assertThrows(RuntimeException.class, () -> Exceptions.throwIfUnchecked(re)));
  }

  @Test
  void testUnwrap() {
    final TestException te = new TestException();
    final InvocationTargetException ite = new InvocationTargetException(te);
    assertSame(te, Exceptions.unwrap(ite));
  }

  private void consumer(final Consumer<String> consumer) { consumer.accept("kitten"); }
  private void consumer(final BiConsumer<String, String> consumer) { consumer.accept("kitten", "kitty"); }
  private void function(final Function<String, String> function) { function.apply("kitten"); }
  private void function(final BiFunction<String, String, String> function) { function.apply("kitten", "kitty"); }
  private void predicate(final Predicate<String> supplier) { supplier.test("kitten"); }
  private void predicate(final BiPredicate<String, String> supplier) { supplier.test("kitten", "kitty"); }
  private void runnable(final Runnable runnable) { runnable.run(); }
  private void supplier(final Supplier<String> supplier) { supplier.get(); }

  private final class TestException extends Exception {}
}
