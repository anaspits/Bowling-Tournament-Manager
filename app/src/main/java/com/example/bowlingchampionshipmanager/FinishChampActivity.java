package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FinishChampActivity extends AppCompatActivity {

    private static TextView winnerTeam;
    public Championship championship;
    private String champuuid;
    private BowlingViewModel bowlingViewModel;
    private RoundDoublelistAdapter roundlistAdapter;
    private SelectParticipantListAdapter blistAdapter;
    private TeamandScoreAdapter tlistAdapter;

    //TODO EXPORT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_champ);

        winnerTeam = findViewById(R.id.winnerTeam);

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ= " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        RecyclerView recyclerViewteam = findViewById(R.id.recyclerViewrank);
        RecyclerView recyclerViewround = findViewById(R.id.recyclerViewround);
        RecyclerView recyclerViewplayers = findViewById(R.id.recyclerViewplayers);
        roundlistAdapter = new RoundDoublelistAdapter(this);
        blistAdapter =  new SelectParticipantListAdapter(this);
        tlistAdapter = new TeamandScoreAdapter(this);
        recyclerViewteam.setAdapter(tlistAdapter);
        recyclerViewteam.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewround.setAdapter(roundlistAdapter);
        recyclerViewround.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewplayers.setAdapter(blistAdapter);
        recyclerViewplayers.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getRankedAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeamandScore>>() { //todo me cd.score
            @Override
            public void onChanged(List<TeamandScore> t) {
                tlistAdapter.setTeams(t);
            tlistAdapter.setChamp(championship); 
            winnerTeam.setText("Winning Team: "+t.get(0).getTeam_name()+" with Score: "+t.get(0).getTeam_score());
            }
        });

        bowlingViewModel.getAllRoundofChamp(champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> rounds) {
                roundlistAdapter.setSelRound(rounds);
                roundlistAdapter.setChamp(championship);
            }
        });

        bowlingViewModel.getAllPlayersofChamp(champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setSelected(part);
                blistAdapter.setChamp(championship);
                blistAdapter.setFinishedFlag(1);
            }
        });
    }

    public void openNewActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void export(View view) { //todo
    }
}
