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

    @Query("DELETE FROM championship where status='finished'")
    void deleteOldChamp();

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM championship")
    LiveData<List<Championship>> getAllChamp();


    // fetch step 1 -> editViwModel
    @Query("SELECT * FROM championship WHERE fchampID=:champID") //oxi to sys
    LiveData<Championship> getChamp(int champID);

    // fetch step 1 -> bowlingViwModel
    @Query("SELECT * FROM championship WHERE champ_uuid=:champUUID")
    LiveData<Championship> getChampUUID(String champUUID);

    /*@Query("SELECT last_insert_rowid() FROM championship")
    LiveData<Championship> getLastInsertChamp(); */ //thelei POJO

    @Query("SELECT * FROM championship WHERE sys_champID=(SELECT MAX(fchampID) FROM championship)") //fchamp gia vash 2
    LiveData<Championship> getLastInsertChamp();

    //@Query("SELECT teamsid FROM championship WHERE fchampID=:champid")
    //LiveData<TeammatesTuple> getTeamsid(int champid);

    /////////////////////////////

    @Query("SELECT MIN(fchampID) FROM championship WHERE (status='created' OR status='started')") //vash 2 //malon axristo
    LiveData<List<Integer>> getActiveChamp();

    @Query("SELECT * FROM championship WHERE (status='created' OR status='started')") //vash 3
    LiveData<List<Championship>> getActiveChamp3();

}

