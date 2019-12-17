package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.bowlingchampionshipmanager.R.id.next_btn;

public class Create1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create1);


    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Back"))
        {
            Intent goback = new Intent(this,MainActivity.class);
            startActivity(goback);
        }
        else if (button_text.equals("Next"))
        {
            Intent gonext = new Intent(this,Create2Activity.class);
            startActivity(gonext);

        }
       /* else if (button_text.equals("Import"))
        {


        } */
    }

}
