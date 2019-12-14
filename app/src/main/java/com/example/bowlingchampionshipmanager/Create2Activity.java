package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Create2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);
        Button button_st  = (Button) findViewById(R.id.next_btn);
        button_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreat3Activity();
            }
        });
    }

    public void openCreat3Activity(){
        Intent intent = new Intent(this, Create3Activity.class);
        startActivity(intent);
    }
}
