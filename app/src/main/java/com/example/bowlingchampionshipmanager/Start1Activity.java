package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Start1Activity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs;
    private static TextView player_11;
    private static TextView hdcp_11;
    private static TextView player_12;
    private static TextView player_13;
    private static TextView player_21;
    private static TextView player_22;
    private static TextView player_23;
    private static TextView hdcp_12;
    private static TextView hdcp_13;
    private static TextView hdcp_21;
    private static TextView hdcp_22;
    private static TextView hdcp_23;
    private static TextView team11;
    private static TextView team22;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start1);

        player_11=(TextView) findViewById(R.id.player_11);
        hdcp_11=(TextView) findViewById(R.id.hdcp11);
        player_12=(TextView) findViewById(R.id. player_12);
        player_13=(TextView) findViewById(R.id. player_13);
        player_21=(TextView) findViewById(R.id. player_21);
        player_22=(TextView) findViewById(R.id. player_22);
        player_23=(TextView) findViewById(R.id. player_23);
        hdcp_12=(TextView) findViewById(R.id.hdcp12);
        hdcp_13=(TextView) findViewById(R.id.hdcp13);
        hdcp_21=(TextView) findViewById(R.id.hdcp21);
        hdcp_22=(TextView) findViewById(R.id.hdcp22);
        hdcp_23=(TextView) findViewById(R.id.hdcp23);
        team11=(TextView) findViewById(R.id.team1);
        team22=(TextView) findViewById(R.id.team2);

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            vs = (ArrayList<ArrayList>) bundleObject.getSerializable("vs");

        }
        round1(vs);
    }

    public void round1(ArrayList<ArrayList> vs){

       /* gia temp3
       Team t1 = vs.get(0);
        Team t2 = vs.get(1);
        player_view.setText("Team: "+t1.getTeamID());
        hdcp_view.setText("Team: "+t2.getTeamID()); */

        //gia vs
        ArrayList<Team> temp= vs.get(0);
        Team t11 = temp.get(0);
        Team t22 = temp.get(1);
        team11.setText("Team: "+t11.getTeamID());
        team22.setText("Team: "+t22.getTeamID());

        ArrayList<Participant> teamates= t11.getTeamates();
        //int i;
        //for (i=0;i<teamates.size();i++) {

            player_11.setText(teamates.get(0).getFN());
            hdcp_11.setText(teamates.get(0).bowlAvg);
        player_12.setText(teamates.get(1).getFN());
        hdcp_12.setText(teamates.get(1).bowlAvg);

        //}
        teamates=t22.getTeamates();
        //for (i=0;i<teamates.size();i++) {
        player_21.setText(teamates.get(0).getFN());
        hdcp_21.setText(teamates.get(0).bowlAvg);
        player_22.setText(teamates.get(1).getFN());
        hdcp_22.setText(teamates.get(1).bowlAvg);


      //  }
    }


    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();

        if (button_text.equals("Start Championship"))
        {
           // Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);

        }

    }
}


