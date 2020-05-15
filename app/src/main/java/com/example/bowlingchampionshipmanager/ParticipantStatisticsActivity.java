package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ParticipantStatisticsActivity extends AppCompatActivity {

    private BowlingViewModel bowlingViewModel;
    public String champuuid;
    public Participant p;
    private TextView textView,player;
    private String flag;
    private SelectParticipantListAdapter blistAdapter;
    private List<Participant> participants;

    //fixme fix UI
    //todo tha mporousa na valw kai ton ari8mo twn champs pou epai3e o ka8e paikths
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_statistics);
        player = findViewById(R.id.player);
        flag = "none";

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            p = (Participant) bundleObject.getSerializable("participant");
            flag = bundleObject.getString("flag");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new SelectParticipantListAdapter(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (flag.equals("all")) {
            bowlingViewModel.getAllActiveParticipants().observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> part) {
                    blistAdapter.setSelected(part);
                    blistAdapter.setbtnSelFlag(1);
                    participants=part;
                }
            });
        } else {
            //todo fix UI
           // recyclerView.setVisibility(View.GONE);
            player.setText("Name: "+p.getFullName());
            player.append("\nHDCP: "+p.getHdcp());
            player.append("\nAverage: "+p.getBowlAvg());
            player.append("\nGames: "+p.getTotal_games());
        }
    }

    public void export(View view) {
        StringBuilder data = new StringBuilder();

        if (flag.equals("all")) {
            data.append(" ,Name,HDCP,Average,Games");


        for (int i = 0; i < participants.size(); i++) {
            Participant p =participants.get(i);
            data.append("\n" + (i+1)+"," + p.getFullName()+","+p.getHdcp()+","+p.getBowlAvg()+","+p.getTotal_games());
        }

            try {
                //saving the file into device
                FileOutputStream out = openFileOutput("bowling_championship_players_stat.csv", Context.MODE_PRIVATE);
                out.write((data.toString()).getBytes());
                out.close();

                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "bowling_championship_players_stat.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.bowlingchampionshipmanager.fileprovider", filelocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "Send mail"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data.append("Name,HDCP,Average,Games");
            data.append("\n" + p.getFullName()+","+p.getHdcp()+","+p.getBowlAvg()+","+p.getTotal_games());

            try {
                //saving the file into device
                FileOutputStream out = openFileOutput("bowling_championship_player_stat.csv", Context.MODE_PRIVATE);
                out.write((data.toString()).getBytes());
                out.close();

                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "bowling_championship_player_stat.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.bowlingchampionshipmanager.fileprovider", filelocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "Send mail"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
