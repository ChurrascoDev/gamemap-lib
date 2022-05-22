package com.github.imthenico.gmlib.handler;

import com.github.imthenico.gmlib.ModelData;
import com.google.gson.JsonObject;

public interface DataSerializer<T extends ModelData> {

    JsonObject serializeData(T mapData);

}