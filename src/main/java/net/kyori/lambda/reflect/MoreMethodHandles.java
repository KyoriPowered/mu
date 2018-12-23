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

import net.kyori.lambda.Composer;
import net.kyori.lambda.collection.LoadingMap;
import net.kyori.lambda.exception.Exceptions;
import net.kyori.lambda.function.ThrowingSupplier;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * A collection of utilities for working with method handles.
 */
public final class MoreMethodHandles {
  private static final Constructor<MethodHandles.Lookup> LOOKUP_CONSTRUCTOR = Composer.get(ThrowingSupplier.of(() -> {
    final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
    constructor.setAccessible(true);
    return constructor;
  }));
  private static final Map<Class<?>, MethodHandles.Lookup> LOOKUPS = LoadingMap.concurrent(requestedLookupClass -> {
    try {
      return LOOKUP_CONSTRUCTOR.newInstance(requestedLookupClass, MethodHandles.Lookup.PUBLIC | MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PACKAGE);
    } catch(final InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw Exceptions.rethrow(e);
    }
  });

  private MoreMethodHandles() {
  }

  /**
   * Returns a {@link MethodHandles.Lookup lookup object} with full capabilities to emulate all
   * supported bytecode behaviors, including private access, on a target class.
   *
   * @param targetClass the target class
   * @return a lookup object for the target class, with private access
   */
  public static MethodHandles.@NonNull Lookup privateLookupIn(final @NonNull Class<?> targetClass) {
    return LOOKUPS.get(targetClass);
  }

  /**
   * Makes a direct method handle to {@code method}.
   *
   * @param targetClass the target class
   * @param method the reflected method
   * @return a method handle which can invoke the reflected method
   * @see MethodHandles.Lookup#unreflect(Method)
   */
  public static @NonNull MethodHandle unreflect(final @NonNull Class<?> targetClass, final @NonNull Method method) {
    try {
      return privateLookupIn(targetClass).unreflect(method);
    } catch(final IllegalAccessException e) {
      throw Exceptions.rethrow(e);
    }
  }

  /**
   * Makes a direct method handle to {@code method}.
   *
   * @param method the reflected method
   * @return a method handle which can invoke the reflected method
   * @see MethodHandles.Lookup#unreflect(Method)
   */
  public static @NonNull MethodHandle unreflect(final @NonNull Method method) {
    return unreflect(method.getDeclaringClass(), method);
  }

  /**
   * Produces a method handle giving read access to a reflected field.
   *
   * @param targetClass the target class
   * @param field the reflected field
   * @return a method handle which can load values from the reflected field
   * @see MethodHandles.Lookup#unreflectGetter(Field)
   */
  public static @NonNull MethodHandle unreflectGetter(final @NonNull Class<?> targetClass, final @NonNull Field field) {
    try {
      return privateLookupIn(targetClass).unreflectGetter(field);
    } catch(final IllegalAccessException e) {
      throw Exceptions.rethrow(e);
    }
  }

  /**
   * Produces a method handle giving read access to a reflected field.
   *
   * @param field the reflected field
   * @return a method handle which can load values from the reflected field
   * @see MethodHandles.Lookup#unreflectGetter(Field)
   */
  public static @NonNull MethodHandle unreflectGetter(final @NonNull Field field) {
    return unreflectGetter(field.getDeclaringClass(), field);
  }

  /**
   * Produces a method handle giving write access to a reflected field.
   *
   * @param targetClass the target class
   * @param field the reflected field
   * @return a method handle which can store values into the reflected field
   * @see MethodHandles.Lookup#unreflectSetter(Field)
   */
  public static @NonNull MethodHandle unreflectSetter(final @NonNull Class<?> targetClass, final @NonNull Field field) {
    try {
      return privateLookupIn(targetClass).unreflectSetter(field);
    } catch(final IllegalAccessException e) {
      throw Exceptions.rethrow(e);
    }
  }

  /**
   * Produces a method handle giving write access to a reflected field.
   *
   * @param field the reflected field
   * @return a method handle which can store values into the reflected field
   * @see MethodHandles.Lookup#unreflectSetter(Field)
   */
  public static @NonNull MethodHandle unreflectSetter(final @NonNull Field field) {
    return unreflectSetter(field.getDeclaringClass(), field);
  }
}
