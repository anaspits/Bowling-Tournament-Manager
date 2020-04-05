package com.example.bowlingchampionshipmanager;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*import javax.persistence.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence; */



@Entity(tableName = "test_table")
public class Test_table implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name="name")
    private String name;

//na svisw
    @ColumnInfo(name="bowlAvg")
    private int bowlAvg;

    @ColumnInfo(name="sys_teamID")
    int teamid;

    public Test_table( String name) {
       // super();
        //this.id = id;
        this.name = name;

    }

    @Ignore
    ArrayList<Test_table> teamates= new ArrayList<>();
    @Ignore
    Test_table partner;

    public int getId (){ return id;}

    public String getName() {
        return name;
    }

    public int getBowlAvg() {
        return bowlAvg;
    }

    public int getTeamid() {
        return teamid;
    }
    public Test_table getPartner(){
        return partner;
    }

    public ArrayList<Test_table> getTeamates(){
        return teamates;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBowlAvg(int bowlAvg) {
        this.bowlAvg = bowlAvg;
    }
    public void setPartner(Test_table partner){
        this.partner = partner;
        //  if (partner!=null){
        teamates.add(partner);
//        }
    }
    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Test_table>  testcreateParticipantList(BowlingViewModel bowlingViewModel, InputStream inputStream , String line, String cvsSplitBy, ArrayList<Test_table> bowlers)throws IOException {
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
            //Participant p = new Participant(i, fn, ln, ba,0);
           // bowlers.add(p);
            String fullname = fn + " " +ln;
            Test_table t= new Test_table(fullname);
//            bowlingViewModel.insert(t);
            Create1Activity.t_id++;
            bowlers.add(t);
            i++;

        }

        // } catch(IOException e){
        //     e.printStackTrace();
        // }

        return bowlers;

    }

    public static Comparator<Test_table> partBowlAvg = new Comparator<Test_table>() {

        public int compare(Test_table s1, Test_table s2) {

            int ba1 = s1.getBowlAvg();
            int ba2 = s2.getBowlAvg();

            /*For ascending order*/
            return ba1 - ba2;

            /*For descending order*/
            //rollno2-rollno1;
        }
    };
    public ArrayList<Test_table> generateTeams (BowlingViewModel bowlingViewModel, int playersPerTeam, ArrayList<Test_table> bowlers){
        //Logic for generating teams(pairs)

        //Sort by bowling average
        Collections.sort(bowlers, Test_table.partBowlAvg);
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

            //Participant p = bowlers.get(i);
            Test_table p =bowlers.get(i);
            p.setTeamid(i+1);
            //System.out.println("Team " + p.getTeamid() + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");

        }
        return bowlers;
    }

}
