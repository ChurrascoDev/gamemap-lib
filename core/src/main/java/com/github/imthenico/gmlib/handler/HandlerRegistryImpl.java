package com.github.imthenico.gmlib.handler;

import com.github.imthenico.gmlib.CustomMapFactory;
import com.github.imthenico.gmlib.GameMap;
import com.github.imthenico.gmlib.ModelData;
import com.github.imthenico.gmlib.util.TypeIdentifier;
import com.github.imthenico.gmlib.util.TypeIdentity;
import com.sun.org.apache.regexp.internal.RE;

import java.util.*;
import java.util.function.Predicate;

class HandlerRegistryImpl implements HandlerRegistry {

    private final List<Entry> registeredEntries;

    HandlerRegistryImpl(List<Entry> registeredEntries) {
        this.registeredEntries = new ArrayList<>(registeredEntries);
    }

    @Override
    public <D extends ModelData> DataSerializer<D> getSerializer(Class<D> modelClass) {
        return filter(entry -> entry.handlerType == HandlerType.SERIALIZER && entry.typeIdentifier.validateIdentity(TypeIdentity.of(modelClass)));
    }

    @Override
    public <D extends ModelData> DataDeserializer<D> getDeserializer(Class<D> modelClass) {
        return filter(entry -> entry.handlerType == HandlerType.DESERIALIZER && entry.typeIdentifier.validateIdentity(TypeIdentity.of(modelClass)));
    }

    @Override
    public <D extends ModelData, T extends GameMap> CustomMapFactory<D, T> getMapFactory(Class<D> modelClass, Class<T> mapClass) {
        return filter(entry -> entry.handlerType == HandlerType.MAP_FACTORY && entry.typeIdentifier.validateIdentity(TypeIdentity.of(modelClass, mapClass)));
    }

    @SuppressWarnings("unchecked")
    private <T> T filter(Predicate<Entry> entryPredicate) {
        for (Entry registeredEntry : registeredEntries) {
            if (entryPredicate.test(registeredEntry)) return (T) registeredEntry.handler;
        }

        return null;
    }

    static class BuilderImpl implements Builder {

        private final List<Entry> entries = new ArrayList<>();

        @Override
        public <D extends ModelData> Builder serializer(Class<D> clazz, DataSerializer<D> serializer) {
            return add(TypeIdentifier.singleClass(clazz), HandlerType.SERIALIZER, serializer);
        }

        @Override
        public <D extends ModelData> Builder deserializer(Class<D> clazz, DataDeserializer<D> deserializer) {
            return add(TypeIdentifier.singleClass(clazz), HandlerType.DESERIALIZER, deserializer);
        }

        @Override
        public <D extends ModelData, T extends GameMap> Builder customMapFactory(Class<D> modelClass, Class<T> mapClass, CustomMapFactory<D, T> mapFactory) {
            return add(TypeIdentifier.twoClasses(modelClass, mapClass), HandlerType.MAP_FACTORY, mapFactory);
        }

        @Override
        public Builder consume(DataManipulationModule dataManipulationModule) {
            dataManipulationModule.configure(this);
            return this;
        }

        @Override
        public HandlerRegistry build() {
            return new HandlerRegistryImpl(entries);
        }

        private Builder add(TypeIdentifier typeIdentifier, HandlerType type, Object handler) {
            Objects.requireNonNull(typeIdentifier, "typeIdentifier");
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(handler, "handler");

            Entry entry = new Entry(typeIdentifier, type, handler);

            if (entries.contains(entry)) throw new IllegalArgumentException("Entry is already registered");

            entries.add(entry);
            return this;
        }
    }

    private static class Entry {

        private final TypeIdentifier typeIdentifier;
        private final HandlerType handlerType;
        private final Object handler;

        public Entry(TypeIdentifier typeIdentifier, HandlerType handlerType, Object handler) {
            this.typeIdentifier = typeIdentifier;
            this.handlerType = handlerType;
            this.handler = handler;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(typeIdentifier, entry.typeIdentifier) && Objects.equals(handlerType, entry.handlerType) && Objects.equals(handler, entry.handler);
        }

        @Override
        public int hashCode() {
            return Objects.hash(typeIdentifier, handlerType, handler);
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "typeIdentifier=" + typeIdentifier +
                    ", handlerType=" + handlerType +
                    ", handler=" + handler +
                    '}';
        }
    }
}
