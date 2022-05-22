package com.github.imthenico.gmlib.metadata;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface MetadataHolder extends Iterable<DataEntry> {

    void set(@NotNull Object key, @Nullable Object value);

    @Nullable Object get(@NotNull Object key);

    <T> T getExpected(@NotNull Object key, @NotNull Class<T> expectedClass);

    boolean has(@NotNull Object key);

    @Nullable Object remove(@NotNull Object key);

    @NotNull Set<Object> keys();

    @NotNull Set<Object> values();

    @NotNull MetadataSnapshot newMetadataSnapshot();

}