package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.bowlingchampionshipmanager.RoundActivity.status_flag;

public class PinsRoundActivity extends AppCompatActivity {

    private static TextView textTitle;
    private static Button nextRound_btn, exitRound_btn;
    public static int calc_pressed = 0;
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    private static EditText editpins;
    private static TextView team1,hdcp_view, sumHDCP, sum1st, sum2nd, sum3rd;
    public int bowlId;
    public static Team t;
   // public static Round r;
    public static String tuuid, champuuid;
    public Championship championship;
    private BowlingViewModel bowlingViewModel;
    private RoundScoreListAdapter2 blistAdapter;
    private Championship_detail cd;
    private int cds_count; //oles oi omades tou champ
    private int fin_cds_count; //oi omades tou champ pou teleiwsan ta rounds tous
    public static Round curRound;
    private int score1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins_round);

        textTitle = findViewById(R.id.textTitle);
        nextRound_btn = findViewById(R.id.nextRound_btn);
        exitRound_btn = findViewById(R.id.exitRound_btn);
        team1 = (TextView) findViewById(R.id.team1);

       //
        sumHDCP = findViewById(R.id.sumHDCP);
        sum1st = findViewById(R.id.sum1st);
        sum2nd = findViewById(R.id.sum2nd);
        sum3rd = findViewById(R.id.sum3rd);
        //

        editpins=findViewById(R.id.editpins);
        calc_pressed = 0;

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters = (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            bowlId = bundleObject.getInt("bowlId"); //to sys tou team
            t = (Team) bundleObject.getSerializable("b_object");
            tuuid = t.getUuid();
            score1 = t.getScore();
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

        //todo na rwthsw posa rounds paizontai sta pins afou den einai antipales omades
        bowlingViewModel.getNextRoundofTeamofChamp(tuuid, champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> ro) {
                if (ro.size() != 0) {
                    curRound = ro.get(0);
                    textTitle.append(" " + String.valueOf(ro.get(0).getFroundid()));
                    System.out.println("kai to size einai " + ro.size());
                    System.out.println("Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is round " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());
                   /* if (tuuid.equals(curRound.getTeam1UUID())) { //perito
                        textTitle.append("\n" + "Team " + t.getTeamName());
                    } else {
                        textTitle.append("\n" + "Team " + t.getTeamName());
                    }*/
                    status_flag = curRound.getStatus();
                    System.out.println("2 r.getstatus=flag= " + curRound.getStatus());
                    if (status_flag.equals("last")) {
                        nextRound_btn.setText("Finnish");
                        exitRound_btn.setText("Cancel");
                    }
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

       /* axristo?
       bowlingViewModel.getAllPlayersofTeam3(tuuid, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setBowls(part);
                blistAdapter.setRound(curRound);
                ArrayList<Round_detail> rd = new ArrayList<>(); //fixme na ta pairnw live apo to viewmodel
                for (int i = 0; i < part.size(); i++) {
                    Round_detail round_detail = new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0, part.get(i).getHdcp());
                    System.out.println(" rounddetail: rid " + r.getFroundid() + " pid " + part.get(i).getFN());
                    rd.add(round_detail);
                }
                blistAdapter.setRound_detail(rd);

            }
        }); */

        bowlingViewModel.getAllRound_detail().observe(this, new Observer<List<Round_detail>>() { //axristo-test
            @Override
            public void onChanged(List<Round_detail> rds) {
                System.out.println("rds size " + rds.size());

                for (int i = 0; i < rds.size(); i++) {
                    System.out.println("rd " + i + " round id " + rds.get(i).getRound_uuid() + " player id " + rds.get(i).getParticipant_uuid() + " h " + rds.get(i).getHdcp() + " firste " + rds.get(i).getFirst() + " second " + rds.get(i).getSecond() + " third " + rds.get(i).getThird());
                }
            }
        });
    }

    public void calculateScore(View View) { //todo
        score1 = t.getScore();
t.setScore(Integer.parseInt(editpins.getText().toString())); //edw tha prsthesw tous ponotus
        curRound.setScore1(Integer.parseInt(editpins.getText().toString())); //edw apla tha valw tous pontous
        calc_pressed = 1;

    }

    public void openNewActivity(View View) {
        //String button_text;
        // button_text =((Button)View).getText().toString();
if (calc_pressed ==1) {
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
    } else if (status_flag.equals("last")) {
        curRound.setStatus("done");
        bowlingViewModel.update(curRound);
        //System.out.println("flag= "+cd.getActive_flag());
        System.out.println("cd size= " + cds_count);
        System.out.println("fin cd size= " + fin_cds_count);

        if (cds_count == fin_cds_count) {
            System.out.println("finished");
            championship.setStatus("Finished");
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
    }
} else {
    Toast.makeText(
            getApplicationContext(),
            "You have to calculate the score first",
            Toast.LENGTH_LONG).show();

}
}

    public void exitActivity(View View) { //fixme save&exit
        if (calc_pressed ==1) {
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
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    "You have to calculate the score first",
                    Toast.LENGTH_LONG).show();

        }
    }
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
    }
}

