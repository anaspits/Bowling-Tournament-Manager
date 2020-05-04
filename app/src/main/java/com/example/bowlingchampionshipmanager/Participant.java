package com.example.bowlingchampionshipmanager;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
//import javax.persistence.*;

@Entity(tableName = "participant",indices= {
        @Index(name="index_participant_uuid", value="participant_uuid", unique=true)
}/*, foreignKeys = {
        @ForeignKey(entity = Team.class,
                parentColumns = "sys_teamID",
                childColumns = "sys_teamID"),
        @ForeignKey(entity = Championship.class,
                parentColumns = "champID",
                childColumns = "champID")
}, indices= {
        @Index(name="index_teamID", value="sys_teamID", unique=true),
        @Index(name="index_champID", value="champID", unique=true)
} */
)
public class Participant implements Serializable {

    //Input attributes
    @PrimaryKey(autoGenerate = true)
    @NonNull
   @ColumnInfo(name="participantID")
    int participantID; //to id pou exei o paiktis stin database

    @ColumnInfo(name="participant_uuid")
    String uuid;

    @ColumnInfo(name="fakeID") //axristo //todo: na dw pws kanw autoIncrement
    @NonNull
    int fakeID; //to id pou exei o paiktis sto sugkekrimeno prwtathlima

    @ColumnInfo(name="first_name")
    String firstName;

    @ColumnInfo(name="last_name")
    String lastName;

    @ColumnInfo(name="sex")
    String sex;

    @ColumnInfo(name="avg") //ka8oliko (dia ola ta paixnidia pou exei pai3ei mexri twra) //todo na kanw float
    int bowlAvg;

    @ColumnInfo(name="hdcp") //todo
    int hdcp;

    @ColumnInfo(name="teamID")
    int teamid; //axristo?

    @ColumnInfo(name="champID") //axristo
    int champid; //to id tou prwtathlimatos sto opoio paizei me tin omada teamid kai tous paiktes teamates


    @TypeConverters(Converters.class)
    @ColumnInfo(name = "start_date")
    private Date start_date;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "end_date")
    private Date end_date;

    @ColumnInfo(name="disable_flag")
    private int disable_flag; //0:energo, 1:anenergo

    @Ignore
    ArrayList<Participant> teamates= new ArrayList<>();

    @Ignore
    //@TypeConverters(Converters.class) //axristo
    ArrayList<Integer> teamatesid= new ArrayList<>();

    //Information to be decided
    @Ignore
    Participant partner;
    //int laneNum; //stretch goal

    //getter methods
    public int getParticipantID() {
        return participantID;
    }
    public int getBowlAvg() {
        return bowlAvg;
    }

    public String getFN(){
        return firstName;
    }

    public String getLN(){
        return lastName;
    }

    public String getFullName(){
        String fullname = getFN() + " " +getLN();
        return fullname;
    }
    public int getFakeID() {
        return fakeID;
    }

    public Participant getPartner(){
        return partner;
    }

    public int getTeamid(){
        return teamid;
    }

    public String getSex() {
        return sex;
    }

    /* @Embedded
         @TypeConverters(Converters.class)
         @SerializedName("getTeamates") */
    public ArrayList<Participant> getTeamates(){
        return teamates;
    }

    public ArrayList<Integer> getTeamatesid(){
        return teamatesid;
    }

    public int getHdcp() {
        return hdcp;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getUuid() {
        return uuid;
    }

    public int getDisable_flag() {
        return disable_flag;
    }

    //setter methods
    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setFakeID(int fakeID) {
        this.fakeID = fakeID;
    }

    public void setBowlAvg(int bowlAvg) {
        this.bowlAvg = bowlAvg;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPartner(Participant partner){
        this.partner = partner;
        //  if (partner!=null){
        teamates.add(partner);
        teamatesid.add(partner.getParticipantID());
//        }
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setTeamatesid(ArrayList<Integer> teamatesid) {
        this.teamatesid = teamatesid;
    }

    public void setTeamid(int teamid) { this.teamid = teamid; }

    public void setHdcp(int hdcp) {
        this.hdcp = hdcp;
    }

    public void setDisable_flag(int disable_flag) {
        this.disable_flag = disable_flag;
    }

    //constructor
    public Participant(int fakeID, String uuid, String firstname, String lastname, int bowlAvg, int team, Date start_date, int hdcp, String sex) {
        //this.participantID = participantID;
        this.fakeID = fakeID; //axristo
        this.uuid = uuid;
        this.firstName = firstname;
        this.lastName = lastname;
        this.bowlAvg = bowlAvg;
        this.teamid = team;
        teamates.add(this);
        teamatesid.add(this.participantID);
        this.start_date=start_date;
        this.hdcp=hdcp;
        this.sex=sex;
    }
    public Participant() {
        super();
    }

    /*Comparator for sorting the list by bowlAvg*/
    public static Comparator<Participant> partBowlAvg = new Comparator<Participant>() {

        public int compare(Participant s1, Participant s2) {

            int ba1 = s1.getBowlAvg();
            int ba2 = s2.getBowlAvg();

            /*For ascending order*/
            return ba1 - ba2;

            /*For descending order*/
            //rollno2-rollno1;
        }
    };

    @Ignore
    @Override
    public String toString() {
        return "[ Name: " + firstName + " " + lastName + ", Average: " + bowlAvg + ", Partner: " + partner.getFN() + " " + partner.getLN() + ", Partner Avg: " + partner.getBowlAvg() + "]";
    }

    /**
     * This method fills an ArrayList of Participants with information input by a csv file.
     *
     *         //For each bowler imported from spreadsheet
     *         //Create a Participant Object with listed characteristics
     *         //Load Participant Object into list
     *
     **/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Participant> createParticipantList(BowlingViewModel bowlingViewModel, InputStream inputStream , String line, String cvsSplitBy, ArrayList<Participant> bowlers)throws IOException {
        //try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
        BufferedReader br =
                new BufferedReader(new InputStreamReader(
                        inputStream));

            String [] input = null;
            String fn = null;
            String ln = null;
            int ba = 0;

            int i = 0;
            while ((line = br.readLine()) != null){
                // use comma as seperator

                //get input
                input = line.split(cvsSplitBy);
//                System.out.println(Arrays.toString(input));

                //get first name
                fn = input[0];

                //get last name
                ln = input[1];

                //get avg
                ba = Integer.parseInt(input[2]);

//                System.out.println("id: " + i + ", FN: " +  fn + ", LN: " + ln + ", Avg: " + ba);
               uuid = UUID.randomUUID().toString();
               Long ts = System.currentTimeMillis();
               String timestamp = ts.toString();
               System.out.println("uuid "+ uuid+" ts "+timestamp);
                //Date date = (Date) Calendar.getInstance().getTime();//fixme
               // System.out.println("time = "+date);
                Participant p = new Participant( i,uuid,fn, ln, ba,0,null, 0, null);
                p.setDisable_flag(0);
                bowlers.add(p);
                bowlingViewModel.insert(p);
   //  Long a=     bowlingViewModel.insert(p);
   //  System.out.println(String.valueOf(a));
//Team_detail td = new Team_detail(i+1, bowlingViewModel.insert(p));
/*                bowlingViewModel.insertP(p).observe((LifecycleOwner) this, new Observer<Long>() {
                    @Override
                    public void onChanged(Long aLong) {
                        System.out.println("aLong = "+aLong);

                        Team_detail td = new Team_detail(p.teamid,aLong);
                        bowlingViewModel.insert(td);
                    }
                }); */

Create1Activity.t_id++; //axristo
                i++;

            }

       // } catch(IOException e){
       //     e.printStackTrace();
       // }

        return bowlers;
    }

    public ArrayList<Participant> generateTeams (ArrayList<Participant> bowlers, int playersPerTeam, BowlingViewModel bowlingViewModel, String champID){
        //Logic for generating teams(pairs)

        //Sort by bowling average
        Collections.sort(bowlers, Participant.partBowlAvg);
        //add 5 points to pair in 2D table of poissible matchings?
        //Associate Participants with friends
        //2D table of friendships -- maybe remove from attributes
        //if [][] = 2, add 2 points to pair in 2D table of poissible matchings?
        int i;
        for(i = 0; i < bowlers.size()/playersPerTeam; i++){
            Participant p1 = bowlers.get(i);
            Participant p2 = bowlers.get(bowlers.size() - i - 1);
            p1.setPartner(p2);
            p2.setPartner(p1);
            //(bowlers.get(i)).setPartner(bowlers.get(bowlers.size() - i - 1));
            //(bowlers.get(bowlers.size() - i - 1)).setPartner(bowlers.get(i));


        }

        for(i = 0; i < bowlers.size()/playersPerTeam;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            p.setTeamid(i+1);
            p.getPartner().setTeamid(i+1);
            String tuuid = UUID.randomUUID().toString();
            System.out.println("team uuid "+tuuid);
            Team t = new Team((i+1),tuuid,null,0);
            bowlingViewModel.insert(t);
            t.setTeammates(p.getTeamates());
            Create1Activity.all_the_teams.add(t);
            Championship_detail cd = new Championship_detail(champID,t.getUuid());
            bowlingViewModel.insert(cd);

            // Championship c = new Championship(fchampID,i+1,0,"created"); //vash 2
            // bowlingViewModel.insert(c);
            System.out.println("i+1: "+ i+1 +" Team " + p.getTeamid() + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");

            Team_detail td = new Team_detail(t.getUuid(),p.getUuid());
            bowlingViewModel.insert(td);
            System.out.println("Td: p " + td.getParticipantID() +" team "+  td.getTeamID());

            Team_detail td2 = new Team_detail(t.getUuid(),p.getPartner().getUuid());
            bowlingViewModel.insert(td2);
            System.out.println("Td2 " + td2.getParticipantID() +" "+  td2.getTeamID());


            //vash 3
          /*  System.out.println("champid = "+champID);
            Championship_detail cd = new Championship_detail(champID,i+1);
            bowlingViewModel.insert(cd);

            System.out.println("i+1: "+ i+1 +" Team " + p.getTeamid() + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
            System.out.println("Cd: ch " + (i+1) +" tema "+  cd.getSys_teamID());

            Team_detail td = new Team_detail(t.sys_teamID,p.getParticipant_uuid());
            bowlingViewModel.insert(td);
            System.out.println("Td: p " + td.getParticipant_uuid() +" team "+  td.getTeamID());

            Team_detail td2 = new Team_detail(t.getSys_teamID(),p.getPartner().getParticipant_uuid());
            bowlingViewModel.insert(td2);
            System.out.println("Td2 " + td2.getParticipant_uuid() +" "+  td2.getTeamID()); */

        }
        return bowlers;
    }

    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String [] args){
        //List to store all Participants
        ArrayList<Participant> bowlers = new ArrayList<Participant>();

        Participant s = new Participant(999,"instance", "instance", 999);

        String csvFile="bowlers-list.csv";
        String line = "";
        String cvsSplitBy = ",";

        s.createParticipantList(bowlers, csvFile, line, cvsSplitBy);

        //Logic for generating teams(pairs)

        //Sort by bowling average
        Collections.sort(bowlers, Participant.partBowlAvg);
        //add 5 points to pair in 2D table of poissible matchings?
        //Associate Participants with friends
        //2D table of friendships -- maybe remove from attributes
        //if [][] = 2, add 2 points to pair in 2D table of poissible matchings?
        int i;
        for(i = 0; i < bowlers.size(); i++){
            (bowlers.get(i)).setPartner(bowlers.get(bowlers.size() - i - 1));
            (bowlers.get(bowlers.size() - i - 1)).setPartner(bowlers.get(i));


        }

        for(i = 0; i < bowlers.size()/2;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            System.out.println("Team " + (i + 1) + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
        }



    } */

}
