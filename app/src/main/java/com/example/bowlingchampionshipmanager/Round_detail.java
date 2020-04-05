package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "round_detail")
public class Round_detail  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_roundDetailID;

    @ColumnInfo(name="sys_roundID")
    @NonNull
    private int sys_raoundID;

    @ColumnInfo(name="participantID")
    private int participantID;

    @ColumnInfo(name="score")
    private int score;

    public int getSys_roundDetailID() {
        return sys_roundDetailID;
    }

    public int getSys_raoundID() {
        return sys_raoundID;
    }

    public int getParticipantID() {
        return participantID;
    }

    public int getScore() {
        return score;
    }

    public void setSys_roundDetailID(int sys_roundDetailID) {
        this.sys_roundDetailID = sys_roundDetailID;
    }

    public void setSys_raoundID(int sys_raoundID) {
        this.sys_raoundID = sys_raoundID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Round_detail(int sys_raoundID, int participantID, int score) {
        this.sys_raoundID = sys_raoundID;
        this.participantID = participantID;
        this.score = score;
    }

}
