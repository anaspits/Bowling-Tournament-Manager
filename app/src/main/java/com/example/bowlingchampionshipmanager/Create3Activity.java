package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Create3Activity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    static ArrayList<String> hdcp_parameters;
    private static TextView t;
    private static RadioButton pins;
    private static RadioButton teamsvsteams;
    private static Button start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create3);

       t= (TextView) findViewById(R.id.textView22);

       pins= (RadioButton) findViewById(R.id.pins);
       teamsvsteams= (RadioButton) findViewById(R.id.teamsvsteams);
       start= (Button) findViewById(R.id.next_btn);
       //start.setEnabled(false);


        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            //t.setText(hdcp_parameters.get(0));
        }
    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Back"))
        {
           // Intent goback = new Intent(this,Create2Activity.class);
           // startActivity(goback);
        }
        else if (button_text.equals("Start Championship"))
        {
            //Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);

           if (pins.isChecked()){
                Intent i = new Intent(this, Pins1Activity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers",bowlers);
                extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
                i.putExtras(extras);
                startActivity(i);

            } else if (teamsvsteams.isChecked()) {
                Intent i = new Intent(this, Teamsvsteams1Activity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers",bowlers);
                extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
                extras.putSerializable("all_the_teams",all_the_teams);
                i.putExtras(extras);
                startActivity(i);
            }


        }

    }
}
