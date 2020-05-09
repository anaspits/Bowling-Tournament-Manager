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

public class SelectParticipantActivity extends AppCompatActivity {

    private SelectParticipantListAdapter blistAdapter;
    private BowlingViewModel bowlingViewModel;
    public String champuuid;
    public Championship championship;
    private TextView textView;
   // private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_participant);
textView=findViewById(R.id.textView);
        //flag="none";

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            championship= (Championship) bundleObject.getSerializable("champ");
          //  flag = bundleObject.getString("flag");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new SelectParticipantListAdapter(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      //  if(flag!=null) {
            //System.out.println("flag "+flag);
           // if (flag.equals("stat")) {
                textView.setText("Select a Participant to view their Statistics");
        bowlingViewModel.getAllBowls().observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setSelected(part);
            }
        });

    }

    public void exitActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}

