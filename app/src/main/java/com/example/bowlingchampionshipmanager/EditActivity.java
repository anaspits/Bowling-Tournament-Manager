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
    private EditText editdb,editavg,editteam,edithdcp,editfid,editlastname;
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
        editlastname = findViewById(R.id.editlastname);
        editavg = findViewById(R.id.editavg);
        editteam = findViewById(R.id.editteam);
        edithdcp = findViewById(R.id.edithdcp);
        editfid = findViewById(R.id.editfid);
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
        participant = editViewModel.getBowl(bowlId); //todo: me uuid
        participant.observe(this, new Observer<Participant>() {
            @Override
            public void onChanged(Participant participant) {
                editdb.setText(participant.getFN());
                editlastname.setText(participant.getLN());
                editavg.setText(String.valueOf(participant.getBowlAvg()));
                editteam.setText(String.valueOf(participant.getTeamid()));
                edithdcp.setText(String.valueOf(participant.getHdcp()));
                editfid.setText(String.valueOf(participant.getFakeID()));
            }
        });
    }

    public void updateDB (View view) {
        String updatedName = editdb.getText().toString().trim();
        String uplastname = editlastname.getText().toString().trim();
        String updatedAvg = editavg.getText().toString().trim();
        String updatedTeam = editteam.getText().toString().trim();
        String updatedHdcp = edithdcp.getText().toString().trim();
        String updatedfid = editfid.getText().toString().trim();

        t.setFirstName(updatedName); t.setLastName(uplastname);
        t.setBowlAvg(Integer.parseInt(updatedAvg));
        t.setTeamid(Integer.parseInt(updatedTeam));
        t.setHdcp(Integer.parseInt(updatedHdcp));
        t.setFakeID(Integer.parseInt(updatedfid));
        Intent resultIntent = new Intent();
       // resultIntent.putExtra("bowlId", bowlId);
        resultIntent.putExtra("b_object", (Serializable) t);
      /*axrista  resultIntent.putExtra(UPDATED_NOTE, updatedName);
        resultIntent.putExtra("updatedAvg", updatedAvg);
        resultIntent.putExtra("updatedTeam", updatedTeam);
        resultIntent.putExtra("updatedHdcp", updatedHdcp);
        resultIntent.putExtra("updatedfid", updatedfid); */
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate (View view) {
        finish();
    }
}
