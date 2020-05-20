package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
//import java.sql.Date;
import java.util.Date;
import java.util.ArrayList;

@Entity(tableName = "championship", indices= { @Index(name="index_champ_uuid", value="champ_uuid", unique=true)}/*,foreignKeys = {
        @ForeignKey(entity = Participant.class,
                parentColumns = "participantID",
                childColumns = "participantID"),
        @ForeignKey(entity = Team.class,
                parentColumns = "sys_teamID",
                childColumns = "sys_teamID")
}, indices= {
        @Index(name="index_teamID", value="sys_teamID", unique=true),
        @Index(name="index_participantID", value="participantID", unique=true)
}*/)
public class Championship implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="sys_champID")
    @NonNull
    private int sys_champID; //to id pou exei sti vash

    @ColumnInfo(name="champ_uuid")
    String uuid;

    @ColumnInfo(name="fchampID")
    @NonNull
    int fchampID; //to id pou exei to sugkekrimeno prwtathlima


    @ColumnInfo(name="participantID")//axristo
    @NonNull
    private int participantID; //axristo

    @ColumnInfo(name="sys_teamID") //vash 1: axristo, vash 2: xrhsimo, vash 3: axristo
    @NonNull
    private int teamID;

    @ColumnInfo(name = "round")//axristo //todo na to kanw rounds kai na krata ton arithmo-sunolo twn rounds tou champ?
    private int round; //se poio round vrisketai

    @ColumnInfo(name = "status")
    private String status; //created, active, finished //todo active anti gia started


   // @TypeConverters(Converters.class) // axristo
   // @ColumnInfo(name = "teamsid")
     @Ignore
    private ArrayList<Integer> teamsid;

  //  @TypeConverters(Converters.class) // axristo
   // @ColumnInfo(name = "hdcp_parameters")
    @Ignore
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
    private int type; //1:pins, 2:tvst, 3:friendly, 4:single

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "created_at")
    private Date created_at;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "start_date")
    private Date start_date;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "end_date")
    private Date end_date;

    public int getSys_champID() {
        return sys_champID;
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

    public int getFchampID() {
        return fchampID;
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

    public Date getCreated_at() {
        return created_at;
    }

    public int getType() {
        return type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setSys_champID(int sys_champID) {
        this.sys_champID = sys_champID;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public void setFchampID(int fchampID) {
        this.fchampID = fchampID;
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

    public void setType(int type) { //1:pins, 2:tvst, 3:friendly
        this.type = type;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Championship(int fchampID, String uuid, int teamID, int round, String status) {
       // this.participantID = participantID;
        this.fchampID = fchampID;
        this.uuid=uuid;
        this.teamID = teamID; //vash 2
        this.round = round;
        this.status = status;
    }

}
