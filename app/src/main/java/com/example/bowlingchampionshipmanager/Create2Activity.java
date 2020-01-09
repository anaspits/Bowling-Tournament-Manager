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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        //textView = (TextView) findViewById(R.id.row11);
        display_teams = (TextView) findViewById(R.id.teams);

        Bundle bundleObject = this.getIntent().getExtras();
       if(bundleObject!=null){

            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            }


       /*Participant p = bowlers.get(0);
        String fnn =p.getFN();
        textView.setText(fnn); */
        int i;
        for(i = 0; i < bowlers.size()/2;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            //System.out.println("Team " + (i + 1) + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
            display_teams.append("Team " + p.getTeam() + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )" + "\n");
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
            i.putExtras(bundle);
            startActivity(i);

        }
    }
}
