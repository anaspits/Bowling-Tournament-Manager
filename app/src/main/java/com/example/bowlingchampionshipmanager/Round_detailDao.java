package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Round_detailDao {
    @Insert
    void insert(Round_detail t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Round_detail t);

    @Delete
    int delete(Round_detail t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM round_detail")
    LiveData<List<Round_detail>> getAllRound_detail();

    @Query("SELECT * FROM round_detail WHERE participant_uuid=:pid AND round_uuid=:rid")
    LiveData<Round_detail> getRound_detail(String pid, String rid);

    @Query("SELECT * FROM round_detail WHERE participant_uuid=:pid ")
    LiveData<List<Round_detail>> getallAllRound_detailofplayer(String pid);

}
