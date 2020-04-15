package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

@Entity(tableName = "team",indices= {@Index(name="index_teamUUID", value="team_uuid", unique=true)}/*,foreignKeys = {
        @ForeignKey(entity = Championship.class,
                parentColumns = "champID",
                childColumns = "champID")
}, indices= {
        @Index(name="index_champID", value="champID", unique=true)
}*/)
public class Team implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="sys_teamID")
    int sys_teamID;

    @ColumnInfo(name="team_uuid")
    String uuid;

    @ColumnInfo(name="fteamID")
    @NonNull
    private int fTeamID; //to id pou exei h omada sto sugkekrimeno prwtathlima  //to thelw gia to generateTeams

    @ColumnInfo(name="team_name")
    private String teamName;

    @ColumnInfo(name = "score")
    private int score;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "start_date")
    private Date start_date;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "end_date")
    private Date end_date;

    @ColumnInfo(name = "vs") //axristo
    private int this_vs; // to id tis omadas me thn opoia paizoun antipales sto sugkekrimeno prwtathlima champid//se poio round paizoyn antipales

    @ColumnInfo(name = "champID") //axristo
    private int champid; //todo: gia vash 2 na ginei list me ta champid sta opoia summetexei h omada

    @Ignore
    //@ColumnInfo(name = "round") //axristo
    private int round; //se poio round vriskonte me thn antipalh omada

    @Ignore
    private ArrayList<Team> vs= new ArrayList<>(); //list me tis omades pou antagwnizetai h antistoixh Team, h thesi ths antagwnistrias omadas sth lista einai kai o gyros ston opoio paizoun antipales

    @Ignore
    private ArrayList<Participant> teammates; //dexetai to ArrayList teams, xrisimo!


    //@TypeConverters(Converters.class) // axristo
    //@ColumnInfo(name = "teammatesid")
    @Ignore
    private ArrayList<Integer> teammatesid; //dexetai ta ids twn paiktwn pou paizoun se afti tin omada

    @ColumnInfo(name = "active_flag") //an einai energh, dld paizei se trexon championship
    private int active_flag;

    public int getSys_teamID(){
        return sys_teamID;
    }

    public int getFTeamID(){
        return fTeamID;
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

    public int getActive_flag() { //0:energo, 1: mh energo
        return active_flag;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public ArrayList<Integer> getTeammatesid() {
        return teammatesid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setSys_teamID(int sys_teamID) {
        this.sys_teamID = sys_teamID;
    }

    public void setfTeamID(int fTeamID) {
        this.fTeamID = fTeamID;
    }

    public void setVs(ArrayList<Team> vs) {
        this.vs = vs;
    }

    public void setTeammates(ArrayList<Participant> teammates) {
        this.teammates = teammates;
    }

    public void setTeammatesid(ArrayList<Integer> teammatesid) {
        this.teammatesid = teammatesid;
    }

    public void setTeamName(String name) {
        teamName = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setActive_flag(int active_flag) {
        this.active_flag = active_flag; //0 energo, 1 teleiwse // todo mhpw na to valw sto champ_detail?
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

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Team(int fTeamID, String uuid, String teamName, int score) {
        // public Team(int fTeamID, String teamName, ArrayList teammates, int score) {
        this.fTeamID = fTeamID;
        this.uuid = uuid;
        if (teamName!=null) {
            this.teamName = teamName;
        } else {
            this.teamName= String.valueOf(this.fTeamID); //todo: to name na apoteleitai apo champid kai fteamid
        }
        //this.teammates = teammates;
        this.score=score;
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
                if (t1.getFTeamID()!=this.fTeamID) {

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
        return "[ ID: " + sys_teamID + ", Name: " + teamName  + "]";
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
