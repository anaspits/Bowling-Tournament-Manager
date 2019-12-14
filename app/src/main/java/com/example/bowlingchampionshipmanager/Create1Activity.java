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

        Button button_st  = (Button) findViewById(next_btn);
        button_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreate2Activity();
            }
        });
    }

    public void openCreate2Activity(){
        Intent intent2 = new Intent(this, Create2Activity.class);
        startActivity(intent2);
    }

}
