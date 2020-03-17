package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BowlingDao {

    @Insert
    void insert(Participant t);

    @Query("SELECT * FROM participant")
    LiveData<List<Participant>> getAllBowls();

    // fetch step 1 -> editViwModel
    @Query("SELECT * FROM participant WHERE participantID=:bowlId")
    LiveData<Participant> getBwol(int bowlId);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Participant participant);

    @Delete
    int delete(Participant participant);

}
