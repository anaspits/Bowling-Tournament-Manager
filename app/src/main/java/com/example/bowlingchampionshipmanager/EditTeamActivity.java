package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class EditTeamActivity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener {

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    public static final int UPDATE_TEAM_ACTIVITY_REQUEST_CODE = 3;
    public static final int UPDATE_CHAMP_ACTIVITY_REQUEST_CODE = 4;
    public static final String BOWL_ID="bowlId";
    static final String UPDATED_NOTE = "bowl_text";
    private EditText editname,editscore,editround;
    private Bundle bundle;
    private int bowlId;
    private String tuuid;
    private LiveData<Team> team;
    private TextView tid;

    //private Test_table t;
    private Team t;
    private Championship c;

    EditViewModel editViewModel;
    private BowlingListAdapter blistAdapter;
    private BowlingViewModel bowlingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        editname = findViewById(R.id.editn);
        editscore = findViewById(R.id.editsc);
        editround = findViewById(R.id.editr);
        tid = findViewById(R.id.teamid);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            bowlId = bundle.getInt("bowlId");
            //tid.setText(String.valueOf(bowlId));
            // t = (Test_table) bundle.getSerializable("b_object");
            t = (Team) bundle.getSerializable("b_object");
           tuuid= t.getUuid();
           c = (Championship) bundle.getSerializable("champ");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.precyclerView);
        blistAdapter = new BowlingListAdapter(this, this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       /* bowlingViewModel.getAllPlayersofTeam(bowlId).observe(this, new Observer<List<Participant>>() { //vash 1
            @Override
            public void onChanged(List<Participant> p) {
                blistAdapter.setBowls(p);
            }
        });
        bowlingViewModel.getTeammates(bowlId).observe(this, new Observer<List<Participant>>() { //vash 1
            @Override
            public void onChanged(List<Participant> participants) {
                blistAdapter.setBowls(participants);
            }
        });*/

       //vash 3
        bowlingViewModel.getAllPlayersofTeam3(tuuid, c.getUuid()).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> t) {
                    blistAdapter.setBowls(t);

            }
        });
        editViewModel = ViewModelProviders.of(this).get(EditViewModel.class);

        //fetch step 3
       // team = editViewModel.getTeam(bowlId);
        //team.observe(this, new Observer<Team>() {
        bowlingViewModel.getTeamfromUUID(tuuid).observe(this, new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                tid.append(" No. "+ String.valueOf(bowlId));
                editname.setText(team.getTeamName());
                editscore.setText(String.valueOf(team.getScore()));
                editround.setText(String.valueOf(team.getRound()));
            }
        });
    }
    public void updateDB (View view) {
        String updatedsc = editscore.getText().toString().trim();
        String updatedr = editround.getText().toString().trim();
        String updatedn = editname.getText().toString().trim();

        t.setTeamName(updatedn);
        t.setScore(Integer.parseInt(updatedsc));
        t.setRound(Integer.parseInt(updatedr));
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

    @Override
    public void OnDeleteClickListener(Participant myNote) {
        //bowlingViewModel.delete(myNote);
        //todo na kanw remove ton paikth apo thn omada H' na mhn to kanw ka8olou
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;

        if (requestCode == UPDATE_CHAMP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { //axristo?
            Button c= findViewById(R.id.back_btn);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Championship t;
                t = (Championship) bundleObject.getSerializable("b_object");
                bowlingViewModel.update(t);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == UPDATE_TEAM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Team t;
                t = (Team) bundleObject.getSerializable("b_object");
                bowlingViewModel.update(t);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            //c.setText("Here");

            //check dao for the update()
            //update step 3
            // Code to update the note
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Participant t;
                t = (Participant) bundleObject.getSerializable("b_object");

                bowlingViewModel.update(t);

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        }
    }
}
