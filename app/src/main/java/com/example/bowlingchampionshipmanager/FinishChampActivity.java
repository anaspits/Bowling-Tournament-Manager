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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinishChampActivity extends AppCompatActivity implements TeamatesAdapter.OnDeleteClickListener {

    private static TextView winnerTeam,textTitle,txtrounds;
    private LinearLayout playerinfo;
    public Championship championship;
    private String champuuid;
    private BowlingViewModel bowlingViewModel;
    private RoundDoublelistAdapter roundlistAdapter;
   //private SelectParticipantListAdapter blistAdapter2;
    private PlayerandGamesAdapter blistAdapter;
    private TeamandScoreAdapter tlistAdapter;
    private List<TeamandScore> teams;
    private List<Round> rounds;
    private List<PlayerandGames> players;
    private List<TeammatesTuple> playersandteams;
    private TeamatesAdapter tplistAdapter;

    //TODO single
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_champ);

        winnerTeam = findViewById(R.id.winnerTeam);
        textTitle = findViewById(R.id.textTitle);
        txtrounds= findViewById(R.id.txtrounds);
        playerinfo = findViewById(R.id.playerinfo);

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ= " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        RecyclerView recyclerViewteam = findViewById(R.id.recyclerViewrank);
        RecyclerView recyclerViewround = findViewById(R.id.recyclerViewround);
        RecyclerView recyclerViewplayers = findViewById(R.id.recyclerViewplayers);
        roundlistAdapter = new RoundDoublelistAdapter(this);
        //blistAdapter2 =  new SelectParticipantListAdapter(this);
        tplistAdapter = new TeamatesAdapter(this,this);
        blistAdapter =  new PlayerandGamesAdapter(this);
        tlistAdapter = new TeamandScoreAdapter(this);
        recyclerViewteam.setAdapter(tlistAdapter);
        recyclerViewteam.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewround.setAdapter(roundlistAdapter);
        recyclerViewround.setLayoutManager(new LinearLayoutManager(this));

        if(championship.getStatus().equals("Finished")) {
            playerinfo.setVisibility(View.VISIBLE);
            textTitle.setText("Finished Championship");
            recyclerViewplayers.setAdapter(blistAdapter);
            recyclerViewplayers.setLayoutManager(new LinearLayoutManager(this));
        } else {
            playerinfo.setVisibility(View.GONE);
            recyclerViewplayers.setAdapter(tplistAdapter);
            recyclerViewplayers.setLayoutManager(new LinearLayoutManager(this));
            textTitle.setText("Ongoing Championship");
            winnerTeam.setVisibility(View.GONE);
        }
        bowlingViewModel.getRankedAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeamandScore>>() {
            @Override
            public void onChanged(List<TeamandScore> t) { //fixme emfanizei ta score opws na nai -?
                tlistAdapter.setTeams(t);
            tlistAdapter.setChamp(championship); 
            winnerTeam.setText("Winning Team: "+t.get(0).getTeam_name()+"\n"+" with Score: "+t.get(0).getTeam_score());
            teams=t;
            }
        });

       // bowlingViewModel.getAllRoundofChamp(champuuid).observe(this, new Observer<List<Round>>() {
        bowlingViewModel.getDoneRoundsofChamp(champuuid).observe(this, new Observer<List<Round>>() {
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

      //todo na to kanw me query me dates, etsi den leitourgei
/* bowlingViewModel.getAllPlayerScoreGamesofChampOrdered(champuuid).observe(this, new Observer<List<PlayerandGames>>() { //ola ta rounds, ara pairnei ta teleftaia scores twn teleftaiwn rounds, pou omws an den exoun paixtei einai 0
            @Override
            public void onChanged(List<PlayerandGames> part) {
                ArrayList<PlayerandGames> p= new ArrayList<>();
                for(int i=0;i<part.size();i++) { //thelw na parw ta teleftaia scores tou teleftaiou round pou epe3an
                    if (part.get(i).getGames() != 0) {
                        if (i == part.size() - 1) {
                            if(i!=0 && part.get(i).getFroundid()==part.get(i-1).getFroundid()) { //elegxw an einai sto idio round me ton prohgoumeno paikth
                                p.add(part.get(i)); //kai ton vazw sth lista
                            } else {
                                break; //an den einai teleiwsame
                            }
                        } else if (part.get(i).getFroundid() == part.get(i + 1).getFroundid()) {
                            p.add(part.get(i));
                        } else {
                            p.add(part.get(i));
                            break;
                        }
                    }else if ( part.get(i).getFroundid()==1){
                        p.add(part.get(i));
                    }
                }
                blistAdapter.setPlayers(p);
                blistAdapter.setChamp(championship);
                players=p;
            }
        }); */


//fixme den leitourgei....den pairnei to updated_at apo to rd kai afou ta dates einai null pairnei to pio palio round anti to pio rosfato
     /*   bowlingViewModel.getAllPlayerScoreGamesofChamp(champuuid).observe(this, new Observer<List<PlayerandGames>>() {
            @Override
            public void onChanged(List<PlayerandGames> part) {
                ArrayList<PlayerandGames> p= new ArrayList<>();
                for(int i=0;i<part.size();i++) { //pairnw gia ka8e paikth ola ta rd tou, 8elw to pio prosfato round pou epai3e, alliws 0
                    System.out.println("PLAYERANDGAMES "+part.get(i).getFirstName()+" round "+part.get(i).getFroundid()+" date "+part.get(i).getRound_updated_date());
                    if(i==part.size()-1){ //an einai o teleftaios paikths me to teleftaio round pou epai3e
                        if (!p.get(p.size()-1).getParticipant_uuid().equals(part.get(i).getParticipant_uuid())){ //tsekarw an ton exw valei hdh sth lista
                            p.add(part.get(i)); //kai an den ton exw valei hdh tote ton vazw sth lista (giati eite afto einai to pio prosfato rd tou H' den exei pai3ei kanena)
                        }
                    }else if(part.get(i).getParticipant_uuid()==part.get(i+1).getParticipant_uuid()) { //an einai k o epomenos o idios paiths, ara pame ston epomeno gyro
                        if(part.get(i).getRound_updated_date()==null ) { //an date= null ara den exei pai3ei afton ton gyro (k epeidh einai auxousa seira den 8a exei paixei oute tous epomenous)
                            continue; //fixme break if? //sunexizw mexri na ftasw sto teleftaio tou rd kai na to valw tote sth lista
                        }else if (part.get(i+1).getRound_updated_date()==null){ //an to date aftou einai den einai null kai einai null to date tou epomenou, ara afto to date einai to teleftaio rd pou epai3e (dld den epai3e ta epomena)
                            p.add(part.get(i)); //kai ara to vaz sth lista
System.out.println("EDW me dates: round "+part.get(i).getFroundid()+" date "+part.get(i).getRound_updated_date()+" epomeno round "+ part.get(i+1).getFroundid()+"epomeno date "+part.get(i+1).getRound_updated_date());
                        }
                    } else { //an afto einai to teleftaio rd tou paikth aftou
                        if(p.size()==0){ //an einai adeia h lista ara den exei mpei kanenas paikths
                            p.add(part.get(i)); //ara ton vazw
                        }else if (!p.get(p.size()-1).getParticipant_uuid().equals(part.get(i).getParticipant_uuid())){ //tsekarw an ton exw valei hdh sth lista
                            p.add(part.get(i)); //kai an den ton exw valei hdh tote ton vazw sth lista (giati eite afto einai to pio prosfato rd tou H' den exei pai3ei kanena)
                        }
                    }
                }
                blistAdapter.setPlayers(p);
                blistAdapter.setChamp(championship);
                players=p;
            }
        }); */
//fixme gia odd number f teams: gia kapoio logo otan paizontai alloi gyroi px ths omadas 2, oi upoloipoi paiktes (px ths omadas 1) exoun ola 0
   /* bowlingViewModel.getAllPlayerScoreGamesofChampOrdered(champuuid).observe(this, new Observer<List<PlayerandGames>>() {
        @Override
        public void onChanged(List<PlayerandGames> part) { //epistrefei ta rounds kai ta score twn paiktwn me seira apo rounds
            ArrayList<PlayerandGames> p = new ArrayList<>(); //8a parw ta score apo to teleutaio round pou epai3an (to pio prosfato)
            for (int i = 0; i < part.size(); i++) {
                System.out.println(" round " + part.get(i).getFroundid() + " " + part.get(i).getFirstName());
                if (i == part.size() - 1) { //an einai o teleftaios paikths
                     if(i!=0 && part.get(i).getFroundid()==part.get(i-1).getFroundid()) { //elegxw an einai sto idio round me ton prohgoumeno paikth
                    p.add(part.get(i)); //kai ton vazw sth lista
                    } else {
                        break; //an den einai teleiwsame
                    }
                } else if (part.get(i).getFroundid() == part.get(i + 1).getFroundid()) { //an o paikths aftos einai sto idio round me ton epomeno
                    p.add(part.get(i)); //ton vazw sth lista k sunexizw
                } else { //an o epomenos einai se allo round
                    p.add(part.get(i)); //vazw afton sth lista k stamataw giati oi upolipoi guroi den me endiaferoun
                    break;
                }
            }
            if(championship.getStatus().equals("Finished")) {
            blistAdapter.setPlayers(p);
            blistAdapter.setChamp(championship);
            }
            players = p;
        }
    }); */
   //todo bye- test it - komple mallon
        bowlingViewModel. getAllPlayerScoreGamesofChampofDoneRoundsOrderedByplayer(champuuid).observe(this, new Observer<List<PlayerandGames>>() {
            @Override
            public void onChanged(List<PlayerandGames> part) { //epistrefei ta rounds kai ta score twn paiktwn me alfavitikh seira apo last name
                ArrayList<PlayerandGames> p = new ArrayList<>(); //8a parw ta score apo to teleutaio round pou epai3an (to pio prosfato) (pou den 8a einai bye - gia odd numofteams)
                for (int i = 0; i < part.size(); i++) {
                    if (!part.get(i).getParticipant_uuid().equals("blind")){
                        System.out.println(" round " + part.get(i).getFroundid() + " " + part.get(i).getFirstName());
                    if (i == part.size() - 1) { //an einai o teleftaios paikths
                        p.add(part.get(i)); //kai ton vazw sth lista
                    } else if (!part.get(i).getParticipant_uuid().equals(part.get(i + 1).getParticipant_uuid())) { //an o paikths aftos den einai idios me ton epomeno ara afto einai to teleftaio round pou epai3e
                        p.add(part.get(i)); //ton vazw sth lista k sunexizw
                    }
                }
            }
                blistAdapter.setPlayers(p);
                blistAdapter.setChamp(championship);
                players=p;
            }
        });

    //an den exei teleiwsei to champ pairnw apla tous players
        if (!championship.getStatus().equals("Finished")) {
            bowlingViewModel.getAllTeamatesofAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeammatesTuple>>() {
                @Override
                public void onChanged(List<TeammatesTuple> p1) {
                    playersandteams = p1;
                    tplistAdapter.setTeams(p1);
                    tplistAdapter.setChamp(championship);
                }
            });
        }

        //pairnw tous paiktes ka8e omadas tou champ aftou
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity(); //kleinei ola ta prohgoumena activities
        } else {
            finish();
        }

    }

    public void export(View view) {
        ExportCSV ex = new ExportCSV();
        StringBuilder data= ex.exportFinishedChamp( championship, rounds, teams, players,   playersandteams);


/*        StringBuilder data = new StringBuilder();
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
*/
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

    @Override
    public void OnDeleteClickListener(TeammatesTuple myNote) {
    }
}
