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
    @Query("SELECT * FROM team WHERE sys_teamID=:teamID") //vash 2
    LiveData<Team> getTeam(int teamID);

    @Query("SELECT * FROM team WHERE team_uuid=:teamID") //vash 3
    LiveData<Team> getTeamfromUUID(String teamID);

    @Query("SELECT * FROM team WHERE sys_teamID=:teamID")
    Team getTeam2(int teamID);

    //step 1 ->BowlingViewModel //vash1
    @Query("SELECT * FROM team WHERE champID=:champid ORDER BY sys_teamID") //to order xreiazetai? //oles tis omades tou champ //vash 2
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

    @Query("SELECT team.sys_teamID,team.team_uuid,team.fteamID,team.team_name,team.score,team.start_date,team.end_date,team.vs,team.champID,team.active_flag FROM team INNER JOIN championship_detail ON team.team_uuid=championship_detail.team_uuid WHERE championship_detail.champ_uuid=:champid ORDER BY team.fteamID") //test panw
    LiveData<List<Team>> getAllTeamsofChamp3( String champid); //plan B

    @Query("SELECT team.sys_teamID,team.team_uuid,team.fteamID,team.team_name,team.score,team.start_date,team.end_date,team.vs,team.champID,team.active_flag FROM team INNER JOIN championship_detail ON team.team_uuid=championship_detail.team_uuid WHERE championship_detail.champ_uuid=:champid AND championship_detail.active_flag>0 ORDER BY team.fteamID")
    LiveData<List<Team>> getActiveTeamsofChamp(String champid);

   // @Query("SELECT team.sys_teamID,team.team_uuid,team.fteamID,team.team_name,team.score,team.start_date,team.end_date,team.vs,team.champID,team.active_flag FROM team INNER JOIN championship_detail ON team.team_uuid=championship_detail.team_uuid WHERE championship_detail.champ_uuid=:champid ORDER BY championship_detail.team_score DESC")
   @Query("SELECT team.team_uuid,team.fteamID,team.team_name,championship_detail.team_score FROM team INNER JOIN championship_detail ON team.team_uuid=championship_detail.team_uuid WHERE championship_detail.champ_uuid=:champid ORDER BY championship_detail.team_score DESC")
   LiveData<List<TeamandScore>> getRankedAllTeamsofChamp( String champid);

    @Query("SELECT team.team_uuid,team.fteamID,team.team_name,round.score1, round.score2, round.points1, round.points2, round.team1UUID, round.team2UUID FROM team INNER JOIN round ON (team.team_uuid=round.team1UUID OR team.team_uuid=round.team2UUID )WHERE round.champ_uuid=:champid AND round.froundid=:frid")
    LiveData<List<TeamandRoundScore>> geAllTeamsofRoundofChamp( String champid, int frid);

    @Query("SELECT team.team_uuid,team.fteamID,team.team_name,round.score1, round.score2, round.points1, round.points2, round.team1UUID, round.team2UUID FROM team INNER JOIN round ON (team.team_uuid=round.team1UUID OR team.team_uuid=round.team2UUID )WHERE round.champ_uuid=:champid")
    LiveData<List<TeamandRoundScore>> geAllTeamsofChamp( String champid);

    @Transaction
    @Query("SELECT * FROM championship WHERE champ_uuid=:champuuid") //todo: na valw order
    LiveData<List<ActiveChampsTuple>>  getAllTeamsofChamp2(String champuuid); //vash 3 //douleueiiiii!!

    @Transaction
    @Query("SELECT * FROM team WHERE team_uuid=:s")
    LiveData<List<TeammatesTuple>>  getAllPlayersofTeam3(String s);

}
