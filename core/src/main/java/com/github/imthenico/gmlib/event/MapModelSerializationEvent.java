package com.github.imthenico.gmlib.event;

import com.github.imthenico.gmlib.MapModel;
import com.github.imthenico.json.JsonTreeBuilder;

public class MapModelSerializationEvent extends ModelEvent {

    private final JsonTreeBuilder jsonTreeBuilder;

    public MapModelSerializationEvent(MapModel<?> mapModel, JsonTreeBuilder jsonTreeBuilder) {
        super(mapModel);
        this.jsonTreeBuilder = jsonTreeBuilder;
    }

    public JsonTreeBuilder getJsonTreeBuilder() {
        return jsonTreeBuilder;
    }
}