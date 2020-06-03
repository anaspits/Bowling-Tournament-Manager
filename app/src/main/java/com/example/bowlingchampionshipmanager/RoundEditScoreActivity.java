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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RoundEditScoreActivity extends AppCompatActivity {

    private static TextView textTitle;
    private static TextView sumHDCP,sum1st,sum2nd,sum3rd,sumHDCP2,sum1st2,sum2nd2,sum3rd2, txtscore1, txtscore2, totalsum1, txtpoints1,totalsum2, txtpoints2;
    private static TextView team1txt, team2txt;
    private CheckBox checkboxhdcp;
    public static Team team1, team2; //team1: selected team, team2: h alla (ane3artitws pws einai ari8mimenes oi omades sto round object)
    public static Round r;
    public static String tuuid1, tuuid2;
    private int score1, score2;
    private BowlingViewModel bowlingViewModel;
    private RoundListAdapter rlistAdapter;
    private RoundScoreListAdapter2 blistAdapter;
    private RoundScoreListAdapterTeam2 blistAdapter2;
    public String champuuid;
    public Championship championship;
    private Championship_detail cd1, cd2;
    public int calc_pressed=0;
    private ExportCSV exp = new ExportCSV();
    int first_sum1=0;
    int second_sum1=0;
    int third_sum1=0;
    int sum_hdcp1=0;
    int first_sum2=0;
    int second_sum2=0;
    int third_sum2=0;
    int sum_hdcp2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_edit_score);

        textTitle= findViewById(R.id.textTitle);
        team1txt=(TextView) findViewById(R.id.team1);
        team2txt=(TextView) findViewById(R.id.team2);
        sumHDCP =findViewById(R.id.sumHDCP);
        sum1st=findViewById(R.id.sum1st);
        sum2nd=findViewById(R.id.sum2nd);
        sum3rd=findViewById(R.id.sum3rd);
        sumHDCP2 =findViewById(R.id.sumHDCP2);
        sum1st2=findViewById(R.id.sum1st2);
        sum2nd2=findViewById(R.id.sum2nd2);
        sum3rd2=findViewById(R.id.sum3rd2);
        txtscore1=findViewById(R.id.score1);
        txtscore2=findViewById(R.id.score2);
        txtpoints1=findViewById(R.id.points);
        totalsum1=findViewById(R.id.txvTotalSum);
        txtpoints2=findViewById(R.id.points2);
        totalsum2=findViewById(R.id.txvTotalSum2);
        checkboxhdcp= findViewById(R.id.checkBox);
        calc_pressed=0;

        first_sum1=0;
        second_sum1=0;
        third_sum1=0;
        sum_hdcp1=0;
        first_sum2=0;
        second_sum2=0;
        third_sum2=0;
        sum_hdcp2=0;

        System.out.println("arxh edit calc_pressed="+calc_pressed);

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){

            team1 = (Team) bundleObject.getSerializable("selTeam");
            tuuid1= team1.getUuid();

            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = "+championship.getFchampID()+" "+ championship.getUuid());
            champuuid = championship.getUuid();
            r = (Round) bundleObject.getSerializable("round");
            if (r!= null) {
                System.out.println("got round from sel " + r.getFroundid() + " uuid "+ r.getRounduuid()+ " stat " + r.getStatus() +  " with t1: " + r.getTeam1ID() + " and t2: " + r.getTeam2ID() + " and sysID: " + r.getRoundid());
            } else {
                System.out.println("got round from sel ERROR");
            }
            if (tuuid1.equals(r.getTeam1UUID())) {
                //textTitle.append("\n"+"Team "+team1.getTeamName()+" VS Team " + r.getTeam2ID());
                team2txt.setText( "Team " + r.getTeam2ID());
                tuuid2=r.getTeam2UUID();
            } else {
                //textTitle.append("\n"+"Team "+team1.getTeamName()+" VS Team " + r.getTeam1ID());
                team2txt.setText( "Team " + r.getTeam1ID());
                tuuid2=r.getTeam1UUID();
            }

        }
            System.out.println("Team selected: "+team1.getFTeamID()+" sys "+team1.getSys_teamID()+" uuid "+team1.getUuid());
            team1txt.setText("Team "+team1.getTeamName() );
        textTitle.setText("Round "+r.getFroundid());
        textTitle.append("\nLanes:"+r.getLanes());

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        blistAdapter = new RoundScoreListAdapter2(this);
        blistAdapter2 = new RoundScoreListAdapterTeam2(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(blistAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        //get Team 2 by tuuid2
        bowlingViewModel.getTeamfromUUID(tuuid2).observe(this, new Observer<Team>() {
            @Override
            public void onChanged(Team te) {
team2=te;

            }
        });



        bowlingViewModel.getAllPlayersofTeam3(tuuid1, champuuid).observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> part) {
                    System.out.println("Proble size "+part.size());
                    blistAdapter.setBowls(part);
                    blistAdapter.setRound(r);
                    blistAdapter.setChamp(championship);
                    ArrayList<Round_detail> rd = new ArrayList<>(); //fixme na ta pairnw live apo to viewmodel
                    for (int i = 0; i < part.size(); i++) {
                        Round_detail round_detail= new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0,part.get(i).getHdcp(), 0,champuuid,r.getFroundid(), Calendar.getInstance().getTime() );
                        System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ part.get(i).getFirstName());
                        rd.add(round_detail);
                    }
                    blistAdapter.setRound_detail(rd);

                }
            });

            //team2
                        bowlingViewModel.getAllPlayersofTeam3(tuuid2, champuuid).observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> part) {
                    blistAdapter2.setBowls(part);
                    blistAdapter2.setRound(r);
                    blistAdapter2.setChamp(championship);
                    ArrayList<Round_detail> rd = new ArrayList<>();
                    for (int i = 0; i < part.size(); i++) {
                        Round_detail round_detail= new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0,part.get(i).getHdcp(), 0,champuuid,r.getFroundid(),Calendar.getInstance().getTime() );
                        System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ part.get(i).getFirstName());
                        rd.add(round_detail);
                    }
                    blistAdapter2.setRound_detail(rd);

                }
            });

        //pairnw to champ_detail ths omadas 1 gia na dw to score ths
        bowlingViewModel.getChamp_detailofTeamandChamp(tuuid1, champuuid).observe(this, new Observer<Championship_detail>() {
            @Override
            public void onChanged(Championship_detail c1) {
                cd1 = c1;
                System.out.println("1 exei score=" + c1.getScore());
            }
        });

        //pairnw to champ_detail ths omadas 2 gia na dw to score ths
        bowlingViewModel.getChamp_detailofTeamandChamp(tuuid2, champuuid).observe(this, new Observer<Championship_detail>() {
            @Override
            public void onChanged(Championship_detail c2) {
                cd2 = c2;
                System.out.println("2 exei score=" + c2.getScore());
            }
        });

    }

    public void calculateScore(View View) {
        //gia tin omada 1
       // score1=team1.getScore(); prin
        score1=cd1.getScore(); //meta //todo check it
         first_sum1=0;
         second_sum1=0;
         third_sum1=0;
         sum_hdcp1=0;
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++){
            // team1.setText(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(" emda player "  +" id "+RoundScoreListAdapter2.editModelArrayList.get(i).getUuid()+" hdcp "+RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " +RoundScoreListAdapter2.rd.size()+" rid "+RoundScoreListAdapter2.rd.get(i).getRound_uuid()+" pid "+RoundScoreListAdapter2.rd.get(i).getParticipant_uuid()+" h "+ RoundScoreListAdapter2.rd.get(i).getHdcp() +" 1st "+ RoundScoreListAdapter2.rd.get(i).getFirst()+" 2nd "+ RoundScoreListAdapter2.rd.get(i).getSecond()+" 3rd "+ RoundScoreListAdapter2.rd.get(i).getThird());

            first_sum1 += RoundScoreListAdapter2.rd.get(i).getFirst();
            second_sum1 +=  RoundScoreListAdapter2.rd.get(i).getSecond();
            third_sum1 +=  RoundScoreListAdapter2.rd.get(i).getThird();
            sum_hdcp1 +=RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp();

            // //upologizw to score tou paikth gia ola ta rounds mexri twra autou tou champ (an den einai blind)
            if(RoundScoreListAdapter2.rd.get(i).getBlind()==0 && !RoundScoreListAdapter2.editModelArrayList.get(i).getUuid().equals("blind")) {
                int i2 = i;
            bowlingViewModel.getAllDoneRound_detailofplayerofChamp(RoundScoreListAdapter2.editModelArrayList.get(i).getUuid(),champuuid).observe(this, new Observer<List<Round_detail>>() {
                @Override
                public void onChanged(List<Round_detail> allrdchamp) {

                        float avg = 0;
                        //int games = (3 * allrdchamp.size()) + 3;//+3 gia ta paixnidia aftou tou round, afou den epistrefetai apo to query giati den exei ginei upadate(roud), ginetai sto roundActivity //to allrounds epistrefei ta done rounds, ara kai afta pou o paikths htan blind, opote einai la8os
                    int games =3; //aftou tou round
                    System.out.println("1games a " + games + " pl " +RoundScoreListAdapter2.editModelArrayList.get(i2).getFullName()+" "+ RoundScoreListAdapter2.editModelArrayList.get(i2).getUuid());
                        for (int j = 0; j < allrdchamp.size(); j++) {
                            if (allrdchamp.get(j).getBlind() == 0) {
                                avg += allrdchamp.get(j).getScore(); //ta score twn prohgoumenwn rounds
                                games+=3; //ka8e round pou epai3e (dld den htan blind)
                            }
                        }
                        System.out.println("1 prohgoumena score " + avg);
                        avg += (RoundScoreListAdapter2.rd.get(i2).getFirst() + RoundScoreListAdapter2.rd.get(i2).getSecond() + RoundScoreListAdapter2.rd.get(i2).getThird()); //ta score aftou tou round
                        System.out.println("1 prohgoume+afto to score " + avg);
                        if (games != 0) {
                            avg = avg / games;
                        }

                        //ypologismos hdcp//todo test it kai na mhn allazei an to vazei o paikths
                    System.out.println("1 hdcp champ.tav " + championship.getHdcp_tav());
                    if(checkboxhdcp.isChecked()) {
                        int hdcp=RoundScoreListAdapter2.editModelArrayList.get(i2).calculateHDCPofPlayer(RoundScoreListAdapter2.editModelArrayList.get(i2), avg, championship, bowlingViewModel);
                        RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                        RoundScoreListAdapter2.editModelArrayList.get(i2).setHdcp(hdcp);
                       /* if (RoundScoreListAdapter2.editModelArrayList.get(i2).getUuid().equals("blind") && championship.getHdcp_less() != 0) { //an einai omada me ligoterous paiktes //todo na rwthsw
                            int hdcp = (int) ((championship.getHdcp_less() - avg) * (championship.getHdcp_factor()/100));
                            RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                            System.out.println("1 hdcp less" + hdcp);
                        } else if (championship.getHdcp_tav() != 0) {
                            int hdcp = (int) ((championship.getHdcp_tav() - avg) * (championship.getHdcp_factor()/100));
                            RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                            System.out.println("1 hdcp tav " + hdcp);
                        }*/
                    }
                        System.out.println("1games b " + games + " avg " + avg+" hdcp "+ RoundScoreListAdapter2.rd.get(i2).getHdcp());
                        RoundScoreListAdapter2.rd.get(i2).setAvg(avg);
                        RoundScoreListAdapter2.rd.get(i2).setGames(games);
                       RoundScoreListAdapter2.editModelArrayList.get(i2).setTotal_games(3+RoundScoreListAdapter2.editModelArrayList.get(i2).getTotal_games()); //ola ta prohgoumena games ever pou eixe o paikths + ta 3 aftou tou gyrou
                    //RoundScoreListAdapter2.editModelArrayList.get(i2).setTotal_games(games);
                }
               });
            }
        }
        first_sum1 += sum_hdcp1;
        second_sum1 += sum_hdcp1;
        third_sum1 += sum_hdcp1;
        sumHDCP.setText(String.valueOf(sum_hdcp1));
        sum1st.setText(String.valueOf(first_sum1));
        sum2nd.setText(String.valueOf(second_sum1));
        sum3rd.setText(String.valueOf(third_sum1 ));
        totalsum1.setText("Total Sum: "+(first_sum1+second_sum1+third_sum1));

        //gia tin omada 2
       // score2=team2.getScore(); prin
        score2=cd2.getScore(); //meta
        System.out.println("score2: "+score2);
         first_sum2=0;
         second_sum2=0;
         third_sum2=0;
         sum_hdcp2=0;
        for (int i = 0; i < RoundScoreListAdapterTeam2.editModelArrayList.size(); i++){ //todo mporw na to valw sthn panw epanalhpsh
            System.out.println(team1txt.getText() + " " + RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(" emda player "  +" id "+RoundScoreListAdapterTeam2.editModelArrayList.get(i).getUuid()+" hdcp "+RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " +RoundScoreListAdapterTeam2.rd.size()+" rid "+RoundScoreListAdapterTeam2.rd.get(i).getRound_uuid()+" pid "+RoundScoreListAdapterTeam2.rd.get(i).getParticipant_uuid()+" h "+ RoundScoreListAdapterTeam2.rd.get(i).getHdcp() +" 1st "+ RoundScoreListAdapterTeam2.rd.get(i).getFirst()+" 2nd "+ RoundScoreListAdapterTeam2.rd.get(i).getSecond()+" 3rd "+ RoundScoreListAdapterTeam2.rd.get(i).getThird());
            first_sum2 += RoundScoreListAdapterTeam2.rd.get(i).getFirst();
            second_sum2 +=  RoundScoreListAdapterTeam2.rd.get(i).getSecond();
            third_sum2 +=  RoundScoreListAdapterTeam2.rd.get(i).getThird();
            sum_hdcp2 +=RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp();

            // //upologizw to score tou paikth gia ola ta rounds mexri twra autou tou champ //todo pins

            if(RoundScoreListAdapterTeam2.rd.get(i).getBlind()==0) {
                int i3 = i;
                bowlingViewModel.getAllDoneRound_detailofplayerofChamp(RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getUuid(), champuuid).observe(this, new Observer<List<Round_detail>>() {
                    @Override
                    public void onChanged(List<Round_detail> allrdchamp2) {
                        float avg2 = 0;
                        //int games2 = 3 * allrdchamp2.size() + 3;
                        int games2 = 3;
                        System.out.println("2games " + games2 + " player " + RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getUuid() + RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getFullName());
                        for (int j = 0; j < allrdchamp2.size(); j++) {
                            System.out.println("2 prohgoumena score " + allrdchamp2.get(j).getScore());
                            if (allrdchamp2.get(j).getBlind() == 0) {
                                avg2 += allrdchamp2.get(j).getScore();
                                games2 += 3;
                            }
                        }
                        avg2 += (RoundScoreListAdapterTeam2.rd.get(i3).getFirst() + RoundScoreListAdapterTeam2.rd.get(i3).getSecond() + RoundScoreListAdapterTeam2.rd.get(i3).getThird());
                        System.out.println(" afto to score  1st " + RoundScoreListAdapterTeam2.rd.get(i3).getFirst() + " 2nd " + RoundScoreListAdapterTeam2.rd.get(i3).getSecond() + " 3rd " + RoundScoreListAdapterTeam2.rd.get(i3).getThird());

                        System.out.println("2 prohgoume+afto to score " + avg2);
                        if (games2 != 0) {
                            avg2 = avg2 / (games2);
                        }

                        //ypologismos hdcp//todo test it kai na mhn allazei an to vazei o paikths
                        System.out.println("checked " + checkboxhdcp.isChecked());
                        if(checkboxhdcp.isChecked()) {
                            if (RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getUuid().equals("blind") && championship.getHdcp_less() != 0) { //an einai omada me ligoterous paiktes //todo na rwthsw
                                double hdcp =  ((championship.getHdcp_less() - avg2) * (championship.getHdcp_factor()/100));
                                RoundScoreListAdapterTeam2.rd.get(i3).setHdcp((int)hdcp);
                                System.out.println("1 hdcp less" + hdcp);
                                RoundScoreListAdapterTeam2.editModelArrayList.get(i3).setHdcp((int)hdcp);
                            } else if (championship.getHdcp_tav() != 0) {
                                double hdcp =  ((championship.getHdcp_tav() - avg2) * (championship.getHdcp_factor()*0.01));
                                RoundScoreListAdapterTeam2.rd.get(i3).setHdcp((int)hdcp);
                                System.out.println("1 hdcp tav " + hdcp);
                                RoundScoreListAdapterTeam2.editModelArrayList.get(i3).setHdcp((int)hdcp);
                            }

                        }
                        System.out.println("2games b " + games2 + " avg " + avg2);
                        RoundScoreListAdapterTeam2.rd.get(i3).setAvg(avg2);
                        RoundScoreListAdapterTeam2.rd.get(i3).setGames(games2);
                        RoundScoreListAdapterTeam2.editModelArrayList.get(i3).setTotal_games(3+RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getTotal_games());
                    }
                });
            }
        }
        first_sum2 += sum_hdcp2;
        second_sum2 += sum_hdcp2;
        third_sum2 += sum_hdcp2;
        sumHDCP2.setText(String.valueOf(sum_hdcp2));
        sum1st2.setText(String.valueOf(first_sum2));
        sum2nd2.setText(String.valueOf(second_sum2));
        sum3rd2.setText(String.valueOf(third_sum2 ));
        totalsum2.setText("Total Sum: "+(first_sum2+second_sum2+third_sum2));

        //pontoi
        int pontoi1=0;
        int pontoi2=0;
        //player vs player/game
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++){
            //ka8e omada 3ekinaei se ka8e champ apo to 0
            //upologizei to ka8e first,second,third 3exwrista
            if (RoundScoreListAdapter2.rd.get(i).getFirst()>RoundScoreListAdapterTeam2.rd.get(i).getFirst()){
                pontoi1 +=2;
            } else if (RoundScoreListAdapter2.rd.get(i).getFirst()<RoundScoreListAdapterTeam2.rd.get(i).getFirst()){
                pontoi2+=2;
            } else {
                //todo isopalia posoi pontoi?
                pontoi1 +=2;
                pontoi2+=2;
            }
            if (RoundScoreListAdapter2.rd.get(i).getSecond()>RoundScoreListAdapterTeam2.rd.get(i).getSecond()){
                pontoi1 +=2;
            } else if (RoundScoreListAdapter2.rd.get(i).getSecond()<RoundScoreListAdapterTeam2.rd.get(i).getSecond()){
                pontoi2+=2;
            } else {
                //todo isopalia posoi pontoi?
                pontoi1 +=2;
                pontoi2+=2;
            }
            if (RoundScoreListAdapter2.rd.get(i).getThird()>RoundScoreListAdapterTeam2.rd.get(i).getThird()){
                pontoi1 +=2;
            } else if (RoundScoreListAdapter2.rd.get(i).getThird()<RoundScoreListAdapterTeam2.rd.get(i).getThird()){
                pontoi2+=2;
            } else {
                //todo isopalia posoi pontoi?
                pontoi1 +=2;
                pontoi2+=2;
            }
        }
        //sunolo korinwn ana paixnidi // me hdcp
        if(first_sum1>first_sum2){
            pontoi1+=3;
        } else if(first_sum1<first_sum2){
            pontoi2+=3;
        } else {
            //todo isopalia?
            pontoi1+=3;
            pontoi2+=3;
        }
        if(second_sum1>second_sum2){
            pontoi1+=3;
        } else if(second_sum1<second_sum2){
            pontoi2+=3;
        } else {
            //todo isopalia?
            pontoi1+=3;
            pontoi2+=3;
        }
        if(third_sum1>third_sum2){
            pontoi1+=3;
        } else if(third_sum1<third_sum2){
            pontoi2+=3;
        } else {
            //todo isopalia?
            pontoi1+=3;
            pontoi2+=3;
        }
        //geniko sunolo korinwn
        if(first_sum1+second_sum1+third_sum1>first_sum2+second_sum2+third_sum2){
            pontoi1+=5;
        } else if(first_sum1+second_sum1+third_sum1<first_sum2+second_sum2+third_sum2){
            pontoi2+=5;
        } else {
            //isopalia
            pontoi1+=5;
            pontoi2+=5;
        }


        System.out.println("Prin score1: "+score1+ " score2: "+score2 );
        score1+=pontoi1; // kalutera na krathsw sto rd.score to score tou gyrou kai sto team.score to sunolo twn pontwn ths omadas
        score2+=pontoi2;//todo na to valw sto opennewActivity?
        System.out.println("Meta score1: "+score1+ " score2: "+score2 +" points1 "+pontoi1+" points2 "+pontoi2);
        //team1.setScore(score1);//todo na rwthsw
       // team2.setScore(score2);
        txtscore1.setText("Score: "+score1);
        txtscore2.setText("Score: "+score2);
        txtpoints1.setText("Points: "+pontoi1);
        txtpoints2.setText("Points: "+pontoi2);
        if(team1.getUuid().equals(r.getTeam1UUID())){
            r.setScore1(score1);
            r.setScore2(score2);
            r.setPoints1(pontoi1);
            r.setPoints2(pontoi2);
        }else if (team1.getUuid().equals(r.getTeam2UUID())){
            r.setScore1(score2);
            r.setScore2(score1);
            r.setPoints1(pontoi2);
            r.setPoints2(pontoi1);
        }

        //bowlingViewModel.update(r); //todo svisto
        calc_pressed=1;
    }
    public void openNewActivity(View View) {
        //String button_text;
        // button_text =((Button)View).getText().toString();
        System.out.println("calc_pressed=" + calc_pressed);
        if (calc_pressed == 1) {
            team1.setScore(score1);
            team2.setScore(score2);
            System.out.println("team1 score " + team1.getScore() + " sid " + team1.getSys_teamID());
            System.out.println("team2 score " + team2.getScore() + " sid " + team2.getSys_teamID());
            //bowlingViewModel.update(team1); oxi edw
            //bowlingViewModel.update(team2);

            for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                if(!RoundScoreListAdapter2.editModelArrayList.get(i).getUuid().equals("blind")) {
                    System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
                    System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

                    //upologizw to score tou paikth gia auto to round
                    RoundScoreListAdapter2.rd.get(i).setScore((RoundScoreListAdapter2.rd.get(i).getFirst() + RoundScoreListAdapter2.rd.get(i).getSecond() + RoundScoreListAdapter2.rd.get(i).getThird())); //todo na rwthsw an edw /3? xwris to hdcp
                    RoundScoreListAdapter2.rd.get(i).setUpdated_at(Calendar.getInstance().getTime());
                    bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));
                    bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
                    System.out.println("1 rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " score " + RoundScoreListAdapter2.rd.get(i).getScore() + " avg " + RoundScoreListAdapter2.rd.get(i).getAvg() + " games " + RoundScoreListAdapter2.rd.get(i).getGames() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());
                }
                //upologizw to score tou paikth gia ola ta rounds mexri twra autou tou champ //todo pins
             /*   int i2 = i;
                bowlingViewModel.getAllDoneRound_detailofplayerofChamp(RoundScoreListAdapter2.editModelArrayList.get(i).getUuid(),champuuid).observe(this, new Observer<List<Round_detail>>() {
                    @Override
                    public void onChanged(List<Round_detail> allrdchamp) {
                        float avg=0;
                        int games = (3*allrdchamp.size()) + 3;//+3 gia ta paixnidia aftou tou round, afou den epistrefetai apo to query giati den exei ginei upadate(roud), ginetai sto roundActivity
                        System.out.println("1games a "+games);
                        for(int j=0;j<allrdchamp.size();j++){
                            avg+=allrdchamp.get(j).getScore(); //ta score twn prohgoumenwn rounds
                        }
                        avg+=(RoundScoreListAdapter2.rd.get(i2).getFirst()+ RoundScoreListAdapter2.rd.get(i2).getSecond()+ RoundScoreListAdapter2.rd.get(i2).getThird()); //ta score aftou tou round
                        if(games!=0) {
                            avg = avg / (3 * games);
                        }
                        System.out.println("1games b "+games+ " avg "+avg);
                        RoundScoreListAdapter2.rd.get(i2).setAvg(avg);
                        RoundScoreListAdapter2.rd.get(i2).setGames(games);
                        bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i2));
                    }
                }); */

                //to idio k gia team2
                if (!RoundScoreListAdapterTeam2.editModelArrayList.get(i).getUuid().equals("blind")) {
                    RoundScoreListAdapterTeam2.rd.get(i).setScore((RoundScoreListAdapterTeam2.rd.get(i).getFirst() + RoundScoreListAdapterTeam2.rd.get(i).getSecond() + RoundScoreListAdapterTeam2.rd.get(i).getThird()));
                    RoundScoreListAdapterTeam2.rd.get(i).setUpdated_at(Calendar.getInstance().getTime());
                    bowlingViewModel.update(RoundScoreListAdapterTeam2.editModelArrayList.get(i));
                    bowlingViewModel.update(RoundScoreListAdapterTeam2.rd.get(i));
                    System.out.println("2 rd: " + i + " rid " + RoundScoreListAdapterTeam2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapterTeam2.rd.get(i).getParticipant_uuid() + " score " + RoundScoreListAdapterTeam2.rd.get(i).getScore() + " avg " + RoundScoreListAdapterTeam2.rd.get(i).getAvg() + " h " + RoundScoreListAdapterTeam2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapterTeam2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapterTeam2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapterTeam2.rd.get(i).getThird());
                }
               /* int i3 = i;
                bowlingViewModel.getAllDoneRound_detailofplayerofChamp(RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getUuid(),champuuid).observe(this, new Observer<List<Round_detail>>() {
                    @Override
                    public void onChanged(List<Round_detail> allrdchamp) {
                        float avg=0;
                        int games = 3*allrdchamp.size()+3;
                        System.out.println("2games "+games);
                        for(int j=0;j<allrdchamp.size();j++){
                            avg+=allrdchamp.get(j).getScore();
                        }
                        avg+=(RoundScoreListAdapterTeam2.rd.get(i3).getFirst()+ RoundScoreListAdapterTeam2.rd.get(i3).getSecond()+ RoundScoreListAdapterTeam2.rd.get(i3).getThird());
                        if(games!=0) {
                            avg = avg / (games);
                        }
                        System.out.println("2games b "+games+ " avg "+avg);
                        RoundScoreListAdapterTeam2.rd.get(i3).setAvg(avg);
                        RoundScoreListAdapterTeam2.rd.get(i3).setGames(games);
                        bowlingViewModel.update(RoundScoreListAdapterTeam2.rd.get(i3));
                    }
                }); */

            }


            //test
            if (team2 != null) {
                System.out.println(" sto edit, tema2 " + team2.getFTeamID());
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("selTeam", (Serializable) team1);
            resultIntent.putExtra("team2", (Serializable) team2);
            resultIntent.putExtra("round2", (Serializable) r);
            resultIntent.putExtra("score1", score1); //to score ths selected team
            resultIntent.putExtra("score2", score2);
            setResult(RESULT_OK, resultIntent);
            finish();

        }else{
            Toast.makeText(
                    getApplicationContext(),
                    "You have to calculate the score first",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void exitActivity(View View) {
finish();
    }

    public void export(View view) {
        if(calc_pressed==1){
            if(checkboxhdcp.isChecked()){
                sum_hdcp1=0;
                for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                    sum_hdcp1+=RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp();
                }
                sum_hdcp2=0;
                for (int i = 0; i < RoundScoreListAdapterTeam2.editModelArrayList.size(); i++) {
                    sum_hdcp2+=RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp();
                }
            }
        StringBuilder data=  exp.exportRoundEditScore(championship,r,team1,team2,RoundScoreListAdapter2.editModelArrayList,RoundScoreListAdapterTeam2.editModelArrayList,RoundScoreListAdapter2.rd,RoundScoreListAdapterTeam2.rd,first_sum1,second_sum1,third_sum1,sum_hdcp1,first_sum2,second_sum2,third_sum2,sum_hdcp2);
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
        }else{
            Toast.makeText(
                    getApplicationContext(),
                    "You have to calculate the score first",
                    Toast.LENGTH_LONG).show();
        }
    }

}
