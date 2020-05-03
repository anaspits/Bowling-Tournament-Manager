package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//TODO /DONE!/ NA KANW EDITROUND H' mporw na xrhshmpoihsw to round pou pernaw apo to select, meta na parw th lista apo getRoundsofTeam, na to psa3w ekei mesa kai na vrw to epomeno round kai na to perasw sto epomeno Round
//TODO NA FTIAXW TA KOUMPIA EXPPORT KAI EXIT
public class RoundActivity extends AppCompatActivity implements RoundListAdapter.OnDeleteClickListener {
    public static final int UPDATE_SCORE_REQUEST_CODE = 10;
    public static int save_pressed = 0;
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs;//list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
    // public static ArrayList<Team> temp3; //lista me omades opou paizoun antipales oi omades se seiriakes theseis, dld 0-1, 2-3 klp
    private static TextView textTitle;
    private static Button nextRound_btn, exitRound_btn;
    private static EditText txvHDCP, txv1, txv2, txv3;
    private static TextView player_view, hdcp_view, sumHDCP, sum1st, sum2nd, sum3rd, sumHDCP2, sum1st2, sum2nd2, sum3rd2;
    private static TextView first;
    private static TextView second;
    private static TextView third;
    private static TextView player2_view;
    private static TextView hdcp2_view;
    private static TextView first2;
    private static TextView second2;
    private static TextView third2;
    private static TextView team1;
    private static TextView team2;
    public int bowlId;
    public static Team t, t2;
    public static Round r, r2;
    public static String tuuid;
    private int score1, score2; //todo
    private BowlingViewModel bowlingViewModel;
    private RoundListAdapter rlistAdapter;
    private RoundScoreListAdapter2 blistAdapter;
    private RoundScoreListAdapter2 blistAdapter2;
    private SelectRoundAdapter rscorelistAdapter;
    public String champuuid;
    public Championship championship;
    private Championship_detail cd, cd2;
    private int cds_count; //oles oi omades tou champ
    private int fin_cds_count; //oi omades tou champ pou teleiwsan ta rounds tous
    public static List<Round> rofTeam;
    public static List<Round> test;
    public static List<Round> test2;
    public static Round curRound;
    public static Round curRound2;
    public static String status_flag;
    public static List<Participant> part1; //apo livedata
    public static List<Participant> part2; //msw sunartisis


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        Bundle bundleObject = this.getIntent().getExtras();

    /*xwris vash     player_view=(TextView) findViewById(R.id.player);
        hdcp_view=(TextView) findViewById(R.id.hdcp_view);
        first=(TextView) findViewById(R.id.first_view);
        second=(TextView) findViewById(R.id.second_view);
        third=(TextView) findViewById(R.id.third_view);

        player2_view=(TextView) findViewById(R.id.player2);
        hdcp2_view=(TextView) findViewById(R.id.hdcp2_view);
        first2=(TextView) findViewById(R.id.first2_view);
        second2=(TextView) findViewById(R.id.second2_view);
        third2=(TextView) findViewById(R.id.third2_view); */

        textTitle = findViewById(R.id.textTitle);
        nextRound_btn = findViewById(R.id.nextRound_btn);
        exitRound_btn = findViewById(R.id.exitRound_btn);
//todo na emfanizei ta telika score twn omadwn
        //ROUNDEDITSCORE gia participants ths kathe omadas PART 0//
        team1 = (TextView) findViewById(R.id.team1);
        team2 = (TextView) findViewById(R.id.team2);
        sumHDCP = findViewById(R.id.sumHDCP);
        sum1st = findViewById(R.id.sum1st);
        sum2nd = findViewById(R.id.sum2nd);
        sum3rd = findViewById(R.id.sum3rd);
        sumHDCP2 = findViewById(R.id.sumHDCP2);
        sum1st2 = findViewById(R.id.sum1st2);
        sum2nd2 = findViewById(R.id.sum2nd2);
        sum3rd2 = findViewById(R.id.sum3rd2);
//ROUNDEDITSCORE gia participants ths kathe omadas PART 0//

        save_pressed = 0;
        System.out.println("arxh  ave_pressed = "+save_pressed);

        if (bundleObject != null) {
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers"); //axreiasto
            hdcp_parameters = (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            vs = (ArrayList<ArrayList>) bundleObject.getSerializable("vs");
            bowlId = bundleObject.getInt("bowlId"); //to sys tou team
            t = (Team) bundleObject.getSerializable("b_object");
            tuuid = t.getUuid();
            //score1 = t.getScore();
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();

            t2 = (Team) bundleObject.getSerializable("team2");
            if (t2 != null) {
                System.out.println("got team from edit" + t2.getFTeamID());
            }
            //PAS ROUND PART 4//
            r = (Round) bundleObject.getSerializable("round");
            if (r != null) {
                System.out.println("got round from sel " + r.getFroundid() + " uuid " + r.getRounduuid() + " stat " + r.getStatus() + " with t1: " + r.getTeam1ID() + " and t2: " + r.getTeam2ID() + " and sysID: " + r.getRoundid());
            } else {
                System.out.println("got round from sel ERROR");
            } //PAS ROUND PART 4//

            r2 = (Round) bundleObject.getSerializable("round2");
            if (r2 != null) {
                System.out.println("got round from edit " + r2.getFroundid() + " uuid " + r2.getRounduuid() + " stat " + r2.getStatus() + " with t1: " + r2.getTeam1ID() + " and t2: " + r2.getTeam2ID() + " and sysID: " + r2.getRoundid());
            } else {
                System.out.println("got round from edit ERROR");
            }
        }

        System.out.println("Team selected: " + t.getFTeamID() + " sys " + t.getSys_teamID() + " uuid " + t.getUuid());
        team1.setText("Team " + t.getTeamName());

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
        rlistAdapter = new RoundListAdapter(this, this);

        //ROUNDEDITSCORE gia participants ths kathe omadas PART1//
      /*  blistAdapter = new RoundScoreListAdapter2(this);
       // blistAdapter2 = new RoundScoreListAdapter2(this);
        recyclerView.setAdapter(blistAdapter);*/
        //RoundEditScore gia participants ths kathe omadas PART1//

        rscorelistAdapter = new SelectRoundAdapter(this);
        recyclerView.setAdapter(rscorelistAdapter); //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView2.setAdapter(blistAdapter2);
        // recyclerView2.setLayoutManager(new LinearLayoutManager(this));

//PAS ROUND PART 5//
       /* bowlingViewModel.getRoundsofTeam(t.getUuid(),champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> rounds) {
                    //rlistAdapter.setRounds(r);
                    rlistAdapter.returnRounds2(rounds);
                    // details.append("size of round of team 1: "+String.valueOf(r.size())+"\n");
                    System.out.println("size of round of team: "+rounds.size() +" status "+ rounds.get(0).getStatus() );
                    test = rounds;
                    test2 = rlistAdapter.returnRounds3(rounds);
                    System.out.println("test3 size of round of team: "+test.size() );
                // textTitle.append(String.valueOf(r.get(0).getFroundid()));
                System.out.println("FRoundid "+rounds.get(0).getFroundid() ); //ton prwto gyro
                for (int i = 0; i <rounds.size();i++) {
                    System.out.println("round, i= " +i+", round id " +rounds.get(i).getRounduuid() + " team1 id " + rounds.get(i).getTeam1ID() + " team2 id " +  rounds.get(i).getTeam2ID());
                }
            }
        });*/ //PAS ROUND PART 5//


        bowlingViewModel.getNextRoundofTeamofChamp(tuuid, champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> ro) {
                if (ro.size() != 0) {
                    rlistAdapter.setRounds(ro);
                    rscorelistAdapter.setSelRound(ro);//
                    rscorelistAdapter.setChamp(championship);//
                    rscorelistAdapter.setSelTeam(t);//
                    Round rou = ro.get(0);
                    rlistAdapter.returnCurrentRound(rou); //todo na svhsw
                    // textTitle.append(" "+String.valueOf(ro.get(0).getFroundid()));
                    System.out.println("kai to size einai " + ro.size());
                    System.out.println("Current Round of team " + t.getFTeamID() + " stat " + rou.getStatus() + " is round " + rou.getFroundid() + " with t1: " + rou.getTeam1ID() + " and t2: " + rou.getTeam2ID() + " and sysID: " + rou.getRoundid());
                    curRound2 = rou;
                    //curRound.setStatus("done");
                    if (tuuid.equals(rou.getTeam1UUID())) {
                        textTitle.append("\n" + "Team " + t.getTeamName() + " VS Team " + rou.getTeam2ID());
//                        team2.setText( "Team " + rou.getTeam2ID());
                    } else {
                        textTitle.append("\n" + "Team " + t.getTeamName() + " VS Team " + rou.getTeam1ID());
                        //team2.setText( "Team " + rou.getTeam1ID());
                    }
                    //r.setStatus("done");
                    //bowlingViewModel.update(curRound);
                }
            }
        });
//        System.out.println("2.5 Current Round of team "+t.getFTeamID()+" is round "+curRound.getFroundid()+" stat "+curRound.getStatus()+" with t1: "+curRound.getTeam1ID()+" and t2: "+ curRound.getTeam2ID()+" and sysID: "+curRound.getRoundid());
        System.out.println("dokimh " + rlistAdapter.mNotes.get(0).getFroundid() + " t1 " + rlistAdapter.mNotes.get(0).getTeam1ID() + " t2 " + rlistAdapter.mNotes.get(0).getTeam2ID() + " size " + rlistAdapter.mNotes.size());


        //pairnw to champ_detail ths omadas 1 team gia na dw to active_flag
        bowlingViewModel.getChamp_detailofTeamandChamp(tuuid, champuuid).observe(this, new Observer<Championship_detail>() {
            @Override
            public void onChanged(Championship_detail c) {
                cd = c;
                System.out.println("1 exei active flag=" + c.getActive_flag()+"kai score "+c.getScore());
                if(cd.getActive_flag()==1){
                    nextRound_btn.setText("Finish");
                    exitRound_btn.setVisibility(View.GONE);
                }
            }
        });

        //checkarw an oles oi omades tou champ exount teleiwsei me ta rounds
        //prwts pairnw oles tis omades tou champ
        bowlingViewModel.getChamp_detailofChamp(champuuid).observe(this, new Observer<List<Championship_detail>>() { //axristo-test
            @Override
            public void onChanged(List<Championship_detail> cs) {
                cds_count = cs.size();
                System.out.println("cds_count=" + cds_count);
            }
        });
        //kai meta pairnw tis omades tou champ me active_flag=1, dld oses teleiwsan kai tsekarw an o arithmos tous einai isos me ton ar8mo olwn twn omadwn
        bowlingViewModel.getChamp_detailofChampofFinnishedTeams(champuuid).observe(this, new Observer<List<Championship_detail>>() { //axristo-test
            @Override
            public void onChanged(List<Championship_detail> cs) {
                fin_cds_count = cs.size();
                System.out.println("fin_cds_count=" + fin_cds_count);
            }
        });


        //ROUNDEDITSCORE gia participants ths kathe omadas PART2//
/*        bowlingViewModel.getAllPlayersofTeam3(tuuid, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setBowls(part);
//                System.out.println("r id here "+r.getFroundid());
               // blistAdapter.setRound(rlistAdapter.mNotes.get(0)); //fixme
                blistAdapter.setRound(r);
               // r=rlistAdapter.mNotes.get(0);
                  ArrayList<Round_detail> rd = new ArrayList<>();
                for (int i = 0; i < part.size(); i++) {
                    Round_detail round_detail= new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0,part.get(i).getHdcp() );
                    System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ part.get(i).getFN());
                    rd.add(round_detail);
                }
                blistAdapter.setRound_detail(rd);
                System.out.println("part size "+part.size());
                part2=part;
                //blistAdapter.returnParticiapants(part);
               // System.out.println("part 1 ="+part1.size());
                System.out.println("part 2 ="+part2.size());

             /*   txvHDCP=(EditText) findViewById(R.id.txvHDCP);
                txv1 =(EditText) findViewById(R.id.txv1);
                txv2 =(EditText) findViewById(R.id.txv2);
                txv3 =(EditText) findViewById(R.id.txv3);
                int sum =0;
                int sumhdcp=0;
                for (int i=0;i<part.size();i++){
                    String updatedHdcp = txvHDCP.getText().toString().trim();
                    String updated1 = txv1.getText().toString().trim();
                    String updated2 = txv2.getText().toString().trim();
                    String updated3 = txv3.getText().toString().trim();

                    part1.get(i).setHdcp(Integer.parseInt(updatedHdcp));
                    sum = sum+Integer.parseInt(updated1)+Integer.parseInt(updated2)+Integer.parseInt(updated3)+ Integer.parseInt(updatedHdcp);
                    sumhdcp =Integer.parseInt(updatedHdcp);
                    //todo na rwthsw an to avg einai to score tou paikth se kathe guro
                }
                if (tuuid.equals(curRound.getTeam1UUID())) {
                    curRound.setScore1(sum);
                    System.out.println("score 1 ="+curRound.getScore1());
                } else {
                    curRound.setScore2(sum);
                    System.out.println("score 2 ="+curRound.getScore2());
                } */

         /*  }
        }); */
        //ROUNDEDITSCORE gia participants ths kathe omadas PART2//

       /*na svisw bowlingViewModel.getAllPlayersofOpositeTeam(tuuid, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
               // blistAdapter2.setBowls(part);
                System.out.println("oponents "+part.size());
                System.out.println("oponents "+part.get(0).getFullName());
            }
        }); */

        //round();

        //test
    /*    for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++){

            team1.setText(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));

        } */
        //

        //test rd
        bowlingViewModel.getAllRound_detail().observe(this, new Observer<List<Round_detail>>() { //axristo-test
            @Override
            public void onChanged(List<Round_detail> rds) {
                System.out.println("rds size " + rds.size());

                for (int i = 0; i < rds.size(); i++) {
                    System.out.println("rd " + i + " round id " + rds.get(i).getRound_uuid() + " player id " + rds.get(i).getParticipant_uuid() + " score "+rds.get(i).getScore()+" h " + rds.get(i).getHdcp() + " firste " + rds.get(i).getFirst() + " second " + rds.get(i).getSecond() + " third " + rds.get(i).getThird());
                }
            }
        });

        //test round
        bowlingViewModel.getAllRound().observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> t) {
                for (int i = 0; i < t.size(); i++) {
                    System.out.println("round " + i + " round id " + t.get(i).getFroundid() + " status "+t.get(i).getStatus()+" team1 " + t.get(i).getTeam1ID() + " score1 " + t.get(i).getScore1() + " team2 " + t.get(i).getTeam2ID() + " score2 " + t.get(i).getScore2());
                }

            }
        });

    }

    public void round() { //xwris vash

       /* gia temp3
       Team t1 = vs.get(0);
        Team t2 = vs.get(1);
        player_view.setText("Team: "+t1.getFTeamID());
        hdcp_view.setText("Team: "+t2.getFTeamID()); */

        //gia vs
        ArrayList<Team> temp = vs.get(0);
        Team t1 = temp.get(0);
        Team t2 = temp.get(1);
        team1.setText("Team: " + t1.getFTeamID());
        team2.setText("Team: " + t2.getFTeamID());

        ArrayList<Participant> teamates = t1.getTeammates();
        int i;
        for (i = 0; i < teamates.size(); i++) {

            player_view.append(teamates.get(i).getFN() + "\n");
            hdcp_view.append(teamates.get(i).bowlAvg + "\n");

        }
        teamates = t2.getTeammates();
        for (i = 0; i < teamates.size(); i++) {

            player2_view.append(teamates.get(i).getFN() + "\n");
            hdcp2_view.append(teamates.get(i).bowlAvg + "\n");

        }
    }


    public static void getRoundsofTeam(List<Round> r) { //ola ta rounds
        rofTeam = r;
        System.out.println("roundactivity rofteam " + rofTeam.size());
    }

    public static void getRoundofTeam(Round r) { //to currentRound //todo na to svhsw kai osa kanw st sunarthsh na ta kanw  sto opennewactivity h pio panw
        curRound = r;
        textTitle.setText("Round " + String.valueOf(r.getFroundid()));
        System.out.println("2 Current Round of team " + t.getFTeamID() + " is round " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());
        status_flag = r.getStatus();
        System.out.println("2 r.getstatus=flag= " + r.getStatus());
        if (status_flag.equals("last")) {
            nextRound_btn.setText("Finish"); //to kanw pio panw
            exitRound_btn.setVisibility(View.GONE);
        }
        curRound.setStatus("done");
    }

    public static void getParticipantsofTeam(List<Participant> p) {
        part1 = p;
    } //axristo

    //ROUNDEDITSCORE gia participants ths kathe omadas PART3//
 /*   public void calculateScore(View View) {
        //gia tin omada 1
        ArrayList<Integer> first1= new ArrayList<>(); //ta vazw se pinakes gia na exw ta score tou kate paikth gia na kanw meta tis sugkriseis gia tous vathmous ths oamadas
        ArrayList<Integer> second1= new ArrayList<>();
        ArrayList<Integer> third1= new ArrayList<>();
        int first_sum1=0;
        int second_sum1=0;
        int third_sum1=0;
        int sum_hdcp1=0;
        int[] sum={0,0,0};
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++){
            // team1.setText(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(" emda player "  +" id "+RoundScoreListAdapter2.editModelArrayList.get(i).getUuid()+" hdcp "+RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

           // bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));
           // bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
            System.out.println(" rd size " +RoundScoreListAdapter2.rd.size()+" rid "+RoundScoreListAdapter2.rd.get(i).getRound_uuid()+" pid "+RoundScoreListAdapter2.rd.get(i).getParticipant_uuid()+" h "+ RoundScoreListAdapter2.rd.get(i).getHdcp() +" 1st "+ RoundScoreListAdapter2.rd.get(i).getFirst()+" 2nd "+ RoundScoreListAdapter2.rd.get(i).getSecond()+" 3rd "+ RoundScoreListAdapter2.rd.get(i).getThird());

           /* first1.add(Integer.parseInt(RoundScoreListAdapter2.edited[0])); //to first tou paikth i //todo ta 8elw gia sugkriseis me tous antipalous
            second1.add(Integer.parseInt(RoundScoreListAdapter2.edited[1])); //to second tou paikth i
            third1.add(Integer.parseInt(RoundScoreListAdapter2.edited[2])); */
       /*     first_sum1 += RoundScoreListAdapter2.rd.get(i).getFirst();
            second_sum1 +=  RoundScoreListAdapter2.rd.get(i).getSecond();
            third_sum1 +=  RoundScoreListAdapter2.rd.get(i).getThird();
            sum_hdcp1 +=RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp();
        }
        first_sum1 += sum_hdcp1;
        second_sum1 += sum_hdcp1;
        third_sum1 += sum_hdcp1;
        sumHDCP.setText(String.valueOf(sum_hdcp1));
        sum1st.setText(String.valueOf(first_sum1));
        sum2nd.setText(String.valueOf(second_sum1));
        sum3rd.setText(String.valueOf(third_sum1 ));

    }*/ //ROUNDEDITSCORE gia participants ths kathe omadas PART3//


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);

        System.out.println("GOT from editrounscore: ");
        if (requestCode == UPDATE_SCORE_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundleObject = resultData.getExtras();
            if (bundleObject != null) {
                t = (Team) bundleObject.getSerializable("selTeam");
                t2 = (Team) bundleObject.getSerializable("team2");
                r2 = (Round) bundleObject.getSerializable("round2");
                curRound=r2;
                score1 = bundleObject.getInt("score1");
                score2 = bundleObject.getInt("score2");

                System.out.println("GOT from editrounscore: ");
                System.out.println("SCORE1: "+score1+" SCORE2 "+score2 ); //fixme poio score einai poias omadas? pairniountai la8os
                System.out.println("ROUND " + r2.getFroundid() + " sid " + r2.getRounduuid() + " status " + r2.getStatus() + " t1: " + r2.getTeam1ID() + " score " + r2.getScore1() + " t2: " + r2.getTeam2ID() + " score " + r2.getScore2() + " uuid " + r2.getRounduuid());
                System.out.println("AND t1 " + t.getFTeamID() + " sid " + t.getSys_teamID() + " score " + t.getScore() + " uuid " + t.getUuid()); //fixme
                System.out.println("AND t2 " + t2.getFTeamID() + " sid " + t2.getSys_teamID() + " score " + t2.getScore() + " uuid " + t2.getUuid());
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();
                save_pressed = 1;
                System.out.println("GOT save_pressed="+save_pressed);
                //pairnw to champ_detail ths omadas 2 team gia na dw to active_flag //todo na to valw sto onresult
                if (t2 != null) {
                    bowlingViewModel.getChamp_detailofTeamandChamp(t2.getUuid(), champuuid).observe(this, new Observer<Championship_detail>() {
                        @Override
                        public void onChanged(Championship_detail c) {
                            cd2 = c;
                            System.out.println("2 exei active flag=" + c.getActive_flag());
                        }
                    });
                }
            }
        }
    }


    public void openNewActivity(View View) {
        //String button_text;
        // button_text =((Button)View).getText().toString();

        //ROUNDEDITSCORE gia participants ths kathe omadas PART4//
      /*  for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
            System.out.println(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
            System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
            bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));
            bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
            System.out.println(" rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());
        }*/
//ROUNDEDITSCORE gia participants ths kathe omadas PART4//

// PASS ROUND PART 6//
/*        System.out.println("list of rounds: test size "+test.size()+ " me gt(0)= "+test.get(0).getRounduuid());
        System.out.println(" currRound "+curRound.getRounduuid()+" status "+curRound.getStatus()+" and sysID: " + curRound.getRoundid());
        System.out.println(" selround "+r.getRounduuid()+" status "+r.getStatus()+" and sysID: " + r.getRoundid());

        Round passr=r;
for (int i=0;i<test.size();i++){
    if(r.getRounduuid().equals(test.get(i).getRounduuid())){
        System.out.println("Mesa stin if: currRound "+r.getRounduuid()+" = "+test.get(i).getRounduuid()+" status "+r.getStatus()+" and sysID: " + r.getRoundid());
        if(test.get(i).getStatus()!="last") {
            passr = test.get(i + 1);
            System.out.println("Meta selround "+passr.getFroundid()+" uuid "+passr.getRounduuid()+" status "+passr.getStatus() +" t1: "+passr.getTeam1ID()+" t2: "+passr.getTeam2ID()+" and sysID: " + passr.getRoundid());

        }
    break;
    }
}*///PASS ROUND PART 6//
        System.out.println("save_pressed="+save_pressed);
        if (save_pressed == 1){
            if(t.getUuid().equals(curRound.getTeam1UUID())){
                cd.setScore(score1);
                cd2.setScore(score2);
            } else if(t.getUuid().equals(curRound.getTeam2UUID())){
                cd.setScore(score2);
                cd2.setScore(score1);
            }
            bowlingViewModel.update(t);
            bowlingViewModel.update(t2);
          //  cd.setScore(score1); to kanw sto onresult
           // cd2.setScore(score2); to kanw sto onresult
            System.out.println(" lol dokimh " + RoundListAdapter.mNotes.get(0).getFroundid() + " t1 " + RoundListAdapter.mNotes.get(0).getTeam1ID() + " t2 " + RoundListAdapter.mNotes.get(0).getTeam2ID());
        System.out.println("3 Current Round of team " + t.getFTeamID() + " is currround2 " + curRound2.getFroundid() + " with t1: " + curRound2.getTeam1ID() + " and t2: " + curRound2.getTeam2ID() + " and sysID: " + curRound2.getRoundid());
        System.out.println("4 Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is curround " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid()+" score1 "+curRound.getScore1()+" score2 "+curRound.getScore2());
      //  if (r2 != null)
       //     System.out.println("4.5 Current Round of team " + t.getFTeamID() + " stat " + r2.getStatus() + " is r2 " + r2.getFroundid() + " with t1: " + r2.getTeam1ID() + " and t2: " + r2.getTeam2ID() + " and sysID: " + r2.getRoundid()+ " and sysID: " + r2.getRoundid()+" score1 "+r2.getScore1()+" score2 "+r2.getScore2());
        System.out.println("stat " + status_flag);

      /*  System.out.println("1 prin flag= "+cd.getActive_flag());
        int a=cd.getActive_flag()-1;
        cd.setActive_flag(a);
        System.out.println("1 meta flag= "+cd.getActive_flag());
        bowlingViewModel.update(cd);//fixme mhpws na to valw sto roundeditscoreactivity? */

            if (cd.getActive_flag() > 0) {
                System.out.println("1 prin flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
                cd.setActive_flag(cd.getActive_flag() - 1);
                System.out.println("1 meta flag= " + cd.getActive_flag());
                bowlingViewModel.update(cd);
                System.out.println("1 meta flag:  fin_cds_count=" + fin_cds_count);
                if( cd.getActive_flag()==0){
                    fin_cds_count++;
                    System.out.println("1 meta flag mesa if:  fin_cds_count=" + fin_cds_count);
                }
            } else if (cd.getActive_flag() == 0) {
                System.out.println("1 flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
                bowlingViewModel.update(cd);
            } else {
                System.out.println("1 PROBLEM me flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
                bowlingViewModel.update(cd);
            }
            if (t2 != null) {
                if (cd2.getActive_flag() > 0) {
                    System.out.println("2 prin flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID()); //team2
                    cd2.setActive_flag(cd2.getActive_flag() - 1);
                    System.out.println("2 meta flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID());
                    bowlingViewModel.update(cd2);
                    if( cd2.getActive_flag()==0){
                        fin_cds_count++;
                        System.out.println("2 meta flag mesa if:  fin_cds_count=" + fin_cds_count);
                    }
                } else if (cd2.getActive_flag() == 0) {
                    System.out.println("2 flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID());
                    bowlingViewModel.update(cd2);
                } else {
                    System.out.println("2 PROBLEM me flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID());
                    bowlingViewModel.update(cd2);
                }
            }else {System.out.println("2 PROBLEM me null");} ///svisto

            System.out.println("cds_count=" + cds_count + " fin_cds_count=" + fin_cds_count);
            if (cds_count == fin_cds_count) {
                System.out.println("champ finished");
                championship.setStatus("Finished"); //todo id champ finished open endofchpampActivity me ta results
                bowlingViewModel.update(championship);
            }

        //META
        if (cd.getActive_flag() > 0) { //next
            bowlingViewModel.update(curRound);
            System.out.println("5 Current round stat = " + curRound.getStatus());
  //  if(r2!=null){        bowlingViewModel.update(r2);
   //         System.out.println("5.5 Current round stat = " + r2.getStatus());}

            Intent i = new Intent(this, RoundActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers", bowlers);
            extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
            extras.putSerializable("all_the_teams", all_the_teams);
            extras.putSerializable("vs", vs);
            extras.putSerializable("champ", championship);
            extras.putSerializable("b_object", t); //selected team
            i.putExtras(extras);
            //finish();
            System.out.println("here 2 " + curRound.getStatus());
            startActivity(i);
        } else if (cd.getActive_flag() == 0) { //finish //todo na upologizw to neo avg tou ka8e paikth
            curRound.setStatus("done"); //giati? afou einai hdh done
            bowlingViewModel.update(curRound);
            //textTitle.setText("This Round has already been done");
            System.out.println("flag= " + cd.getActive_flag());
            System.out.println("cd size= " + cds_count);
            System.out.println("fin cd size= " + fin_cds_count);


            for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
            System.out.println("Gia to avg: team1-"+team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
            System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
                int i2 = i;
                bowlingViewModel.getallAllRound_detailofplayer(RoundScoreListAdapter2.editModelArrayList.get(i).getUuid()).observe(this, new Observer<List<Round_detail>>() {
               @Override
               public void onChanged(List<Round_detail> rd) {
           int avg=0; //todo na to kanw float h' na rwthsw
           for(int r=0;r<rd.size();r++){
               System.out.println("Prin paikths " +  RoundScoreListAdapter2.editModelArrayList.get(i2).getFN() +" i2 "+i2+" rounduuid "+rd.get(i2).getRound_uuid()+" r.score "+rd.get(i2).getScore()+" bowlavg "+RoundScoreListAdapter2.editModelArrayList.get(i2).getBowlAvg()+" avg "+ avg+" rd size "+rd.size());
                       avg+= rd.get(r).getScore();
               System.out.println("Mesa paikths " +  RoundScoreListAdapter2.editModelArrayList.get(i2).getFN() +" r.score "+rd.get(i2).getScore()+" bowlavg "+RoundScoreListAdapter2.editModelArrayList.get(i2).getBowlAvg()+" avg "+ avg);

           }
           avg=avg/(3*rd.size());
           RoundScoreListAdapter2.editModelArrayList.get(i2).setBowlAvg(avg);
            bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i2));
                   System.out.println("Meta paikths " +  RoundScoreListAdapter2.editModelArrayList.get(i2).getFN() +" r.score "+rd.get(i2).getScore()+" bowlavg "+RoundScoreListAdapter2.editModelArrayList.get(i2).getBowlAvg()+" avg "+ avg);
           }
           });
        }
if(championship.getStatus().equals( "Finished")){
    Intent i = new Intent(this, FinishChampActivity.class);
    Bundle extras = new Bundle();
    extras.putSerializable("champ", championship);
    //axrista?
    extras.putSerializable("bowlers", bowlers);
    extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
    extras.putSerializable("all_the_teams", all_the_teams);
    extras.putSerializable("b_object", t); //selected team
    i.putExtras(extras);
    startActivity(i);
    finish();

            } else{
                Intent i = new Intent(this, FinishTeamActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers", bowlers);
                extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                extras.putSerializable("all_the_teams", all_the_teams);
                extras.putSerializable("vs", vs);
                extras.putSerializable("champ", championship);
                extras.putSerializable("b_object", t); //selected team
                i.putExtras(extras);
                startActivity(i);
                finish();
            }
        }

/* PRIN
        //curRound.setStatus("done");//fixme
        if (status_flag.equals("next")) {
           // curRound.setStatus("done");
            bowlingViewModel.update(curRound);
            System.out.println("5 Current round stat = " + curRound.getStatus());
            bowlingViewModel.update(r2);
            System.out.println("5.5 Current round stat = " + r2.getStatus());

         /*TEST   bowlingViewModel.getNextRoundofTeamofChamp(tuuid,champuuid).observe(this, new Observer<List<Round>>() { //axristo-test
                @Override
                public void onChanged(List<Round> ro) {
                    if(ro.size()!=0) {
                        Round r = ro.get(0);
                        System.out.println("6 Current Round of team "+t.getFTeamID()+" is round "+r.getFroundid()+"round stat = " + r.getStatus());

                    }
                }
            }); */
/*

            // Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);
            System.out.println("here 1 " + curRound.getStatus());
            Intent i = new Intent(this, RoundActivity.class);
            // Intent i = new Intent(this, MainActivity.class);
            Bundle extras = new Bundle();
            //PASS ROUND PART 7//
            //extras.putSerializable("round", passr);
            //PASS ROUND PART 7//
            extras.putSerializable("bowlers", bowlers);
            extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
            extras.putSerializable("all_the_teams", all_the_teams);
            extras.putSerializable("vs", vs);
            extras.putSerializable("champ", championship);
            extras.putSerializable("b_object", t); //selected team
            i.putExtras(extras);
            //finish();
            System.out.println("here 2 " + curRound.getStatus());
            startActivity(i);


        } else if (status_flag.equals("last")){
                curRound.setStatus("done");//todo na kanw eepilogh "last-done"
                bowlingViewModel.update(curRound);//fixme?
            //textTitle.setText("This Round has already been done");
            //System.out.println("flag= "+cd.getActive_flag());
            System.out.println("cd size= "+cds_count);
            System.out.println("fin cd size= "+fin_cds_count);
           // cd.setActive_flag(1); //fixme
           // bowlingViewModel.update(cd);
            if(cds_count==fin_cds_count){
                System.out.println("finished");
                championship.setStatus("Finnished");
                bowlingViewModel.update(championship);
            }
                Intent i = new Intent(this, MainActivity.class);
            //axrista?
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers", bowlers);
                extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                extras.putSerializable("all_the_teams", all_the_teams);
                extras.putSerializable("vs", vs);
            extras.putSerializable("champ", championship);
            extras.putSerializable("b_object", t); //selected team
                i.putExtras(extras); //
                startActivity(i);
        } PRIN */

    }else{
            Toast.makeText(
                    getApplicationContext(),
                    "You have to edit and save the score first",
                    Toast.LENGTH_LONG).show();
        }

}

    public void exitActivity(View View) {
        System.out.println("save_pressed=" + save_pressed);
        if (save_pressed == 1) {
            if(t.getUuid().equals(curRound.getTeam1UUID())){
                cd.setScore(score1);
                cd2.setScore(score2);
            } else if(t.getUuid().equals(curRound.getTeam2UUID())){
                cd.setScore(score2);
                cd2.setScore(score1);
            }
            bowlingViewModel.update(t);
            bowlingViewModel.update(t2);
          //  cd.setScore(score1);to kanw sto onresult //?
           // cd2.setScore(score2);to kanw sto onresult //?
            System.out.println("exit curround status "+curRound.getStatus());
            curRound.setStatus("done");//axristo afou to kanw pio panw
            bowlingViewModel.update(curRound);
            //bowlingViewModel.update(championship);
            // t.setActive_flag(1);
            // bowlingViewModel.update(t);

            if (cd.getActive_flag() > 0) {
                System.out.println("1 prin flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
                cd.setActive_flag(cd.getActive_flag() - 1);
                System.out.println("1 meta flag= " + cd.getActive_flag());
                bowlingViewModel.update(cd);
                System.out.println("1 meta flag:  fin_cds_count=" + fin_cds_count);
                if( cd.getActive_flag()==0){
                    fin_cds_count++;
                    System.out.println("1 meta flag mesa if:  fin_cds_count=" + fin_cds_count);
                }
            } else if (cd.getActive_flag() == 0) {
                System.out.println("1 flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
                bowlingViewModel.update(cd);
            } else {
                System.out.println("1 PROBLEM me flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
                bowlingViewModel.update(cd);
            }
            if (t2 != null) {
                if (cd2.getActive_flag() > 0) {
                    System.out.println("2 prin flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID()); //team2
                    cd2.setActive_flag(cd2.getActive_flag() - 1);
                    System.out.println("2 meta flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID());
                    bowlingViewModel.update(cd2);
                    if( cd2.getActive_flag()==0){
                        fin_cds_count++;
                        System.out.println("2 meta flag mesa if:  fin_cds_count=" + fin_cds_count);
                    }
                } else if (cd2.getActive_flag() == 0) {
                    System.out.println("2 flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID());
                    bowlingViewModel.update(cd2);
                } else {
                    System.out.println("2 PROBLEM me flag= " + cd2.getActive_flag() + " t2 " + t2.getFTeamID());
                    bowlingViewModel.update(cd2);
                }
            }else {System.out.println("2 PROBLEM me null");} ///svisto

            System.out.println("cds_count=" + cds_count + " fin_cds_count=" + fin_cds_count);
            if (cds_count == fin_cds_count) {
                System.out.println("champ finished");
                championship.setStatus("Finished");
                bowlingViewModel.update(championship);
            }
     /*   System.out.println("1 prin flag= "+cd.getActive_flag());
        cd.setActive_flag(cd.getActive_flag()-1);
        System.out.println("1 meta flag= "+cd.getActive_flag());
        bowlingViewModel.update(cd);
        if (t2!=null) {
            System.out.println("2 prin flag= " + cd2.getActive_flag()); //team2
            cd2.setActive_flag(cd2.getActive_flag() - 1);
            System.out.println("2 meta flag= " + cd2.getActive_flag());
            bowlingViewModel.update(cd2);
        }*/

            Intent i = new Intent(this, MainActivity.class);
            //axrista?
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers", bowlers);
            extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
            extras.putSerializable("all_the_teams", all_the_teams);
            extras.putSerializable("vs", vs);
            extras.putSerializable("champ", championship);
            extras.putSerializable("b_object", t); //selected team
            i.putExtras(extras); //
            startActivity(i);
            finish();
        } else{
            Toast.makeText(
                    getApplicationContext(),
                    "You have to edit the score first",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void cancel(View view) {
        curRound.setStatus("next"); //axristo
       // bowlingViewModel.update(curRound); //todo H' apla den kanw update vasika
        //bowlingViewModel.update(championship);
        // t.setActive_flag(0);
        // bowlingViewModel.update(t);
        Intent i = new Intent(this, MainActivity.class);
        //axrista?
        Bundle extras = new Bundle();
        extras.putSerializable("bowlers", bowlers);
        extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
        extras.putSerializable("all_the_teams", all_the_teams);
        extras.putSerializable("vs", vs);
        extras.putSerializable("champ", championship);
        extras.putSerializable("b_object", t); //selected team
        i.putExtras(extras); //
        startActivity(i);
        finish();
    }

    @Override
    public void OnDeleteClickListener(Round myNote) {
        bowlingViewModel.delete(myNote);
    }
}
