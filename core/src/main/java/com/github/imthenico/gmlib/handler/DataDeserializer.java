package com.github.imthenico.gmlib.handler;

import com.github.imthenico.gmlib.ModelData;
import com.google.gson.JsonObject;

public interface DataDeserializer<T extends ModelData> {

    T deserializeData(JsonObject jsonObject);

}