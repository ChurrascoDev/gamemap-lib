package com.github.imthenico.gmlib.exception;

public class MapCreationException extends RuntimeException {
    public MapCreationException(String str) {
        super(str);
    }

    public MapCreationException(Throwable cause) {
        super(cause);
    }
}