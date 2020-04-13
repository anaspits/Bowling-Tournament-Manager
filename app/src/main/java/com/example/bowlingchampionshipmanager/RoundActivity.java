package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoundActivity extends AppCompatActivity implements RoundListAdapter.OnDeleteClickListener{ //todo na deixnei tous participants
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs;//list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
   // public static ArrayList<Team> temp3; //lista me omades opou paizoun antipales oi omades se seiriakes theseis, dld 0-1, 2-3 klp
    private static TextView textTitle,player_view;
    private static TextView hdcp_view;
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
    public static Team t;
    public String tuuid;
    private BowlingViewModel bowlingViewModel;
    private RoundListAdapter rlistAdapter;
    public String champuuid;
    public Championship championship; //todo na to perasw?
    public static List<Round> rofTeam;
    public static List<Round> test;
    public static List<Round> test2;
    public static Round curRound;
    public static Round curRound2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        Bundle bundleObject = this.getIntent().getExtras();

        player_view=(TextView) findViewById(R.id.player);
        hdcp_view=(TextView) findViewById(R.id.hdcp_view);
        first=(TextView) findViewById(R.id.first_view);
        second=(TextView) findViewById(R.id.second_view);
        third=(TextView) findViewById(R.id.third_view);
        team1=(TextView) findViewById(R.id.team1);
        team2=(TextView) findViewById(R.id.team2);
        player2_view=(TextView) findViewById(R.id.player2);
        hdcp2_view=(TextView) findViewById(R.id.hdcp2_view);
        first2=(TextView) findViewById(R.id.first2_view);
        second2=(TextView) findViewById(R.id.second2_view);
        third2=(TextView) findViewById(R.id.third2_view);
        textTitle=(TextView) findViewById(R.id.third2_view);



        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers"); //axreiasto
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            vs = (ArrayList<ArrayList>) bundleObject.getSerializable("vs");
           bowlId = bundleObject.getInt("bowlId");
           t = (Team) bundleObject.getSerializable("b_object");
           tuuid= t.getUuid();
           championship = (Championship) bundleObject.getSerializable("champ");
           System.out.println("Champ in round = "+championship.getFchampID()+" "+ championship.getUuid());
        }

        System.out.println("Team selected: "+t.getFTeamID()+" sys "+t.getSys_teamID()+" uuid "+t.getUuid());
team1.setText(t.getTeamName() );

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        rlistAdapter = new RoundListAdapter(this,this);
        recyclerView.setAdapter(rlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bowlingViewModel.getRoundsofTeam(t.getUuid()).observe(this, new Observer<List<Round>>() { //todo na valw kai to champ
            @Override
            public void onChanged(List<Round> r) {
                    rlistAdapter.setRounds(r);
                    rlistAdapter.returnRounds2(r);
                    // details.append("size of round of team 1: "+String.valueOf(r.size())+"\n");
                    System.out.println("size of round of team: "+r.size() );
                    test = r;
                    test2 = rlistAdapter.returnRounds3(r);
                    System.out.println("test3 size of round of team: "+test.size() );
                 textTitle.append(String.valueOf(r.get(0).getFroundid()));
                System.out.println("FRoundid "+r.get(0).getFroundid() ); //ton prwto gyro
            }
        });
        if(test!=null) { //test
            System.out.println("test4 size of round of team 1: "+test.size() );
        } else{
            System.out.println("test4 wtf");
        } //
        if(test2!=null) { //test
            System.out.println("test5 size of round of team 1: "+test2.size() );
        } else{
            System.out.println("test5 wtf");
        } //

       bowlingViewModel.getNextRoundofTeamofChamp(tuuid,champuuid).observe(this, new Observer<Round>() {
            @Override
            public void onChanged(Round r) {
                if(r!=null) {
                    System.out.println("Current Round of team "+t.getFTeamID()+" is round "+r.getFroundid()+" with t1: "+r.getTeam1ID()+" and t2: "+ r.getTeam2ID()+" and sysID: "+r.getRoundid());
                    curRound2=r;
                    rlistAdapter.returnCurrentRound(r);
                }
            }
        });
        //round();

        //score(t);
    }

    public void round(){

       /* gia temp3
       Team t1 = vs.get(0);
        Team t2 = vs.get(1);
        player_view.setText("Team: "+t1.getFTeamID());
        hdcp_view.setText("Team: "+t2.getFTeamID()); */

       //gia vs
        ArrayList<Team> temp= vs.get(0);
        Team t1 = temp.get(0);
        Team t2 = temp.get(1);
        team1.setText("Team: "+t1.getFTeamID());
        team2.setText("Team: "+t2.getFTeamID());

        ArrayList<Participant> teamates= t1.getTeammates();
        int i;
        for (i=0;i<teamates.size();i++) {

            player_view.append(teamates.get(i).getFN()+"\n");
            hdcp_view.append(teamates.get(i).bowlAvg+"\n");

        }
        teamates=t2.getTeammates();
        for (i=0;i<teamates.size();i++) {

            player2_view.append(teamates.get(i).getFN()+"\n");
            hdcp2_view.append(teamates.get(i).bowlAvg+"\n");

        }
    }

    public void score(Team te){
        //todo: upologismos tou neou score k update TeamScore k RoundTeamScore k participant score
    }

    public static void getRoundsofTeam(List<Round> r){ //ola ta rounds
        rofTeam = r;
        System.out.println("roundactivity rofteam "+rofTeam.size());
    }

    public static void getRoundofTeam(Round r) { //to currentRound
        curRound=r;
        System.out.println("2 Current Round of team "+t.getFTeamID()+" is round "+curRound.getFroundid()+" with t1: "+curRound.getTeam1ID()+" and t2: "+ curRound.getTeam2ID()+" and sysID: "+curRound.getRoundid());

    }


    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        System.out.println("3 Current Round of team "+t.getFTeamID()+" is round "+curRound2.getFroundid()+" with t1: "+curRound2.getTeam1ID()+" and t2: "+ curRound2.getTeam2ID()+" and sysID: "+curRound2.getRoundid());

        if (curRound.getStatus()=="next") {
            curRound.setStatus("done");
            bowlingViewModel.update(curRound);
            if (button_text.equals("next")) {
                // Intent gonext = new Intent(this,Create3Activity.class);
                //startActivity(gonext);
                curRound.setStatus("done");
                bowlingViewModel.update(curRound);
                Intent i = new Intent(this, RoundActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers", bowlers);
                extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                extras.putSerializable("all_the_teams", all_the_teams);
                extras.putSerializable("vs", vs);
                i.putExtras(extras);
                startActivity(i);

            }
        } else if (curRound.getStatus()=="last"){
            curRound.setStatus("done");
            bowlingViewModel.update(curRound);
            if (button_text.equals("next")) {
                // Intent gonext = new Intent(this,Create3Activity.class);
                //startActivity(gonext);
                curRound.setStatus("done");
                bowlingViewModel.update(curRound);
                Intent i = new Intent(this, MainActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers", bowlers);
                extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                extras.putSerializable("all_the_teams", all_the_teams);
                extras.putSerializable("vs", vs);
                i.putExtras(extras);
                startActivity(i);

            }
        }

    }

    @Override
    public void OnDeleteClickListener(Round myNote) {
        bowlingViewModel.delete(myNote);
    }
}
