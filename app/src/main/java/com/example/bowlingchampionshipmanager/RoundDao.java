package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoundDao {
    @Insert
    void insert(Round t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Round t);

    @Delete
    int delete(Round t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM round")
    LiveData<List<Round>> getAllRound();

    @Query("SELECT * FROM round WHERE champ_uuid=:champuuid")
    LiveData<List<Round>> getAllRoundofChamp(String champuuid);

    @Query("SELECT * FROM round WHERE champ_uuid=:champuuid AND status='done'")
    LiveData<List<Round>> getDoneRoundsofChamp(String champuuid);

    @Query("SELECT round.sys_roundID,round.round_uuid, round.froundid, round.fchampID, round.champ_uuid, round.team1ID, round.team2ID,round.team1UUID, round.team2UUID, round.score1, round.score2,round.points1,round.points2,round.status, round.date FROM round INNER JOIN team ON (round.team1UUID=team.team_uuid or round.team2UUID=team.team_uuid) WHERE (team.team_uuid=:teamuuid AND (round.status='next' OR round.status='last') AND round.champ_uuid=:champuuid)")
    LiveData<List<Round>> getRoundsofTeam( String teamuuid,String champuuid); //todo na valw k order. To status den to 8elw mallon, MAllon axristo

    @Query("SELECT round.sys_roundID,round.round_uuid, round.froundid, round.fchampID, round.champ_uuid, round.team1ID, round.team2ID,round.team1UUID, round.team2UUID, round.score1, round.score2,round.points1,round.points2,round.status, round.date FROM round INNER JOIN team ON (round.team1UUID=team.team_uuid or round.team2UUID=team.team_uuid) WHERE team.team_uuid=:teamuuid AND round.champ_uuid=:champuuid ORDER BY round.froundid")
    LiveData<List<Round>> getAllRoundsofTeam( String teamuuid,String champuuid);

    @Query("SELECT * FROM  round WHERE (team1UUID=:teamuuid or team2UUID=:teamuuid) and champ_uuid=:champuuid and status='current'")
    LiveData<Round> getCurrentRoundofTeam(String teamuuid, String champuuid); //na svisw

    @Query("SELECT round.sys_roundID,round.round_uuid, round.froundid, round.fchampID, round.champ_uuid, round.team1ID, round.team2ID,round.team1UUID, round.team2UUID, round.score1, round.score2,round.points1,round.points2,round.status, round.date FROM  round INNER JOIN team ON (round.team1UUID=team.team_uuid or round.team2UUID=team.team_uuid) WHERE (team.team_uuid=:teamuuid and round.champ_uuid=:champuuid AND (round.status='next' OR round.status='last')) ORDER BY round.froundid ASC LIMIT 1 ") //DESC 8a fernei to last round
    LiveData<List<Round>> getNextRoundofTeamofChamp(String teamuuid, String champuuid);

}
