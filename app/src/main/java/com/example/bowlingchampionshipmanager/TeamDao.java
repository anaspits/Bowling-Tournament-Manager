package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
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
    @Query("SELECT * FROM team WHERE sys_teamID=:teamID")
    LiveData<Team> getTeam(int teamID);

    @Query("SELECT * FROM team WHERE sys_teamID=:teamID")
    Team getTeam2(int teamID);

    //step 1 ->BowlingViewModel //vash1
    @Query("SELECT * FROM team WHERE champID=:champid ORDER BY sys_teamID") //to order xreiazetai? //oles tis omades tou champ
    LiveData<List<Team>> getAllTeamsofChamp(int champid);

   /* //step 1 ->BowlingViewModel
    @Query("SELECT teammatesid FROM team  WHERE sys_teamID=:sys_teamID") //olous tous umpaiktes tou paikth //dokimh, na to svisw an einai axristo
    LiveData<List<Integer>> getTeammates(int sys_teamID); //gia na to xrishmopoihsw prepei na kanw POJO, dld nea klash */

    //@Query("SELECT teammatesid FROM team WHERE sys_teamID=:teamid")
    //List<TeammatesTuple> getTeammatesid(int teamid);

    //////////////////////////////////////////

    /*@Query("SELECT teamid, team.fchampID, team_name, team.score, team.start_date, team.end_date, vs, team.champID, active_flag FROM team INNER JOIN championship ON team.teamid=championship.sys_teamID WHERE championship.fchampID=:fchampid") //vash 2
    LiveData<List<Team>>  getAllTeamsofChamp2(int fchampid); */

    //@Query("SELECT team.teamid, team.fteamID, team.team_name, team.score, team.start_date, team.end_date, team.vs, team.champID, team.active_flag  //vash 3
    //@Query("SELECT team.teamid, team.fteamID, team.team_name, team.score, team.start_date, team.end_date, team.vs, team.champID, team.active_flag FROM team INNER JOIN championship_detail ON team.teamid=championship_detail.sys_teamID WHERE championship_detail.sys_champID=:champid") //vash 3
    @Transaction
    @Query("SELECT * FROM championship")
    LiveData<List<ActiveChampsTuple>>  getAllTeamsofChamp2();

}
