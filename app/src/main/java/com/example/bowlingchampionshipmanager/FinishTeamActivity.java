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
import java.util.List;

public class FinishTeamActivity extends AppCompatActivity {

    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    private static TextView textTitle, finalsc, textView1;
    public Championship championship;
    public static Team t;
    private String tuuid, champuuid;
    private BowlingViewModel bowlingViewModel;
    private SelectRoundAdapter roundlistAdapter;
    private SelectParticipantListAdapter blistAdapter; //gia to ka8oliko avg tou paikth
    private PlayerandGamesAdapter plistAdapter; //git to sugkekrimeno avg tu champ
    private List<PlayerandGames> players;
    private List<Round> r;
    private String flag;

//TODO EXPORT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_team);

        textTitle = findViewById(R.id.textTitle); //fixme gia stat gia unfinished teams
        textView1 = findViewById(R.id.textView1);
        finalsc = findViewById(R.id.finalsc);

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers"); //axreiasto
            hdcp_parameters = (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            t = (Team) bundleObject.getSerializable("b_object"); //todo na min dexetai team, alla mono uuid
            textTitle.setText("Finish Championship for Team "+t.getTeamName());
            textView1.setText("Results for Team: "+t.getTeamName());
            tuuid = t.getUuid();
            //score1 = t.getScore();
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();
        }

        if(championship.getStatus().equals("Finished")){
            textTitle.setText("Finished Championship");
        }else {
            textTitle.setText("Ongoing Championship");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        roundlistAdapter = new SelectRoundAdapter(this);
        blistAdapter =  new SelectParticipantListAdapter(this);
        plistAdapter =  new PlayerandGamesAdapter(this);
        recyclerView.setAdapter(roundlistAdapter); //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView2.setAdapter(blistAdapter);
        recyclerView2.setAdapter(plistAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


        //bowlingViewModel.getAllRoundsofTeam(t.getUuid(),champuuid).observe(this, new Observer<List<Round>>() {
            bowlingViewModel.getDoneRoundsofTeam(t.getUuid(),champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> rounds) {
                roundlistAdapter.setSelRound(rounds);
                roundlistAdapter.setChamp(championship);
                roundlistAdapter.setSelTeam(t);
                roundlistAdapter.setFinishedFlag(1);
                r=rounds;
            }
        });

        //pairnw to champ_detail ths omadas 1 team gia na dw to score tou
        bowlingViewModel.getChamp_detailofTeamandChamp(tuuid, champuuid).observe(this, new Observer<Championship_detail>() {
            @Override
            public void onChanged(Championship_detail c) {
                finalsc.setText("Final Score: "+c.getScore()); //fixme gia kapoio logo to cd.score einai to a8roisma twn 2 teleftaiwn // komple fainetai
            }
        });

     /*   bowlingViewModel.getAllPlayersofTeam3(tuuid, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setSelected(part);
                blistAdapter.setChamp(championship);
                blistAdapter.setbtnSelFlag(1);
            }
        }); */


        //bowlingViewModel.getAllPlayerScoreGamesofTeamOrdered(champuuid,tuuid).observe(this, new Observer<List<PlayerandGames>>() {
        bowlingViewModel.getAllPlayerScoreGamesofTeamofDoneRoundsOrdered(champuuid,tuuid).observe(this, new Observer<List<PlayerandGames>>() {
            @Override
            public void onChanged(List<PlayerandGames> part) { //epistrefei ta rounds kai ta score twn paiktwn me seira apo rounds
                ArrayList<PlayerandGames> p= new ArrayList<>(); //8a parw ta score apo to teleutaio round pou epai3an (to pio prosfato)
                for(int i=0;i<part.size();i++){
                    System.out.println(" round "+part.get(i).getFroundid()+" "+part.get(i).getFirstName());
                    if (i==part.size()-1){ //an einai o teleftaios paikths
                        if(i!=0 && part.get(i).getFroundid()==part.get(i-1).getFroundid()) { //elegxw an einai sto idio round me ton prohgoumeno paikth
                            p.add(part.get(i)); //kai ton vaz sth lista
                        } else {
                            break; //an den einai teleiwsame
                        }
                    }
                    else if (part.get(i).getFroundid()==part.get(i+1).getFroundid()){ //an o paikths aftos einai sto idio round me ton epomeno
                        p.add(part.get(i)); //ton vazw sth lista k sunexizw
                    }  else { //an o epomenos einai se allo round
                        p.add(part.get(i)); //vazw afton sth lista k stamataw giati oi upolipoi guroi den me endiaferoun
                        break;
                    }
                }
                plistAdapter.setPlayers(p);
                plistAdapter.setChamp(championship);
                players=p;
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
        finish();
    }

    public void export(View view) {
        ExportCSV ex = new ExportCSV();
        StringBuilder data= ex.exportFinishedTeam(championship,r, t, players);

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
