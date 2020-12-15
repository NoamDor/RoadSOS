package com.example.roadsos.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProblemDao {
    @Query("select * from Problem")
    LiveData<List<Problem>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Problem... problems);

    @Delete
    void delete(Problem... problems);
}
