package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

class TeamandRoundScore {
    @ColumnInfo(name="team_uuid")
    String team_uuid;

    @ColumnInfo(name="fteamID")
    @NonNull
    private int fteamID; //to id pou exei h omada sto sugkekrimeno prwtathlima  //to thelw gia to generateTeams

    @ColumnInfo(name="team_name")
    private String team_name;

    @ColumnInfo(name="score1")
    private int score1;

    @ColumnInfo(name="score2")
    private int score2;

    @ColumnInfo(name="points1")
    private int points1;

    @ColumnInfo(name="points2")
    private int points2;

    @ColumnInfo(name="team1UUID")
    String team1_uuid;

    @ColumnInfo(name="team2UUID")
    String team2_uuid;

    @ColumnInfo(name="status")
    String status;

    public String getTeam_uuid() {
        return team_uuid;
    }

    public int getFteamID() {
        return fteamID;
    }

    public String getTeam_name() {
        return team_name;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public int getPoints1() {
        return points1;
    }

    public int getPoints2() {
        return points2;
    }

    public String getTeam1_uuid() {
        return team1_uuid;
    }

    public String getTeam2_uuid() {
        return team2_uuid;
    }

    public void setTeam_uuid(String team_uuid) {
        this.team_uuid = team_uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setFteamID(int fteamID) {
        this.fteamID = fteamID;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public void setPoints1(int points1) {
        this.points1 = points1;
    }

    public void setPoints2(int points2) {
        this.points2 = points2;
    }

    public void setTeam1_uuid(String team1_uuid) {
        this.team1_uuid = team1_uuid;
    }

    public void setTeam2_uuid(String team2_uuid) {
        this.team2_uuid = team2_uuid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TeamandRoundScore(String team_uuid, int fteamID, String team_name, int score1, int score2, int points1, int points2, String team1_uuid, String team2_uuid, String status) {
        this.team_uuid = team_uuid;
        this.fteamID = fteamID;
        this.team_name = team_name;
        this.score1 = score1;
        this.score2 = score2;
        this.points1 = points1;
        this.points2 = points2;
        this.team1_uuid = team1_uuid;
        this.team2_uuid = team2_uuid;
        this.status=status;
    }
}

