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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinishChampActivity extends AppCompatActivity {

    private static TextView winnerTeam,textTitle,txtrounds;
    public Championship championship;
    private String champuuid;
    private BowlingViewModel bowlingViewModel;
    private RoundDoublelistAdapter roundlistAdapter;
   //private SelectParticipantListAdapter blistAdapter;
    private PlayerandGamesAdapter blistAdapter;
    private TeamandScoreAdapter tlistAdapter;
    private List<TeamandScore> teams;
    private List<Round> rounds;
    private List<PlayerandGames> players;
    private List<TeammatesTuple> playersandteams;

    //TODO EXPORT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_champ);

        winnerTeam = findViewById(R.id.winnerTeam);
        textTitle = findViewById(R.id.textTitle);
        txtrounds= findViewById(R.id.txtrounds);

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ= " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();
        }

        if(championship.getStatus().equals("Finished")){
            textTitle.setText("Finished Championship");
        }else {
            textTitle.setText("Ongoing Championship");
            winnerTeam.setVisibility(View.GONE);
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        RecyclerView recyclerViewteam = findViewById(R.id.recyclerViewrank);
        RecyclerView recyclerViewround = findViewById(R.id.recyclerViewround);
        RecyclerView recyclerViewplayers = findViewById(R.id.recyclerViewplayers);
        roundlistAdapter = new RoundDoublelistAdapter(this);
       // blistAdapter =  new SelectParticipantListAdapter(this);
        blistAdapter =  new PlayerandGamesAdapter(this);
        tlistAdapter = new TeamandScoreAdapter(this);
        recyclerViewteam.setAdapter(tlistAdapter);
        recyclerViewteam.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewround.setAdapter(roundlistAdapter);
        recyclerViewround.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewplayers.setAdapter(blistAdapter);
        recyclerViewplayers.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getRankedAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeamandScore>>() { //todo me cd.score
            @Override
            public void onChanged(List<TeamandScore> t) { //fixme emfanizei ta score opws na nai
                tlistAdapter.setTeams(t);
            tlistAdapter.setChamp(championship); 
            winnerTeam.setText("Winning Team: "+t.get(0).getTeam_name()+"\n"+" with Score: "+t.get(0).getTeam_score());
            teams=t;
            }
        });

        bowlingViewModel.getAllRoundofChamp(champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> r) {
                roundlistAdapter.setSelRound(r);
                roundlistAdapter.setChamp(championship);
                rounds=r;
                if(r.size()==0){
                    txtrounds.setText("No rounds have been played yet");
                }
            }
        });
//svisto
      /*  bowlingViewModel.getAllPlayersofChamp(champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setSelected(part);
                blistAdapter.setChamp(championship);
                blistAdapter.setbtnSelFlag(1);
            }
        }); */
        bowlingViewModel.getAllPlayerScoreGamesofChampOrdered(champuuid).observe(this, new Observer<List<PlayerandGames>>() {
            @Override
            public void onChanged(List<PlayerandGames> part) {
                ArrayList<PlayerandGames> p= new ArrayList<>();
                for(int i=0;i<part.size();i++){
                    if (i==part.size()-1){
                        break;
                    }
                   else if (part.get(i).getFroundid()==part.get(i+1).getFroundid()){
                        p.add(part.get(i));
                    }  else {
                        p.add(part.get(i));
                       break;
                    }
                }
                blistAdapter.setPlayers(p);
                blistAdapter.setChamp(championship);
                players=p;
            }
        });

        //pairnw tous paiktes ka8e omadas tou champ aftou //todo na dw an pairnei ontws mono aftou tou champ
          bowlingViewModel.getAllTeamatesofAllRankedTeamsofChamp(champuuid).observe(this, new Observer<List<TeammatesTuple>>() {
            @Override
            public void onChanged(List<TeammatesTuple> p1) {
                playersandteams = p1;
                for (int i = 0; i < p1.size(); i++) {
                    System.out.println("TEST1 team " + p1.get(i).getC().getFTeamID());
                    for (int j = 0; j < p1.get(i).getT().size(); j++) {
                        System.out.println("TEST1 team " + p1.get(i).getC().getFTeamID() + " pl "+p1.get(i).getT().get(j).getFullName());
                    }
                }
            }
        });
         /* test svisto bowlingViewModel.getTeammatesofTeamsofChamp(champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> p2) {
                playersandteams=p2;
                for (int j = 0; j < p2.size(); j++) {
                    System.out.println("TEST1 team " + p1.get(i).getC().getFTeamID() + " pl "+p1.get(i).getT().get(j).getFullName());
                }
            }
        }); */
    }

    public void openNewActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void export(View view) {
        StringBuilder data = new StringBuilder();
        data.append("Championship No.,"+championship.fchampID+",UUID:,"+champuuid);

        data.append("\nWinning Team,"+teams.get(0).getTeam_name()+",Score: "+teams.get(0).getTeam_score());
        data.append("\nTeam Ranking");
        data.append("\n,Team,Score");
        for (int i=0;i<teams.size();i++){
            data.append("\n"+teams.get(i).getTeam_name()+",");
            for (int j = 0; j < playersandteams.get(i).getT().size(); j++) {
                data.append(playersandteams.get(i).getT().get(j).getLastName());
                if(j!=(playersandteams.get(i).getT().size()-1)){
                    data.append("-");
                }
                System.out.println("TEST2 team " + playersandteams.get(i).getC().getFTeamID() + " pl "+playersandteams.get(i).getT().get(j).getFullName());
            }
            data.append(","+teams.get(i).getTeam_score());
        }

        data.append("\n");
        data.append("\nRounds");
        data.append("\nRound,Team,Points,Score");
        for (int i=0;i<rounds.size();i++){
            data.append("\n"+rounds.get(i).getFroundid()+","+rounds.get(i).getTeam1ID()+","+rounds.get(i).getPoints1()+","+rounds.get(i).getScore1());
            data.append("\n"+",VS "+rounds.get(i).getTeam2ID()+","+rounds.get(i).getPoints2()+","+rounds.get(i).getScore2());
        }

        data.append("\n");
        data.append("\nPlayers");
        data.append("\nPlayer,Average,HDCP,Games");
        for (int i=0;i<players.size();i++){
            data.append("\n"+players.get(i).getFirstName()+players.get(i).getLastName()+","+players.get(i).getBowlAvg()+","+players.get(i).getHdcp()+","+players.get(i).getGames());
        }

        try {
            //saving the file into device
            FileOutputStream out = openFileOutput("bowling_championship_finishedChamp_stat.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "bowling_championship_finishedChamp_stat.csv");
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
