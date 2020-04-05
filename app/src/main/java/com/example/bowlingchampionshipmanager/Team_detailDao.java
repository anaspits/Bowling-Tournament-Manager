package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Team_detailDao {
    @Insert
    void insert(Team_detail t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Team_detail t);

    @Delete
    int delete(Team_detail t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM team_detail")
    LiveData<List<Team_detail>> getAllTeam_detail();

}
