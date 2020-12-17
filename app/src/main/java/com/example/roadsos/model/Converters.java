package com.example.roadsos.model;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static String toJson(ProblemType pt) {
        Gson gson = new Gson();
        return gson.toJson(pt);
    }

    @TypeConverter
    public static ProblemType toProblemType(String json) {
        return new Gson().fromJson(json, ProblemType.class);
    }
}
