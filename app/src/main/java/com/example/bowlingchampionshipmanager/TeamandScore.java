package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class TeamandScore {
    @ColumnInfo(name="team_uuid")
    String team_uuid;

    @ColumnInfo(name="fteamID")
    @NonNull
    private int fteamID; //to id pou exei h omada sto sugkekrimeno prwtathlima  //to thelw gia to generateTeams

    @ColumnInfo(name="team_name")
    private String team_name;

    @ColumnInfo(name="team_score")
    private int team_score;

    public String getTeam_uuid() {
        return team_uuid;
    }

    public int getFteamID() {
        return fteamID;
    }

    public String getTeam_name() {
        return team_name;
    }

    public int getTeam_score() {
        return team_score;
    }

    public void setTeam_uuid(String team_uuid) {
        this.team_uuid = team_uuid;
    }

    public void setFteamID(int fteamID) {
        this.fteamID = fteamID;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public void setTeam_score(int team_score) {
        this.team_score = team_score;
    }

    public TeamandScore(String team_uuid, int fteamID, String team_name, int team_score) {
        this.team_uuid = team_uuid;
        this.fteamID = fteamID;
        this.team_name = team_name;
        this.team_score = team_score;
    }
}
