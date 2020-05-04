package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

class PlayerandGames {
    @ColumnInfo(name="participant_uuid")
    @NonNull
    private String participant_uuid;

    @ColumnInfo(name="first_name")
    private String firstName;

    @ColumnInfo(name="last_name")
    private String lastName;

    @ColumnInfo(name="round_uuid")
    @NonNull
    private String round_uuid;


    @ColumnInfo(name="avg") //todo na kanw float
    private float bowlAvg;

    @ColumnInfo(name="hdcp")
    private int hdcp;


   // @ColumnInfo(name="froundid")
    @Ignore
    private String froundid;

    @ColumnInfo(name="games")
    private int games;


    @NonNull
    public String getParticipant_uuid() {
        return participant_uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getBowlAvg() {
        return bowlAvg;
    }

    public int getHdcp() {
        return hdcp;
    }

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    @NonNull
    public String getFroundid() {
        return froundid;
    }

    public int getGames() {
        return games;
    }

    public void setParticipant_uuid(@NonNull String participant_uuid) {
        this.participant_uuid = participant_uuid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBowlAvg(float bowlAvg) {
        this.bowlAvg = bowlAvg;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public void setHdcp(int hdcp) {
        this.hdcp = hdcp;
    }

    public void setRound_uuid(@NonNull String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public void setFroundid(@NonNull String froundid) {
        this.froundid = froundid;
    }

    public PlayerandGames(@NonNull String participant_uuid, String firstName, String lastName, float bowlAvg, int hdcp, @NonNull String round_uuid,int games) {
        this.participant_uuid = participant_uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bowlAvg = bowlAvg;
        this.hdcp = hdcp;
        this.round_uuid = round_uuid;
       // this.froundid = froundid;
        this.games=games;
    }
}
