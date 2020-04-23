package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.bowlingchampionshipmanager.RoundActivity.status_flag;

public class PinsRoundActivity extends AppCompatActivity {

    private static TextView textTitle;
    private static Button nextRound_btn, exitRound_btn;
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    private static TextView team1;
    private static TextView hdcp_view, sumHDCP, sum1st, sum2nd, sum3rd;
    public int bowlId;
    public static Team t;
    public static Round r;
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
        sumHDCP = findViewById(R.id.sumHDCP);
        sum1st = findViewById(R.id.sum1st);
        sum2nd = findViewById(R.id.sum2nd);
        sum3rd = findViewById(R.id.sum3rd);

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
        recyclerView.setAdapter(blistAdapter);

        //todo na rwthsw posa rounds paizontai sta pins afou den einai antipales omades
        bowlingViewModel.getNextRoundofTeamofChamp(tuuid, champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> ro) {
                if (ro.size() != 0) {
                    curRound = ro.get(0);
                    textTitle.append(" " + String.valueOf(ro.get(0).getFroundid()));
                    System.out.println("kai to size einai " + ro.size());
                    System.out.println("Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is round " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());
                    if (tuuid.equals(curRound.getTeam1UUID())) { //perito
                        textTitle.append("\n" + "Team " + t.getTeamName());
                    } else {
                        textTitle.append("\n" + "Team " + t.getTeamName());
                    }
                    status_flag = curRound.getStatus();
                    System.out.println("2 r.getstatus=flag= " + r.getStatus());
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

        bowlingViewModel.getAllPlayersofTeam3(tuuid, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setBowls(part);
                blistAdapter.setRound(r);
                ArrayList<Round_detail> rd = new ArrayList<>(); //fixme na ta pairnw live apo to viewmodel
                for (int i = 0; i < part.size(); i++) {
                    Round_detail round_detail = new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0, part.get(i).getHdcp());
                    System.out.println(" rounddetail: rid " + r.getFroundid() + " pid " + part.get(i).getFN());
                    rd.add(round_detail);
                }
                blistAdapter.setRound_detail(rd);

            }
        });
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

    public void calculateScore(View View) {
        //gia tin omada 1
        score1 = t.getScore();
        int first_sum1 = 0;
        int second_sum1 = 0;
        int third_sum1 = 0;
        int sum_hdcp1 = 0;
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
            // team1.setText(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
            System.out.println(" emda player " + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " + RoundScoreListAdapter2.rd.size() + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());

            first_sum1 += RoundScoreListAdapter2.rd.get(i).getFirst();
            second_sum1 += RoundScoreListAdapter2.rd.get(i).getSecond();
            third_sum1 += RoundScoreListAdapter2.rd.get(i).getThird();
            sum_hdcp1 += RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp();
        }
        first_sum1 += sum_hdcp1;
        second_sum1 += sum_hdcp1;
        third_sum1 += sum_hdcp1;
        sumHDCP.setText(String.valueOf(sum_hdcp1));
        sum1st.setText(String.valueOf(first_sum1));
        sum2nd.setText(String.valueOf(second_sum1));
        sum3rd.setText(String.valueOf(third_sum1));

    }

    public void openNewActivity(View View) {
        //String button_text;
        // button_text =((Button)View).getText().toString();

        System.out.println("team1 score " + t.getScore() + " sid " + t.getSys_teamID());//todo na rwthsw
        bowlingViewModel.update(t);
        //todo na mhn mporei na patithei an den pathsw prwta calculate
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
            System.out.println(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
            System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

            bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));
            bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
            System.out.println(" rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird());

        }

        System.out.println("Current Round of team " + t.getFTeamID() + " stat " + curRound.getStatus() + " is curround " + curRound.getFroundid() + " with t1: " + curRound.getTeam1ID() + " and t2: " + curRound.getTeam2ID() + " and sysID: " + curRound.getRoundid());
        System.out.println("stat " + status_flag);
        System.out.println("1 prin flag= " + cd.getActive_flag());
        int a = cd.getActive_flag() - 1;
        cd.setActive_flag(a);//fixme na kanw update to cd kai gia thn 2h omada
        System.out.println("1 meta flag= " + cd.getActive_flag());
        bowlingViewModel.update(cd);//fixme

        curRound.setStatus("done");
        if (status_flag.equals("next")) {
            // curRound.setStatus("done");
            bowlingViewModel.update(curRound);
            System.out.println("here 1 " + curRound.getStatus());
            Intent i = new Intent(this, Pins1Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers", bowlers);
            extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
            extras.putSerializable("all_the_teams", all_the_teams);
            extras.putSerializable("champ", championship);
            extras.putSerializable("b_object", t); //selected team
            i.putExtras(extras);
            System.out.println("here 2 " + curRound.getStatus());
            startActivity(i);
        } else if (status_flag.equals("last")){
            curRound.setStatus("done");
            bowlingViewModel.update(curRound);
            //System.out.println("flag= "+cd.getActive_flag());
            System.out.println("cd size= "+cds_count);
            System.out.println("fin cd size= "+fin_cds_count);

            if(cds_count==fin_cds_count){
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

}

    public void exitActivity(View View) { //fixme save&exit
        curRound.setStatus("done");
        bowlingViewModel.update(curRound);

        if(cds_count==fin_cds_count){
            championship.setStatus("Finished");
            bowlingViewModel.update(championship);
        }
        System.out.println("1 prin flag= "+cd.getActive_flag());
        cd.setActive_flag(cd.getActive_flag()-1);
        System.out.println("1 meta flag= "+cd.getActive_flag());
        bowlingViewModel.update(cd);
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

