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

    @ColumnInfo(name = "round")//axristo
    private int round; //se poio round vrisketai

    @ColumnInfo(name = "status")
    private String status; //0: energo, 1:teleiwse


    @TypeConverters(Converters.class) // axristo
    @ColumnInfo(name = "teamsid")
    // @Ignore
    private ArrayList<Integer> teamsid;

    @TypeConverters(Converters.class) // axristo
    @ColumnInfo(name = "hdcp_parameters")
    private ArrayList<Integer> hdcp_parameters;

    @ColumnInfo(name = "hdcp_beginners")
    private int hdcp_beginners;

    @ColumnInfo(name = "hdcp_advanced")
    private int hdcp_adv;

    @ColumnInfo(name = "hdcp_less")
    private int hdcp_less;

    @ColumnInfo(name = "hdcp_percentage")
    private int hdcp_factor;

    @ColumnInfo(name = "hdcp_tavani")
    private int hdcp_tav;

    @ColumnInfo(name = "type")
    private int type; //1,2,3..

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "start_date")
    private Date start_date;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "end_date")
    private Date end_date;

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

    public ArrayList<Integer> getHdcp_parameters() {
        return hdcp_parameters;
    }

    public int getFakeID() {
        return fakeID;
    }

    public int getHdcp_beginners() {
        return hdcp_beginners;
    }

    public int getHdcp_adv() {
        return hdcp_adv;
    }

    public int getHdcp_less() {
        return hdcp_less;
    }

    public int getHdcp_factor() {
        return hdcp_factor;
    }

    public int getHdcp_tav() {
        return hdcp_tav;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
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

    public void setHdcp_parameters(ArrayList<Integer> hdcp_parameters) {
        this.hdcp_parameters = hdcp_parameters;
    }

    public void setFakeID(int fakeID) {
        this.fakeID = fakeID;
    }

    public void setHdcp_beginners(int hdcp_beginners) {
        this.hdcp_beginners = hdcp_beginners;
    }

    public void setHdcp_adv(int hdcp_adv) {
        this.hdcp_adv = hdcp_adv;
    }

    public void setHdcp_less(int hdcp_less) {
        this.hdcp_less = hdcp_less;
    }

    public void setHdcp_factor(int hdcp_factor) {
        this.hdcp_factor = hdcp_factor;
    }

    public void setHdcp_tav(int hdcp_tav) {
        this.hdcp_tav = hdcp_tav;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Championship( int fakeID, int teamID, int round, String status) {
       // this.participantID = participantID;
        this.fakeID = fakeID;
        this.teamID = teamID;
        this.round = round;
        this.status = status;
    }

}
