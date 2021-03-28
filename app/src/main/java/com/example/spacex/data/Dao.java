package com.example.spacex.data;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM SpaceTable")
    LiveData<List<SpaceData>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<SpaceData> data);

    @Query("DELETE FROM SpaceTable")
    void deleteAll();

    @Update
    void update(SpaceData data);
}
