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

    //isopalies
    private ArrayList<Integer> draw_mgame = new ArrayList<>();
    private ArrayList<Integer> draw_mset = new ArrayList<>();
    private ArrayList<Integer> draw_msetbegining = new ArrayList<>();
    private ArrayList<Integer> draw_wgamebegining = new ArrayList<>();
    private  ArrayList<Integer> draw_wsetbegining = new ArrayList<>();
    private ArrayList<Integer> draw_wgame = new ArrayList<>();
    private  ArrayList<Integer> draw_wset = new ArrayList<>();
    private ArrayList<Integer> draw_mgamebegining = new ArrayList<>();
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
                rd = rds;
                mangame = 0;
                mangamebegining = 0;
                manset = 0;
                mansetbegining = 0;
                fgame = 0;
                fgamebegining = 0;
                fset = 0;
                fsetbegining = 0;
                pos = 0;
                pos2 = 0;
                pos3 = 0;
                pos4 = 0;
                fpos = 0;
                fpos2 = 0;
                fpos3 = 0;
                fpos4 = 0;
                ArrayList<Integer> scoreandpos = new ArrayList<>();
                ArrayList<Integer> scoreandpos2 = new ArrayList<>();
                for (int i = 0; i < rds.size(); i++) { //gia ka8e gyrw apo tous prohgoumenos done ths r
                    // System.out.println("rd " + i +" frid "+rds.get(i).getFroundid()+ " round id " + rds.get(i).getRound_uuid() + " player id " + rds.get(i).getParticipant_uuid() + " score " + rds.get(i).getScore() + " h " + rds.get(i).getHdcp() + " firste " + rds.get(i).getFirst() + " second " + rds.get(i).getSecond() + " third " + rds.get(i).getThird() + " games " + rds.get(i).getGames() + " blind " + rds.get(i).getBlind() + " avg " + rds.get(i).getAvg());

                    if (rds.get(i).getSex().equals("m")|| rds.get(i).getSex().equals("M") || rds.get(i).getSex().equals("male") || rds.get(i).getSex().equals("MALE") || rds.get(i).getSex().equals("Male") || rds.get(i).getSex().equals("antras")) { //sygkrinw ta paixnidia k ta set gia ton andra k thn gynaika kai pairnw ta max
                        scoreandpos = rds.get(i).calcSetAndGamesStat(rds, r, i, mangame, mangamebegining, manset, mansetbegining, pos, pos2, pos3, pos4);
                        mangame = scoreandpos.get(0);
                        mangamebegining = scoreandpos.get(1);
                        manset = scoreandpos.get(2);
                        mansetbegining = scoreandpos.get(3);
                        pos = scoreandpos.get(4);
                        pos2 = scoreandpos.get(5);
                        pos3 = scoreandpos.get(6);
                        pos4 = scoreandpos.get(7);

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
                    } else { //gia female //todo na to kanw else-if gia female kai meta else gia to an valoun kati akuro
                        scoreandpos2 = rds.get(i).calcSetAndGamesStat(rds, r, i, fgame, fgamebegining, fset, fsetbegining, fpos, fpos2, fpos3, fpos4);
                        fgame = scoreandpos2.get(0);
                        fgamebegining = scoreandpos2.get(1);
                        fset = scoreandpos2.get(2);
                        fsetbegining = scoreandpos2.get(3);
                        fpos = scoreandpos2.get(4);
                        fpos2 = scoreandpos2.get(5);
                        fpos3 = scoreandpos2.get(6);
                        fpos4 = scoreandpos2.get(7);

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

                //isopalies
                //pairnaw to max gia ka8e agwna-set
              draw_mgamebegining.add(mangamebegining);
                draw_mgame.add(mangame);
                draw_mset.add(manset);
                draw_msetbegining.add(mansetbegining);
                draw_wgamebegining.add(fgamebegining);
                draw_wsetbegining.add(fsetbegining);
                draw_wgame.add(fgame);
                draw_wset.add(fset);
                //pairnaw tis 8eseis twn prwtwn paiktwn me ta antistoixa max scores agwna-set
                draw_mgamebegining.add(pos);
                draw_mgame.add(pos2);
                draw_mset.add(pos4);
                draw_msetbegining.add(pos3);
                draw_wgamebegining.add(fpos);
                draw_wsetbegining.add(fpos3);
                draw_wgame.add(fpos2);
                draw_wset.add(fpos4);
                for (int j = 0; j < rds.size(); j++) {
                    System.out.println("j "  + j);
                    if (rds.get(j).getSex().equals("m")) { //sygkrinw ta paixnidia k ta set gia ton andra k thn gynaika kai pairnw ta max
                        if(rds.get(j).drawforGameBegining(rds, j,mangamebegining,pos)!=-1){
                            draw_mgamebegining.add(rds.get(j).drawforGameBegining(rds, j,mangamebegining,pos));
                        }
                        if(rds.get(j).drawforGame(rds,r, j,mangame,pos2)!=-1){
                            draw_mgame.add(rds.get(j).drawforGame(rds,r, j,mangame,pos2));
                        }
                        if(rds.get(j).drawforSetBegining(rds, j,mansetbegining,pos3)!=-1){
                            draw_msetbegining.add(rds.get(j).drawforSetBegining(rds, j,mansetbegining,pos3));
                        }
                        if(rds.get(j).drawforSet(rds,r, j,manset,pos4)!=-1){
                            draw_mset.add(rds.get(j).drawforSet(rds,r, j,manset,pos4));
                        }
                    }else {
                        if(rds.get(j).drawforGameBegining(rds, j,fgamebegining,fpos)!=-1){
                            draw_wgamebegining.add(rds.get(j).drawforGameBegining(rds, j,fgamebegining,fpos));
                        }
                        if(rds.get(j).drawforGame(rds,r, j,fgame,fpos2)!=-1){
                            draw_wgame.add(rds.get(j).drawforGame(rds,r, j,fgame,fpos2));
                        }
                        if(rds.get(j).drawforSetBegining(rds, j,fsetbegining,fpos3)!=-1){
                            draw_wsetbegining.add(rds.get(j).drawforSetBegining(rds, j,fsetbegining,fpos3));
                        }
                        if(rds.get(j).drawforSet(rds,r, j,fset,fpos4)!=-1){
                            draw_wset.add(rds.get(j).drawforSet(rds,r, j,fset,fpos4));
                        }

                    }
                }
                System.out.println("manbeg size "+draw_mgamebegining.size());

                if (mangame==0)
                {
                    man1.setText("Paixnidi Antrwn \n"+mangame);
                } else {
                    man1.setText("Paixnidi Antrwn \n"+rds.get(pos2).getLastName()+" "+rds.get(pos2).getFirstName());
                    for(int i=2;i<draw_mgame.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame kai to draw_mgame.get(1) exei to pos2
                        man1.append("\n"+rds.get(draw_mgame.get(i)).getLastName()+" "+rds.get(draw_mgame.get(i)).getFirstName());
                    }
                    man1.append("\n "+mangame);
                }
                if (mangamebegining==0){
                    man2.setText("Paixnidi Antrwn Ap' Arxhs \n"+mangamebegining);
                }else{
                    man2.setText("Paixnidi Antrwn Ap' Arxhs \n"+rds.get(pos).getLastName()+" "+rds.get(pos).getFirstName());
                    for(int i=2;i<draw_mgamebegining.size();i++){
                        man2.append("\n"+rds.get(draw_mgamebegining.get(i)).getLastName()+" "+rds.get(draw_mgamebegining.get(i)).getFirstName());
                    }
                    man2.append("\n "+mangamebegining);
                }
                if (manset==0){
                    man3.setText("Set Antrwn  \n"+manset);
                }else{
                    man3.setText("Set Antrwn  \n"+rds.get(pos4).getLastName()+" "+rds.get(pos4).getFirstName());
                    for(int i=2;i<draw_mset.size();i++){
                        man3.append("\n"+rds.get(draw_mset.get(i)).getLastName()+" "+rds.get(draw_mset.get(i)).getFirstName());
                    }
                    man3.append("\n "+manset);
                }
                if(mansetbegining==0){
                    man4.setText("Set Antrwn Ap' Arxhs \n"+mansetbegining);
                }else{
                    man4.setText("Set Antrwn Ap' Arxhs \n"+rds.get(pos3).getLastName()+" "+rds.get(pos3).getFirstName());
                    for(int i=2;i<draw_msetbegining.size();i++){
                        man4.append("\n"+rds.get(draw_msetbegining.get(i)).getLastName()+" "+rds.get(draw_msetbegining.get(i)).getFirstName());
                    }
                    man4.append("\n "+mansetbegining);
                }
                if(fgame==0){
                    f1.setText("Paixnidi Gynaikwn \n"+fgame);
                }else {
                    f1.setText("Paixnidi Gynaikwn \n"+rds.get(fpos2).getLastName()+" "+rds.get(fpos2).getFirstName());
                    for(int i=2;i<draw_wgame.size();i++){
                        f1.append("\n"+rds.get(draw_wgame.get(i)).getLastName()+" "+rds.get(draw_wgame.get(i)).getFirstName());
                    }
                    f1.append("\n "+fgame);
                }
                if(fgamebegining==0){
                    f2.setText("Paixnidi Gynaikwn Ap' Arxhs \n"+fgamebegining);
                }else {
                    f2.setText("Paixnidi Gynaikwn Ap' Arxhs \n"+rds.get(fpos).getLastName()+" "+rds.get(fpos).getFirstName());
                    for(int i=2;i<draw_wgamebegining.size();i++){
                        f2.append("\n"+rds.get(draw_wgamebegining.get(i)).getLastName()+" "+rds.get(draw_wgamebegining.get(i)).getFirstName());
                    }
                    f2.append("\n "+fgamebegining);
                }
                if(fset==0){
                    f3.setText("Set Gynaikwn  \n"+fset);
                }else {
                    f3.setText("Set Gynaikwn  \n"+rds.get(fpos4).getLastName()+" "+rds.get(fpos4).getFirstName());
                    for(int i=2;i<draw_wset.size();i++){
                        f3.append("\n"+rds.get(draw_wset.get(i)).getLastName()+" "+rds.get(draw_wset.get(i)).getFirstName());
                    }
                    f3.append("\n "+fset);
                }
                if(fsetbegining==0){
                    f4.setText("Set Gynaikwn Ap' Arxhs \n"+fsetbegining);
                }else {
                    f4.setText("Set Gynaikwn Ap' Arxhs \n" + rds.get(fpos3).getLastName() + " " + rds.get(fpos3).getFirstName());
                    for(int i=2;i<draw_wsetbegining.size();i++){
                        f4.append("\n"+rds.get(draw_wsetbegining.get(i)).getLastName()+" "+rds.get(draw_wsetbegining.get(i)).getFirstName());
                    }
                    f4.append("\n "+fsetbegining);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }

    public void export(View view) { //todo na to valw sto class
ExportCSV exp= new ExportCSV();
        StringBuilder data = new StringBuilder();
        data = exp.exportSpecificRoundStat(championship,r,max,winner,playersandteams,teams,players,rd,draw_mgame,draw_mgamebegining,draw_mset,draw_msetbegining,draw_wgame,draw_wgamebegining,draw_wset,draw_wsetbegining);

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
