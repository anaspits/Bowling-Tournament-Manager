package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "team")
public class Team implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int teamID;

    @ColumnInfo(name="fakeID")
    @NonNull
    private int fteamID; //to id pou exei h omada sto sugkekrimeno prwtathlima

    @ColumnInfo(name="team_name")
    private String teamName;

    @ColumnInfo(name = "score")
    private int score;


    @ColumnInfo(name = "vs")
    private int this_vs; // to id tis omadas me thn opoia paizoun antipales sto sugkekrimeno prwtathlima champid

    @ColumnInfo(name = "champID")
    private int champid; //se poio round paizoyn antipales

    @ColumnInfo(name = "round")
    private int round; //se poio round vriskonte me thn antipalh omada

    @Ignore
    private ArrayList<Team> vs= new ArrayList<>(); //list me tis omades pou antagwnizetai h antistoixh Team, h thesi ths antagwnistrias omadas sth lista einai kai o gyros ston opoio paizoun antipales

    @Ignore
    private ArrayList<Participant> teammates; //dexetai to ArrayList teams



    public Team(int fteamID, String teamName, ArrayList teammates, int score) {
        this.fteamID = fteamID;
        if (teamName!=null) {
            this.teamName = teamName;
        } else {
            this.teamName= String.valueOf(this.fteamID);
        }
        this.teammates = teammates;
        this.score=score;
    }

    public int getTeamID(){
        return teamID;
    }

    public int getfTeamID(){
        return fteamID;
    }

    public String getTeamName(){
        return teamName;
    }
    public ArrayList<Participant> getTeammates(){
        return teammates;
    }
    public int getScore(){return score;}
    public int getThis_vs() {
        return this_vs;
    }

    public int getChampid() {
        return champid;
    }

    public int getRound() {
        return round;
    }

    public void setFteamID(int fteamID) {
        this.fteamID = fteamID;
    }
    public void setTeamName(String name) {
        teamName = name;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void setThis_vs(int this_vs) {
        this.this_vs = this_vs;
    }

    public void setChampid(int champid) {
        this.champid = champid;
    }

    public void setRound(int round) {
        this.round = round;
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
                if (t1.getfTeamID()!=this.fteamID) {

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
