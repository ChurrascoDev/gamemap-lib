package com.github.imthenico.gmlib.metadata;

import com.github.imthenico.gmlib.util.ThrowableFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class DataEntry {

    private final Object key;
    private final Object value;

    DataEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public @NotNull Object getKey() {
        return key;
    }

    public @Nullable Object getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T expectedValue(@NotNull Class<T> expectedClass) {
        return expectedClass.isInstance(value) ? (T) value : null;
    }

    @SuppressWarnings("unchecked")
    public <T> void ifIsExpected(@NotNull Class<T> expectedClass, @NotNull Consumer<T> consumer) {
        if (expectedClass.isInstance(value)) {
            consumer.accept((T) value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T throwIf(@NotNull Class<T> expectedClass, @NotNull ThrowableFunction<T, Throwable> supplier) {
        if (expectedClass.isInstance(value)) {
            try {
                Throwable throwable = supplier.apply((T) value);

                if (throwable != null)
                    throw throwable;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return (T) value;
    }
}