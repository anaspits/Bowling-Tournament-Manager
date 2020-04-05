package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "team_detail")
public class Team_detail {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_teamDetailID;

    @ColumnInfo(name="sys_teamID")
    @NonNull
    private int teamID;

    @ColumnInfo(name="sys_participantID")
    @NonNull
    private int participantID;

    public int getSys_teamDetailID() {
        return sys_teamDetailID;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getParticipantID() {
        return participantID;
    }

    public void setSys_teamDetailID(int sys_teamDetailID) {
        this.sys_teamDetailID = sys_teamDetailID;
    }
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    public Team_detail(int teamID, int participantID) {
        this.teamID = teamID;
        this.participantID = participantID;
    }
}
