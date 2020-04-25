package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
            Intent start = new Intent(this,Create1Activity.class);
            startActivity(start);
        }  else if (button_text.equals("Continue Championship")){
            Intent i = new Intent(this,ContinueChampActivity.class);
            startActivity(i);
        }  else if (button_text.equals("Exit")){
            finish(); //todo na kleinei olh thn efarmogh, oxi mono to activity
        }

    }

}
