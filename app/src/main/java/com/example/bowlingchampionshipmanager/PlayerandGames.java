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

    @ColumnInfo(name="round_uuid")
    @NonNull
    private String round_uuid;


    @ColumnInfo(name="avg") //todo na kanw float
    private float bowlAvg;

    @ColumnInfo(name="hdcp")
    private int hdcp;


    @ColumnInfo(name="froundid")
    private int froundid;

    @ColumnInfo(name="games")
    private int games;

    @ColumnInfo(name="first") //tou participant
    private int first;
    @ColumnInfo(name="second") //tou participant
    private int second;
    @ColumnInfo(name="third") //tou participant
    private int third;


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

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    @NonNull
    public int getFroundid() {
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

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setThird(int third) {
        this.third = third;
    }

    public void setRound_uuid(@NonNull String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public void setFroundid(@NonNull int froundid) {
        this.froundid = froundid;
    }

    public PlayerandGames(@NonNull String participant_uuid, String firstName, String lastName, float bowlAvg, int hdcp, @NonNull String round_uuid, int froundid, int games, int first, int second, int third) {
        this.participant_uuid = participant_uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bowlAvg = bowlAvg;
        this.hdcp = hdcp;
        this.round_uuid = round_uuid;
        this.froundid = froundid;
        this.games=games;
        this.first=first;
        this.second=second;
        this.third=third;
    }
}
