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
//import java.sql.Date;
import java.time.OffsetDateTime;
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
    @NonNull
    String uuid;

    @ColumnInfo(name="fakeID") //axristo //todo: na dw pws kanw autoIncrement
    int fakeID; //to id pou exei o paiktis sto sugkekrimeno prwtathlima

    @ColumnInfo(name="first_name")
    String firstName;

    @ColumnInfo(name="last_name")
    String lastName;

    @ColumnInfo(name="sex")
    String sex; //m,f

    @ColumnInfo(name="avg") //ka8oliko (dia ola ta paixnidia pou exei pai3ei mexri twra) //todo na kanw float kai na einai klasma!
    int bowlAvg;

    @ColumnInfo(name="hdcp") //todo
    int hdcp;

    @ColumnInfo(name="total_games")
    private int total_games;

    @ColumnInfo(name="teamID")
    int teamid; //axristo?

    @ColumnInfo(name="champID") //axristo
    int champid; //to id tou prwtathlimatos sto opoio paizei me tin omada teamid kai tous paiktes teamates


    @TypeConverters(Converters.class)
    @ColumnInfo(name = "created_at")
    private Date created_at;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "updated_at")
    private Date updated_at;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "disabled_at_date")
    private Date disabled_at_date;

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

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFullName(){
        String fullname =  getLastName()+ " " +getFirstName() ;
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

    public Date getCreated_at() {
        return created_at;
    }

    public Date getDisabled_at_date() {
        return disabled_at_date;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public String getUuid() {
        return uuid;
    }

    public int getTotal_games() {
        return total_games;
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

    public void setTotal_games(int total_games) {
        this.total_games = total_games;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setDisabled_at_date(Date disabled_at_date) {
        this.disabled_at_date = disabled_at_date;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
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
    public Participant(int fakeID, String uuid, String firstname, String lastname, int bowlAvg, int team, Date created_at, int hdcp, String sex, int disable_flag) {
        //this.participantID = participantID;
        this.fakeID = fakeID; //axristo
        this.uuid = uuid;
        this.firstName = firstname;
        this.lastName = lastname;
        this.bowlAvg = bowlAvg;
        this.teamid = team;
        teamates.add(this);
        teamatesid.add(this.participantID);
        this.created_at =created_at;
        this.hdcp=hdcp;
        this.sex=sex;
        this.disable_flag=disable_flag;
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
        return "[ Name: " + firstName + " " + lastName + ", "+sex+", Average: " + bowlAvg + ", Partner: " + partner.getFirstName() + " " + partner.getLastName() + ", Partner Avg: " + partner.getBowlAvg() + "]";
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
            String sex = null;
            int ba = 0, hdcp=0;

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

                //get last name
                sex = input[2];

                //get avg
                ba = Integer.parseInt(input[3]);

                //get hdcp
                hdcp = Integer.parseInt(input[4]);

//                System.out.println("id: " + i + ", FN: " +  fn + ", LN: " + ln + ", Avg: " + ba);
               uuid = UUID.randomUUID().toString();
               //date
              /* Long ts = System.currentTimeMillis();
               String timestamp = ts.toString();
               System.out.println("uuid "+ uuid+" ts "+timestamp);*/
                Date date =  Calendar.getInstance().getTime(); //doulevei
                System.out.println("date = "+date);
               /* DateFormat df =new SimpleDateFormat(Constants.MILLIS_I18N);
                System.out.println(" df "+df.toString());
                DateFormat df2 =new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                System.out.println(" df "+df2.toString());*/
                //H'
                OffsetDateTime offsetDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    offsetDateTime = OffsetDateTime.now();
                }
                System.out.println(" off "+String.valueOf(offsetDateTime));


                Participant p = new Participant( i,uuid,fn, ln, ba,0,date, hdcp, sex, 0);
                p.setDisable_flag(0);
                bowlers.add(p);
                //bowlingViewModel.insert(p);

                System.out.println("particiapant date ="+p.getCreated_at());
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
//TODO NA ELEGXW AN oi playersperteam me tous bowlers symvadizoun se ari8mo (px oxi playersperteam>bowlers)
        //Sort by bowling average
        if(bowlers.size()%playersPerTeam!=0){ //an einai peritos arithmos pros8eto to Blind gia na ginei zugos
            do {
                Participant p = new Participant(0, "blind", "BLIND", "BLIND", 0, 0, null, 0, "", 1);
                bowlers.add(p);

            }while (bowlers.size()%playersPerTeam!=0);
        }
        Collections.sort(bowlers, Participant.partBowlAvg);
        //add 5 points to pair in 2D table of poissible matchings?
        //Associate Participants with friends
        //2D table of friendships -- maybe remove from attributes
        //if [][] = 2, add 2 points to pair in 2D table of poissible matchings?

for (int i=0;i<bowlers.size();i++){
    System.out.println(i+" bowl "+bowlers.get(i).getFullName()+" "+bowlers.get(i).getBowlAvg());
}
        int i;
        if(playersPerTeam==1) {
            for(i = 0; i < bowlers.size();i++) {
                Participant p = bowlers.get(i);
                p.setTeamid(i + 1);
                String tuuid = UUID.randomUUID().toString();
                System.out.println("team uuid " + tuuid);
                Team t = new Team((i + 1), tuuid, null, 0);
                Date date = Calendar.getInstance().getTime();
                t.setStart_date(date);
                //bowlingViewModel.insert(t);
                ArrayList<Participant> tmates= new ArrayList<>();
                tmates.add(p);
                t.setTeammates(tmates);
                Create1Activity.all_the_teams.add(t);
                Championship_detail cd = new Championship_detail(champID, t.getUuid(), Calendar.getInstance().getTime());
                //bowlingViewModel.insert(cd);

                String tduuid = UUID.randomUUID().toString();
                Team_detail td = new Team_detail(t.getUuid(), p.getUuid(), Calendar.getInstance().getTime(),tduuid );
                //bowlingViewModel.insert(td);
                System.out.println("Td: p " + td.getParticipantID() + " team " + td.getTeamID());

            }
        }else if(playersPerTeam==2) {//an playersPerTea=2
            for (i = 0; i < bowlers.size() / playersPerTeam; i++) {
                Participant p1 = bowlers.get(i);
                Participant p2 = bowlers.get(bowlers.size() - i - 1);
                System.out.println("p1 "+p1.getFullName()+" "+p1.getBowlAvg()+" p2 "+p2.getFullName()+" "+p2.getBowlAvg());
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
            Date date =  Calendar.getInstance().getTime();
            t.setStart_date(date);
            //bowlingViewModel.insert(t);
            t.setTeammates(p.getTeamates());
            Create1Activity.all_the_teams.add(t);
            Championship_detail cd = new Championship_detail(champID,t.getUuid(),Calendar.getInstance().getTime() );
            //bowlingViewModel.insert(cd);

          //vash 2  // Championship c = new Championship(fchampID,i+1,0,"created"); //vash 2
            // bowlingViewModel.insert(c);
            System.out.println("i+1: "+ i+1 +" Team " + p.getTeamid() + ": " + p.getFirstName() + " " + p.getLastName() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFirstName() + " " + p.getPartner().getLastName() + " (Avg: " + p.getPartner().getBowlAvg() + " )");

            Team_detail td = new Team_detail(t.getUuid(),p.getUuid(),Calendar.getInstance().getTime(),UUID.randomUUID().toString() );
            //bowlingViewModel.insert(td);
            System.out.println("Td: p " + td.getParticipantID() +" team "+  td.getTeamID());

            Team_detail td2 = new Team_detail(t.getUuid(),p.getPartner().getUuid(),Calendar.getInstance().getTime(), UUID.randomUUID().toString());
            //bowlingViewModel.insert(td2);
            System.out.println("Td2 " + td2.getParticipantID() +" "+  td2.getTeamID());


            //vash 3
          /*  System.out.println("champid = "+champID);
            Championship_detail cd = new Championship_detail(champID,i+1);
            bowlingViewModel.insert(cd);

            System.out.println("i+1: "+ i+1 +" Team " + p.getTeamid() + ": " + p.getFirstName() + " " + p.getLastName() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFirstName() + " " + p.getPartner().getLastName() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
            System.out.println("Cd: ch " + (i+1) +" tema "+  cd.getSys_teamID());

            Team_detail td = new Team_detail(t.sys_teamID,p.getParticipant_uuid());
            bowlingViewModel.insert(td);
            System.out.println("Td: p " + td.getParticipant_uuid() +" team "+  td.getTeamID());

            Team_detail td2 = new Team_detail(t.getSys_teamID(),p.getPartner().getParticipant_uuid());
            bowlingViewModel.insert(td2);
            System.out.println("Td2 " + td2.getParticipant_uuid() +" "+  td2.getTeamID()); */

        }
        }else {
            //an playersPerTea>2
            ArrayList<Team> teams = new ArrayList<>();
            int counter1=0, counter2=1;
            for (i = 0; i < bowlers.size() / playersPerTeam; i++) { //gia oles tis omades
                System.out.println("team No "+i);
                ArrayList<Participant> tmates = new ArrayList<>();
                for (int j = 0; j < playersPerTeam; j++) {//vazw osous paiktes osoi oi playersPerTeam
                    if (j%2==0) {
                    /*    Participant p1; //gia na vazei prwta ton teleftaio alla den leitourgei kai polu swsta
                        if(j==0) {
                            p1 = bowlers.get(bowlers.size() - i - 1);
                            tmates.add(p1);
                        }else {
                            p1 = bowlers.get(bowlers.size() - i - j);
                            tmates.add(p1);
                        }*/
                        Participant p1=bowlers.get(counter1);
                        counter1++;
                        tmates.add(p1);
                        System.out.println(j+" "+p1.getFullName()+" "+p1.getBowlAvg()+" "+p1.getSex());
                    }else {
                       // Participant p2 = bowlers.get(i+j); gia na vazei meta, ton prwto
                        Participant p2= bowlers.get(bowlers.size() - counter2);
                        counter2++;
                        tmates.add(p2);
                        System.out.println(j+" "+p2.getFullName()+" "+p2.getBowlAvg()+" "+p2.getSex());
                    }
                }
                String tuuid = UUID.randomUUID().toString();
                System.out.println("team uuid "+tuuid);
                Team t = new Team((i+1),tuuid,null,0);
                Date date =  Calendar.getInstance().getTime();
                t.setStart_date(date);
                t.setTeammates(tmates);
                //bowlingViewModel.insert(t);
                teams.add(t);
                System.out.println(" teamates "+tmates.get(0).getFullName());
                System.out.println(" team teamates "+t.getTeammates().get(0).getFullName());
                Create1Activity.all_the_teams.add(t);
                Championship_detail cd = new Championship_detail(champID,t.getUuid(),Calendar.getInstance().getTime() );
                //bowlingViewModel.insert(cd);

                //td
                for (int j = 0; j < playersPerTeam; j++) {//vazw ta td gia ka8e paikth ths omadas
                    Participant p = t.getTeammates().get(j);
                    System.out.println("i+1: " + (i + 1) + " Team " + teams.get(i).getFTeamID() + ": " + p.getFirstName() + " " + p.getLastName() + " Avg: " + p.getBowlAvg() );

                    Team_detail td = new Team_detail(t.getUuid(), p.getUuid(),Calendar.getInstance().getTime(), UUID.randomUUID().toString());
                   // bowlingViewModel.insert(td);
                    System.out.println("Td: p " + td.getParticipantID() + " team " + td.getTeamID());

                }
            }
            //td
     /*       for (i = 0; i < bowlers.size() / playersPerTeam; i++) { //gia oles tis omades
                Team t = teams.get(i);
                System.out.println("Team "+ t.getFTeamID());
                for (int j = 0; j < playersPerTeam; j++) {//vazw ta td gia ka8e paikth ths omadas
                    Participant p = t.getTeammates().get(j);
                    System.out.println("i+1: " + (i + 1) + " Team " + teams.get(i).getFTeamID() + ": " + p.getFirstName() + " " + p.getLastName() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFirstName() + " " + p.getPartner().getLastName() + " (Avg: " + p.getPartner().getBowlAvg() + " )");

                    Team_detail td = new Team_detail(t.getUuid(), p.getUuid());
                    bowlingViewModel.insert(td);
                    System.out.println("Td: p " + td.getParticipantID() + " team " + td.getTeamID());

                    Team_detail td2 = new Team_detail(t.getUuid(), p.getPartner().getUuid());
                    bowlingViewModel.insert(td2);
                    System.out.println("Td2 " + td2.getParticipantID() + " " + td2.getTeamID());
                }
            } */
        }
        return bowlers;
    }

    //ready teams
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Participant> importReadyTeams(BowlingViewModel bowlingViewModel, InputStream inputStream , String line, String cvsSplitBy, ArrayList<Participant> bowlers, String champID)throws IOException {
        //try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
        BufferedReader br =
                new BufferedReader(new InputStreamReader(
                        inputStream));

        String[] input = null;
        String fn = null;
        String ln = null;
        String sex = null;
        int ba = 0;
        int hdcp = 0;

        int i = 0, teamcounter=1;
        while ((line = br.readLine()) != null) {
            // use comma as seperator
            //get input
            input = line.split(cvsSplitBy);
//                System.out.println(Arrays.toString(input));
            ArrayList<Participant> tmates = new ArrayList<>();
            for (int j = 0; j < input.length; j+=5){
                //get first name
                fn = input[j+0];

            //get last name
            ln = input[j+1];

            //get last name
            sex = input[j+2];

            //get avg
            ba = Integer.parseInt(input[j+3]);

            //get avg
            hdcp = Integer.parseInt(input[j+4]);

//                System.out.println("id: " + i + ", FN: " +  fn + ", LN: " + ln + ", Avg: " + ba);
                if (fn.equals("BLIND") || ln.equals("BLIND")){
                    uuid = "blind";
                    Participant p = new Participant(0, "blind", "BLIND", "", 0, 0, null, 0, "", 1);
                    bowlers.add(p);
                    tmates.add(p);
                }else {
                    uuid = UUID.randomUUID().toString();
                    Long ts = System.currentTimeMillis();
                    String timestamp = ts.toString();
                    System.out.println("uuid " + uuid + " ts " + timestamp);
                    Date date =  Calendar.getInstance().getTime();
                    Participant p = new Participant(i, uuid, fn, ln, ba, 0, date, hdcp, sex, 0);
                    bowlers.add(p);
                    tmates.add(p);
                    //bowlingViewModel.insert(p);

                    Create1Activity.t_id++; //axristo
                    i++;
                }
        }
            //team
            String tuuid = UUID.randomUUID().toString();
            System.out.println("team uuid "+tuuid);
            Team t = new Team(teamcounter,tuuid,null,0);
            Date date =  Calendar.getInstance().getTime();
            t.setStart_date(date);
            teamcounter++;
            //bowlingViewModel.insert(t);
            t.setTeammates(tmates);
            Create1Activity.all_the_teams.add(t);
            Championship_detail cd = new Championship_detail(champID,t.getUuid(), Calendar.getInstance().getTime());
           // bowlingViewModel.insert(cd);

            //td
            for (int k = 0; k < tmates.size(); k++) {//vazw ta td gia ka8e paikth ths omadas
                System.out.println("tmates:  Team " + t.getFTeamID()+" p "+tmates.get(k).getFullName());
                System.out.println("team teamates:  Team " + t.getFTeamID()+" p "+t.getTeammates().get(k).getFullName());
                Participant p1 = t.getTeammates().get(k);
                System.out.println("i+1: " + (k + 1) + " Team " + t.getFTeamID() + ": " + p1.getFirstName() + " " + p1.getLastName() + " Avg: " + p1.getBowlAvg() );

                Team_detail td = new Team_detail(t.getUuid(), p1.getUuid(),Calendar.getInstance().getTime(),UUID.randomUUID().toString() );
                //bowlingViewModel.insert(td);
                System.out.println("Td: p " + td.getParticipantID() + " team " + td.getTeamID());

            }

        }

        // } catch(IOException e){
        //     e.printStackTrace();
        // }

        return bowlers;
    }

    public void insertAllToDatabase(BowlingViewModel bowlingViewModel, ArrayList<Participant> bowlers,  ArrayList<Team> all_the_teams, Championship ch, Boolean singleflag,ArrayList<Participant> existing_players) {

        //players
        for (int i = 0; i < bowlers.size(); i++) {
            if (bowlers.get(i).getUuid().equals("blind") == false) {
                bowlingViewModel.insert(bowlers.get(i));
            }
        }

        for (int i = 0; i < existing_players.size(); i++) {
            if (existing_players.get(i).getUuid().equals("blind") == false) {
                bowlingViewModel.update(existing_players.get(i));
            }
        }

            //teams
            for (int j = 0; j < all_the_teams.size(); j++) {
                bowlingViewModel.insert(all_the_teams.get(j));
                //cd
                Championship_detail cd = new Championship_detail(ch.getUuid(), all_the_teams.get(j).getUuid(), Calendar.getInstance().getTime());
                bowlingViewModel.insert(cd);
//td
                Team t = all_the_teams.get(j);
                for (int k = 0; k < t.getTeammates().size(); k++) {//vazw ta td gia ka8e paikth ths omadas
                    System.out.println("team teamates:  Team " + t.getFTeamID() + " p " + t.getTeammates().get(k).getFullName());
                    Participant p1 = t.getTeammates().get(k);
                    System.out.println("i+1: " + (k + 1) + " Team " + t.getFTeamID() + ": " + p1.getFirstName() + " " + p1.getLastName() + " Avg: " + p1.getBowlAvg());

                    Team_detail td = new Team_detail(t.getUuid(), p1.getUuid(), Calendar.getInstance().getTime(),UUID.randomUUID().toString() );
                    bowlingViewModel.insert(td);
                    System.out.println("Td: p " + td.getParticipantID() + " team " + td.getTeamID());

                }

        }
    }

    public int calculateHDCPofPlayer(Participant player, float avg, Championship championship, BowlingViewModel bowlingViewModel){
        //todo na rwthsw
     //   for (int i = 0; i <players.size(); i++) {
          //  System.out.println("hdcp paikths " + i + " " + players.get(i).getFullName() + " id " + players.get(i).getUuid() + " hdcp " + players.get(i).getHdcp());
        System.out.println("hdcp paikths " + player.getFullName() + " id " + player.getUuid() + " hdcp " + player.getHdcp()+" avg "+avg);

       /*     float avg=0;
            int games=0;
            for(int r=0;r<rd.size();r++){
                System.out.println("Prin paikths " + player.getFirstName() + " rounduuid "+rd.get(r).getRound_uuid()+ " r.score "+rd.get(r).getScore());
                System.out.println( " avg "+ avg);
                avg+= rd.get(r).getScore();
                games+=rd.get(r).getGames();
                System.out.println("Mesa paikths " +  player.getFirstName() +" r.score "+rd.get(r).getScore()+" bowlavg "+" avg "+ avg);

            }
            if(games!=0) {
                avg = avg /games;
            } */
       int x=0;
        System.out.println("1 hdcp champ.tav " + championship.getHdcp_tav());
        if (player.getUuid().equals("blind") && championship.getHdcp_less() != 0) { //an einai omada me ligoterous paiktes
            x = championship.getHdcp_less();
            System.out.println("1 hdcp less");
        } else if (championship.getHdcp_tav() != 0) {
            x=championship.getHdcp_tav();
            System.out.println("1 hdcp tav " + championship.getHdcp_tav());
        }
            double hdcp =  ((x - avg) * (championship.getHdcp_factor()*0.01));
            player.setHdcp((int)hdcp);  //edw??
        System.out.println("1 hdcp x (" + x +" - "+avg+")*("+championship.getHdcp_factor()+"/100)= "+(x-avg)+" * "+(championship.getHdcp_factor()*0.01)+"="+hdcp+" int "+(int)hdcp);


           // bowlingViewModel.update(player);
            System.out.println("hdcp Meta paikths " +  player.getFirstName() +" bowlavg "+player.getBowlAvg()+" avg "+ avg+" hdcp "+ player.getHdcp());


           /* int i2 = i;
            bowlingViewModel.getAllDoneRound_detailofplayerofChamp(players.get(i).getUuid(),championship.getUuid()).observe((LifecycleOwner) this, new Observer<List<Round_detail>>() {
                @Override
                public void onChanged(List<Round_detail> rd) {
                    int avg=0; //todo na to kanw float h' na rwthsw
                    System.out.println("i2 "+i2+" rd.size "+rd.size());
                    for(int r=0;r<rd.size();r++){
                        System.out.println("Prin paikths " + players.get(i2).getFirstName() +" i2 "+i2);
                        System.out.println( " rounduuid "+rd.get(r).getRound_uuid());
                        System.out.println( " r.score "+rd.get(r).getScore());
                        System.out.println( " avg "+ avg);
                        avg+= rd.get(r).getScore();
                        System.out.println("Mesa paikths " +  players.get(i2).getFirstName() +" r.score "+rd.get(r).getScore()+" bowlavg "+" avg "+ avg);

                    }
                    if(rd.size()!=0) {
                        avg = avg / (3 * rd.size()); //dia games
                    }
                    if(championship.getHdcp_tav()!=0){ //todo test it
                        int hdcp = (int) ((championship.getHdcp_tav()-avg)*championship.getHdcp_factor());
                        players.get(i2).setHdcp(hdcp);
                        System.out.println(" hdcp "+hdcp);
                    }
                    bowlingViewModel.update(players.get(i2));
                    System.out.println("Meta paikths " +  players.get(i2).getFirstName() +" bowlavg "+players.get(i2).getBowlAvg()+" avg "+ avg);
                }
            }); */
       // }
        return (int)hdcp;
    }

    //to ka8oliko
    public void calculateAVGofPlayer(Participant player,List<Round_detail> rd, Championship championship, BowlingViewModel bowlingViewModel){

        //for (int i = 0; i <players.size(); i++) {
         //   System.out.println("Avg paikths " + i + " " + players.get(i).getFullName() + " id " + players.get(i).getUuid() + " hdcp " + players.get(i).getHdcp());
         //  int i2 = i;

           System.out.println("Avg paikths " + " " + player.getFullName() + " id " + player.getUuid() + " hdcp " + player.getHdcp()+" rd size "+rd.size());
                    float avg = 0;
                    int games = 0;
                    for (int r = 0; r < rd.size(); r++) {
                        if (rd.get(r).getBlind() == 0) {
                            System.out.println("Prin paikths " + player.getFirstName());
                            System.out.println(" rounduuid " + rd.get(r).getRound_uuid());
                            System.out.println(" r.score " + rd.get(r).getScore());
                            System.out.println(" bowlavg " + player.getBowlAvg());
                            System.out.println(" avg " + avg);
                            if (rd.get(r).getUpdated_at() != null) {
                                avg += rd.get(r).getScore();
                                games += 3;
                                System.out.println("Mesa paikths " + player.getFirstName() + " r.score " + rd.get(r).getScore() + " bowlavg " + player.getBowlAvg() + " avg " + avg + " games " + games);
                            }
                        }
                    }
                        if (games != 0) {
                            avg = avg / games;
                        }
                        player.setBowlAvg((int) avg);//todo float
                        System.out.println(" e3w paikths "+ player.getFirstName() + " neo avg "+avg);
                        int hdcp=calculateHDCPofPlayer(player, avg, championship, bowlingViewModel);
                        player.setHdcp(hdcp);
                   /* if(championship.getHdcp_tav()!=0){ //todo test it
                        int hdcp = (int) ((championship.getHdcp_tav()-avg)*championship.getHdcp_factor()/100);
                        RoundScoreListAdapter2.editModelArrayList.get(i2).setHdcp(hdcp);
                        System.out.println(" hdcp "+hdcp);
                    }*/
                        player.setTotal_games(games);
                        bowlingViewModel.update(player);
                        System.out.println("avg Meta paikths " + player.getFirstName() + " bowlavg " + player.getBowlAvg() + " avg " + avg + " hdcp " + player.getHdcp() + " games "+games);


        // }
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
            System.out.println("Team " + (i + 1) + ": " + p.getFirstName() + " " + p.getLastName() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFirstName() + " " + p.getPartner().getLastName() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
        }



    } */

}
