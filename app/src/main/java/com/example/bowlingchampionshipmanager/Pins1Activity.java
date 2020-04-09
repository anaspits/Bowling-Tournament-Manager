package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Pins1Activity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener{

    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    static ArrayList<String> hdcp_parameters;
    public String teamuuid;
    private BowlingViewModel bowlingViewModel;
    private BowlingListAdapter blistAdapter;
    private BowlingListAdapter blistAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins1);

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            //t.setText(hdcp_parameters.get(0));
            teamuuid=bundleObject.getString("teamid");
        }


        Button button_imp  = (Button) findViewById(R.id.button_import);
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        blistAdapter = new BowlingListAdapter(this, this);
        blistAdapter2 = new BowlingListAdapter(this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        System.out.println("teamuuid "+teamuuid);
        bowlingViewModel.getAllPlayersofTeam2("9376e458-1de9-405e-a09d-7e6926f47e83").observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> t) {
                if(t!=null) {
                    button_imp.setText(String.valueOf(t.size()));
                    //blistAdapter.setBowls(t.get(0).getT());
                    System.out.println("participants "+t.get(0).uuid);
                    blistAdapter.setBowls(t);
                    //int a = t.get(0).getSys_teamID();
                    button_imp.setText(String.valueOf(t.size()));
                } else{
                    button_imp.setText("wtf");
                }

            }
        });
    }
    @Override
    public void OnDeleteClickListener(Participant myNote) {
        bowlingViewModel.delete(myNote);
    }

}
