package com.github.imthenico.gmlib.handler;

import com.github.imthenico.gmlib.CustomMapFactory;
import com.github.imthenico.gmlib.GameMap;
import com.github.imthenico.gmlib.ModelData;

public interface HandlerRegistry {

    <D extends ModelData> DataSerializer<D> getSerializer(Class<D> modelClass);

    <D extends ModelData> DataDeserializer<D> getDeserializer(Class<D> modelClass);

    <D extends ModelData, T extends GameMap> CustomMapFactory<D, T> getMapFactory(
            Class<D> modelClass, Class<T> mapClass
    );

    static Builder builder() {
        return new HandlerRegistryImpl.BuilderImpl();
    }

    interface Builder {

        <D extends ModelData> Builder serializer(Class<D> clazz, DataSerializer<D> serializer);

        <D extends ModelData> Builder deserializer(Class<D> clazz, DataDeserializer<D> deserializer);

        <D extends ModelData, T extends GameMap> Builder customMapFactory(
                Class<D> modelClass, Class<T> mapClass, CustomMapFactory<D, T> mapFactory
        );

        Builder consume(DataManipulationModule dataManipulationModule);

        HandlerRegistry build();

    }
}