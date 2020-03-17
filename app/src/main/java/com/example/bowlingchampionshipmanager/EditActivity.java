package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity {

    public static final String BOWL_ID="bowlId";
    static final String UPDATED_NOTE = "bowl_text";
    private EditText editdb,editavg,editteam,edithdcp;
    private Bundle bundle;
    private int bowlId;
    private LiveData<Participant> participant;
    private TextView testid;

    //private Test_table t;
    private Participant t;

    EditViewModel editViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editdb = findViewById(R.id.editdb);
        editavg = findViewById(R.id.editavg);
        editteam = findViewById(R.id.editteam);
        edithdcp = findViewById(R.id.edithdcp);
        testid =findViewById(R.id.testid);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            bowlId = bundle.getInt("bowlId");
testid.setText(String.valueOf(bowlId));
           // t = (Test_table) bundle.getSerializable("b_object");
            t =  (Participant) bundle.getSerializable("b_object");
        }

        editViewModel = ViewModelProviders.of(this).get(EditViewModel.class);

        //fetch step 3
        participant = editViewModel.getBowl(bowlId);
        participant.observe(this, new Observer<Participant>() {
            @Override
            public void onChanged(Participant participant) {
                editdb.setText(participant.getFullName());
                editavg.setText(String.valueOf(participant.getBowlAvg()));
                editteam.setText(String.valueOf(participant.getTeamid()));
                edithdcp.setText(String.valueOf(participant.getHdcp()));
            }
        });
    }

    public void updateDB (View view) {
        String updatedName = editdb.getText().toString().trim();
        String updatedAvg = editavg.getText().toString().trim();
        String updatedTeam = editteam.getText().toString().trim();
        String updatedHdcp = edithdcp.getText().toString().trim();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("bowlId", bowlId);
        resultIntent.putExtra("b_object", (Serializable) t);
        resultIntent.putExtra(UPDATED_NOTE, updatedName);
        resultIntent.putExtra("updatedAvg", updatedAvg);
        resultIntent.putExtra("updatedTeam", updatedTeam);
        resultIntent.putExtra("updatedHdcp", updatedHdcp);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate (View view) {
        finish();
    }
}
