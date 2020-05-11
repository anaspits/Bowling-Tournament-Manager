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
public interface BowlingDao {
    //ola ta Queries gia tous Participants //epistrefoun Participant

    @Insert
    void insert(Participant t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM participant")
    LiveData<List<Participant>> getAllBowls();

    @Query("SELECT * FROM participant WHERE disable_flag=0")
    LiveData<List<Participant>> getAllActiveParticipants();

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
    @Query("SELECT * FROM participant WHERE teamID=:teamid") //vash 1 //olous tous paiktes mias omadas//ToDo: na to kanw k gia sugkekrimeno champ //allagh to teamid
    LiveData<List<Participant>> getAllPlayersofTeam( int teamid);

    //-> BowlingViewModel
    @Query("SELECT * FROM participant ORDER BY teamID") //vash 1 //olous tous paiktes me seira omadas //ToDo:na to kanw k gia sugkekrimeno champ H na to enwsw me to katw
    LiveData<List<Participant>> getAllPlayersofTeamsOrdered();

    //-> BowlingViewModel
    @Query("SELECT * FROM participant WHERE champID=:champid") //vash 1 //olous tous paiktes tou champ//ToDo:na to kanw k gia sugkekrimeno champ
    LiveData<List<Participant>> getAllPlayersofChamp(int champid);

  ////  //@Query("SELECT * FROM participant WHERE sys_participantID IN (SELECT teamatesid FROM team WHERE teamID=:teamid )") //olous tous umpaiktes tou paikth //dokimh, na to svisw an einai axristo
    //LiveData<List<Participant>> getTeammates(int teamid);
@Query("SELECT * FROM participant WHERE teamID=:teamid ") //NA SVISW
    LiveData<List<Participant>> getTeammates(int teamid);////////

    ///////////////

    @Query("SELECT participant.participantID, participant.participant_uuid, participant.fakeID, participant.first_name, participant.last_name, participant.sex, participant.avg,  participant.hdcp,participant.total_games, participant.teamID,participant.champID, participant.start_date, participant.end_date, participant.disable_flag FROM participant INNER JOIN team_detail ON participant.participant_uuid=team_detail.participant_uuid INNER JOIN championship_detail ON team_detail.team_uuid = championship_detail.team_uuid  WHERE championship_detail.champ_uuid=:chID AND team_detail.team_uuid=:teamid ORDER BY participant.avg DESC") //vash 3 //olous tous paiktes mias omadas//ToDo: allagh to teamid
    LiveData<List<Participant>> getAllPlayersofTeam3( String teamid, String chID); //vash 3

    @Query("SELECT participant.participantID,  participant.participant_uuid, participant.fakeID, participant.first_name, participant.last_name,participant.sex,participant.avg,  participant.hdcp,hdcp,participant.total_games, participant.teamID,participant.champID, participant.start_date, participant.end_date, participant.disable_flag FROM participant INNER JOIN team_detail ON participant.participant_uuid=team_detail.participant_uuid INNER JOIN championship_detail ON team_detail.team_uuid = championship_detail.team_uuid INNER JOIN team ON team.team_uuid=team_detail.team_uuid INNER JOIN round ON (round.team1UUID=team.team_uuid or round.team2UUID=team.team_uuid) WHERE (team.team_uuid!=:teamid AND (round.team1UUID=team.team_uuid or round.team2UUID=team.team_uuid) AND round.champ_uuid=:chID AND championship_detail.champ_uuid=:chID AND (round.status='next' OR round.status='last')) ORDER BY round.froundid") //WHERE championship_detail.champ_uuid=:chID AND team_detail.team_uuid=:teamid //vash 3 //olous tous paiktes mias omadas
    LiveData<List<Participant>> getAllPlayersofOpositeTeam( String teamid, String chID); //svhsto den leitourgei


    @Query("SELECT * FROM participant WHERE first_name=:fn AND  last_name=:ln")
    LiveData<List<Participant>> getParticipantByName( String fn, String ln);

    @Query("SELECT participant.participantID, participant.participant_uuid, participant.fakeID, participant.first_name, participant.last_name, participant.sex, participant.avg,  participant.hdcp,hdcp,participant.total_games, participant.teamID,participant.champID, participant.start_date, participant.end_date, participant.disable_flag FROM participant INNER JOIN team_detail ON participant.participant_uuid=team_detail.participant_uuid INNER JOIN championship_detail ON team_detail.team_uuid = championship_detail.team_uuid  WHERE championship_detail.champ_uuid=:chID ORDER BY participant.avg DESC")
    LiveData<List<Participant>> getAllPlayersofChamp( String chID); //vash 3

    //todo test it:
    @Query("SELECT participant.participantID, participant.participant_uuid, participant.fakeID, participant.first_name, participant.last_name, participant.sex, participant.avg,  participant.hdcp,hdcp,participant.total_games, participant.teamID,participant.champID, participant.start_date, participant.end_date, participant.disable_flag FROM participant INNER JOIN team_detail ON participant.participant_uuid=team_detail.participant_uuid INNER JOIN championship_detail ON team_detail.team_uuid = championship_detail.team_uuid  WHERE championship_detail.champ_uuid=:champid ORDER BY team_detail.team_uuid ")
    LiveData<List<Participant>> getTeammatesofTeamsofChamp( String champid);
}
