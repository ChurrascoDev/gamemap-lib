package com.github.imthenico.gmlib.util;

import java.util.Objects;

public class PairKey {

    private final Object left;
    private final Object right;

    public PairKey(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairKey pairKey = (PairKey) o;
        return Objects.equals(left, pairKey.left) && Objects.equals(right, pairKey.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public static PairKey nonNull(Object left, Object right) {
        return new PairKey(
                Objects.requireNonNull(left),
                Objects.requireNonNull(right)
        );
    }
}