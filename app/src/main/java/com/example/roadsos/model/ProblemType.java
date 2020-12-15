package com.example.roadsos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ProblemType implements Serializable {
    @PrimaryKey
    @NonNull
    public int id;
    public String name;
    public String imageUrl;

    ProblemType(int id, String name, String imageUrl) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    ProblemType() {
    }

    public String getName() {
        return name;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getImageUrl() { return imageUrl; }
}
