package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "championship"/*,foreignKeys = {
        @ForeignKey(entity = Participant.class,
                parentColumns = "participantID",
                childColumns = "participantID"),
        @ForeignKey(entity = Team.class,
                parentColumns = "teamID",
                childColumns = "teamID")
}, indices= {
        @Index(name="index_teamID", value="teamID", unique=true),
        @Index(name="index_participantID", value="participantID", unique=true)
}*/)
public class Championship implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int champID; //to id pou exei sti vash

    @ColumnInfo(name="fakeID")
    @NonNull
    int fakeID; //to id pou exei to sugkekrimeno prwtathlima


    @ColumnInfo(name="participantID")
    @NonNull
    private int participantID; //axristo

    @ColumnInfo(name="teamID")
    @NonNull
    private int teamID;

    @ColumnInfo(name = "round")
    private int round; //se poio round vrisketai

    @TypeConverters(Converters.class) // add here
    @ColumnInfo(name = "teamsid")
    // @Ignore
    private ArrayList<Integer> teamsid;

    public int getChampID() {
        return champID;
    }

    public int getParticipantID() {
        return participantID;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getRound() {
        return round;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Integer> getTeamsid() {
        return teamsid;
    }

    public void setChampID(int champID) {
        this.champID = champID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTeamsid(ArrayList<Integer> teamsid) {
        this.teamsid = teamsid;
    }

    @ColumnInfo(name = "status")
    private String status;

    public Championship( int fakeID, int teamID, int round, String status) {
       // this.participantID = participantID;
        this.fakeID = fakeID;
        this.teamID = teamID;
        this.round = round;
        this.status = status;
    }

}
