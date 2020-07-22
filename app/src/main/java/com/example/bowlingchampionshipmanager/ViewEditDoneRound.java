package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

//UNDER CONSTRUCTION
public class ViewEditDoneRound extends AppCompatActivity {

    private static TextView textTitle;
    private static Button nextRound_btn, exitRound_btn;
    private static TextView teams;
    public static Team t, t2;
    public static Round r, r2;
    public static String tuuid1, tuuid2;
    private int score1, score2;
    private BowlingViewModel bowlingViewModel;
    public String champuuid;
    public Championship championship;
    private Championship_detail cd, cd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_done_round);

        textTitle = findViewById(R.id.textTitle);
        teams= findViewById(R.id.teams);
        nextRound_btn = findViewById(R.id.nextRound_btn);
        exitRound_btn = findViewById(R.id.exitRound_btn);
        Bundle bundleObject = this.getIntent().getExtras();
        //save_pressed = 0;
        //System.out.println("arxh  ave_pressed = "+save_pressed);

        if (bundleObject != null) {
            t = (Team) bundleObject.getSerializable("b_object");
            tuuid1 = t.getUuid();
            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = " + championship.getFchampID() + " " + championship.getUuid());
            champuuid = championship.getUuid();

            r = (Round) bundleObject.getSerializable("round");
            if (r != null) {
                System.out.println("got round from sel " + r.getFroundid() + " uuid " + r.getRounduuid() + " stat " + r.getStatus() + " with t1: " + r.getTeam1ID() + " and t2: " + r.getTeam2ID() + " and sysID: " + r.getRoundid());
            } else {
                System.out.println("got round from sel ERROR");
            }

        }

        textTitle.setText("Round No."+r.getFroundid());


        if (championship.getType()==2) {
            if (t.getUuid().equals(r.getTeam1UUID())) {
                //textTitle.append("\n"+"Team "+team1.getTeamName()+" VS Team " + r.getTeam2ID());
                teams.setText("Team " + t.getTeamName()+ " VS Team "+r.getTeam2ID());
                tuuid2=r.getTeam2UUID();
            } else {
                teams.setText("Team "+t.getTeamName()+" VS Team " + r.getTeam1ID());
                tuuid2=r.getTeam1UUID();
            }
        }
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);

    }

}
