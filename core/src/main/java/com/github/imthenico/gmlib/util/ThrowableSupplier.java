package com.github.imthenico.gmlib.util;

public interface ThrowableSupplier<T> {

    T get() throws Exception;

}