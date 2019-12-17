package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Create3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create3);
    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Back"))
        {
            Intent goback = new Intent(this,Create2Activity.class);
            startActivity(goback);
        }
        else if (button_text.equals("Start Championship"))
        {
            Intent gonext = new Intent(this,Create3Activity.class);
            startActivity(gonext);

        }

    }
}
