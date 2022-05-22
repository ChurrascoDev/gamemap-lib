package com.github.imthenico.gmlib.event;

import com.github.imthenico.gmlib.MapModel;

public class ModelEvent {

    private final MapModel<?> mapModel;

    ModelEvent(MapModel<?> mapModel) {
        this.mapModel = mapModel;
    }

    public MapModel<?> getMapModel() {
        return mapModel;
    }
}