package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Teamsvsteams1Activity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamsvsteams1);
        Bundle bundleObject = this.getIntent().getExtras();

        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
        }
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
