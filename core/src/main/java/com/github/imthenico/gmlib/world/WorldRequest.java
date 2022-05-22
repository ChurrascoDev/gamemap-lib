package com.github.imthenico.gmlib.world;

import java.util.*;
import java.util.function.Consumer;

public class WorldRequest {

    private final String name;
    private final Map<String, Object> extraData;

    public WorldRequest(String name, Map<String, Object> extraData) {
        this.name = name;
        this.extraData = extraData;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public static WorldRequest of(String name, Map<String, Object> extraData) {
        return new WorldRequest(Objects.requireNonNull(name), extraData != null ? extraData : new HashMap<>());
    }

    public static WorldRequest of(String name) {
        return of(name, null);
    }

    public static WorldRequest configure(String name, Consumer<Map<String, Object>> action) {
        Map<String, Object> extraData = new HashMap<>();

        action.accept(extraData);

        return new WorldRequest(name, extraData);
    }
}