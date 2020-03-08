package com.example.bowlingchampionshipmanager;

import java.io.Serializable;
import java.util.ArrayList;


public class Team implements Serializable {
    private int teamID;
    private String teamName;
    private ArrayList<Participant> teamates; //dexetai to ArrayList teams
    private int score;
    private ArrayList<Team> vs= new ArrayList<>(); //list me tis omades pou antagwnizetai h antistoixh Team, h thesi ths antagwnistrias omadas sth lista einai kai o gyros ston opoio paizoun antipales

    public Team(int teamID, String teamName, ArrayList teamates, int score) {
        this.teamID = teamID;
        if (teamName!=null) {
            this.teamName = teamName;
        } else {
            this.teamName= String.valueOf(this.teamID);
        }
        this.teamates = teamates;
        this.score=score;
    }

    public int getTeamID(){
        return teamID;
    }
    public String getTeamName(){
        return teamName;
    }
    public ArrayList<Participant> getTeamates(){
        return teamates;
    }
    public int getScore(){return score;}

    public void setTeamName(String name) {
        teamName = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void roundRobin(int teams, int round, ArrayList<Team> all_the_teams) {
        if (((teams%2 != 0) && (round != teams - 1))||(teams <= 0))
            throw new IllegalArgumentException();
        int[] cycle = new int[teams];
        int n = teams /2;
        for (int i = 0; i < n; i++) {
            cycle[i] = i + 1;
            cycle[teams - i - 1] = cycle[i] + n;
        }

        for(int d = 1; d <= round; d++) {

            //System.out.println(String.format("Round %d", d));
            for (int i = 0; i < n; i++) {
                Team t1 = all_the_teams.get(i); //gia thn i omada
                if (t1.getTeamID()!=this.teamID) {

                    //System.out.println(String.format("teamid %d - teamid %d",cycle[i],cycle[teams - i - 1]));
                }
            }
            int temp = cycle[1];
            for (int i = 1; i < teams - 1; i++) {
                int pr = cycle[i+1];
                cycle[i+1] = temp;
                temp = pr;
            }
            cycle[1] = temp;
        }
    }


   /* @Override
    public String toString() {
        return "[ ID: " + teamID + ", Name: " + teamName  + "]";
    } */

   /* public StringBuilder displayTeams(){
        int i;
        StringBuilder display_teams= new StringBuilder();
        for (i=0; i<teamates.size();i++) {
            ArrayList<Participant> temp = teamates.get(i);

            display_teams.append("\n"+"Team " + (i+1) +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                display_teams.append(temp.get(j).getFN() +"  ");
            }

        }
        return display_teams;
    } */
}
