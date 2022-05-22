package com.github.imthenico.gmlib.event;

import com.github.imthenico.gmlib.MapModel;

public class MapModelDeserializationEvent extends ModelEvent {

    public MapModelDeserializationEvent(MapModel<?> mapModel) {
        super(mapModel);
    }
}