package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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


    @ColumnInfo(name="teamID")
    @NonNull
    private int teamID;

    public int getChampid() {
        return champid;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getRoundid() {
        return roundid;
    }


    public void setChampid(int champid) {
        this.champid = champid;
    }


    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setRoundid(int round) {
        this.roundid = round;
    }

    public Round(int roundid, int champid, int teamID) {
        this.roundid = roundid;
        this.champid = champid;
        this.teamID = teamID;
    }


}
