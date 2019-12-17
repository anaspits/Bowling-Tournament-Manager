package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Create2Activity extends AppCompatActivity {
    /*TextView textView; */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);


       /* textView = (TextView) findViewById(R.id.text);*/

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
            Intent gonext = new Intent(this,Create3Activity.class);
            startActivity(gonext);

        }
        else if (button_text.equals("HDCP Parameters"))
        {
            Intent goHDCP = new Intent(this,HDCPActivity.class);
            startActivity(goHDCP);

        }
    }
}
