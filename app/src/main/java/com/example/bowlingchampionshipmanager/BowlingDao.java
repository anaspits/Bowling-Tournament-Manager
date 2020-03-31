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
    //ola ta Queries gia tous Participants //epistrefoun Participant

    @Insert
    void insert(Participant t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM participant")
    LiveData<List<Participant>> getAllBowls();

    //step 1 ->BowlingViewModel
    @Query("SELECT COUNT(participantID) FROM participant WHERE champID=:champID")
    int getAllPlayersofChamp2( int champID); //na to kaw List<Participant> anti gia int //na to svisw

    // fetch step 1 -> editViwModel
    @Query("SELECT * FROM participant WHERE participantID=:bowlId")
    LiveData<Participant> getBwol(int bowlId);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Participant participant);

    @Delete
    int delete(Participant participant);

    //-> BowlingViewModel
    @Query("SELECT * FROM participant WHERE teamID=:teamid") // olous tous paiktes mias omadas//ToDo: na to kanw k gia sugkekrimeno champ //allagh to teamid
    LiveData<List<Participant>> getAllPlayersofTeam( int teamid);

    //-> BowlingViewModel
    @Query("SELECT * FROM participant ORDER BY teamID") //olous tous paiktes me seira omadas //ToDo:na to kanw k gia sugkekrimeno champ H na to enwsw me to katw
    LiveData<List<Participant>> getAllPlayersofTeamsOrdered();

    //-> BowlingViewModel
    @Query("SELECT * FROM participant WHERE champID=:champid") ////olous tous paiktes tou champ//ToDo:na to kanw k gia sugkekrimeno champ
    LiveData<List<Participant>> getAllPlayersofChamp(int champid);

    @Query("SELECT * FROM participant WHERE participantID IN (SELECT teamatesid FROM team WHERE teamID=:teamid )") //olous tous umpaiktes tou paikth //dokimh, na to svisw an einai axristo
    LiveData<List<Participant>> getTeammates(int teamid);


}
