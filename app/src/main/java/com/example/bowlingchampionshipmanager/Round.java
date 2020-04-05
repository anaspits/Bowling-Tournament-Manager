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

@Entity(tableName = "round"/*,foreignKeys = {
        @ForeignKey(entity = Championship.class,
                parentColumns = "champID",
                childColumns = "champID"),
        @ForeignKey(entity = Team.class,
        parentColumns = "sys_teamID",
        childColumns = "sys_teamID")
},indices= {
        @Index(name="index_champID", value="champID", unique=true),
        @Index(name="index_teamID", value="sys_teamID", unique=true)
}*/)
public class Round implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="sys_roundid")
    private int roundid;

    @ColumnInfo(name="froundid")
    private int froundid;

    @ColumnInfo(name="fchampID")
    @NonNull
    private int champid;

    @ColumnInfo(name="team1ID")
    private int team1ID;

    @ColumnInfo(name="team2ID")
    private int team2ID;

    @ColumnInfo(name="score1")
    private int score1;


    @ColumnInfo(name="score2")
    private int score2;

    @TypeConverters(Converters.class)
    @ColumnInfo(name="date")
    private Date date;

    public int getChampid() {
        return champid;
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

    public void setChampid(int champid) {
        this.champid = champid;
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


    public Round(int roundid, int champid, int team1ID) {
        this.roundid = roundid;
        this.champid = champid;
        this.team1ID = team1ID;
    }


}
