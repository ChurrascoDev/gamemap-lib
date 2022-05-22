package com.github.imthenico.gmlib.handler;

import com.github.imthenico.gmlib.ModelData;
import com.github.imthenico.json.JsonReader;

public interface DataDeserializer<T extends ModelData> {

    T deserializeData(JsonReader jsonReader);

}