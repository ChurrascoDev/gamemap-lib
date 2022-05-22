package com.github.imthenico.gmlib.event;

import com.github.imthenico.gmlib.MapModel;
import com.google.gson.JsonObject;

public class MapModelSerializationEvent extends ModelEvent {

    private final JsonObject jsonObject;

    public MapModelSerializationEvent(MapModel<?> mapModel, JsonObject jsonObject) {
        super(mapModel);
        this.jsonObject = jsonObject;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }
}