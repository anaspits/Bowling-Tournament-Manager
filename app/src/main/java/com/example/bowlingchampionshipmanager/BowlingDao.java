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

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM participant")
    LiveData<List<Participant>> getAllBowls();

    //step 1 ->BowlingViewModel
    @Query("SELECT COUNT(participantID) FROM participant WHERE champID=:champID")
    int getAllPlayersofChamp2( int champID); //na to kaw List<Participant> anti gia int

    // fetch step 1 -> editViwModel
    @Query("SELECT * FROM participant WHERE participantID=:bowlId")
    LiveData<Participant> getBwol(int bowlId);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Participant participant);

    @Delete
    int delete(Participant participant);

    //-> BowlingViewModel
    @Query("SELECT * FROM participant WHERE teamID=:teamid") //na to kanw k gia sugkekrimeno champ
    LiveData<List<Participant>> getAllPlayersofTeam( int teamid);
    //-> BowlingViewModel
    @Query("SELECT * FROM participant ORDER BY teamID") //na to kanw k gia sugkekrimeno champ
    LiveData<List<Participant>> getAllPlayersofTeamOrdered();
    //-> BowlingViewModel
    @Query("SELECT * FROM participant WHERE champID=:champid") //na to kanw k gia sugkekrimeno champ
    LiveData<List<Participant>> getAllPlayersofTeamOrdered(int champid);

}
