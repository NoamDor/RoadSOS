package com.example.roadsos.model;

import androidx.room.Room;

import com.example.roadsos.App;

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(App.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}