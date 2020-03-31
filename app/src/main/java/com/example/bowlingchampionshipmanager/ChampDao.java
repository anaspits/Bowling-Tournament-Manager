package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChampDao { //gia ta Team
    @Insert
    void insert(Championship t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Championship t);

    @Delete
    int delete(Championship t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM championship")
    LiveData<List<Championship>> getAllChamp();


    // fetch step 1 -> editViwModel
    @Query("SELECT * FROM championship WHERE champID=:champID")
    LiveData<Championship> getChamp(int champID);

    /*@Query("SELECT last_insert_rowid() FROM championship")
    LiveData<Championship> getLastInsertChamp(); */ //thelei POJO

    @Query("SELECT * FROM championship WHERE champID=(SELECT MAX(champID) FROM championship)")
    LiveData<Championship> getLastInsertChamp();

    @Query("SELECT teamsid FROM championship WHERE champID=:champid")
    LiveData<TeammatesTuple> getTeamsid(int champid);

}
