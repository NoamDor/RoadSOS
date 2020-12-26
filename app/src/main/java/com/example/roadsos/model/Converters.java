package com.example.roadsos.model;

import androidx.room.TypeConverter;

import com.example.roadsos.enums.StatusCode;
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

    @TypeConverter
    public static String toJson(ProblemStatus status) {
        Gson gson = new Gson();
        return gson.toJson(status);
    }

    @TypeConverter
    public static ProblemStatus toProblemStatus(String json) {
        return new Gson().fromJson(json, ProblemStatus.class);
    }
}
