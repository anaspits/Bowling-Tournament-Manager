package com.example.bowlingchampionshipmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class EditChampActivity extends AppCompatActivity implements TeamListAdapter.OnDeleteClickListener{

    public static final String BOWL_ID="bowlId";
    static final String UPDATED_NOTE = "bowl_text";
    private EditText editstatus, editround;
    private Bundle bundle;
    private int bowlId;
    private LiveData<Championship> champ;
    private Championship t;
    private TextView cname;

    EditViewModel editViewModel;
    private TeamListAdapter tlistAdapter;
    private BowlingViewModel bowlingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_champ);

        editstatus = findViewById(R.id.editstatus);
        editround = findViewById(R.id.editround);
        cname = findViewById(R.id.cname);

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.crecyclerView);
        tlistAdapter = new TeamListAdapter(this, this);
        recyclerView.setAdapter(tlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bowlingViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> team) {
                tlistAdapter.setTeams(team);
            }
        });

        bundle = getIntent().getExtras();

        if (bundle != null) {
            bowlId = bundle.getInt("bowlId");
            cname.setText(String.valueOf(bowlId));
            // t = (Test_table) bundle.getSerializable("b_object");
            t =  (Championship) bundle.getSerializable("b_object");
        }

        editViewModel = ViewModelProviders.of(this).get(EditViewModel.class);

        //fetch step 3
        champ = editViewModel.getChamp(bowlId);
        champ.observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship champ) {
                cname.append("No. "+ String.valueOf(bowlId));
                editstatus.setText(String.valueOf(champ.getStatus()));
                editround.setText(String.valueOf(champ.getRound()));
            }
        });
    }

    public void updateDB (View view) {
        String updatedst = editstatus.getText().toString().trim();
        String updatedr = editround.getText().toString().trim();

        t.setStatus(updatedst);
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
    public void OnDeleteClickListener(Team myNote) {
        bowlingViewModel.delete(myNote);
    }
}
