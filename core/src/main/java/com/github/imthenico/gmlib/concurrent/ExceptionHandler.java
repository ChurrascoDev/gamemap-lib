package com.github.imthenico.gmlib.concurrent;

import java.util.function.Function;

public class ExceptionHandler<T> implements Function<Throwable, T> {

    public static <T> ExceptionHandler<T> create() {
        return new ExceptionHandler<>();
    }

    @Override
    public T apply(Throwable throwable) {
       throwable.printStackTrace();
       return null;
    }
}