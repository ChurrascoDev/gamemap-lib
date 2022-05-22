package com.github.imthenico.gmlib.util;

public interface ThrowableFunction<T, R> {

    R apply(T t) throws Throwable;

}