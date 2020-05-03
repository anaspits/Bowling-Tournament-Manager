package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Championships"))
        {   Intent i = new Intent(this, ContinueChampActivity.class);
            Bundle extras = new Bundle();
            extras.putString("flag", "champ_stat");
            i.putExtras(extras);
            startActivity(i);
        }  else if (button_text.equals("Teams")) {

        }else if (button_text.equals("Players")) {

        }else if (button_text.equals("Rounds")) {
            Intent i = new Intent(this, ContinueChampActivity.class);
            Bundle extras = new Bundle();
            extras.putString("flag", "rounds_stat");
            i.putExtras(extras);
            startActivity(i);
        }  else if (button_text.equals("Exit")){
            finish(); 
        }

    }
}
