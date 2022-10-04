package com.router.clients.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.router.clients.rest.model.TimeRecord;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    Gson gson;
    Type listType;

    public JsonParser() {
        listType = new TypeToken<ArrayList<TimeRecord>>(){}.getType();

        gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
                .create();
    }

    public TimeRecord getObjectFromJson(String response, Class<TimeRecord> tClass) {
        return gson.fromJson(response, tClass);
    }

    public List<TimeRecord> getListOfObjectsFromJson(String response) {
        return new Gson().fromJson(response, listType);
    }
}
