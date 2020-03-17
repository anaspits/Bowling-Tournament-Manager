package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewActivity extends AppCompatActivity {

    public static final String NEW_ADDED = "new_added";
    private EditText newparticipant;
    private EditText newavg;
    private EditText newteam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        newparticipant = findViewById(R.id.newadded);
        newavg = findViewById(R.id.newavg);
        newteam = findViewById(R.id.newteam);
        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(newparticipant.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                    String name = newparticipant.getText().toString();
                    String avg = newavg.getText().toString();
                    String team = newavg.getText().toString();

                    resultIntent.putExtra(NEW_ADDED, name);
                    resultIntent.putExtra("new_avg", avg);
                    resultIntent.putExtra("new_team", team);
                    setResult(RESULT_OK, resultIntent);
                }

                finish();
            }
        });
    }
}
