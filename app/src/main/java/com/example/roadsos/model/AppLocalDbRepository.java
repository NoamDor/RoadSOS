package com.example.roadsos.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.roadsos.App;

@Database(entities = {ProblemType.class, Problem.class}, version = 8)
@TypeConverters({Converters.class})
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ProblemTypeDao problemTypeDao();
    public abstract ProblemDao problemDao();
}
