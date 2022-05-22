package com.github.imthenico.gmlib;

import com.google.gson.JsonObject;

public class Serialized {

    private final JsonObject jsonObject;
    private final String json;

    Serialized(JsonObject jsonObject, String json) {
        this.jsonObject = jsonObject;
        this.json = json;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public String getJson() {
        return json;
    }
}