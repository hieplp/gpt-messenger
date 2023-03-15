package com.hieplp.messenger.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class JsonConverter {
    private static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .create();

    private JsonConverter() {
        throw new IllegalStateException("Utility class: JsonConverter");
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
        }
        return gson;
    }

    public static JsonElement toJsonElement(Object object) {
        return getGson().toJsonTree(object);
    }

    public static JsonElement toJsonElement(String object) {
        return JsonParser.parseString(object);
    }

    public static JsonObject toJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public static JsonObject toJsonObject(Object object) {
        return JsonParser.parseString(getGson().toJson(object)).getAsJsonObject();
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

    public static <T> T fromJson(JsonObject jsonObject, Class<T> clazz) {
        return getGson().fromJson(jsonObject, clazz);
    }

    public static <T> T fromMap(Map<?, ?> map, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, clazz);
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        return getGson().fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }
}
