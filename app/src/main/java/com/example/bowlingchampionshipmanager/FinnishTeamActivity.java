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

public class FinnishTeamActivity extends AppCompatActivity {

    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    private static TextView textTitle, finalsc, textView1;
    public Championship championship;
    public static Team t;
    private String tuuid, champuuid;
    private BowlingViewModel bowlingViewModel;
    private SelectRoundAdapter roundlistAdapter;
    private SelectParticipantListAdapter blistAdapter;

//TODO EXPORT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finnish_team);

        textTitle = findViewById(R.id.textTitle);
        textView1 = findViewById(R.id.textView1);
        finalsc = findViewById(R.id.finalsc);

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers"); //axreiasto
            hdcp_parameters = (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            t = (Team) bundleObject.getSerializable("b_object");
            textTitle.setText("Finnish Championship for Team "+t.getTeamName());
            textView1.setText("Results for Team: "+t.getTeamName());
            tuuid = t.getUuid();
            //score1 = t.getScore();
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        roundlistAdapter = new SelectRoundAdapter(this);
        blistAdapter =  new SelectParticipantListAdapter(this);
        recyclerView.setAdapter(roundlistAdapter); //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(blistAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        //todo na upologisw to neo avg twn paiktwn
        bowlingViewModel.getAllRoundsofTeam(t.getUuid(),champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> rounds) {
                roundlistAdapter.setSelRound(rounds);
                roundlistAdapter.setChamp(championship);
                roundlistAdapter.setSelTeam(t);
                roundlistAdapter.setFinishedFlag(1);
            }
        });

        //pairnw to champ_detail ths omadas 1 team gia na dw to score tou
        bowlingViewModel.getChamp_detailofTeamandChamp(tuuid, champuuid).observe(this, new Observer<Championship_detail>() {
            @Override
            public void onChanged(Championship_detail c) {
                finalsc.setText("Final Score: "+c.getScore());
            }
        });

        bowlingViewModel.getAllPlayersofTeam3(tuuid, champuuid).observe(this, new Observer<List<Participant>>() {
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
        //axrista?
        Bundle extras = new Bundle();
        extras.putSerializable("bowlers", bowlers);
        extras.putSerializable("champ", championship);
        extras.putSerializable("b_object", t); //selected team
        i.putExtras(extras); //
        startActivity(i);
    }
}
