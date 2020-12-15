package com.example.roadsos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProblemTypeDao {
    @Query("select * from ProblemType")
    LiveData<List<ProblemType>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ProblemType... students);

    @Delete
    void delete(ProblemType... problems);
}