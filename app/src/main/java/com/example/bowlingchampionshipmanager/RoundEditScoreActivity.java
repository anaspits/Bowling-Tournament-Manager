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

public class RoundEditScoreActivity extends AppCompatActivity {

    private static TextView textTitle;
    private static TextView sumHDCP,sum1st,sum2nd,sum3rd,sumHDCP2,sum1st2,sum2nd2,sum3rd2, txtscore1, txtscore2;;
    private static TextView team1txt, team2txt;
    public static Team team1, team2;
    public static Round r;
    public static String tuuid1, tuuid2;
    private int score1, score2;
    private BowlingViewModel bowlingViewModel;
    private RoundListAdapter rlistAdapter;
    private RoundScoreListAdapter2 blistAdapter;
    private RoundScoreListAdapterTeam2 blistAdapter2;
    public String champuuid;
    public Championship championship;
    public int calc_pressed=0;

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
        calc_pressed=0;
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
                textTitle.append("\n"+"Team "+team1.getTeamName()+" VS Team " + r.getTeam2ID());
                team2txt.setText( "Team " + r.getTeam2ID());
                tuuid2=r.getTeam2UUID();
            } else {
                textTitle.append("\n"+"Team "+team1.getTeamName()+" VS Team " + r.getTeam1ID());
                team2txt.setText( "Team " + r.getTeam1ID());
                tuuid2=r.getTeam1UUID();
            }

        }
            System.out.println("Team selected: "+team1.getFTeamID()+" sys "+team1.getSys_teamID()+" uuid "+team1.getUuid());
            team1txt.setText("Team "+team1.getTeamName() );
        textTitle.setText("Round "+r.getFroundid());

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

            //team2
                        bowlingViewModel.getAllPlayersofTeam3(tuuid2, champuuid).observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> part) {
                    blistAdapter2.setBowls(part);
                    blistAdapter2.setRound(r);
                    ArrayList<Round_detail> rd = new ArrayList<>();
                    for (int i = 0; i < part.size(); i++) {
                        Round_detail round_detail= new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0,part.get(i).getHdcp() );
                        System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ part.get(i).getFN());
                        rd.add(round_detail);
                    }
                    blistAdapter2.setRound_detail(rd);

                }
            });


    }

    public void calculateScore(View View) {
        //gia tin omada 1
        score1=team1.getScore();
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

        //gia tin omada 2
        score2=team2.getScore();
        System.out.println("score2: "+score2);
        int first_sum2=0;
        int second_sum2=0;
        int third_sum2=0;
        int sum_hdcp2=0;
        for (int i = 0; i < RoundScoreListAdapterTeam2.editModelArrayList.size(); i++){ //todo mporw na to valw sthn panw epanalhpsh
            System.out.println(team1txt.getText() + " " + RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(" emda player "  +" id "+RoundScoreListAdapterTeam2.editModelArrayList.get(i).getUuid()+" hdcp "+RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " +RoundScoreListAdapterTeam2.rd.size()+" rid "+RoundScoreListAdapterTeam2.rd.get(i).getRound_uuid()+" pid "+RoundScoreListAdapterTeam2.rd.get(i).getParticipant_uuid()+" h "+ RoundScoreListAdapterTeam2.rd.get(i).getHdcp() +" 1st "+ RoundScoreListAdapterTeam2.rd.get(i).getFirst()+" 2nd "+ RoundScoreListAdapterTeam2.rd.get(i).getSecond()+" 3rd "+ RoundScoreListAdapterTeam2.rd.get(i).getThird());
            first_sum2 += RoundScoreListAdapterTeam2.rd.get(i).getFirst();
            second_sum2 +=  RoundScoreListAdapterTeam2.rd.get(i).getSecond();
            third_sum2 +=  RoundScoreListAdapterTeam2.rd.get(i).getThird();
            sum_hdcp2 +=RoundScoreListAdapterTeam2.editModelArrayList.get(i).getHdcp();
        }
        first_sum2 += sum_hdcp2;
        second_sum2 += sum_hdcp2;
        third_sum2 += sum_hdcp2;
        sumHDCP2.setText(String.valueOf(sum_hdcp2));
        sum1st2.setText(String.valueOf(first_sum2));
        sum2nd2.setText(String.valueOf(second_sum2));
        sum3rd2.setText(String.valueOf(third_sum2 ));

        //pontoi
        int pontoi1=0;
        int pontoi2=0;
        //player vs player
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++){
            //todo na rwthsw an to geniko score ths omadas kratietai H 3ekinaei e ka8e champ apo to 0
            //todo na rwthsw an upologize to ka8e first,second,third 3exwrista H' olo mazi san 1st+2nd+3rd
            if (RoundScoreListAdapter2.rd.get(i).getFirst()>RoundScoreListAdapterTeam2.rd.get(i).getFirst()){
                pontoi1 +=2;
            } else if (RoundScoreListAdapter2.rd.get(i).getFirst()<RoundScoreListAdapterTeam2.rd.get(i).getFirst()){
                pontoi2+=2;
            } else {
                //todo isopalia posoi pontoi?
            }
            if (RoundScoreListAdapter2.rd.get(i).getSecond()>RoundScoreListAdapterTeam2.rd.get(i).getSecond()){
                pontoi1 +=2;
            } else if (RoundScoreListAdapter2.rd.get(i).getSecond()<RoundScoreListAdapterTeam2.rd.get(i).getSecond()){
                pontoi2+=2;
            } else {
                //todo isopalia posoi pontoi?
            }
            if (RoundScoreListAdapter2.rd.get(i).getThird()>RoundScoreListAdapterTeam2.rd.get(i).getThird()){
                pontoi1 +=2;
            } else if (RoundScoreListAdapter2.rd.get(i).getThird()<RoundScoreListAdapterTeam2.rd.get(i).getThird()){
                pontoi2+=2;
            } else {
                //todo isopalia posoi pontoi?
            }
        }
        //sunolo korinwn ana paixnidi //todo na rwthsw an einai me hdcp H' xwris
        if(first_sum1>first_sum2){
            pontoi1+=3;
        } else if(first_sum1<first_sum2){
            pontoi2+=3;
        } else {
            //todo isopalia?
        }
        if(second_sum1>second_sum2){
            pontoi1+=3;
        } else if(second_sum1<second_sum2){
            pontoi2+=3;
        } else {
            //todo isopalia?
        }
        if(third_sum1>third_sum2){
            pontoi1+=3;
        } else if(third_sum1<third_sum2){
            pontoi2+=3;
        } else {
            //todo isopalia?
        }
        //geniko sunolo korinwn
        if(first_sum1+second_sum1+third_sum1>first_sum2+second_sum2+third_sum2){
            pontoi1+=5;
        } else if(first_sum1+second_sum1+third_sum1<first_sum2+second_sum2+third_sum2){
            pontoi2+=5;
        } else {
            //isopalia
        }

        score1+=pontoi1; //todo na rwthsw
        score2+=pontoi2;//todo na to valw sto opennewActivity
        team1.setScore(score1);//todo na rwthsw
        team2.setScore(score2);
        txtscore1.setText("Score: "+pontoi1);
        txtscore2.setText("Score: "+pontoi2); //todo na valw koumpi sunolo (tou sum)
        r.setScore1(score1); //todo na rwtisw
        r.setScore2(score2);
        //bowlingViewModel.update(r); //todo svisto
        calc_pressed=1;
    }
    public void openNewActivity(View View) {
        //String button_text;
        // button_text =((Button)View).getText().toString();
        System.out.println("calc_pressed=" + calc_pressed);
        if (calc_pressed == 1) {
            System.out.println("team1 score " + team1.getScore() + " sid " + team1.getSys_teamID());//todo na rwthsw
            System.out.println("team2 score " + team2.getScore() + " sid " + team2.getSys_teamID());
            bowlingViewModel.update(team1);//todo na kanw update sthn RoundActivity->openNewActivity k exit
            bowlingViewModel.update(team2);
            //todo na mhn mporei na patithei an den pathsw prwta calculate
            for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
                System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

                bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));
                bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
                System.out.println(" rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());

                bowlingViewModel.update(RoundScoreListAdapterTeam2.editModelArrayList.get(i));
                bowlingViewModel.update(RoundScoreListAdapterTeam2.rd.get(i));
            }

            //test
            if (team2 != null) {
                System.out.println(" sto edit, tema2 " + team2.getFTeamID());
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("selTeam", (Serializable) team1); //todo na kanw update edw to round me ta nea scores
            resultIntent.putExtra("team2", (Serializable) team2);
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
