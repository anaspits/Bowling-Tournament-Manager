package com.example.bowlingchampionshipmanager;

import java.io.Serializable;
import java.util.ArrayList;


public class Team implements Serializable {
    int teamID;
    String teamName;
    ArrayList<Participant> teamates;

    public Team(int teamID, String teamName, Participant teamates) {
        this.teamID = teamID;
        this.teamName = teamName;
        //this.teamates = teamates;
    }

    public String getTeamNameName(){
        return teamName;
    }
    public void setTeamName(String name) {
        teamName = name;
    }
}
