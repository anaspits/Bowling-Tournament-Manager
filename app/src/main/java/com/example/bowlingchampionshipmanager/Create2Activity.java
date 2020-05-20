package com.example.bowlingchampionshipmanager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Create2Activity extends AppCompatActivity implements TeamatesAdapter.OnDeleteClickListener {

    private static TextView textView;
    private static TextView display_teams;
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    private int playersPerTeam=2;

    private String TAG = this.getClass().getSimpleName();
    private BowlingListAdapter blistAdapter;
    private BowlingListAdapter blistAdapter2;
    private TeamListAdapter tlistAdapter;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    public static final int UPDATE_TEAM_ACTIVITY_REQUEST_CODE = 3;
    public static final int UPDATE_CHAMP_ACTIVITY_REQUEST_CODE = 4;
    private static final int SELECT_TEAM_ACTIVITY_REQUEST_CODE = 6;
    private BowlingViewModel bowlingViewModel;
    public static int sum;
    private static int champinsertID;
    public static int countTeam;
    public String teamuuid;
    public String champuuid;
    public Championship championship;
    private List<TeammatesTuple> playersandteams;
    private TeamatesAdapter tplistAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        OnBackPressedCallback cb =new OnBackPressedCallback(true){ //todo den 8a xanetai to progress, na exw ena toast pou na grafei saved
            @Override
            public void handleOnBackPressed(){
                openDialog();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this,cb);
        
        ///recyclerview
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //blistAdapter = new BowlingListAdapter(this, this);
       // blistAdapter2 = new BowlingListAdapter(this, this);
        //tlistAdapter = new TeamListAdapter(this, this);
        //recyclerView.setAdapter(blistAdapter);
        tplistAdapter = new TeamatesAdapter(this,this);
        recyclerView.setAdapter(tplistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //addnew sto database
        Button addnew= findViewById(R.id.addnew);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create2Activity.this, AddNewActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });


       /* bowlingViewModel.getAllPlayersofTeamOrdered().observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> participants) {
                blistAdapter.setBowls(participants);
            }
        }); */
/*        bowlingViewModel.getAllPlayersofChamp(0).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> participants) {
                //blistAdapter2.setBowls(participants);
//                sum =blistAdapter2.getItemCount();
                //addnew.setText(String.valueOf(sum));
            }
        }); */
       /* bowlingViewModel.getTeammates(7).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> participants) {
                blistAdapter.setBowls(participants);
            }
        });*/

////////////

        //textView = (TextView) findViewById(R.id.row11);
        display_teams = (TextView) findViewById(R.id.teams);

        Bundle bundleObject = this.getIntent().getExtras();
       if(bundleObject!=null){

            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
           all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
           championship = (Championship) bundleObject.getSerializable("champ");
           champuuid = bundleObject.getString("champuuid");
            }

      /*todo if(bowlers==null){
             bowlingViewModel.getBowlsofChamp{
             bowlers=b;
             }
             to idio k gia all_the_teams
       }*/

        bowlingViewModel.getAllTeamatesofAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeammatesTuple>>() {
            @Override
            public void onChanged(List<TeammatesTuple> p1) {
                playersandteams = p1;
                tplistAdapter.setTeams(p1);
                tplistAdapter.setChamp(championship);
            }
        });

        //System.out.println("Team 1 = " + all_the_teams.get(0).getTeamName());
        ///na svisw
        bowlingViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> team) {
                //tlistAdapter.setTeams(team);
    /*gai sysid ws primary            for(int i=0; i<all_the_teams.size();i++){
                    all_the_teams.get(i).setTeamName(String.valueOf(team.get(i).getSys_teamID()));
                    team.get(i).setTeamName(String.valueOf(team.get(i).getSys_teamID()));
                    bowlingViewModel.update(team.get(i));
                    ArrayList<Participant> t = all_the_teams.get(i).getTeammates();
                   /* for (int j=0; j<t.size();j++) {
                        System.out.println("Td: teamid "+team.get(i).getSys_teamID()+" pid "+t.get(j).getParticipant_uuid());
                        Team_detail td = new Team_detail(team.get(i).getSys_teamID(),t.get(j).getParticipant_uuid());
                        bowlingViewModel.insert(td);
                    } */ /*

                }
               // int id = team.get(2).getSys_teamID();
                //addnew.setText(String.valueOf(id));
                sum=team.get(2).getSys_teamID(); telos*/

              //  tlistAdapter.setTeams(team);
              //  tlistAdapter.setChamp(championship);
            }
        });


        //insert td and chd vash 2
        bowlingViewModel.getParticipantByName("Johnnie", "Taft").observe(this, new Observer<List<Participant>>() { //axristo
            @Override
            public void onChanged(List<Participant> team) {
                //System.out.println("sum = id = "+team.get(0).getParticipant_uuid()); //
            }
        });
       /* bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
            @Override
            public void onChanged(List<Championship> ch) {
                champinsertID =ch.size();
                System.out.println("chid 3 " +ch.get(champinsertID-1).getSys_champID());
                System.out.println("chid 4 " +all_the_teams.get(0).getSys_teamID());
                for (int i=0; i<all_the_teams.size();i++) {
                    Championship_detail cd = new Championship_detail(champinsertID,all_the_teams.get(i).getSys_teamID());
                    bowlingViewModel.insert(cd);

                    ArrayList<Participant> t = all_the_teams.get(i).getTeammates();
                    for (int j=0; j<t.size();j++) {
                        Team_detail td = new Team_detail(all_the_teams.get(i).getSys_teamID(),t.get(j).getParticipant_uuid());
                        bowlingViewModel.insert(td);
                    }
                }
            }
        });
        System.out.println("chid 3" +champinsertID);
        for (int i=0; i<all_the_teams.size();i++) {
            Championship_detail cd = new Championship_detail(champinsertID,all_the_teams.get(i).getSys_teamID());
            bowlingViewModel.insert(cd);

            ArrayList<Participant> t = all_the_teams.get(i).getTeammates();
            for (int j=0; j<t.size();j++) {
                Team_detail td = new Team_detail(all_the_teams.get(i).getSys_teamID(),t.get(j).getParticipant_uuid());
                bowlingViewModel.insert(td);
            }
        }*/



       /*Participant p = bowlers.get(0);
        String fnn =p.getFirstName();
        textView.setText(fnn); */
       /*
       //palia emfanish, xwris to teams
       int i;
        for(i = 0; i < bowlers.size()/2;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            //System.out.println("Team " + (i + 1) + ": " + p.getFirstName() + " " + p.getLastName() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFirstName() + " " + p.getPartner().getLastName() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
            display_teams.append("Team " + p.getTeamid() + ": " + p.getFirstName() + " " + p.getLastName() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFirstName() + " " + p.getPartner().getLastName() + " (Avg: " + p.getPartner().getBowlAvg() + " )" + "\n");
        } */

       //nea emfanish me th xrhsh tou arraylist teams pou exei mesa tou arraylist me Participant
        int i;
       /* for (i=0; i<teams.size();i++) {
            ArrayList<Participant> temp = teams.get(i);

            display_teams.append("\n"+"Team " + (i+1) +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                display_teams.append(temp.get(j).getFirstName() +"  ");
            }

        } */

        //nea nea TELIKH emfanish me xrhsh tou arraylist all_the_teams pou exei mesa Team gia thn vash 1
/*apo edw 201 ws 210        for (i=0; i<all_the_teams.size();i++) {
            Team t = all_the_teams.get(i);
            ArrayList<Participant> temp =  t.getTeammates();

            display_teams.append("\n"+"Team " + t.getFTeamID() +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                display_teams.append(temp.get(j).getFirstName() +"  ");
            }
        } ws edw */

    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);

        ////////gia insert sto database tou room
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
          /*axrista  String nam =resultData.getStringExtra(AddNewActivity.NEW_ADDED);
            int avg = Integer.parseInt(resultData.getStringExtra("new_avg"));
            int team = Integer.parseInt(resultData.getStringExtra("new_avg"));
            int hdcp = Integer.parseInt(resultData.getStringExtra("new_avg")); //na to valw ston constructor
            int champ = Integer.parseInt(resultData.getStringExtra("new_avg"));
           // int fakeid = bowlingViewModel.getAllPlayersofChamp(champ);
            //int fakeid = Integer.parseInt(resultData.getStringExtra("new_fid")); */
            int fakeid = sum+1;

            //Test_table t= new Test_table( resultData.getStringExtra(AddNewActivity.NEW_ADDED));
            //na ftia3w to name
            //Participant t = new Participant(fakeid,resultData.getStringExtra(AddNewActivity.NEW_ADDED), "",avg,team);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null) {
                Participant t = (Participant) bundleObject.getSerializable("b_object");
                t.setFakeID(fakeid);
                bowlingViewModel.insert(t);
                Create1Activity.t_id++; //axristo

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c = findViewById(R.id.back_btn);
            //c.setText("Here");

            //check dao for the update()
            //update step 3
            // Code to update the note
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
              /*axrista  int bowlId;
                String nam;
                int avg,team,hdcp,fid;
                //Test_table t; */
                Participant t;

              /*axrista  bowlId = bundleObject.getInt("bowlId");
                nam = bundleObject.getString(EditActivity.UPDATED_NOTE);
                avg = Integer.parseInt( bundleObject.getString("updatedAvg")); //alliws  avg = Integer.valueOf(bundleObject.getString("updatedAvg"));
                //team = Integer.parseInt( bundleObject.getString("updatedTeam"));
                hdcp = Integer.parseInt( bundleObject.getString("updatedHdcp"));
                fid = Integer.parseInt( bundleObject.getString("updatedfid")); */
                //na kanw ta sets stin EditActivity anti gia edw              // t= (Test_table) bundleObject.getSerializable("b_object");
                t = (Participant) bundleObject.getSerializable("b_object");
                /*t.setFirstName(nam); t.setLastName("");
                t.setBowlAvg(avg);
                // t.setTeamid(team);
                t.setHdcp(hdcp);
                t.setFchampID(fid); */
                // Test_table t1 = new Test_table(bowlId,up);
                bowlingViewModel.update(t);

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == UPDATE_TEAM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Team t;
                t = (Team) bundleObject.getSerializable("b_object");
                bowlingViewModel.update(t);
                countTeam = bundleObject.getInt("count");
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == SELECT_TEAM_ACTIVITY_REQUEST_CODE) { ////////gia edit kai update
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Team t;
                t = (Team) bundleObject.getSerializable("b_object");
                countTeam = bundleObject.getInt("count");
                teamuuid = t.getUuid();
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        }
///////////////////////////
    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        /*if(button_text.equals("Back"))
        {
            Intent goback = new Intent(this,Create1Activity.class);
            startActivity(goback);
        }
        else */ if (button_text.equals("Next"))
        {
            //Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);
            Intent i =  new Intent(Create2Activity.this, Create3Activity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bowlers",bowlers);
            bundle.putSerializable("all_the_teams",all_the_teams);
            bundle.putString("teamid",teamuuid); //todo giati to pairnaw auto?
            bundle.putString("champuuid",champuuid);
            bundle.putSerializable("champ",championship);
            i.putExtras(bundle);
            startActivity(i);

        }
        else if (button_text.equals("HDCP Parameters"))
        {
            //Intent goHDCP = new Intent(this,HDCPActivity.class);
            //startActivity(goHDCP);
            Intent i =  new Intent(Create2Activity.this, HDCPActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bowlers",bowlers);
            bundle.putSerializable("all_the_teams",all_the_teams);
            bundle.putString("champuuid",champuuid);
            bundle.putSerializable("champ",championship);
            i.putExtras(bundle);
            startActivity(i);

        }
    }

    @Override
    public void OnDeleteClickListener(TeammatesTuple myNote) {
        myNote.getC().setActive_flag(1);
    }
}

