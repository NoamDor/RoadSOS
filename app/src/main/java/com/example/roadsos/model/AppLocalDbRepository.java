package com.example.roadsos.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roadsos.App;

@Database(entities = {ProblemType.class}, version = 3)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ProblemTypeDao problemTypeDao();
}
