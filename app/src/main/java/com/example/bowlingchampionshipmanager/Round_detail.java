package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "round_detail",primaryKeys = {"round_uuid","participant_uuid"}, foreignKeys = {
        @ForeignKey(entity = Round.class,
                parentColumns = "round_uuid",
                childColumns = "round_uuid",
                onDelete = CASCADE),
        @ForeignKey(entity = Participant.class,
                parentColumns = "participant_uuid",
                childColumns = "participant_uuid",
                onDelete = CASCADE)//,
       /* @ForeignKey(entity = Championship.class,
                parentColumns = "champ_uuid",
                childColumns = "champ_uuid")*/
}, indices= {
        @Index(name="index_roundID", value="round_uuid"),
        @Index(name="index_round_participantID", value="participant_uuid")//,
        //@Index(name="index_rd_champid", value="champ_uuid"),
})
public class Round_detail  implements Serializable {
   /* @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_roundDetailID; */

    @ColumnInfo(name="round_uuid")
    @NonNull
    private String round_uuid;

    @ColumnInfo(name="participant_uuid")
    @NonNull
    private String participant_uuid;

    @ColumnInfo(name="champ_uuid")
    @NonNull
    private String champ_uuid;

    @ColumnInfo(name="score") //tou participant sto sugkekrimeno round (sunolo korinwn)
    private int score;

    @ColumnInfo(name="avg") //tou participant apo thn arxh tou champ /ton ari8mo twn paixnidiwn tou champ pou den htan blind
    private float avg;

    @ColumnInfo(name="hdcp") //tou participant
    private int hdcp;
    @ColumnInfo(name="first") //tou participant
    private int first;
    @ColumnInfo(name="second") //tou participant
    private int second;
    @ColumnInfo(name="third") //tou participant
    private int third;
    @ColumnInfo(name="blind")
    private int blind; //0: den htan blind, 1: htan blind sto round

    @ColumnInfo(name="games") //ta paixnidia pou epai3e mexri twra se afto to champ
    private int games;

    @ColumnInfo(name="froundid") //ta paixnidia pou epai3e mexri twra se afto to champ
    private int froundid;

    @TypeConverters(Converters.class)
    @ColumnInfo(name="created_at")
    private Date created_at;

    @TypeConverters(Converters.class)
    @ColumnInfo(name="updated_at")
    private Date updated_at;

    @Ignore
    private String checked_auto_calc_hdcp;

    @Ignore
    private int auto_calc_newHdcp; //voithiko gia to neo hdcp pou upologizetai automata

   /* public int getSys_roundDetailID() {
        return sys_roundDetailID;
    } */

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    public String getParticipant_uuid() {
        return participant_uuid;
    }

    public int getScore() {
        return score;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
/* public void setSys_roundDetailID(int sys_roundDetailID) {
        this.sys_roundDetailID = sys_roundDetailID;
    } */


    public int getHdcp() {
        return hdcp;
    }

    public int getBlind() {
        return blind;
    }

    public float getAvg() {
        return avg;
    }

    public void setRound_uuid(String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public int getGames() {
        return games;
    }

    public int getFroundid() {
        return froundid;
    }

    @NonNull
    public String getChamp_uuid() {
        return champ_uuid;
    }

    public String getChecked_auto_calc_hdcp() {
        return checked_auto_calc_hdcp;
    }

    public int getAuto_calc_newHdcp() {
        return auto_calc_newHdcp;
    }

    public void setParticipant_uuid(String participant_uuid) {
        this.participant_uuid = participant_uuid;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setThird(int third) {
        this.third = third;
    }

    public void setHdcp(int hdcp) {
        this.hdcp = hdcp;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public void setChamp_uuid(@NonNull String champ_uuid) {
        this.champ_uuid = champ_uuid;
    }

    public void setBlind(int blind) {
        this.blind = blind;
    }

    public void setFroundid(int froundid) {
        this.froundid = froundid;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setAuto_calc_newHdcp(int auto_calc_newHdcp) {
        this.auto_calc_newHdcp = auto_calc_newHdcp;
    }

    public void setChecked_auto_calc_hdcp(String checked_auto_calc_hdcp) {
        this.checked_auto_calc_hdcp = checked_auto_calc_hdcp;
    }

    public Round_detail(String round_uuid, String participant_uuid, int first, int second, int third, int hdcp, int blind, String champ_uuid, int froundid, Date created_at) {
        this.round_uuid = round_uuid;
        this.participant_uuid = participant_uuid;
        this.first = first;
        this.second = second;
        this.third = third;
        this.hdcp=hdcp;
        this.blind=blind;
        this.champ_uuid=champ_uuid;
        this.froundid=froundid;
        this.created_at=created_at;
    }

}
