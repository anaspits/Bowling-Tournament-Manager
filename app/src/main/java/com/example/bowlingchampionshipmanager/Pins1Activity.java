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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pins1Activity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener{

    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams; //xrhsimo
    static ArrayList<String> hdcp_parameters;
    public String teamuuid;
    private BowlingViewModel bowlingViewModel;
    private BowlingListAdapter blistAdapter;
    public String champuuid;
    private Championship ch;
    public List<Championship_detail> cd;
    private int round; //todo na rwthsw
private EditText editNorounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins1);

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            //t.setText(hdcp_parameters.get(0));
            teamuuid=bundleObject.getString("teamid");
            champuuid = bundleObject.getString("champuuid");
            ch= (Championship) bundleObject.getSerializable("champ");

        }

 System.out.println("all size "+all_the_teams.size());
        Button button_imp  = (Button) findViewById(R.id.button_import);
        editNorounds=findViewById(R.id.editNorounds);

        //na svisw
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        /*blistAdapter = new BowlingListAdapter(this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); */

        //todo na kanw insert ta round k round_detail kai na rwthsw posa rounds ginontai
      /*  for(int d = 1; d <= round; d++) {
            System.out.println(String.format("Round %d", d));
            for (int i = 0; i < all_the_teams.size(); i++) {
                System.out.println(" Team " + all_the_teams.get(i).getFTeamID());
                String ruuid = UUID.randomUUID().toString();
                Round r = new Round(ruuid, d, all_the_teams.get(i).getFTeamID(), 0, champuuid, all_the_teams.get(i).getUuid(), null, all_the_teams.get(i).getScore(), 0, "");
                if (d == round) {
                    r.setStatus("last");
                    System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus());
                } else {
                    r.setStatus("next");
                    System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus() + " chid " + champuuid);
                }

                bowlingViewModel.insert(r);
                //gia ka8e paikth ths ka8e omadas vazw to rd
                ArrayList<Participant> pa = all_the_teams.get(i).getTeammates(); //pairnw tous paiktes ths omadas auths
                for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                    Round_detail rd = new Round_detail(ruuid, pa.get(p).getUuid(), 0, 0, 0, pa.get(p).getHdcp()); //ftiaxnw to rd
                    rd.setScore(pa.get(p).getBowlAvg());
                    bowlingViewModel.insert(rd);
                    System.out.println("Rd round"+r.getFroundid()+" partici "+pa.get(p).getFN()+" "+pa.get(p).getUuid());
                }
            }
        } */

      //allazw to status tou champ
        bowlingViewModel.getChampUUID(champuuid).observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship c) {
                System.out.println(" edw to ch einai "+c.getStatus());
                System.out.println(" edw to ch to 8etw ws started");
                ch=c;
                ch.setStatus("started");
                //bowlingViewModel.update(c);
                System.out.println(" edw to ch to 8etw egine "+c.getStatus());
            }
        });

  /*      //pairnaw sto flag ka8e omadas tou champ ton arithom twn rounds kai se ka8e round aftos 8a meiwnetai mexris otou na ginei 0
        bowlingViewModel.getChamp_detailofChamp(champuuid).observe(this, new Observer<List<Championship_detail>>() {
            @Override
            public void onChanged(List<Championship_detail> c) {
                cd=c;
                for(int i=0;i<c.size();i++){
                    c.get(i).setActive_flag(round);
                    System.out.println("cd of champ "+ch.getFchampID()+" team "+cd.get(i).getSys_teamID());
                }
            }
        });*/
    }


    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();

        //kanw ta rounds kai ta round-detail
      round=Integer.parseInt(editNorounds.getText().toString());
      if(round==0){
          Toast.makeText(
                  getApplicationContext(),
                  "You can't have 0 rounds",
                  Toast.LENGTH_LONG).show();
      } else if (editNorounds.getText().toString().equals("")  || editNorounds.getText().toString().equals("e.g. 4")){
          Toast.makeText(
                  getApplicationContext(),
                  "Please insert the number of rounds",
                  Toast.LENGTH_LONG).show();
        } else{
            System.out.println("rounds " + round);
            for (int d = 1; d <= round; d++) {
                System.out.println(String.format("Round %d", d));
                for (int i = 0; i < all_the_teams.size(); i++) {
                    System.out.println(" Team " + all_the_teams.get(i).getFTeamID());
                    String ruuid = UUID.randomUUID().toString();
                    Round r = new Round(ruuid, d, all_the_teams.get(i).getFTeamID(), 0, champuuid, all_the_teams.get(i).getUuid(), null, all_the_teams.get(i).getScore(), 0, "");
                    if (d == round) {
                        r.setStatus("last");
                        System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus());
                    } else {
                        r.setStatus("next");
                        System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus() + " chid " + champuuid);
                    }

                    bowlingViewModel.insert(r);
                    //gia ka8e paikth ths ka8e omadas vazw to rd
                    ArrayList<Participant> pa = all_the_teams.get(i).getTeammates(); //pairnw tous paiktes ths omadas auths
                    for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                        Round_detail rd = new Round_detail(ruuid, pa.get(p).getUuid(), 0, 0, 0, pa.get(p).getHdcp()); //ftiaxnw to rd
                        rd.setScore(pa.get(p).getBowlAvg());
                        bowlingViewModel.insert(rd);
                        System.out.println("Rd round" + r.getFroundid() + " partici " + pa.get(p).getFN() + " " + pa.get(p).getUuid());
                    }
                }
            }

            bowlingViewModel.update(ch);
            System.out.println("ch status =" + ch.getStatus() + " type " + ch.getType());

            //pairnaw sto flag ka8e omadas tou champ ton arithom twn rounds kai se ka8e round aftos 8a meiwnetai mexris otou na ginei 0
            bowlingViewModel.getChamp_detailofChamp(champuuid).observe(this, new Observer<List<Championship_detail>>() {
                @Override
                public void onChanged(List<Championship_detail> c) {
                    cd = c;
                    for (int i = 0; i < c.size(); i++) {
                        c.get(i).setActive_flag(round);
                        System.out.println("cd of champ " + ch.getFchampID() + " team " + cd.get(i).getSys_teamID());
                        bowlingViewModel.update(cd.get(i));
                    }
                }
            });

            if (button_text.equals("Back")) {
                // Intent goback = new Intent(this,Create2Activity.class);
                // startActivity(goback);
            } else if (button_text.equals("Start Championship")) {
                //Intent gonext = new Intent(this,Create3Activity.class);
                //startActivity(gonext);

                Intent i = new Intent(this, SelectTeamActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers", bowlers);
                extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                extras.putString("teamid", teamuuid);
                extras.putString("champuuid", champuuid);
                extras.putSerializable("champ", ch);
                i.putExtras(extras);
                startActivity(i);
            }
        }
    }

    @Override
    public void OnDeleteClickListener(Participant myNote) {
        bowlingViewModel.delete(myNote);
    }

}
