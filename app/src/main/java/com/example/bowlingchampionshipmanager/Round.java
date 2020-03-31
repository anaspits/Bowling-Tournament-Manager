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
        parentColumns = "teamID",
        childColumns = "teamID")
},indices= {
        @Index(name="index_champID", value="champID", unique=true),
        @Index(name="index_teamID", value="teamID", unique=true)
}*/)
public class Round implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int roundid;

    @ColumnInfo(name="champID")
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


    public void setChampid(int champid) {
        this.champid = champid;
    }


    public void setTeam1ID(int teamID) {
        this.team1ID = teamID;
    }

    public void setRoundid(int round) {
        this.roundid = round;
    }

    public Round(int roundid, int champid, int team1ID) {
        this.roundid = roundid;
        this.champid = champid;
        this.team1ID = team1ID;
    }


}
