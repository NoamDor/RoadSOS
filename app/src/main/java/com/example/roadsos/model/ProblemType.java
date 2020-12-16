package com.example.roadsos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.io.Serializable;

@Entity
public class ProblemType implements Serializable {
    @PrimaryKey
    @NonNull
    private int id;
    private String name;
    private String imageUrl;

    ProblemType(int id, String name, String imageUrl) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    ProblemType() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
