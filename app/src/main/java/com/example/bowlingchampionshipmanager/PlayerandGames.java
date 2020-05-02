package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

class PlayerandGames {
    @ColumnInfo(name="participant_uuid")
    @NonNull
    private String participant_uuid;

    @ColumnInfo(name="first_name")
    private String firstName;

    @ColumnInfo(name="last_name")
    private String lastName;

    @ColumnInfo(name="avg") //todo na kanw float
    private int bowlAvg;

    @ColumnInfo(name="hdcp")
    private int hdcp;

    @ColumnInfo(name="score") //tou participant
    private int score;

    @ColumnInfo(name="round_uuid")
    @NonNull
    private String round_uuid;

    @ColumnInfo(name="froundid")
    @NonNull
    private String froundid;

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

    public int getBowlAvg() {
        return bowlAvg;
    }

    public int getHdcp() {
        return hdcp;
    }

    public int getScore() {
        return score;
    }

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    @NonNull
    public String getFroundid() {
        return froundid;
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

    public void setBowlAvg(int bowlAvg) {
        this.bowlAvg = bowlAvg;
    }

    public void setHdcp(int hdcp) {
        this.hdcp = hdcp;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRound_uuid(@NonNull String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public void setFroundid(@NonNull String froundid) {
        this.froundid = froundid;
    }

    public PlayerandGames(@NonNull String participant_uuid, String firstName, String lastName, int bowlAvg, int hdcp, int score, @NonNull String round_uuid, @NonNull String froundid) {
        this.participant_uuid = participant_uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bowlAvg = bowlAvg;
        this.hdcp = hdcp;
        this.score = score;
        this.round_uuid = round_uuid;
        this.froundid = froundid;
    }
}
