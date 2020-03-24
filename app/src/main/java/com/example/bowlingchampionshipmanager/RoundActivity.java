package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RoundActivity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs;//list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
   // public static ArrayList<Team> temp3; //lista me omades opou paizoun antipales oi omades se seiriakes theseis, dld 0-1, 2-3 klp
    private static TextView player_view;
    private static TextView hdcp_view;
    private static TextView first;
    private static TextView second;
    private static TextView third;
    private static TextView player2_view;
    private static TextView hdcp2_view;
    private static TextView first2;
    private static TextView second2;
    private static TextView third2;
    private static TextView team1;
    private static TextView team2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        Bundle bundleObject = this.getIntent().getExtras();

        player_view=(TextView) findViewById(R.id.player);
        hdcp_view=(TextView) findViewById(R.id.hdcp_view);
        first=(TextView) findViewById(R.id.first_view);
        second=(TextView) findViewById(R.id.second_view);
        third=(TextView) findViewById(R.id.third_view);
        team1=(TextView) findViewById(R.id.team1);
        team2=(TextView) findViewById(R.id.team2);
        player2_view=(TextView) findViewById(R.id.player2);
        hdcp2_view=(TextView) findViewById(R.id.hdcp2_view);
        first2=(TextView) findViewById(R.id.first2_view);
        second2=(TextView) findViewById(R.id.second2_view);
        third2=(TextView) findViewById(R.id.third2_view);




        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers"); //axreiasto
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            vs = (ArrayList<ArrayList>) bundleObject.getSerializable("vs");
        }
        round();
    }

    public void round(){

       /* gia temp3
       Team t1 = vs.get(0);
        Team t2 = vs.get(1);
        player_view.setText("Team: "+t1.getFTeamID());
        hdcp_view.setText("Team: "+t2.getFTeamID()); */

       //gia vs
        ArrayList<Team> temp= vs.get(0);
        Team t1 = temp.get(0);
        Team t2 = temp.get(1);
        team1.setText("Team: "+t1.getFTeamID());
        team2.setText("Team: "+t2.getFTeamID());

        ArrayList<Participant> teamates= t1.getTeammates();
        int i;
        for (i=0;i<teamates.size();i++) {

            player_view.append(teamates.get(i).getFN()+"\n");
            hdcp_view.append(teamates.get(i).bowlAvg+"\n");

        }
        teamates=t2.getTeammates();
        for (i=0;i<teamates.size();i++) {

            player2_view.append(teamates.get(i).getFN()+"\n");
            hdcp2_view.append(teamates.get(i).bowlAvg+"\n");

        }
    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();

        if (button_text.equals("Start Championship"))
        {
            // Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);
            Intent i = new Intent(this, RoundActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putSerializable("all_the_teams",all_the_teams);
            extras.putSerializable("vs",vs);
            i.putExtras(extras);
            startActivity(i);

        }

    }
}
