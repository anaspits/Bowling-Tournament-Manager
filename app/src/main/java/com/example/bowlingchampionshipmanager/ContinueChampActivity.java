package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//na to metaonomasw se selectchamp
public class ContinueChampActivity extends AppCompatActivity implements ChampListAdapter.OnDeleteClickListener{

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    public static final int UPDATE_TEAM_ACTIVITY_REQUEST_CODE = 3;
    public static final int UPDATE_CHAMP_ACTIVITY_REQUEST_CODE = 4;
    private BowlingViewModel bowlingViewModel;
    private EditViewModel eViewModel;
    private ChampListAdapter clistAdapter;
    private TextView textView1,txvTeam,txvNote;
    private String flag=""; //con, old, stat, rounds_stat, teams_stat



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_champ);

        textView1=findViewById(R.id.textView1);
        txvTeam= findViewById(R.id.txvTeam);
        txvNote= findViewById(R.id.txvNote);

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            flag =  bundleObject.getString("flag");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        clistAdapter = new ChampListAdapter(this, this);
        recyclerView.setAdapter(clistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eViewModel= ViewModelProviders.of(this).get(EditViewModel.class);


        if(flag.equals("con")){
       // bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
       bowlingViewModel.getActiveChamp3().observe(this, new Observer<List<Championship>>() {
            @Override
            public void onChanged(List<Championship> c) {
                clistAdapter.setChamp(c);
                clistAdapter.setFlagStat("con");
                if(c.size()==0){
                    textView1.setText("There are no Active Championships at the moment");
                    txvTeam.setText("");
                    txvNote.setText("");
                }
            }
        });
        } else if (flag.equals("old")){
            textView1.setText("Finished Championships");
            bowlingViewModel.getFinChamps().observe(this, new Observer<List<Championship>>() {
                @Override
                public void onChanged(List<Championship> c) {
                    clistAdapter.setChamp(c);
                    clistAdapter.setFlagStat("old");
                    if(c.size()==0){
                        textView1.setText("There are no Finished Championships at the moment");
                        txvTeam.setText("");
                        txvNote.setText("");
                    }
                }
            });
            //todo na ta valw ola se ena bowlingviewmodel kai mesa na exw tis if
        } else if (flag.equals("champ_stat")){
            textView1.setText("Select which Championship's Statistics you want to view");
             bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
            @Override
                public void onChanged(List<Championship> c) {
                    clistAdapter.setChamp(c);
                    clistAdapter.setFlagStat(flag);
                    if(c.size()==0){
                        textView1.setText("There are no Championships at the moment");
                        txvTeam.setText("");
                        txvNote.setText("");
                    }
                }
            });
        } else if (flag.equals("rounds_stat")){
            textView1.setText("Select which Championship's Rounds Statistics you want to view");
            bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
                @Override
                public void onChanged(List<Championship> c) {
                    clistAdapter.setChamp(c);
                    clistAdapter.setFlagStat(flag);
                    if(c.size()==0){
                        textView1.setText("There are no Championships at the moment");
                        txvTeam.setText("");
                        txvNote.setText("");
                    }
                }
            });
        }else if (flag.equals("teams_stat")){
            textView1.setText("Select which Championship's Teams Statistics you want to view");
            bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
                @Override
                public void onChanged(List<Championship> c) {
                    clistAdapter.setChamp(c);
                    clistAdapter.setFlagStat(flag);
                    if(c.size()==0){
                        textView1.setText("There are no Championships at the moment");
                        txvTeam.setText("");
                        txvNote.setText("");
                    }
                }
            });
        } /* else if (flag.equals("players_stat")){
            textView1.setText("Select which Championship's Player Statistics you want to view");
            bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
                @Override
                public void onChanged(List<Championship> c) {
                    clistAdapter.setChamp(c);
                    clistAdapter.setFlagStat(flag);
                    if(c.size()==0){
                        textView1.setText("There are no Championships at the moment");
                        txvTeam.setText("");
                        txvNote.setText("");
                    }
                }
            });
        } */

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;

        if (requestCode == UPDATE_CHAMP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
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
    @Override
    public void OnDeleteClickListener(Championship myNote) {
        bowlingViewModel.delete(myNote);
        //todo na petaei mhnyma an 8elei na to svisei
    }
}
