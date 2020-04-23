package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Championship_detailDao {

    @Insert
    void insert(Championship_detail t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Championship_detail t);

    @Delete
    int delete(Championship_detail t);

    @Query("SELECT * FROM championship_detail")
    LiveData<List<Championship_detail>> getAllChamp_detail();

    @Query("SELECT * FROM championship_detail WHERE champ_uuid=:chid")
    LiveData<List<Championship_detail>> getChamp_detailofChamp( String chid);

    @Query("SELECT * FROM championship_detail WHERE champ_uuid=:chid AND active_flag=0")
    LiveData<List<Championship_detail>> getChamp_detailofChampofFinnishedTeams( String chid);

    @Query("SELECT * FROM championship_detail WHERE team_uuid=:tid AND champ_uuid=:chid")
    LiveData<Championship_detail> getChamp_detailofTeamandChamp(String tid, String chid);

}
