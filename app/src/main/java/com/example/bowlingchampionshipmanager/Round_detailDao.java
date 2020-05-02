package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Round_detailDao {
    @Insert
    void insert(Round_detail t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Round_detail t);

    @Delete
    int delete(Round_detail t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM round_detail")
    LiveData<List<Round_detail>> getAllRound_detail();

    @Query("SELECT * FROM round_detail WHERE participant_uuid=:pid AND round_uuid=:rid")
    LiveData<Round_detail> getRound_detail(String pid, String rid);

    @Query("SELECT * FROM round_detail WHERE participant_uuid=:pid ")
    LiveData<List<Round_detail>> getallAllRound_detailofplayer(String pid);

    @Query("SELECT participant.participant_uuid,participant.first_name, participant.last_name, participant.avg,participant.hdcp ,round_detail.score, round_detail.round_uuid, round.froundid FROM participant INNER JOIN round_detail ON participant.participant_uuid=round_detail.participant_uuid INNER JOIN round ON round_detail.round_uuid=round.round_uuid INNER JOIN team_detail ON participant.participant_uuid=team_detail.participant_uuid INNER JOIN championship_detail ON team_detail.team_uuid = championship_detail.team_uuid  WHERE championship_detail.champ_uuid=:chid AND round_detail.round_uuid=:rid AND round.froundid<=:frid ORDER BY round_detail.score")
    LiveData<List<PlayerandGames>> getPlayerScoreGamesofPreviousRounds( String rid,String chid, int frid);

}
