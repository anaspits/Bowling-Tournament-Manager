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

    @Query("SELECT * FROM round_detail WHERE champ_uuid=:chid")
    LiveData<List<Round_detail>> getAllRound_detailofChamp(String chid);

    @Query("SELECT * FROM round_detail WHERE participant_uuid=:pid AND round_uuid=:rid") //todo gia sugkekrimeno champ
    LiveData<Round_detail> getRound_detail(String pid, String rid);

    @Query("SELECT round_detail.round_uuid, round_detail.participant_uuid,round_detail.champ_uuid, round_detail.score, round_detail.avg, round_detail.hdcp, round_detail.first, round_detail.second, round_detail.third, round_detail.blind, round_detail.games, round_detail.froundid FROM round_detail INNER JOIN round ON round.round_uuid=round_detail.round_uuid WHERE round_detail.champ_uuid=:chid AND round.froundid<=:frid ORDER BY round.froundid") //todo gia gunaika kai antra?
    LiveData<List<Round_detail>> getAllPrevRound_detailofChamp(String chid, int frid);

    @Query("SELECT participant.participant_uuid,participant.first_name, participant.last_name,participant.sex, round_detail.round_uuid,round_detail.score,round_detail.avg,round_detail.hdcp ,round_detail.games,round_detail.froundid, round_detail.first, round_detail.second, round_detail.third FROM participant INNER JOIN round_detail ON participant.participant_uuid=round_detail.participant_uuid INNER JOIN round ON round.round_uuid=round_detail.round_uuid WHERE round_detail.champ_uuid=:chid AND round.froundid<=:frid ORDER BY round.froundid") //todo gia gunaika kai antra?
    LiveData<List<PlayerandGames>> getAllPrevPlayerandGamesofChamp(String chid, int frid); //test apo panw

    @Query("SELECT * FROM round_detail WHERE participant_uuid=:pid AND blind=0") //ka8oliko
    LiveData<List<Round_detail>> getallAllRound_detailofplayer(String pid);

    @Query("SELECT round_detail.round_uuid, round_detail.participant_uuid,round_detail.champ_uuid, round_detail.score, round_detail.avg, round_detail.hdcp, round_detail.first, round_detail.second, round_detail.third, round_detail.blind, round_detail.games, round_detail.froundid FROM round_detail INNER JOIN round ON round.round_uuid=round_detail.round_uuid WHERE round_detail.participant_uuid=:pid AND round.champ_uuid=:chid AND round_detail.blind=0 AND round.status='done'")
    LiveData<List<Round_detail>> getAllRound_detailofplayerofChamp(String pid, String chid);

    @Query("SELECT participant.participant_uuid,participant.first_name, participant.last_name,participant.sex,round_detail.round_uuid,round_detail.score,round_detail.avg,round_detail.hdcp ,round_detail.games,round_detail.froundid, round_detail.first, round_detail.second, round_detail.third FROM participant INNER JOIN round_detail ON participant.participant_uuid=round_detail.participant_uuid WHERE round_detail.champ_uuid=:chid AND round_detail.round_uuid=:rid ORDER BY round_detail.avg")
    LiveData<List<PlayerandGames>> getPlayerScoreGamesofRound(String rid, String chid);

    @Query("SELECT participant.participant_uuid,participant.first_name, participant.last_name,participant.sex,round_detail.round_uuid,round_detail.score,round_detail.avg,round_detail.hdcp ,round_detail.games, round_detail.froundid,round_detail.first, round_detail.second, round_detail.third FROM participant INNER JOIN round_detail ON participant.participant_uuid=round_detail.participant_uuid WHERE round_detail.champ_uuid=:chid AND round_detail.froundid=:frid ORDER BY round_detail.avg")
    LiveData<List<PlayerandGames>> getAllPlayerScoreGamesofRound(int frid, String chid);

    @Query("SELECT participant.participant_uuid,participant.first_name, participant.last_name,participant.sex,round_detail.round_uuid,round_detail.score,round_detail.avg,round_detail.hdcp ,round_detail.games, round_detail.froundid,round_detail.first, round_detail.second, round_detail.third FROM participant INNER JOIN round_detail ON participant.participant_uuid=round_detail.participant_uuid  WHERE round_detail.champ_uuid=:chid ORDER BY participant.participant_uuid")
    LiveData<List<PlayerandGames>> getAllPlayerScoreGamesofChamp(String chid);

    @Query("SELECT participant.participant_uuid,participant.first_name, participant.last_name,participant.sex,round_detail.round_uuid,round_detail.score,round_detail.avg,round_detail.hdcp ,round_detail.games, round_detail.froundid,round_detail.first, round_detail.second, round_detail.third FROM participant INNER JOIN round_detail ON participant.participant_uuid=round_detail.participant_uuid  WHERE round_detail.champ_uuid=:chid ORDER BY round_detail.froundid DESC")
    LiveData<List<PlayerandGames>> getAllPlayerScoreGamesofChampOrdered(String chid);

}
