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

import java.util.List;

public class RoundStatisticsActivity extends AppCompatActivity {

    public static Round r;
    public Championship championship;
    private static TextView winnerTeam;
    private String champuuid;
    private BowlingViewModel bowlingViewModel;
    private PlayerandGamesAdapter blistAdapter; //playergames
    private TeamandScoreAdapter tlistAdapter; //todo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_statistics);

        winnerTeam = findViewById(R.id.winnerTeam);

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        RecyclerView recyclerViewteam = findViewById(R.id.recyclerViewteams);
        RecyclerView recyclerViewplayers = findViewById(R.id.recyclerViewplayers);
        blistAdapter =  new PlayerandGamesAdapter(this);
        tlistAdapter = new TeamandScoreAdapter(this);
        recyclerViewteam.setAdapter(tlistAdapter);
        recyclerViewteam.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewplayers.setAdapter(blistAdapter);
        recyclerViewplayers.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null) {
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = " + championship.getFchampID() + " " + championship.getUuid());
            r = (Round) bundleObject.getSerializable("round");
        }
        System.out.println("got r "+r.getFroundid());
        bowlingViewModel.getPlayerScoreGamesofRound(r.getRounduuid(), champuuid ).observe(this, new Observer<List<PlayerandGames>>() {
            @Override
            public void onChanged(List<PlayerandGames> part) {
                blistAdapter.setPlayers(part);
                blistAdapter.setChamp(championship); //todo na testarw
                System.out.println("pl size "+part.size());
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
