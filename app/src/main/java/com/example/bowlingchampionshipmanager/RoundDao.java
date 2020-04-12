package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoundDao {
    @Insert
    void insert(Round t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Round t);

    @Delete
    int delete(Round t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM round")
    LiveData<List<Round>> getAllRound();
}
