package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
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
import java.util.Calendar;
import java.util.List;

import static com.example.bowlingchampionshipmanager.RoundActivity.status_flag;

public class PinsRoundActivity extends AppCompatActivity {

    private static Button nextRound_btn, exitRound_btn;
    public static int calc_pressed = 0;
    static ArrayList<Participant> bowlers;
    static List<Pins_points> pp;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    private static EditText editpins;
    private static TextView textTitle, team1, pointstxt, scoretxt,hdcp_view, sumHDCP, sum1st, sum2nd, sum3rd;
    public int bowlId;
    public static Team t;
    public static Round r2;
    public static String tuuid, champuuid;
    public Championship championship;
    private BowlingViewModel bowlingViewModel;
    private RoundScoreListAdapter2 blistAdapter;
    private Championship_detail cd;
    private int cds_count; //oles oi omades tou champ
    private int fin_cds_count; //oi omades tou champ pou teleiwsan ta rounds tous
    public static Round curRound;
    private int score1;

    //version 2
    private SelectRoundAdapter rscorelistAdapter;
    public static final int UPDATE_SCORE_REQUEST_CODE = 10;
    public static int save_pressed = 0;
    //2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins_round);

        textTitle = findViewById(R.id.textTitle);
        nextRound_btn = findViewById(R.id.nextRound_btn);
        exitRound_btn = findViewById(R.id.exitRound_btn);
        team1 = (TextView) findViewById(R.id.team1);
        pointstxt =  findViewById(R.id.points);
        scoretxt = findViewById(R.id.score1);

       //axrista
       /* sumHDCP = findViewById(R.id.sumHDCP);
        sum1st = findViewById(R.id.sum1st);
        sum2nd = findViewById(R.id.sum2nd);
        sum3rd = findViewById(R.id.sum3rd);*/
        //

       // version 1 editpins=findViewById(R.id.editpins);
        calc_pressed = 0;

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters = (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            bowlId = bundleObject.getInt("bowlId"); //to sys tou team
            t = (Team) bundleObject.getSerializable("b_object");
            tuuid = t.getUuid();

            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();

        }

        System.out.println("Team selected: " + t.getFTeamID() + " sys " + t.getSys_teamID() + " uuid " + t.getUuid());
        team1.setText("Team " + t.getTeamName());

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new RoundScoreListAdapter2(this);
//        recyclerView.setAdapter(blistAdapter);

        //version 2
        rscorelistAdapter = new SelectRoundAdapter(this);
        recyclerView.setAdapter(rscorelistAdapter); //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//
        //2

        if(bowlers==null){
            bowlingViewModel.getAllPlayersofChamp(champuuid).observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> players) {
                    bowlers= (ArrayList<Participant>) players;
                    System.out.println("bowlers null & players size "+players.size());
                }
            });
        }

        //todo na rwthsw posa rounds paizontai sta pins afou den einai antipales omades
        bowlingViewModel.getNextRoundofTeamofChamp(tuuid, champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> ro) {
                if (ro.size() != 0) {
                    curRound = ro.get(0);
                    //version 2
                    rscorelistAdapter.setSelRound(ro);//
                    rscorelistAdapter.setChamp(championship);//
                    rscorelistAdapter.setSelTeam(t);//
                    //2
                    textTitle.setText("Round " + String.valueOf(ro.get(0).getFroundid()));
                    System.out.println("kai to size einai " + ro.size());
                    System.out.println("Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is round " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());

                    status_flag = curRound.getStatus();
                    System.out.println("2 r.getstatus=flag= " + curRound.getStatus());
                    if (status_flag.equals("last")) {
                        nextRound_btn.setText("Finnish");
                        exitRound_btn.setText("Cancel");
                    }
                    ro.get(0).setStatus("done");
                }
            }
        });

        //pairnw to champ_detail ths omadas 1 team gia na dw to active_flag
        bowlingViewModel.getChamp_detailofTeamandChamp(tuuid, champuuid).observe(this, new Observer<Championship_detail>() {
            @Override
            public void onChanged(Championship_detail c) {
                cd = c;
                System.out.println("1 exei active flag=" + c.getActive_flag());
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
            }
        });
        //kai meta pairnw tis omades tou champ me active_flag=1, dld oses teleiwsan kai tsekarw an o arithmos tous einai isos me ton ar8mo olwn twn omadwn
        bowlingViewModel.getChamp_detailofChampofFinnishedTeams(champuuid).observe(this, new Observer<List<Championship_detail>>() { //axristo-test
            @Override
            public void onChanged(List<Championship_detail> cs) {
                fin_cds_count = cs.size();
            }
        });

       /* axristo? //todo na rwthsw ti ginetai me to sunolo korunwn twn paiktwn ths omadas
       bowlingViewModel.getAllPlayersofTeam3(tuuid, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setBowls(part);
                blistAdapter.setRound(curRound);
                ArrayList<Round_detail> rd = new ArrayList<>();
                for (int i = 0; i < part.size(); i++) {
                    Round_detail round_detail = new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0, part.get(i).getHdcp());
                    System.out.println(" rounddetail: rid " + r.getFroundid() + " pid " + part.get(i).getFirstName());
                    rd.add(round_detail);
                }
                blistAdapter.setRound_detail(rd);

            }
        }); */

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

       //test
        bowlingViewModel.getAllRound_detail().observe(this, new Observer<List<Round_detail>>() { //axristo-test
            @Override
            public void onChanged(List<Round_detail> rds) {
                System.out.println("rds size " + rds.size());

                for (int i = 0; i < rds.size(); i++) {
                    System.out.println("rd " + i + " round id " + rds.get(i).getRound_uuid() + " player id " + rds.get(i).getParticipant_uuid() + " score "+rds.get(i).getScore()+" avg "+rds.get(i).getAvg()+" games "+rds.get(i).getGames()+" h " + rds.get(i).getHdcp() + " firste " + rds.get(i).getFirst() + " second " + rds.get(i).getSecond() + " third " + rds.get(i).getThird()+" crdate "+rds.get(i).getCreated_at()+" update "+rds.get(i).getUpdated_at());
                }
            }
        });
    }

    //version 1
    public void calculateScore(View View) {
        if (editpins.getText().toString().equals("") ){
            Toast.makeText(
                    getApplicationContext(),
                    "Please insert the number of pins",
                    Toast.LENGTH_LONG).show();
        } else {
            score1 = t.getScore(); //cd.getScore()
            int point=0;
            System.out.println("2 pins " + pp.get(0).getPins() + " pointstxt " + pp.get(0).getPoints() + " uuid " + pp.get(0).getPins_uuid() + " champ " + pp.get(0).getChamp_uuid() );

            for (int i = 0; i < pp.size(); i++) {
                if (i==0 && Integer.parseInt(editpins.getText().toString()) <= pp.get(i).getPins()){
                    point+=pp.get(i).getPoints();
                } else if (i==(pp.size()-1) && Integer.parseInt(editpins.getText().toString()) >= pp.get(i).getPins()){
                    point+=pp.get(i).getPoints();
                } else if (Integer.parseInt(editpins.getText().toString()) >= pp.get(i).getPins() && Integer.parseInt(editpins.getText().toString()) < pp.get(i+1).getPins()){ //mexri kai ta x pins = n pointstxt
                    System.out.println(editpins.getText().toString()+">= " + pp.get(i).getPins() + " && " +Integer.parseInt(editpins.getText().toString())+"<"+ pp.get(i+1).getPoints() +" pointstxt "+pp.get(i).getPoints());
                    point+=pp.get(i).getPoints();
                }
            }
            score1+=point;
            //t.setScore(score1); //todo na to kanw sto open activity
            curRound.setScore1(point);
            scoretxt.setText("Score: "+score1);
            pointstxt.setText("Points: "+point);
            calc_pressed = 1;
        }

    } //1


    //version 2
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);

        System.out.println("GOT from editrounscore: ");
        if (requestCode == UPDATE_SCORE_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundleObject = resultData.getExtras();
            if (bundleObject != null) {
                t = (Team) bundleObject.getSerializable("selTeam");
                curRound = (Round) bundleObject.getSerializable("round2");
                curRound.setDate(Calendar.getInstance().getTime());
                score1 = bundleObject.getInt("score1");
                System.out.println("GOT from editrounscore: ");
                System.out.println("SCORE1: "+score1);
                System.out.println("ROUND " + curRound.getFroundid() + " sid " + curRound.getRounduuid() + " status " + curRound.getStatus() + " t1: " + curRound.getTeam1ID() + " score " + curRound.getScore1() + " t2: " + curRound.getTeam2ID() + " score " + curRound.getScore2() +" date "+curRound.getDate()+ " uuid " + curRound.getRounduuid());
                System.out.println("AND t1 " + t.getFTeamID() + " sid " + t.getSys_teamID() + " score " + t.getScore() + " uuid " + t.getUuid());
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();
                save_pressed = 1;
                System.out.println("GOT save_pressed="+save_pressed);
                scoretxt.setText("Team Score: "+t.getScore());
                pointstxt.setText("Points: "+curRound.getScore1());

            }
        }
    }
    //2

//version 1
    public void openNewActivity(View View) {

if (calc_pressed ==1) {
    t.setScore(score1);
    System.out.println("team1 score " + t.getScore() + " sid " + t.getSys_teamID());
    bowlingViewModel.update(t);


    //bowlingViewModel.update(curRound);

   /* for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
        System.out.println(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
        System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

        bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));
        bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
        System.out.println(" rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());

    }*/

    System.out.println("Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is curround " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());
    System.out.println("stat " + status_flag);
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
    } else {
        System.out.println("1 PROBLEM me flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
    }
    System.out.println("cds_count=" + cds_count + " fin_cds_count=" + fin_cds_count);
    if (cds_count == fin_cds_count) {
        System.out.println("champ finished");
        championship.setStatus("Finished");
        championship.setEnd_date(Calendar.getInstance().getTime());
        bowlingViewModel.update(championship);
    }

    curRound.setStatus("done");
    if (status_flag.equals("next")) {
        // curRound.setStatus("done");
        bowlingViewModel.update(curRound);
        System.out.println("here 1 " + curRound.getStatus());
        Intent i = new Intent(this, PinsRoundActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("bowlers", bowlers);
        extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
        extras.putSerializable("all_the_teams", all_the_teams);
        extras.putSerializable("champ", championship);
        extras.putSerializable("b_object", t); //selected team
        i.putExtras(extras);
        System.out.println("here 2 " + curRound.getStatus());
        startActivity(i);
        finish();
    } else if (status_flag.equals("last")) {
        curRound.setStatus("done");
        bowlingViewModel.update(curRound);
        //System.out.println("flag= "+cd.getActive_flag());
        System.out.println("cd size= " + cds_count);
        System.out.println("fin cd size= " + fin_cds_count);

        if (cds_count == fin_cds_count) {
            System.out.println("finished");
            championship.setStatus("Finished");
            championship.setEnd_date(Calendar.getInstance().getTime());
            bowlingViewModel.update(championship);
        }
        Intent i = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("bowlers", bowlers);
        extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
        extras.putSerializable("all_the_teams", all_the_teams);
        extras.putSerializable("champ", championship);
        extras.putSerializable("b_object", t); //selected team
        i.putExtras(extras); //
        startActivity(i);
        finish();
    }
} else {
    Toast.makeText(
            getApplicationContext(),
            "You have to calculate the score first",
            Toast.LENGTH_LONG).show();

}
}
//1

    //version 1
    public void exitActivity(View View) {
        if (calc_pressed ==1) {
            t.setScore(score1);
            curRound.setStatus("done");
            bowlingViewModel.update(curRound);
            System.out.println("team1 score " + t.getScore() + " sid " + t.getSys_teamID());
            bowlingViewModel.update(t);


           /* na svisw for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                System.out.println(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
                System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

                bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));
                bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
                System.out.println(" rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());

            } */

            System.out.println("Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is curround " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());
            System.out.println("stat " + status_flag);
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
            } else {
                System.out.println("1 PROBLEM me flag= " + cd.getActive_flag() + " t " + t.getFTeamID());
            }
            System.out.println("cds_count=" + cds_count + " fin_cds_count=" + fin_cds_count);
            if (cds_count == fin_cds_count) {
                System.out.println("champ finished");
                championship.setStatus("Finished");
                championship.setEnd_date(Calendar.getInstance().getTime());
                bowlingViewModel.update(championship);
            }
            Intent i = new Intent(this, MainActivity.class);
            //axrista?
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers", bowlers);
            extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
            extras.putSerializable("all_the_teams", all_the_teams);
            extras.putSerializable("champ", championship);
            extras.putSerializable("b_object", t); //selected team
            i.putExtras(extras); //
            startActivity(i);
            finish();
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    "You have to calculate the score first",
                    Toast.LENGTH_LONG).show();

        }
    } //1

    //version2
        public void openNewActivity2(View View) {


            System.out.println("save_pressed="+save_pressed);
            if (save_pressed == 1){
                System.out.println("4 Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is curround " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());
                System.out.println("stat " + status_flag);
                System.out.println("team1 score " + t.getScore() + " sid " + t.getSys_teamID());
                bowlingViewModel.update(t);
                cd.setScore(score1);

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

                System.out.println("cds_count=" + cds_count + " fin_cds_count=" + fin_cds_count);
                if (cds_count == fin_cds_count) {
                    System.out.println("champ finished");
                    championship.setStatus("Finished"); //todo id champ finished open endofchpampActivity me ta results
                    championship.setEnd_date(Calendar.getInstance().getTime());
                    bowlingViewModel.update(championship);
                }

                if (cd.getActive_flag() > 0) { //next
                    bowlingViewModel.update(curRound);
                    System.out.println("5 Current round stat = " + curRound.getStatus());

                    Intent i = new Intent(this, PinsRoundActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("bowlers", bowlers);
                    extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                    extras.putSerializable("all_the_teams", all_the_teams);
                    extras.putSerializable("champ", championship);
                    extras.putSerializable("b_object", t); //selected team
                    i.putExtras(extras);
                    finish();
                    System.out.println("here 2 " + curRound.getStatus());
                    startActivity(i);
                    finish();
                } else if (cd.getActive_flag() == 0) { //finish //todo na upologizw to neo avg tou ka8e paikth //finish
                    curRound.setStatus("done");
                    bowlingViewModel.update(curRound);
                    //textTitle.setText("This Round has already been done");
                    System.out.println("flag= " + cd.getActive_flag());
                    System.out.println("cd size= " + cds_count);
                    System.out.println("fin cd size= " + fin_cds_count);

                    //ka8oliko avg
                    for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                        System.out.println("Gia to avg: team1-"+team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
                        System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
                        Participant pa=RoundScoreListAdapter2.editModelArrayList.get(i);
                        bowlingViewModel.getallAllRound_detailofplayer(pa.getUuid()).observe((LifecycleOwner) this, new Observer<List<Round_detail>>() {
                            @Override
                            public void onChanged(List<Round_detail> rd) {
                                pa.calculateAVGofPlayer(pa,rd, championship, bowlingViewModel);
                            }
                        });

                    }
                   /* for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                        System.out.println("Gia to avg: team1-"+team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
                        System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
                        int i2 = i;
                        bowlingViewModel.getallAllRound_detailofplayer(RoundScoreListAdapter2.editModelArrayList.get(i).getUuid()).observe(this, new Observer<List<Round_detail>>() {
                            @Override
                            public void onChanged(List<Round_detail> rd) {
                                int avg=0; //todo na to kanw float h' na rwthsw
                                for(int r=0;r<rd.size();r++){
                                    System.out.println("Prin paikths " +  RoundScoreListAdapter2.editModelArrayList.get(i2).getFirstName() +" i2 "+i2+" rounduuid "+rd.get(i2).getRound_uuid()+" r.score "+rd.get(i2).getScore()+" bowlavg "+RoundScoreListAdapter2.editModelArrayList.get(i2).getBowlAvg()+" avg "+ avg+" rd size "+rd.size());
                                    avg+= rd.get(r).getScore();
                                    System.out.println("Mesa paikths " +  RoundScoreListAdapter2.editModelArrayList.get(i2).getFirstName() +" r.score "+rd.get(i2).getScore()+" bowlavg "+RoundScoreListAdapter2.editModelArrayList.get(i2).getBowlAvg()+" avg "+ avg);

                                }
                                if(rd.size()!=0) {
                                    avg = avg / (3 * rd.size());
                                }
                                RoundScoreListAdapter2.editModelArrayList.get(i2).setBowlAvg(avg); //todo na krataw to ka8oliko avg se allh metavliti sto participant
                                bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i2));
                                System.out.println("Meta paikths " +  RoundScoreListAdapter2.editModelArrayList.get(i2).getFirstName() +" r.score "+rd.get(i2).getScore()+" bowlavg "+RoundScoreListAdapter2.editModelArrayList.get(i2).getBowlAvg()+" avg "+ avg);
                            }
                        });
                    }*/

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
                    //axrista?
                    Bundle extras = new Bundle();
                    extras.putSerializable("bowlers", bowlers);
                    extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                    extras.putSerializable("all_the_teams", all_the_teams);
                    extras.putSerializable("champ", championship);
                    extras.putSerializable("b_object", t); //selected team
                    i.putExtras(extras); //
                    startActivity(i);
                    finish();
                }
             }
            }else{
                Toast.makeText(
                        getApplicationContext(),
                        "You have to edit and save the score first",
                        Toast.LENGTH_LONG).show();
            }

        }
        //version 2
        public void exitActivity2(View View) { //fixme save&exit
            System.out.println("save_pressed=" + save_pressed);
            if (save_pressed == 1) {
                System.out.println("exit curround status "+curRound.getStatus());
                curRound.setStatus("done");
                bowlingViewModel.update(curRound);
                bowlingViewModel.update(t);
                cd.setScore(score1);

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

                System.out.println("cds_count=" + cds_count + " fin_cds_count=" + fin_cds_count);
                if (cds_count == fin_cds_count) {
                    System.out.println("champ finished");
                    championship.setStatus("Finished");
                    championship.setEnd_date(Calendar.getInstance().getTime());
                    bowlingViewModel.update(championship);
                }

                Intent i = new Intent(this, MainActivity.class);
                //axrista?
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers", bowlers);
                extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                extras.putSerializable("all_the_teams", all_the_teams);
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
    //2

    public void cancel(View view) {
        Intent i = new Intent(this, MainActivity.class);
        //axrista?
        Bundle extras = new Bundle();
        extras.putSerializable("bowlers", bowlers);
        extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
        extras.putSerializable("all_the_teams", all_the_teams);
        extras.putSerializable("champ", championship);
        extras.putSerializable("b_object", t); //selected team
        i.putExtras(extras); //
        startActivity(i);
        finish();
    }
}

