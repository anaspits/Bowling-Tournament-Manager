package com.example.bowlingchampionshipmanager;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
//import javax.persistence.*;


public class Participant implements Serializable {
    //Input attributes


    int bowlerID;

    //@Column(name="name")
    String firstName;

    //@Column(name="avg")
    int bowlAvg;

   // @Column(name="hdcp")
    int hdcp;

   // @Column(name="teamid")
    int teamid;

   // @Column(name="champid")
    int champid;

    String lastName;
    ArrayList<Participant> teamates= new ArrayList<>();

    //Information to be decided
    Participant partner;
    //int laneNum; //stretch goal

    //getter methods
    public int getBowlAvg() {
        return bowlAvg;
    }

    public String getFN(){
        return firstName;
    }

    public String getLN(){
        return lastName;
    }

    public Participant getPartner(){
        return partner;
    }

    public int getTeamid(){
        return teamid;
    }

    public ArrayList<Participant> getTeamates(){
        return teamates;
    }

    //setter methods
    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setBowlAvg(int bowlAvg) {
        this.bowlAvg = bowlAvg;
    }

    public void setPartner(Participant partner){
        this.partner = partner;
      //  if (partner!=null){
        teamates.add(partner);
//        }
    }
    public void setTeamid(int teamid) { this.teamid = teamid; }

    //constructor
    public Participant(int bowlerID, String firstname, String lastname, int bowlAvg, int team) {
        this.bowlerID = bowlerID;
        this.firstName = firstname;
        this.lastName = lastname;
        this.bowlAvg = bowlAvg;
        this.teamid = team;
        teamates.add(this);
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
    public ArrayList<Participant> createParticipantList(ArrayList<Participant> bowlers, InputStream inputStream , String line, String cvsSplitBy)throws IOException {
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
                Participant p = new Participant(i, fn, ln, ba,0);
                bowlers.add(p);

                i++;

            }

       // } catch(IOException e){
       //     e.printStackTrace();
       // }

        return bowlers;
    }

    public ArrayList<Participant> generateTeams (ArrayList<Participant> bowlers, int playersPerTeam){
        //Logic for generating teams(pairs)

        //Sort by bowling average
        Collections.sort(bowlers, Participant.partBowlAvg);
        //add 5 points to pair in 2D table of poissible matchings?
        //Associate Participants with friends
        //2D table of friendships -- maybe remove from attributes
        //if [][] = 2, add 2 points to pair in 2D table of poissible matchings?
        int i;
        for(i = 0; i < bowlers.size()/playersPerTeam; i++){
            (bowlers.get(i)).setPartner(bowlers.get(bowlers.size() - i - 1));
            (bowlers.get(bowlers.size() - i - 1)).setPartner(bowlers.get(i));


        }

        for(i = 0; i < bowlers.size()/playersPerTeam;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            p.setTeamid(i+1);
            System.out.println("Team " + p.getTeamid() + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");

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
