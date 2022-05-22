package com.github.imthenico.gmlib.metadata;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class SimpleMetadataHolder implements MetadataHolder {

    private final Map<Object, Object> dataMap = new ConcurrentHashMap<>();

    @Override
    public void set(@NotNull Object key, @Nullable Object value) {
        this.dataMap.put(requireNonNull(key), value);
    }

    @Override
    public @Nullable Object get(@NotNull Object key) {
        return dataMap.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getExpected(@NotNull Object key, @NotNull Class<T> expectedClass) {
        Object found = dataMap.get(key);

        if (!expectedClass.isInstance(found)) {
            return null;
        }

        return (T) found;
    }

    @Override
    public boolean has(@NotNull Object key) {
        return dataMap.containsKey(key);
    }

    @Override
    public @Nullable Object remove(@NotNull Object key) {
        return dataMap.remove(key);
    }

    @Override
    public @NotNull Set<Object> keys() {
        return new HashSet<>(dataMap.keySet());
    }

    @Override
    public @NotNull Set<Object> values() {
        return new HashSet<>(dataMap.values());
    }

    @Override
    public @NotNull MetadataSnapshot newMetadataSnapshot() {
        return new MetadataSnapshot(this);
    }

    @NotNull
    @Override
    public Iterator<DataEntry> iterator() {
        List<DataEntry> dataEntries = new ArrayList<>(dataMap.size());

        for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
            dataEntries.add(new DataEntry(entry.getKey(), entry.getValue()));
        }

        return dataEntries.iterator();
    }
}