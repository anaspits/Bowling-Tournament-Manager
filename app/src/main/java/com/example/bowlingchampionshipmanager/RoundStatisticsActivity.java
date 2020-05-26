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

public class RoundStatisticsActivity extends AppCompatActivity {

    public static Round r;
    public Championship championship;
    private static TextView winnerTeam, man1,man2,man3,man4,f1,f2,f3,f4;
    private String champuuid;
    private int mangame, mangamebegining, manset, mansetbegining, fgame, fgamebegining, fset, fsetbegining,max,pos, pos2, pos3, pos4,fpos, fpos2, fpos3, fpos4;
    private BowlingViewModel bowlingViewModel;
    private PlayerandGamesAdapter blistAdapter; //playergames
    private TeamandRoundScoreAdapter tlistAdapter;
    private TextView textTitle;
    private List<TeamandRoundScore> teams;
    private List<PlayerandGames> players,rd;
    private List<TeammatesTuple> playersandteams;
    private TeamandRoundScore winner;
//fixme gia single
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
        f1= findViewById(R.id.f1);
        f2= findViewById(R.id.f2);
        f3= findViewById(R.id.f3);
        f4= findViewById(R.id.f4);


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
       // bowlingViewModel.geAllTeamsofRoundofChamp( champuuid,r.getFroundid() ).observe(this, new Observer<List<TeamandRoundScore>>() {  //todo order - fixme otan o teleftaios gyros ths omadas einai bye tote deixnei score 0
        bowlingViewModel.geAllNonByeTeamsofRoundofChamp( champuuid,r.getFroundid()).observe(this, new Observer<List<TeamandRoundScore>>() {
            @Override
            public void onChanged(List<TeamandRoundScore> t) {
                tlistAdapter.setTeams(t);
                tlistAdapter.setChamp(championship);
                for(int i=0;i<t.size();i++){
                    System.out.println(" team "+t.get(i).getTeam_name()+" round ");
                }
                teams=t;
                int pos=0;
                max=0;
                for (int i=0;i<t.size();i++){
                    if(t.get(i).getTeam_uuid().equals(t.get(i).getTeam1_uuid())){
                        if(max<=t.get(i).getPoints1()){
                            max=t.get(i).getPoints1();
                            pos=i;
                        }
                    }else {
                        if(max<=t.get(i).getPoints2()){
                            max=t.get(i).getPoints2();
                            pos=i;
                        }
                    }
                }
                winner=t.get(pos);
                winnerTeam.setText("Winning Team of Round: "+t.get(pos).getTeam_name()+"\nWith Points: "+max);
            }
        });

        //players
       // bowlingViewModel.getAllPlayerScoreGamesofRound(r.getFroundid(), champuuid ).observe(this, new Observer<List<PlayerandGames>>() {        //fixme bye - sto finishchamp otan teleiwnei ena champ, an h teleftaia gyra mias omadas htan bye, na pairnietai to teleftaio rd twn paiktwn
        //oxi afto bowlingViewModel.getPlayerScoreGamesofRound(r.getRounduuid(), champuuid ).observe(this, new Observer<List<PlayerandGames>>() { //fixme den epistrefei olous tous players
        bowlingViewModel.getNonByePlayerScoreGamesofRound(r.getFroundid(), champuuid ).observe(this, new Observer<List<PlayerandGames>>() {
        @Override
            public void onChanged(List<PlayerandGames> part) {
                blistAdapter.setPlayers(part);
                blistAdapter.setChamp(championship);
                players=part;
                System.out.println("pl size "+part.size());
            }
        });

        //pairnw tous paiktes ka8e omadas tou champ aftou
        bowlingViewModel.getAllTeamatesofAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeammatesTuple>>() {
            @Override
            public void onChanged(List<TeammatesTuple> p1) {
                playersandteams = p1;
            }
        });

        //bowlingViewModel.getAllPrevRound_detailofChamp(champuuid, r.getFroundid()).observe(this, new Observer<List<Round_detail>>() { //todo gia gunaika kai antra
        bowlingViewModel.getAllPrevPlayerandGamesofChamp(champuuid, r.getFroundid()).observe(this, new Observer<List<PlayerandGames>>() {
            @Override
            public void onChanged(List<PlayerandGames> rds) {
                System.out.println("stat rds size " + rds.size());
                rd=rds;
                mangame=0;
                mangamebegining=0;
                manset=0;
                mansetbegining=0;
                fgame=0;
                fgamebegining=0;
                fset=0;
                fsetbegining=0;
                 pos=0; pos2=0; pos3=0; pos4=0; fpos=0; fpos2=0; fpos3=0; fpos4=0;
                for (int i = 0; i < rds.size(); i++) {
                   // System.out.println("rd " + i +" frid "+rds.get(i).getFroundid()+ " round id " + rds.get(i).getRound_uuid() + " player id " + rds.get(i).getParticipant_uuid() + " score " + rds.get(i).getScore() + " h " + rds.get(i).getHdcp() + " firste " + rds.get(i).getFirst() + " second " + rds.get(i).getSecond() + " third " + rds.get(i).getThird() + " games " + rds.get(i).getGames() + " blind " + rds.get(i).getBlind() + " avg " + rds.get(i).getAvg());
//fixme na ginetai kai gia isopalies
                   if (rds.get(i).getSex().equals("m")) {
                       ArrayList<Integer> scoreandpos=rds.get(i).calcSetAndGamesStat(rds,r,i,mangame,mangamebegining,manset,mansetbegining,pos, pos2, pos3, pos4);
                       mangame=scoreandpos.get(0);
                       mangamebegining=scoreandpos.get(1);
                       manset=scoreandpos.get(2);
                       mansetbegining=scoreandpos.get(3);
                       pos=scoreandpos.get(4);
                       pos2=scoreandpos.get(5);
                       pos3=scoreandpos.get(6);
                       pos4=scoreandpos.get(7);

                 /*   //paixnidi antra aparxhs
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
                    if (mansetbegining <= rds.get(i).getScore()) {
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
                    } */
                    } else { //gia female
                       ArrayList<Integer> scoreandpos2=rds.get(i).calcSetAndGamesStat(rds,r,i,fgame,fgamebegining,fset,fsetbegining,fpos, fpos2, fpos3, fpos4);
                      fgame=scoreandpos2.get(0);
                       fgamebegining=scoreandpos2.get(1);
                       fset=scoreandpos2.get(2);
                       fsetbegining=scoreandpos2.get(3);
                       fpos=scoreandpos2.get(4);
                       fpos2=scoreandpos2.get(5);
                       fpos3=scoreandpos2.get(6);
                       fpos4=scoreandpos2.get(7);
                      /* //paixnidi gynaikas aparxhs
                       if (fgamebegining <= rds.get(i).getFirst()) {
                           fgamebegining = rds.get(i).getFirst();
                           fpos = i;
                       }
                       if (fgamebegining <= rds.get(i).getSecond()) {
                           fgamebegining = rds.get(i).getSecond();
                           fpos = i;
                       }
                       if (fgamebegining <= rds.get(i).getThird()) {
                           fgamebegining = rds.get(i).getThird();
                           fpos = i;
                       }

                       //set antrwn ap'arxhs
                       if (fsetbegining <= rds.get(i).getScore()) {
                           fsetbegining = rds.get(i).getScore();
                           System.out.println("mansetbeg "+fsetbegining+ " i "+i);
                           fpos3 = i;
                       }

                       //paixnidi antra aftou tou gyrou
                       if (rds.get(i).getFroundid()==r.getFroundid()) {
                           if (fgame <= rds.get(i).getFirst()) {
                               fgame = rds.get(i).getFirst();
                               fpos2 = i;
                           }
                           if (fgame <= rds.get(i).getSecond()) {
                               fgame = rds.get(i).getSecond();
                               fpos2 = i;
                           }
                           if (fgame <= rds.get(i).getThird()) {
                               fgame = rds.get(i).getThird();
                               fpos2 = i;
                           }

                           //set antrwn aftou tou gyrou
                           if (fset <= rds.get(i).getScore()) {
                               fset = rds.get(i).getScore();
                               fpos4 = i;
                           }
                       }*/
                }
                }
                if (mangame==0)
                {
                    man1.setText("Paixnidi Antrwn \n"+mangame);
                } else {
                    man1.setText("Paixnidi Antrwn \n"+rds.get(pos2).getLastName()+" "+rds.get(pos2).getFirstName()+" \n"+mangame);
                }
                if (mangamebegining==0){
                    man2.setText("Paixnidi Antrwn Ap' Arxhs \n"+mangamebegining);
                }else{
                    man2.setText("Paixnidi Antrwn Ap' Arxhs \n"+rds.get(pos).getLastName()+" "+rds.get(pos).getFirstName()+"\n "+mangamebegining);
                }
                if (manset==0){
                    man3.setText("Set Antrwn  \n"+manset);
                }else{
                    man3.setText("Set Antrwn  \n"+rds.get(pos4).getLastName()+" "+rds.get(pos4).getFirstName()+"\n "+manset);
                }
                if(mansetbegining==0){
                    man4.setText("Set Antrwn Ap' Arxhs \n"+mansetbegining);
                }else{
                    man4.setText("Set Antrwn Ap' Arxhs \n"+rds.get(pos3).getLastName()+" "+rds.get(pos3).getFirstName()+"\n "+mansetbegining);
                }
                if(fgame==0){
                    f1.setText("Paixnidi Gynaikwn \n"+fgame);
                }else {
                    f1.setText("Paixnidi Gynaikwn \n"+rds.get(fpos2).getLastName()+" "+rds.get(fpos2).getFirstName()+"\n "+fgame);
                }
                if(fgamebegining==0){
                    f2.setText("Paixnidi Gynaikwn Ap' Arxhs \n"+fgamebegining);
                }else {
                    f2.setText("Paixnidi Gynaikwn Ap' Arxhs \n"+rds.get(fpos).getLastName()+" "+rds.get(fpos).getFirstName()+"\n "+fgamebegining);
                }
                if(fset==0){
                    f3.setText("Set Gynaikwn  \n"+fset);
                }else {
                    f3.setText("Set Gynaikwn  \n"+rds.get(fpos4).getLastName()+" "+rds.get(fpos4).getFirstName()+"\n "+fset);
                }
                if(fsetbegining==0){
                    f4.setText("Set Gynaikwn Ap' Arxhs \n"+fsetbegining);
                }else {
                    f4.setText("Set Gynaikwn Ap' Arxhs \n" + rds.get(fpos3).getLastName() + " " + rds.get(fpos3).getFirstName() + "\n " + fsetbegining);
                }

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

    public void export(View view) { //todo na to valw sto class
        StringBuilder data = new StringBuilder();
        data.append("Championship No.,"+championship.fchampID+",UUID:,"+champuuid);
        data.append("\nRound No.,"+r.getFroundid()); //todo na valw kai lanes?
        data.append("\nWinning Team of Round,"+winner.getTeam_name()+",Points: "+max);
        data.append("\nTeam Ranking");
        data.append("\n,Team,Points,Score");
        for (int i=0;i<teams.size();i++){
            data.append("\n"+teams.get(i).getTeam_name()+",");
            for (int j = 0; j < playersandteams.get(i).getT().size(); j++) {
                data.append(playersandteams.get(i).getT().get(j).getLastName());
                if(j!=(playersandteams.get(i).getT().size()-1)){
                    data.append("-");
                }
            }
            if(teams.get(i).getTeam_uuid().equals(teams.get(i).getTeam1_uuid())) {
                data.append("," + teams.get(i).getPoints1()+","+teams.get(i).getScore1());
            }else {
                data.append("," + teams.get(i).getPoints2()+","+teams.get(i).getScore2());
            }
        }

        data.append("\nPlayers");
        data.append("\n,Player,Average,HDCP,Games");
        for (int i=0;i<players.size();i++){
            data.append("\n"+(i+1)+","+players.get(i).getFirstName()+players.get(i).getLastName()+","+players.get(i).getBowlAvg()+","+players.get(i).getHdcp()+","+players.get(i).getGames());
        }

        data.append("\n");
        data.append("\n,Paixnidi Antrwn,,Ap' Arxhs");
        data.append("\n,"+rd.get(pos2).getLastName()+" "+rd.get(pos2).getFirstName()+","+mangame+","+rd.get(pos).getLastName()+" "+rd.get(pos).getFirstName()+","+mangamebegining);
        data.append("\n,Paixnidi Gynaikwn,,Ap' Arxhs");
        data.append("\n,"+rd.get(fpos2).getLastName()+" "+rd.get(fpos2).getFirstName()+","+fgame+","+rd.get(fpos).getLastName()+" "+rd.get(fpos).getFirstName()+","+fgamebegining);
        data.append("\n,Set Antrwn,,Ap' Arxhs");
        data.append("\n,"+rd.get(pos4).getLastName()+" "+rd.get(pos4).getFirstName()+","+manset+","+rd.get(pos3).getLastName()+" "+rd.get(pos3).getFirstName()+","+mansetbegining);
        data.append("\n,Set Gynaikwn,,Ap' Arxhs");
        data.append("\n,"+rd.get(pos4).getLastName()+" "+rd.get(fpos4).getFirstName()+","+fset+","+rd.get(fpos3).getLastName()+" "+rd.get(fpos3).getFirstName()+","+fsetbegining);

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
