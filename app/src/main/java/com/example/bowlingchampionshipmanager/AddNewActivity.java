package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class AddNewActivity extends AppCompatActivity {

    public static final String NEW_ADDED = "new_added";
    private EditText newparticipant,newavg,newteam,newchamp;
    private Participant participant;
    private BowlingViewModel bowlingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        newparticipant = findViewById(R.id.newadded);
        newavg = findViewById(R.id.newavg);
        newteam = findViewById(R.id.newteam);
        newchamp = findViewById(R.id.newchamp);

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);

        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(newparticipant.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                    String name = newparticipant.getText().toString();
                    String avg = newavg.getText().toString();
                    String team = newteam.getText().toString();
                    String champ = newchamp.getText().toString();

                    Participant t = new Participant(0,name, "",Integer.parseInt(avg),Integer.parseInt(team));

                    /*axrista resultIntent.putExtra(NEW_ADDED, name);
                    resultIntent.putExtra("new_avg", avg);
                    resultIntent.putExtra("new_team", team);
                    resultIntent.putExtra("new_champ", champ); */
                    //resultIntent.putExtra("new_fid", sfid);
                    resultIntent.putExtra("b_object", (Serializable) t);
                    setResult(RESULT_OK, resultIntent);
                }

                finish();
            }
        });


    }
}
