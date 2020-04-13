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

    @Query("SELECT round.sys_roundID,round.round_uuid, round.froundid, round.fchampID, round.champ_uuid, round.team1ID, round.team2ID,round.team1UUID, round.team2UUID, round.score1, round.score2,round.status, round.date FROM round INNER JOIN team ON (round.team1UUID=team.team_uuid or round.team2UUID=team.team_uuid) WHERE (team.team_uuid=:teamuuid)")
    LiveData<List<Round>> getRoundsofTeam( String teamuuid); //todo na valw k champid k status=current

    @Query("SELECT * FROM  round WHERE (team1UUID=:teamuuid or team2UUID=:teamuuid) and champ_uuid=:champuuid ORDER BY froundid DESC LIMIT 1 ")
    LiveData<Round> getRoundofTeam( String teamuuid, String champuuid); //todo na valw k champid k status=current
}
