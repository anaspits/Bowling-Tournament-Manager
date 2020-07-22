package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class SelectRoundActivity extends AppCompatActivity {

    private BowlingViewModel bowlingViewModel;
    private TextView textView1,txv2;
    private String flag;
    private SelectRoundAdapter roundlistAdapter;
    public Championship championship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_round);

        textView1=findViewById(R.id.textView1);
       // txv2= findViewById(R.id.txv2);

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            flag =  bundleObject.getString("flag");
            championship = (Championship) bundleObject.getSerializable("champ");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        roundlistAdapter = new SelectRoundAdapter( this);
        recyclerView.setAdapter(roundlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (flag.equals("stat") || flag.equals("edit_round")){
            bowlingViewModel.getDoneRoundsofChamp(championship.getUuid()).observe(this, new Observer<List<Round>>() {
                @Override
                public void onChanged(List<Round> rounds) {
                    if (rounds.size() == 0) {
                        textView1.setText("No Rounds have been played yet");
                       // txv2.setText("");
                    } else{
                    for (int i = 0; i < rounds.size(); i++) {
                        if (i != 0 && rounds.get(i).getFroundid() == rounds.get(i - 1).getFroundid()) {
                            rounds.remove(i); //fixme to 1 to phre 2 forew - na to kanw me distinct
                        }
                    }
                    roundlistAdapter.setSelRound(rounds);
                    roundlistAdapter.setChamp(championship);
                    roundlistAdapter.setflag(flag);
                    }
                }
            });
        } else {
            bowlingViewModel.getAllRoundofChamp(championship.getUuid()).observe(this, new Observer<List<Round>>() {
                @Override
                public void onChanged(List<Round> rounds) {
                    roundlistAdapter.setSelRound(rounds);
                    roundlistAdapter.setChamp(championship);
                   // roundlistAdapter.setflag(flag);
                    //roundlistAdapter.setFlag(1);
                }
            });
        }
    }
}
