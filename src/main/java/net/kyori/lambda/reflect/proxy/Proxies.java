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

import net.kyori.lambda.Composer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A collection of utilities for working with proxies.
 *
 * @see Proxy
 */
public interface Proxies {
  /**
   * Returns a proxy instance for the specified interfaces that dispatches method invocations to the specified invocation handler.
   *
   * @param interfaceType the interface for the proxy class to implement
   * @param handler the invocation handler to dispatch method invocations to
   * @param <T> the type
   * @return a proxy instance
   * @throws IllegalArgumentException if {@code interfaces} contains a class that is not an interface
   */
  static <T> @NonNull T create(final @NonNull Class<T> interfaceType, final @NonNull InvocationHandler handler) {
    return create(interfaceType.getClassLoader(), interfaceType, handler);
  }

  /**
   * Returns a proxy instance for the specified interfaces that dispatches method invocations to the specified invocation handler.
   *
   * @param loader the class loader to define the proxy class
   * @param interfaceType the interface for the proxy class to implement
   * @param handler the invocation handler to dispatch method invocations to
   * @param <T> the type
   * @return a proxy instance
   * @throws IllegalArgumentException if {@code interfaces} contains a class that is not an interface
   */
  static <T> @NonNull T create(final @NonNull ClassLoader loader, final @NonNull Class<T> interfaceType, final @NonNull InvocationHandler handler) {
    return interfaceType.cast(create(loader, new Class<?>[]{interfaceType}, handler));
  }

  /**
   * Returns a proxy instance for the specified interfaces that dispatches method invocations to the specified invocation handler.
   *
   * @param loader the class loader to define the proxy class
   * @param interfaceType the interface for the proxy class to implement
   * @param interfaces the list of interfaces for the proxy class to implement
   * @param handler the invocation handler to dispatch method invocations to
   * @param <T> the type
   * @return a proxy instance
   * @throws IllegalArgumentException if {@code interfaces} contains a class that is not an interface
   */
  static <T> @NonNull T create(final @NonNull ClassLoader loader, final @NonNull Class<T> interfaceType, final @NonNull List<Class<?>> interfaces, final @NonNull InvocationHandler handler) {
    return create(loader, interfaceType, interfaces.toArray(new Class<?>[0]), handler);
  }

  /**
   * Returns a proxy instance for the specified interfaces that dispatches method invocations to the specified invocation handler.
   *
   * @param loader the class loader to define the proxy class
   * @param interfaceType the interface for the proxy class to implement
   * @param interfaces the list of interfaces for the proxy class to implement
   * @param handler the invocation handler to dispatch method invocations to
   * @param <T> the type
   * @return a proxy instance
   * @throws IllegalArgumentException if {@code interfaces} contains a class that is not an interface
   */
  static <T> @NonNull T create(final @NonNull ClassLoader loader, final @NonNull Class<T> interfaceType, final @NonNull Class<?>[] interfaces, final @NonNull InvocationHandler handler) {
    return interfaceType.cast(create(loader, Composer.accept(new ArrayList<>(1 + interfaces.length), list -> {
      list.add(interfaceType);
      Collections.addAll(list, interfaces);
    }), handler));
  }

  /**
   * Returns a proxy instance for the specified interfaces that dispatches method invocations to the specified invocation handler.
   *
   * @param loader the class loader to define the proxy class
   * @param interfaces the list of interfaces for the proxy class to implement
   * @param handler the invocation handler to dispatch method invocations to
   * @return a proxy instance
   * @throws IllegalArgumentException if {@code interfaces} contains a class that is not an interface
   */
  static @NonNull Object create(final @NonNull ClassLoader loader, final @NonNull List<Class<?>> interfaces, final @NonNull InvocationHandler handler) {
    return create(loader, interfaces.toArray(new Class<?>[0]), handler);
  }

  /**
   * Returns a proxy instance for the specified interfaces that dispatches method invocations to the specified invocation handler.
   *
   * @param loader the class loader to define the proxy class
   * @param interfaces the array of interfaces for the proxy class to implement
   * @param handler the invocation handler to dispatch method invocations to
   * @return a proxy instance
   * @throws IllegalArgumentException if {@code interfaces} contains a class that is not an interface
   * @see Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)
   */
  static @NonNull Object create(final @NonNull ClassLoader loader, final @NonNull Class<?>[] interfaces, final @NonNull InvocationHandler handler) {
    return Proxy.newProxyInstance(loader, interfaces, handler);
  }
}
