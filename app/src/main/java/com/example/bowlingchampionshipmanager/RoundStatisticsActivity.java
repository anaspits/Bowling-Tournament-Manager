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
    private static TextView winnerTeam, man1,man2,man3,man4;
    private String champuuid;
    private int mangame, mangamebegining, manset, mansetbegining;
    private BowlingViewModel bowlingViewModel;
    private PlayerandGamesAdapter blistAdapter; //playergames
    private TeamandRoundScoreAdapter tlistAdapter;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_statistics);

        winnerTeam = findViewById(R.id.winnerTeam);
        textTitle=findViewById(R.id.textTitle);
        man1= findViewById(R.id.man1);
        man2= findViewById(R.id.man2);
        man3= findViewById(R.id.man3);
        man4= findViewById(R.id.man4);


        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        RecyclerView recyclerViewteam = findViewById(R.id.recyclerViewteams);
        RecyclerView recyclerViewplayers = findViewById(R.id.recyclerViewplayers);
        blistAdapter =  new PlayerandGamesAdapter(this);
        tlistAdapter = new TeamandRoundScoreAdapter(this);
        recyclerViewteam.setAdapter(tlistAdapter);
      //  recyclerViewteam.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recyclerViewteam.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewplayers.setAdapter(blistAdapter);
        recyclerViewplayers.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null) {
            championship = (Championship) bundleObject.getSerializable("champ");
            champuuid=championship.getUuid();
            System.out.println("Champ in round = " + championship.getFchampID() + " " + championship.getUuid());
            r = (Round) bundleObject.getSerializable("round");
        }
        System.out.println("got r "+r.getFroundid()+" rid "+r.getRounduuid());
        textTitle.setText("Round No."+r.getFroundid());
        //teams
        bowlingViewModel.geAllTeamsofRoundofChamp( champuuid,r.getFroundid() ).observe(this, new Observer<List<TeamandRoundScore>>() {
            @Override
            public void onChanged(List<TeamandRoundScore> t) {
                tlistAdapter.setTeams(t);
                tlistAdapter.setChamp(championship);
            }
        });

        //players
        bowlingViewModel.getAllPlayerScoreGamesofRound(r.getFroundid(), champuuid ).observe(this, new Observer<List<PlayerandGames>>() {
        //bowlingViewModel.getPlayerScoreGamesofRound(r.getRounduuid(), champuuid ).observe(this, new Observer<List<PlayerandGames>>() { //fixme den epistrefei olous tous players
            @Override
            public void onChanged(List<PlayerandGames> part) {
                blistAdapter.setPlayers(part);
                blistAdapter.setChamp(championship);
                System.out.println("pl size "+part.size());
            }
        });

        //todo me onomata k ta fulla twn players
        bowlingViewModel.getAllPrevRound_detailofChamp(champuuid, r.getFroundid()).observe(this, new Observer<List<Round_detail>>() { //todo gia gunaika kai antra
            @Override
            public void onChanged(List<Round_detail> rds) {
                System.out.println("stat rds size " + rds.size());
                mangame=0;
                mangamebegining=0;
                manset=0;
                mansetbegining=0;
                int pos=0, pos2=0, pos3=0, pos4=0;
                for (int i = 0; i < rds.size(); i++) { //fixme
                    System.out.println("rd " + i +" frid "+rds.get(i).getFroundid()+ " round id " + rds.get(i).getRound_uuid() + " player id " + rds.get(i).getParticipant_uuid() + " score " + rds.get(i).getScore() + " h " + rds.get(i).getHdcp() + " firste " + rds.get(i).getFirst() + " second " + rds.get(i).getSecond() + " third " + rds.get(i).getThird() + " games " + rds.get(i).getGames() + " blind " + rds.get(i).getBlind() + " avg " + rds.get(i).getAvg());

                    //if (player.getsex.equals(male) {
                    //paixnidi antra aparxhs
                    if (mangamebegining <= rds.get(i).getFirst()) {
                        mangamebegining = rds.get(i).getFirst();
                        pos = i;
                    }
                    if (mangamebegining <= rds.get(i).getSecond()) {
                        mangamebegining = rds.get(i).getSecond();
                        pos = i;
                    }
                    if (mangamebegining <= rds.get(i).getThird()) {
                        mangamebegining = rds.get(i).getThird();
                        pos = i;
                    }

                    //set antrwn ap'arxhs
                    if (mansetbegining <= rds.get(i).getScore()) { //fixme //komple
                        mansetbegining = rds.get(i).getScore();
                        System.out.println("mansetbeg "+mansetbegining+ " i "+i);
                        pos3 = i;
                    }

                    //paixnidi antra aftou tou gyrou
                    if (rds.get(i).getFroundid()==r.getFroundid()) {
                        if (mangame <= rds.get(i).getFirst()) {
                            mangame = rds.get(i).getFirst();
                            pos2 = i;
                        }
                        if (mangame <= rds.get(i).getSecond()) {
                            mangame = rds.get(i).getSecond();
                            pos2 = i;
                        }
                        if (mangame <= rds.get(i).getThird()) {
                            mangame = rds.get(i).getThird();
                            pos2 = i;
                        }

                        //set antrwn aftou tou gyrou
                        if (manset <= rds.get(i).getScore()) {
                            manset = rds.get(i).getScore();
                            pos4 = i;
                        }
                    }
                    /*} else { //gia female
                    //todo
                }*/
                }
                man1.setText("Paixnidi Antrwn \n"+rds.get(pos).getParticipant_uuid()+" "+mangame);
                man2.setText("Paixnidi Antrwn Ap' Arxhs \n"+rds.get(pos2).getParticipant_uuid()+" "+mangamebegining);
                man3.setText("Set Antrwn  \n"+rds.get(pos4).getParticipant_uuid()+" "+manset);
                man4.setText("Set Antrwn Ap' Arxhs \n"+rds.get(pos3).getParticipant_uuid()+" "+mansetbegining);

                System.out.println("Paixnidi Antrwn "+mangame);
                System.out.println("Paixnidi Antrwn Arxhs"+mangamebegining);
                System.out.println("Set Antrwn "+manset);
                System.out.println("Set Antrwn Arxhs"+mansetbegining);
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
