package com.github.imthenico.gmlib.util;

public interface TypeIdentifier {

    boolean validateIdentity(TypeIdentity typeIdentity);

    static TypeIdentifier singleClass(Class<?> clazz) {
        return typeIdentity -> clazz.equals(typeIdentity.safeGet(0));
    }

    static TypeIdentifier twoClasses(Class<?> one, Class<?> two) {
        return typeIdentity -> one.equals(typeIdentity.safeGet(0)) &&
                two.equals(typeIdentity.safeGet(1));
    }
}