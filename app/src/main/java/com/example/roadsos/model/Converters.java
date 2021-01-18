package com.example.roadsos.model;

import android.location.Location;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

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

    @TypeConverter
    public static String toJson(MyLocation location) {
        Gson gson = new Gson();
        return gson.toJson(location);
    }

    @TypeConverter
    public static MyLocation toLocation(String json) {
        return new Gson().fromJson(json, MyLocation.class);
    }
}
