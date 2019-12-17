package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /*Button button_st  = (Button) findViewById(R.id.button_start);
        button_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreate1Activity();
            }
        });*/
    }

    /*public void openCreate1Activity(){
        Intent intent = new Intent(this, Create1Activity.class);
        startActivity(intent);
    } */

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Start a Championship"))
        {
            Intent goback = new Intent(this,Create1Activity.class);
            startActivity(goback);
        }
        else if (button_text.equals("Exit")){
            finish();
        }

    }
}
