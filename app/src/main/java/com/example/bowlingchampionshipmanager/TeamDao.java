package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TeamDao { //gia ta Team
    @Insert
    void insert(Team t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Team t);

    @Delete
    int delete(Team t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM team")
    LiveData<List<Team>> getAllTeams();


    // fetch step 1 -> BowlViwModel //mipws na to valw sto edit?
    @Query("SELECT * FROM team WHERE teamID=:teamID")
    LiveData<Team> getTeam(int teamID);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM team WHERE champID=:champid ORDER BY teamID") //to order xreiazetai? //oles tis omades tou champ
    LiveData<List<Team>> getAllTeamsofChamp(int champid);

   /* //step 1 ->BowlingViewModel
    @Query("SELECT teammatesid FROM team  WHERE teamID=:teamID") //olous tous umpaiktes tou paikth //dokimh, na to svisw an einai axristo
    LiveData<List<Integer>> getTeammates(int teamID); //gia na to xrishmopoihsw prepei na kanw POJO, dld nea klash */
}
