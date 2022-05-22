package com.github.imthenico.gmlib.util;

import java.util.Objects;

@SuppressWarnings("rawtypes")
public class TypeIdentity {

    private final Class[] classes;

    private TypeIdentity(Class[] classes) {
        this.classes = classes;
    }

    public Class<?>[] getClasses() {
        return classes;
    }

    public Class<?> safeGet(int i) {
        return i >= 0 && i < classes.length ? classes[i] : Class.class;
    }

    public static TypeIdentity of(Class...classes) {
        for (Class aClass : classes) {
            Objects.requireNonNull(aClass, "aClass");
        }

        return new TypeIdentity(classes);
    }
}