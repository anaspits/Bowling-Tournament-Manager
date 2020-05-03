package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "round",indices= {
        @Index(name="index_round_uuid", value="round_uuid", unique=true),
        @Index(name="index_r_champuuid", value="champ_uuid")},foreignKeys = {
        @ForeignKey(entity = Championship.class,
                parentColumns = "champ_uuid",
                childColumns = "champ_uuid")/*,
        @ForeignKey(entity = Team.class,
        parentColumns = "sys_teamID",
        childColumns = "sys_teamID")
},indices= {
        @Index(name="index_champID", value="champID", unique=true),
        @Index(name="index_teamID", value="sys_teamID", unique=true)
*/})
public class Round implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="sys_roundID")
    private int roundid;

    @ColumnInfo(name="round_uuid")
    private String rounduuid;

    @ColumnInfo(name="froundid")
    private int froundid;

    @ColumnInfo(name="fchampID")
    @NonNull
    private int champid;

    @ColumnInfo(name="champ_uuid")
    @NonNull
    private String champuuid;

    @ColumnInfo(name="team1ID") //axristo?
    private int team1ID;

    @ColumnInfo(name="team2ID") //axristo?
    private int team2ID;

    @ColumnInfo(name="team1UUID")
    private String team1UUID;

    @ColumnInfo(name="team2UUID")
    private String team2UUID;

    @ColumnInfo(name="score1")
    private int score1;

    @ColumnInfo(name="score2")
    private int score2;

    @ColumnInfo(name="points1")
    private int points1;

    @ColumnInfo(name="points2")
    private int points2;

    @ColumnInfo(name="status")
    private String status; //H na to kanw int? //done, current, next


    @TypeConverters(Converters.class)
    @ColumnInfo(name="date")
    private Date date;

    public int getChampid() {
        return champid;
    }

    @NonNull
    public String getChampuuid() {
        return champuuid;
    }

    public String getRounduuid() {
        return rounduuid;
    }

    public String getStatus() {
        return status;
    }

    public String getTeam1UUID() {
        return team1UUID;
    }

    public String getTeam2UUID() {
        return team2UUID;
    }

    public int getTeam1ID() {
        return team1ID;
    }

    public int getRoundid() {
        return roundid;
    }

    public int getTeam2ID() {
        return team2ID;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public Date getDate() {
        return date;
    }

    public int getFroundid() {
        return froundid;
    }

    public int getPoints1() {
        return points1;
    }

    public int getPoints2() {
        return points2;
    }

    public void setChampid(int champid) {
        this.champid = champid;
    }

    public void setRounduuid(String rounduuid) {
        this.rounduuid = rounduuid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChampuuid(@NonNull String champuuid) {
        this.champuuid = champuuid;
    }

    public void setTeam1UUID(String team1UUID) {
        this.team1UUID = team1UUID;
    }

    public void setTeam2UUID(String team2UUID) {
        this.team2UUID = team2UUID;
    }

    public void setTeam1ID(int teamID) {
        this.team1ID = teamID;
    }

    public void setRoundid(int round) {
        this.roundid = round;
    }

    public void setFroundid(int froundid) {
        this.froundid = froundid;
    }

    public void setTeam2ID(int team2ID) {
        this.team2ID = team2ID;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPoints1(int points1) {
        this.points1 = points1;
    }

    public void setPoints2(int points2) {
        this.points2 = points2;
    }

    public Round(String rounduuid, int froundid, int team1ID, int team2ID, String champuuid, String team1UUID, String team2UUID, int score1, int score2, String status) {
        this.rounduuid=rounduuid;
        this.froundid = froundid;
        this.team1ID = team1ID;
        this.team2ID = team2ID;
        this.team1UUID = team1UUID;
        this.team2UUID = team2UUID;
        this.champuuid = champuuid;
        this.score1=score1;
        this.score2=score2;
        this.status=status;
    }


}
