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
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//version 2
public class PinsRoundEditActivity extends AppCompatActivity {

    private static TextView textTitle;
    private static TextView sumHDCP,sum1st,sum2nd,sum3rd,txtscore1, totalSumtxt,  pointstxt,team1txt ;
    public static Team team1;
    public static Round r;
    public static String tuuid1;
    private int score1;
    private BowlingViewModel bowlingViewModel;
    private RoundScoreListAdapter2 blistAdapter;
    public String champuuid;
    public Championship championship;
    static List<Pins_points> pp;
    public int calc_pressed=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins_round_edit);

        textTitle= findViewById(R.id.textTitle);
        team1txt=(TextView) findViewById(R.id.team1);
        sumHDCP =findViewById(R.id.sumHDCP);
        sum1st=findViewById(R.id.sum1st);
        sum2nd=findViewById(R.id.sum2nd);
        sum3rd=findViewById(R.id.sum3rd);
        totalSumtxt =findViewById(R.id.txvTotalSum);
        txtscore1=findViewById(R.id.score1);
        pointstxt =  findViewById(R.id.points);
        calc_pressed=0;

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

        }
        System.out.println("Team selected: "+team1.getFTeamID()+" sys "+team1.getSys_teamID()+" uuid "+team1.getUuid());
        team1txt.setText("Team "+team1.getTeamName() );
        textTitle.setText("Round "+r.getFroundid());

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new RoundScoreListAdapter2(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getAllPlayersofTeam3(tuuid1, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setBowls(part);
                blistAdapter.setRound(r);
                ArrayList<Round_detail> rd = new ArrayList<>(); //fixme na ta pairnw live apo to viewmodel
                for (int i = 0; i < part.size(); i++) {
                    Round_detail round_detail= new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0,part.get(i).getHdcp() );
                    System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ part.get(i).getFN());
                    rd.add(round_detail);
                }
                blistAdapter.setRound_detail(rd);

            }
        });

        bowlingViewModel.getPins_pointsofChamp(champuuid).observe(this, new Observer<List<Pins_points>>() {
            @Override
            public void onChanged(List<Pins_points> rds) {
                System.out.println("pp size " + rds.size());
                pp=rds;
                for (int i = 0; i < rds.size(); i++) {
                    System.out.println("pp " + i + " pins " + pp.get(i).getPins() + " pointstxt " + pp.get(i).getPoints() + " uuid " + pp.get(i).getPins_uuid() + " champ " + pp.get(i).getChamp_uuid() );
                    System.out.println("pp " + i + " pins " + rds.get(i).getPins() + " pointstxt " + rds.get(i).getPoints() + " uuid " + rds.get(i).getPins_uuid() + " champ " + rds.get(i).getChamp_uuid() );
                }
            }
        });

    }

    public void calculateScore(View View) {
        score1=team1.getScore();
        //todo r.getscore1 kai pws 8a to pairnw apo to prohgoumeno round to score ths omadas 1?
        int first_sum1=0;
        int second_sum1=0;
        int third_sum1=0;
        int sum_hdcp1=0;
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++){
            // team1.setText(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(" emda player "  +" id "+RoundScoreListAdapter2.editModelArrayList.get(i).getUuid()+" hdcp "+RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " +RoundScoreListAdapter2.rd.size()+" rid "+RoundScoreListAdapter2.rd.get(i).getRound_uuid()+" pid "+RoundScoreListAdapter2.rd.get(i).getParticipant_uuid()+" h "+ RoundScoreListAdapter2.rd.get(i).getHdcp() +" 1st "+ RoundScoreListAdapter2.rd.get(i).getFirst()+" 2nd "+ RoundScoreListAdapter2.rd.get(i).getSecond()+" 3rd "+ RoundScoreListAdapter2.rd.get(i).getThird());

            first_sum1 += RoundScoreListAdapter2.rd.get(i).getFirst();
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

        int totalsum =  first_sum1+second_sum1+third_sum1;
        totalSumtxt.setText("Total sum: "+String.valueOf(totalsum));
//calculate pins score from points
            int point=0;
            System.out.println("2 pins " + pp.get(0).getPins() + " pointstxt " + pp.get(0).getPoints() + " uuid " + pp.get(0).getPins_uuid() + " champ " + pp.get(0).getChamp_uuid() );

            for (int i = 0; i < pp.size(); i++) {
                if (i==0 && totalsum <= pp.get(i).getPins()){
                    point+=pp.get(i).getPoints();
                } else if (i==(pp.size()-1) && totalsum >= pp.get(i).getPins()){
                    point+=pp.get(i).getPoints();
                } else if (totalsum >= pp.get(i).getPins() && totalsum < pp.get(i+1).getPins()){ //mexri kai ta x pins = n pointstxt
                    System.out.println(totalsum+">= " + pp.get(i).getPins() + " && " +totalsum+"<"+ pp.get(i+1).getPoints() +" pointstxt "+pp.get(i).getPoints());
                    point+=pp.get(i).getPoints();
                }
            }
            score1+=point;
            //t.setScore(score1); //todo na to kanw sto open activity
            r.setPoints1(point);
            txtscore1.setText("Score: "+score1);
            pointstxt.setText("Points: "+point);
            calc_pressed = 1;
        }
    public void openNewActivity(View View) {
        //String button_text;
        // button_text =((Button)View).getText().toString();
        System.out.println("calc_pressed=" + calc_pressed);
        if (calc_pressed == 1) {
            team1.setScore(score1);
            r.setScore1(score1);
            System.out.println("team1 score " + team1.getScore() + " sid " + team1.getSys_teamID());

            for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
                System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

               // RoundScoreListAdapter2.editModelArrayList.get(i).setBowlAvg((RoundScoreListAdapter2.rd.get(i).getFirst()+ RoundScoreListAdapter2.rd.get(i).getSecond()+ RoundScoreListAdapter2.rd.get(i).getThird())/3);
                bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i)); //todo na kanw update kai to score tou paikth kai sto telos na upologizw to avg

                RoundScoreListAdapter2.rd.get(i).setScore( (RoundScoreListAdapter2.rd.get(i).getFirst()+ RoundScoreListAdapter2.rd.get(i).getSecond()+ RoundScoreListAdapter2.rd.get(i).getThird())/3);
                bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
                System.out.println(" rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird()+RoundScoreListAdapter2.rd.get(i).getScore());

            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("selTeam", (Serializable) team1); //todo na kanw update edw to round me ta nea scores
            resultIntent.putExtra("round2", (Serializable) r);
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

}