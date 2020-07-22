package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//UNDER CONSTRUCTION
//o user 8a kanei edit ena palio round enos sugkekrimenou champ
//8a pairnei to champ, to round kai thn omada (h' omades)
//8a upologizei 3ana ta score twn omadwn
//8a aferei apo to champ_detail.getScore ths omadas to palio score pou einai to round.getpoints
//kai 8a pros8etei sto champ_detail.getScore ths omadas to nei upologismeno score (pou 8a mpainei pleon sto round.getpoints, 8a allazei kai to round.score antistoixa)
//epishw 8a prepei na ananewnei ola ta epomena round.getscore1 k round.getScore2
//8a allazei kai to round_detail.getHDCP tou paikth
public class EditDoneRound extends AppCompatActivity {

    private static TextView textTitle;
    private static TextView sumHDCP,sum1st,sum2nd,sum3rd,sumHDCP2,sum1st2,sum2nd2,sum3rd2, txtscore1, txtscore2, totalsum1, txtpoints1,totalsum2, txtpoints2;
    private static TextView team1txt, team2txt;
    private static LinearLayout team2header, team2sums,team2results;
    public static Team team1, team2; //team1: selected team, team2: h alla (ane3artitws pws einai ari8mimenes oi omades sto round object)
    public static Round r;
    public static String tuuid1, tuuid2;
    private int new_score1, new_score2;
    private BowlingViewModel bowlingViewModel;
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
        setContentView(R.layout.activity_edit_done_round);

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
        team2header= findViewById(R.id.team2header);
        team2sums= findViewById(R.id.team2sums);
        team2results= findViewById(R.id.team2results);
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

            if (championship.getType()==2){ //if it's teams vs teams
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

        }
        System.out.println("Team selected: "+team1.getFTeamID()+" sys "+team1.getSys_teamID()+" uuid "+team1.getUuid());
        team1txt.setText("Team "+team1.getTeamName() );
        textTitle.setText("Round "+r.getFroundid());
        textTitle.append("\nLanes:"+r.getLanes());
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new RoundScoreListAdapter2(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (championship.getType()==2) { //if it's teams vs teams
            team2header.setVisibility(View.VISIBLE);
            team2sums.setVisibility(View.VISIBLE);
            team2results.setVisibility(View.VISIBLE);
            RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
            blistAdapter2 = new RoundScoreListAdapterTeam2(this);
            recyclerView2.setAdapter(blistAdapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            recyclerView2.setVisibility(View.VISIBLE);

            //get Team 2 by tuuid2
            bowlingViewModel.getTeamfromUUID(tuuid2).observe(this, new Observer<Team>() {
                @Override
                public void onChanged(Team te) {
                    team2=te;

                }
            });

            //get players of team2
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

            //pairnw to champ_detail ths omadas 2 gia na dw to score ths
            bowlingViewModel.getChamp_detailofTeamandChamp(tuuid2, champuuid).observe(this, new Observer<Championship_detail>() {
                @Override
                public void onChanged(Championship_detail c2) {
                    cd2 = c2;
                    System.out.println("2 exei score=" + c2.getScore());
                }
            });

        } else {
            team2header.setVisibility(View.GONE);
            team2sums.setVisibility(View.GONE);
            team2results.setVisibility(View.GONE);
        }


        //get players of team1
        bowlingViewModel.getAllPlayersofTeam3(tuuid1, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                System.out.println("Proble size "+part.size());
                blistAdapter.setBowls(part);
                blistAdapter.setRound(r);
                blistAdapter.setChamp(championship);
                blistAdapter.setCheckboxHDCPVisibility(false);
                ArrayList<Round_detail> rd = new ArrayList<>(); //fixme na ta pairnw live apo to viewmodel
                for (int i = 0; i < part.size(); i++) {
                    Round_detail round_detail= new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0,part.get(i).getHdcp(), 0,champuuid,r.getFroundid(), Calendar.getInstance().getTime() );
                    System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ part.get(i).getFirstName());
                    rd.add(round_detail);
                }
                blistAdapter.setRound_detail(rd);

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

    }

    public void calculateScore(View View) {
        //gia tin omada 1
        new_score1 = cd1.getScore()-r.getPoints1(); //meta //todo check it
        first_sum1 = 0;
        second_sum1 = 0;
        third_sum1 = 0;
        sum_hdcp1 = 0;
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) { //gia ka8e paikth
            System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
            System.out.println(" emda player " + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " + RoundScoreListAdapter2.rd.size() + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());

            first_sum1 += RoundScoreListAdapter2.rd.get(i).getFirst();
            second_sum1 += RoundScoreListAdapter2.rd.get(i).getSecond();
            third_sum1 += RoundScoreListAdapter2.rd.get(i).getThird();

            //auto-calculate to hdcp an afto einai 0 ston prwto gyro
            if (r.getFroundid() == 1 && RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() == 0) { //an einai o prwtos gyros kai to HDCP tou paikth einai 0 tote upologizw to hdcp apo tis korines pou ekane twra se afton ton gyro
                float first_avg = (RoundScoreListAdapter2.rd.get(i).getFirst() + RoundScoreListAdapter2.rd.get(i).getSecond() + RoundScoreListAdapter2.rd.get(i).getThird());
                int first_hdcp = RoundScoreListAdapter2.editModelArrayList.get(i).calculateHDCPofPlayer(RoundScoreListAdapter2.editModelArrayList.get(i), first_avg, championship, bowlingViewModel);
                RoundScoreListAdapter2.editModelArrayList.get(i).setHdcp(first_hdcp);
                RoundScoreListAdapter2.rd.get(i).setHdcp(first_hdcp);
                System.out.println("First hdcp =" + first_hdcp);
            }
            sum_hdcp1 += RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp();

            //upologizw to score tou paikth gia ola ta rounds mexri twra autou tou champ (an den einai blind)
            if (RoundScoreListAdapter2.rd.get(i).getBlind() == 0 && !RoundScoreListAdapter2.editModelArrayList.get(i).getUuid().equals("blind")) {
                int i2 = i;
                bowlingViewModel.getAllDoneRound_detailofplayerofChamp(RoundScoreListAdapter2.editModelArrayList.get(i).getUuid(), champuuid).observe(this, new Observer<List<Round_detail>>() {
                    @Override
                    public void onChanged(List<Round_detail> allrdchamp) {

                        float avg = 0;
                        //int games = (3 * allrdchamp.size()) + 3;//+3 gia ta paixnidia aftou tou round, afou den epistrefetai apo to query giati den exei ginei upadate(roud), ginetai sto roundActivity //to allrounds epistrefei ta done rounds, ara kai afta pou o paikths htan blind, opote einai la8os
                        int games = 3; //aftou tou round
                        System.out.println("1games a " + games + " pl " + RoundScoreListAdapter2.editModelArrayList.get(i2).getFullName() + " " + RoundScoreListAdapter2.editModelArrayList.get(i2).getUuid());
                        for (int j = 0; j < allrdchamp.size(); j++) {
                            if (allrdchamp.get(j).getBlind() == 0) {
                                avg += allrdchamp.get(j).getScore(); //ta score twn prohgoumenwn rounds
                                games += 3; //ka8e round pou epai3e (dld den htan blind)
                            }
                        }
                        System.out.println("1 prohgoumena score " + avg);
                        avg += (RoundScoreListAdapter2.rd.get(i2).getFirst() + RoundScoreListAdapter2.rd.get(i2).getSecond() + RoundScoreListAdapter2.rd.get(i2).getThird()); //ta score aftou tou round
                        System.out.println("1 prohgoume+afto to score " + avg);
                        if (games != 0) {
                            avg = avg / games;
                        }

                        //ypologismos hdcp//todo otan kanw check kai meta 3echeck den pairnei to hdcp pou evale o xrhsth
                        System.out.println("1 hdcp champ.tav " + championship.getHdcp_tav());
                        if (RoundScoreListAdapter2.rd.get(i2).getChecked_auto_calc_hdcp() != null && RoundScoreListAdapter2.rd.get(i2).getChecked_auto_calc_hdcp().equals("yes")) {
                            int hdcp = RoundScoreListAdapter2.editModelArrayList.get(i2).calculateHDCPofPlayer(RoundScoreListAdapter2.editModelArrayList.get(i2), avg, championship, bowlingViewModel);
                            //  RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                            //RoundScoreListAdapter2.editModelArrayList.get(i2).setHdcp(hdcp);
                            RoundScoreListAdapter2.rd.get(i2).setAuto_calc_newHdcp(hdcp);
                            System.out.println("hdcp e3w meta rd.hdcp_neo=" + RoundScoreListAdapter2.rd.get(i2).getAuto_calc_newHdcp() + " player.hdcp=" + RoundScoreListAdapter2.editModelArrayList.get(i2).getHdcp());
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
                        System.out.println("1games b " + games + " avg " + avg + " hdcp " + RoundScoreListAdapter2.rd.get(i2).getHdcp());
                        RoundScoreListAdapter2.rd.get(i2).setAvg(avg);
                        RoundScoreListAdapter2.rd.get(i2).setGames(games);
                        RoundScoreListAdapter2.editModelArrayList.get(i2).setTotal_games(3 + RoundScoreListAdapter2.editModelArrayList.get(i2).getTotal_games()); //ola ta prohgoumena games ever pou eixe o paikths + ta 3 aftou tou gyrou
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
        sum3rd.setText(String.valueOf(third_sum1));
        totalsum1.setText("Total Sum: " + (first_sum1 + second_sum1 + third_sum1));

        if (championship.getType() == 2) {
            //gia tin omada 2
            new_score2 = cd2.getScore()-r.getPoints2();
        System.out.println("new_score2: " + new_score2);
        first_sum2 = 0;
        second_sum2 = 0;
        third_sum2 = 0;
        sum_hdcp2 = 0;
        for (int i = 0; i < RoundScoreListAdapterTeam2.editModelArrayList.size(); i++) { //todo mporw na to valw sthn panw epanalhpsh
            System.out.println(team1txt.getText() + " " + RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
            System.out.println(" emda player " + " id " + RoundScoreListAdapterTeam2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " + RoundScoreListAdapterTeam2.rd.size() + " rid " + RoundScoreListAdapterTeam2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapterTeam2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapterTeam2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapterTeam2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapterTeam2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapterTeam2.rd.get(i).getThird());
            first_sum2 += RoundScoreListAdapterTeam2.rd.get(i).getFirst();
            second_sum2 += RoundScoreListAdapterTeam2.rd.get(i).getSecond();
            third_sum2 += RoundScoreListAdapterTeam2.rd.get(i).getThird();

            if (r.getFroundid() == 1 && RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp() == 0) { //an einai o prwtos gyros kai to HDCP tou paikth einai 0 tote upologizw to hdcp apo tis korines pou ekane twra se afton ton gyro
                float first_avg = (RoundScoreListAdapterTeam2.rd.get(i).getFirst() + RoundScoreListAdapterTeam2.rd.get(i).getSecond() + RoundScoreListAdapterTeam2.rd.get(i).getThird());
                int first_hdcp = RoundScoreListAdapterTeam2.editModelArrayList.get(i).calculateHDCPofPlayer(RoundScoreListAdapterTeam2.editModelArrayList.get(i), first_avg, championship, bowlingViewModel);
                RoundScoreListAdapterTeam2.editModelArrayList.get(i).setHdcp(first_hdcp);
                RoundScoreListAdapterTeam2.rd.get(i).setHdcp(first_hdcp);
            }
            sum_hdcp2 += RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp();

            // //upologizw to score tou paikth gia ola ta rounds mexri twra autou tou champ //todo pins

            if (RoundScoreListAdapterTeam2.rd.get(i).getBlind() == 0) {
                int i3 = i;
                bowlingViewModel.getAllDoneRound_detailofplayerofChamp(RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getUuid(), champuuid).observe(this, new Observer<List<Round_detail>>() {
                    @Override
                    public void onChanged(List<Round_detail> allrdchamp2) {
                        float avg2 = 0;
                        //int games2 = 3 * allrdchamp2.size() + 3;
                        int games2 = 3;
                        System.out.println("2games " + games2 + " player " + RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getUuid() + RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getFullName());
                        for (int j = 0; j < allrdchamp2.size(); j++) {
                            if(!allrdchamp2.get(j).getRound_uuid().equals(r.getRounduuid())) { //gia na mhn metrhsei 2 fores afto to round pou kanoume edit (afou einai hdh done)
                                System.out.println("2 prohgoumena score " + allrdchamp2.get(j).getScore());
                                if (allrdchamp2.get(j).getBlind() == 0) {
                                    avg2 += allrdchamp2.get(j).getScore();
                                    games2 += 3;
                                }
                            }
                        }
                        avg2 += (RoundScoreListAdapterTeam2.rd.get(i3).getFirst() + RoundScoreListAdapterTeam2.rd.get(i3).getSecond() + RoundScoreListAdapterTeam2.rd.get(i3).getThird());
                        System.out.println(" afto to score  1st " + RoundScoreListAdapterTeam2.rd.get(i3).getFirst() + " 2nd " + RoundScoreListAdapterTeam2.rd.get(i3).getSecond() + " 3rd " + RoundScoreListAdapterTeam2.rd.get(i3).getThird());

                        System.out.println("2 prohgoume+afto to score " + avg2);
                        if (games2 != 0) {
                            avg2 = avg2 / (games2);
                        }

                        //ypologismos hdcp
                        if (RoundScoreListAdapterTeam2.rd.get(i3).getChecked_auto_calc_hdcp() != null && RoundScoreListAdapterTeam2.rd.get(i3).getChecked_auto_calc_hdcp().equals("yes")) { //an einai checked
                            int hdcp = RoundScoreListAdapter2.editModelArrayList.get(i3).calculateHDCPofPlayer(RoundScoreListAdapterTeam2.editModelArrayList.get(i3), avg2, championship, bowlingViewModel);
                            //  RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                            //RoundScoreListAdapterTeam2.editModelArrayList.get(i3).setHdcp(hdcp); sto telos- opennewactivity
                            RoundScoreListAdapterTeam2.rd.get(i3).setAuto_calc_newHdcp(hdcp); //to neo hdcp

                        }
                        System.out.println("2games b " + games2 + " avg " + avg2);
                        RoundScoreListAdapterTeam2.rd.get(i3).setAvg(avg2);
                        RoundScoreListAdapterTeam2.rd.get(i3).setGames(games2);
                        RoundScoreListAdapterTeam2.editModelArrayList.get(i3).setTotal_games(3 + RoundScoreListAdapterTeam2.editModelArrayList.get(i3).getTotal_games());
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
        sum3rd2.setText(String.valueOf(third_sum2));
        totalsum2.setText("Total Sum: " + (first_sum2 + second_sum2 + third_sum2));

        //pontoi
        int pontoi1 = 0;
        int pontoi2 = 0;
        //player vs player/game
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
            //ka8e omada 3ekinaei se ka8e champ apo to 0
            //upologizei to ka8e first,second,third 3exwrista
            if (RoundScoreListAdapter2.rd.get(i).getFirst() > RoundScoreListAdapterTeam2.rd.get(i).getFirst()) {
                pontoi1 += 2;
            } else if (RoundScoreListAdapter2.rd.get(i).getFirst() < RoundScoreListAdapterTeam2.rd.get(i).getFirst()) {
                pontoi2 += 2;
            } else {
                //todo isopalia posoi pontoi?
                pontoi1 += 2;
                pontoi2 += 2;
            }
            if (RoundScoreListAdapter2.rd.get(i).getSecond() > RoundScoreListAdapterTeam2.rd.get(i).getSecond()) {
                pontoi1 += 2;
            } else if (RoundScoreListAdapter2.rd.get(i).getSecond() < RoundScoreListAdapterTeam2.rd.get(i).getSecond()) {
                pontoi2 += 2;
            } else {
                //todo isopalia posoi pontoi?
                pontoi1 += 2;
                pontoi2 += 2;
            }
            if (RoundScoreListAdapter2.rd.get(i).getThird() > RoundScoreListAdapterTeam2.rd.get(i).getThird()) {
                pontoi1 += 2;
            } else if (RoundScoreListAdapter2.rd.get(i).getThird() < RoundScoreListAdapterTeam2.rd.get(i).getThird()) {
                pontoi2 += 2;
            } else {
                //todo isopalia posoi pontoi?
                pontoi1 += 2;
                pontoi2 += 2;
            }
        }
        //sunolo korinwn ana paixnidi // me hdcp
        if (first_sum1 > first_sum2) {
            pontoi1 += 3;
        } else if (first_sum1 < first_sum2) {
            pontoi2 += 3;
        } else {
            //todo isopalia?
            pontoi1 += 3;
            pontoi2 += 3;
        }
        if (second_sum1 > second_sum2) {
            pontoi1 += 3;
        } else if (second_sum1 < second_sum2) {
            pontoi2 += 3;
        } else {
            //todo isopalia?
            pontoi1 += 3;
            pontoi2 += 3;
        }
        if (third_sum1 > third_sum2) {
            pontoi1 += 3;
        } else if (third_sum1 < third_sum2) {
            pontoi2 += 3;
        } else {
            //todo isopalia?
            pontoi1 += 3;
            pontoi2 += 3;
        }
        //geniko sunolo korinwn
        if (first_sum1 + second_sum1 + third_sum1 > first_sum2 + second_sum2 + third_sum2) {
            pontoi1 += 5;
        } else if (first_sum1 + second_sum1 + third_sum1 < first_sum2 + second_sum2 + third_sum2) {
            pontoi2 += 5;
        } else {
            //isopalia
            pontoi1 += 5;
            pontoi2 += 5;
        }


        System.out.println("Prin new_score1: " + new_score1 + " new_score2: " + new_score2);
        new_score1 += pontoi1; // kalutera na krathsw sto rd.score to score tou gyrou kai sto team.score to sunolo twn pontwn ths omadas
        new_score2 += pontoi2;//todo na to valw sto opennewActivity?
        System.out.println("Meta new_score1: " + new_score1 + " new_score2: " + new_score2 + " points1 " + pontoi1 + " points2 " + pontoi2);
        //team1.setScore(new_score1);//todo na rwthsw
        // team2.setScore(new_score2);
        txtscore1.setText("Score: " + new_score1);
        txtscore2.setText("Score: " + new_score2);
        txtpoints1.setText("Points: " + pontoi1);
        txtpoints2.setText("Points: " + pontoi2);
        if (team1.getUuid().equals(r.getTeam1UUID())) {
            r.setScore1(new_score1);
            r.setScore2(new_score2);
            r.setPoints1(pontoi1);
            r.setPoints2(pontoi2);
        } else if (team1.getUuid().equals(r.getTeam2UUID())) {
            r.setScore1(new_score2);
            r.setScore2(new_score1);
            r.setPoints1(pontoi2);
            r.setPoints2(pontoi1);
        }

    }

        //bowlingViewModel.update(r); //todo svisto
        calc_pressed=1;
    }


}
