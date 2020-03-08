package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Create2Activity extends AppCompatActivity {
    private static TextView textView;
    private static TextView display_teams;
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    private int playersPerTeam=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        //textView = (TextView) findViewById(R.id.row11);
        display_teams = (TextView) findViewById(R.id.teams);

        Bundle bundleObject = this.getIntent().getExtras();
       if(bundleObject!=null){

            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
           all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            }


       /*Participant p = bowlers.get(0);
        String fnn =p.getFN();
        textView.setText(fnn); */
       /*
       //palia emfanish, xwris to teams
       int i;
        for(i = 0; i < bowlers.size()/2;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            //System.out.println("Team " + (i + 1) + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
            display_teams.append("Team " + p.getTeamid() + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )" + "\n");
        } */

       //nea emfanish me th xrhsh tou arraylist teams pou exei mesa tou arraylist me Participant
        int i;
       /* for (i=0; i<teams.size();i++) {
            ArrayList<Participant> temp = teams.get(i);

            display_teams.append("\n"+"Team " + (i+1) +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                display_teams.append(temp.get(j).getFN() +"  ");
            }

        } */

        //nea nea emfanish me xrhsh tou arraylist all_the_teams pou exei mesa Team
        for (i=0; i<all_the_teams.size();i++) {
            Team t = all_the_teams.get(i);
            ArrayList<Participant> temp =  t.getTeamates();

            display_teams.append("\n"+"Team " + t.getTeamID() +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                display_teams.append(temp.get(j).getFN() +"  ");
            }
        }

    }




    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Back"))
        {
            Intent goback = new Intent(this,Create1Activity.class);
            startActivity(goback);
        }
        else if (button_text.equals("Next"))
        {
            //Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);
            Intent i =  new Intent(Create2Activity.this, Create3Activity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bowlers",bowlers);
            bundle.putSerializable("all_the_teams",all_the_teams);
            i.putExtras(bundle);
            startActivity(i);

        }
        else if (button_text.equals("HDCP Parameters"))
        {
            //Intent goHDCP = new Intent(this,HDCPActivity.class);
            //startActivity(goHDCP);
            Intent i =  new Intent(Create2Activity.this, HDCPActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bowlers",bowlers);
            bundle.putSerializable("all_the_teams",all_the_teams);
            i.putExtras(bundle);
            startActivity(i);

        }
    }
}
