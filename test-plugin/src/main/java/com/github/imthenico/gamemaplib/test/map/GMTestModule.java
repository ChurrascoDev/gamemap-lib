package com.github.imthenico.gamemaplib.test.map;

import com.github.imthenico.gmlib.handler.HandlerRegistry;
import com.github.imthenico.gmlib.handler.DataManipulationModule;
import com.google.gson.Gson;

public class GMTestModule implements DataManipulationModule {

    private final Gson gson;

    public GMTestModule(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void configure(HandlerRegistry.Builder builder) {
        builder.deserializer(ArenaModelData.class, jsonObject -> gson.fromJson(jsonObject, ArenaModelData.class))
                .serializer(ArenaModelData.class, mapData -> gson.toJsonTree(mapData).getAsJsonObject())
                .customMapFactory(ArenaModelData.class, Arena.class, new ArenaMapFactory());
    }
}