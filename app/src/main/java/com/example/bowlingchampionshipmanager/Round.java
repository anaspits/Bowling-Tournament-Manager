package com.example.bowlingchampionshipmanager;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
//import java.sql.Date;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "round",indices= {
        @Index(name="index_round_uuid", value="round_uuid", unique=true),
        @Index(name="index_r_champuuid", value="champ_uuid")},foreignKeys = {
        @ForeignKey(entity = Championship.class,
                parentColumns = "champ_uuid",
                childColumns = "champ_uuid",
                onDelete = CASCADE)/*,
        @ForeignKey(entity = Team.class,
        parentColumns = "sys_teamID",
        childColumns = "sys_teamID")
},indices= {
        @Index(name="index_champID", value="champID", unique=true),
        @Index(name="index_teamID", value="sys_teamID", unique=true)
*/})
public class Round implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="sys_roundID")
    private int roundid;

    @ColumnInfo(name="round_uuid")
    private String rounduuid;

    @ColumnInfo(name="froundid")
    private int froundid;

    @ColumnInfo(name="fchampID")
    @NonNull
    private int champid;

    @ColumnInfo(name="champ_uuid")
    @NonNull
    private String champuuid;

    @ColumnInfo(name="team1ID") //axristo?
    private int team1ID;

    @ColumnInfo(name="team2ID") //axristo?
    private int team2ID;

    @ColumnInfo(name="team1UUID")
    private String team1UUID;

    @ColumnInfo(name="team2UUID")
    private String team2UUID;

    @ColumnInfo(name="score1")
    private int score1;

    @ColumnInfo(name="score2")
    private int score2;

    @ColumnInfo(name="points1")
    private int points1;

    @ColumnInfo(name="points2")
    private int points2;

    @ColumnInfo(name="lane1")
    private int lane1;

    @ColumnInfo(name="lane2")
    private int lane2;

    @ColumnInfo(name="status")
    private String status; //H na to kanw int? //done, current, next

    @TypeConverters(Converters.class)
    @ColumnInfo(name="date")
    private Date date;

    public int getChampid() {
        return champid;
    }

    @NonNull
    public String getChampuuid() {
        return champuuid;
    }

    public String getRounduuid() {
        return rounduuid;
    }

    public String getStatus() {
        return status;
    }

    public String getTeam1UUID() {
        return team1UUID;
    }

    public String getTeam2UUID() {
        return team2UUID;
    }

    public int getTeam1ID() {
        return team1ID;
    }

    public int getRoundid() {
        return roundid;
    }

    public int getTeam2ID() {
        return team2ID;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public Date getDate() {
        return date;
    }

    public int getFroundid() {
        return froundid;
    }

    public int getPoints1() {
        return points1;
    }

    public int getPoints2() {
        return points2;
    }

    public int getLane1() {
        return lane1;
    }

    public int getLane2() {
        return lane2;
    }

    public void setChampid(int champid) {
        this.champid = champid;
    }

    public void setRounduuid(String rounduuid) {
        this.rounduuid = rounduuid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChampuuid(@NonNull String champuuid) {
        this.champuuid = champuuid;
    }

    public void setTeam1UUID(String team1UUID) {
        this.team1UUID = team1UUID;
    }

    public void setTeam2UUID(String team2UUID) {
        this.team2UUID = team2UUID;
    }

    public void setTeam1ID(int teamID) {
        this.team1ID = teamID;
    }

    public void setRoundid(int round) {
        this.roundid = round;
    }

    public void setFroundid(int froundid) {
        this.froundid = froundid;
    }

    public void setTeam2ID(int team2ID) {
        this.team2ID = team2ID;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPoints1(int points1) {
        this.points1 = points1;
    }

    public void setPoints2(int points2) {
        this.points2 = points2;
    }

    public void setLane1(int lane1) {
        this.lane1 = lane1;
    }

    public void setLane2(int lane2) {
        this.lane2 = lane2;
    }

    public Round(String rounduuid, int froundid, int team1ID, int team2ID, String champuuid, String team1UUID, String team2UUID, int score1, int score2, String status) {
        this.rounduuid=rounduuid;
        this.froundid = froundid;
        this.team1ID = team1ID;
        this.team2ID = team2ID;
        this.team1UUID = team1UUID;
        this.team2UUID = team2UUID;
        this.champuuid = champuuid;
        this.score1=score1;
        this.score2=score2;
        this.status=status;
    }

    //todo test it
    void roundRobin(int teams, int round, BowlingViewModel bowlingViewModel, ArrayList<Team> all_the_teams, TextView details, List<TeammatesTuple> playersandteams) {
        //temp2=new Team[round][teams];
        if (((teams%2 != 0) && (round != teams - 1))||(teams <= 0))
            throw new IllegalArgumentException(); //todo na dw ti ginetai me to roundrobin monwn omadwn px 3 omades
        int[] cycle = new int[teams];
        int n = teams /2;
        for (int i = 0; i < n; i++) {
            cycle[i] = i + 1;
            cycle[teams - i - 1] = cycle[i] + n;

        }

        //int counter2=0; //gia thn swsth emfanisei twn antipalwn omadwn gia thn temp3
        for(int d = 1; d <= round; d++) {
            details.append(" Round "+ d +": \n");

            //System.out.println(String.format("Round %d", d));
            for (int i = 0; i < n; i++) {
                ArrayList<Team> temp1 = new ArrayList<>(); //voithitiki lista gia thn vs
                //int counter=0; //gia thn 2h omada gia ton temp2
                for (int j=0; j<all_the_teams.size();j++){ //vriskw poia omada exetazoume
                    if (all_the_teams.get(j).getFTeamID()== cycle[i]){
                        Team t1 = all_the_teams.get(j);
                        System.out.println("t1 teammates " + t1.getTeammates().size());
                        temp1.add(t1); //kai tin pernaw sthn lista temp1

                        //temp2[i][counter]=t1;
                        //counter++;

                        //temp3.add(t1);
                    }
                }
                for (int j=0; j<all_the_teams.size();j++){ //vriskw tin alli omada poy tha einai antipalos
                    if (all_the_teams.get(j).getFTeamID()== cycle[teams - i - 1]){
                        Team t2 = all_the_teams.get(j);
                        System.out.println("t2 teammates " + t2.getTeammates().size());
                        temp1.add(t2); //tin pernaw kai afti sti lista gia na exw mia lista apo antipales omades

                        //temp2[i][counter]=t2;
                        //counter++;

                        //temp3.add(t2);
                    }
                }

                //vs.add(temp1); //pernaw tin lista twn 2 antipalwn omadwn stin lista vs, opou h thesi tous einai o gyros ston opoio paizoun+1
                details.append(" Team "+temp1.get(0).getFTeamID()+ " vs Team "+temp1.get(1).getFTeamID()+"\n");

                String ruuid= UUID.randomUUID().toString();
                Round r = new Round (ruuid,d,temp1.get(0).getFTeamID(), temp1.get(1).getFTeamID() , champuuid, temp1.get(0).getUuid(),  temp1.get(1).getUuid(),temp1.get(0).getScore(),temp1.get(1).getScore(), "");
                if (d==1){
                    // r.setStatus("current");
                    r.setStatus("next");
                    System.out.println("Round d= "+r.getFroundid()+" t1: "+r.getTeam1ID()+" t2: " + r.getTeam2ID()+" stat "+r.getStatus() +" chid "+ champuuid);
                } else if (d==round){
                    r.setStatus("last");
                    System.out.println("Round d= "+r.getFroundid()+" t1: "+r.getTeam1ID()+" t2: " + r.getTeam2ID()+" stat "+r.getStatus());
                } else {
                    r.setStatus("next");
                    System.out.println("Round d= "+r.getFroundid()+" t1: "+r.getTeam1ID()+" t2: " + r.getTeam2ID()+" stat "+r.getStatus());
                }
                bowlingViewModel.insert(r);
                //gia ka8e paikth ths ka8e omadas vazw to rd
                for(int t=0;t<temp1.size();t++) { //gia ka8e omada autou tou gurou
                    ArrayList<Participant> pa =temp1.get(t).getTeammates(); //pairnw tous paiktes ths omadas auths
                    if(pa!=null) { //an o user ekopse kai sunexise to create
                        for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                            System.out.println("me pa.teamates");
                            Round_detail rd = new Round_detail(ruuid, pa.get(p).getUuid(), 0, 0, 0, pa.get(p).getHdcp(), 0, champuuid, r.getFroundid(), Calendar.getInstance().getTime()); //ftiaxnw to rd
                            bowlingViewModel.insert(rd);
                            System.out.println("Rd round" + r.getFroundid() + " partici " + pa.get(p).getFirstName() + " " + pa.get(p).getUuid());

                        }
                    }else {
                        for (int tm = 0; tm < playersandteams.size();tm++) { //psaxnw na vrw thn omada pou meletame sto playersandteams
                            if (playersandteams.get(tm).getC().getUuid().equals(temp1.get(t).getUuid())) { //gia na parw tous paiktes ths omadas afths
                                List<Participant> pa2 = playersandteams.get(tm).getT();
                                for (int p = 0; p < pa2.size(); p++) {
                                    System.out.println("me pa2 k playersandteams");
                                    Round_detail rd = new Round_detail(ruuid, pa2.get(p).getUuid(), 0, 0, 0, pa2.get(p).getHdcp(), 0, champuuid, r.getFroundid(), Calendar.getInstance().getTime()); //ftiaxnw to rd
                                    //rd.setScore(pa.get(p).getBowlAvg());
                                    bowlingViewModel.insert(rd);
                                    System.out.println("Rd round" + r.getFroundid() + " partici " + pa2.get(p).getFirstName() + " " + pa2.get(p).getUuid());
                                }
                                System.out.println("break");
                                break;
                            }
                        }
                    }
                }
                //emfanish me temp2
                //details.append(" Team "+temp2[i][0].getFTeamID()+ " vs Team "+temp2[i][1].getFTeamID()+"\n");

                //emfanish me temp3
                //details.append(" Team "+temp3.get(counter2).getFTeamID()+ " vs Team "+temp3.get(counter2+1).getFTeamID()+"\n");
                //counter2=counter2+2;

                //details.append("Prepei Team "+cycle[i]+ " vs Team "+cycle[teams - i - 1]+"\n");
                //System.out.println(String.format("teamid %d - teamid %d",cycle[i],cycle[teams - i - 1]));
            }
            details.append("\n");
            int temp = cycle[1];
            for (int i = 1; i < teams - 1; i++) {
                int pr = cycle[i+1];
                cycle[i+1] = temp;
                temp = pr;
            }
            cycle[1] = temp;
        }
    }
public List<Round> assignLanes(ArrayList<Round> rounds, int lanes, int r, BowlingViewModel bowlingViewModel, ArrayList<Team> all_the_teams){
        if (((lanes%2 != 0) && (r != lanes - 1))||(lanes <= 0))
            throw new IllegalArgumentException();
        int[] cycle = new int[lanes];
        int n = lanes /2;
    for (int i = 0; i < n; i++) {
        cycle[i] = i + 1;
        cycle[lanes - i - 1] = cycle[i] + n;
    }

        String[] pairoflanes = new String[n];
    for (int i = 0; i < lanes; i++) {
        if(i!=lanes-1) {
            pairoflanes[i] = (i + "-" + (i + 1));
        }
    }

//8a parw gia ka8e omada ta rounds ths
    //gia ka8e round ths omadas 8a vazw kainourio lane
        return rounds;
}

}
