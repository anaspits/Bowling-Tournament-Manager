package com.example.bowlingchampionshipmanager;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Create3Activity extends AppCompatActivity implements DetailListAdapter.OnDeleteClickListener {
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    static ArrayList<String> hdcp_parameters;
    public String teamuuid; //axristo
    private static TextView textTitle,fixedcap;
    private static RadioButton pins;
    private static RadioButton teamsvsteams;
    private static Button start;
    private List<TeammatesTuple> playersandteams;
    private BowlingViewModel bowlingViewModel;
    private DetailListAdapter dlistAdapter;
    public String champuuid;
    public Championship championship;
    private  EditText editLanes,editfixedcap;
    private int lanes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create3);

        OnBackPressedCallback cb =new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed(){
                openDialog();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this,cb);

        textTitle=findViewById(R.id.textTitle);
       //t= (TextView) findViewById(R.id.textView22);

       pins= (RadioButton) findViewById(R.id.pins);
       teamsvsteams= (RadioButton) findViewById(R.id.teamsvsteams);
       start= (Button) findViewById(R.id.next_btn);
        editLanes =  findViewById(R.id.editLanes);
        editfixedcap=  findViewById(R.id.editfixedcap);
        editfixedcap.setVisibility(View.GONE);
        fixedcap=  findViewById(R.id.fixedcap);
        fixedcap.setVisibility(View.GONE);
       //start.setEnabled(false);

        teamsvsteams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editfixedcap.setVisibility(View.VISIBLE);
                fixedcap.setVisibility(View.VISIBLE);
            }
        });

        pins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editfixedcap.setVisibility(View.GONE);
                fixedcap.setVisibility(View.GONE);
            }
        });

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            playersandteams= (List<TeammatesTuple>) bundleObject.getSerializable("teammates");
            //t.setText(hdcp_parameters.get(0));
            teamuuid=bundleObject.getString("teamid"); //todo na pernaw to object
            champuuid = bundleObject.getString("champuuid");
            championship= (Championship) bundleObject.getSerializable("champ");
            textTitle.append(" No."+championship.getSys_champID());
            if(championship.getType()==4){
                teamsvsteams.setVisibility(View.GONE);
            }
        }

        if(all_the_teams!=null) {
            System.out.println("all size 3 " + all_the_teams.size());
            System.out.println("tm size 3 " + playersandteams.size());
        }else {
            bowlingViewModel.getAllTeamsofChamp3(champuuid).observe(this, new Observer<List<Team>>() {
                @Override
                public void onChanged(List<Team> te) {
                    all_the_teams= (ArrayList<Team>) te;
                }
            });

            bowlingViewModel.getAllTeamatesofAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeammatesTuple>>() {
                @Override
                public void onChanged(List<TeammatesTuple> p1) {
                    playersandteams = p1;
                }
            });
        }

        if(bowlers!=null) {
            System.out.println("bowlers size " + bowlers.size());
        }else {
            System.out.println(" bowlers null");
            bowlingViewModel.getAllPlayersofChamp(champuuid).observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> part) {
                    bowlers= (ArrayList<Participant>) part;
                }
            });

        }
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
      /*  RecyclerView recyclerView = findViewById(R.id.recyclerView);
        dlistAdapter = new DetailListAdapter(this, this);
        recyclerView.setAdapter(dlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getAllTeam_detail().observe(this, new Observer<List<Team_detail>>() { //an krathsw to uuid to auto einai axristo
            @Override
            public void onChanged(List<Team_detail> td) {
                dlistAdapter.setTeam_detail(td);
                int a= dlistAdapter.getItemCount();
                System.out.println("count ="+a);

            }
        }); */

        //axristo
        bowlingViewModel.getChampUUID(champuuid).observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship c) {
championship = c;

            }
        });
    }

    public void openDialog() {
        WarningDialog exampleDialog = new WarningDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();

        if(!editLanes.getText().toString().matches("") || !TextUtils.isEmpty(editLanes.getText())) {
            lanes = Integer.parseInt(editLanes.getText().toString());

        if(button_text.equals("Back"))
        {
           // Intent goback = new Intent(this,Create2Activity.class);
           // startActivity(goback);
        }
        else if (button_text.equals("Next"))
        {
            //Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);

           if (pins.isChecked()){
               if(championship.getType()==4){
                   Intent i = new Intent(this, Pins1Activity.class);
                   Bundle extras = new Bundle();
                   extras.putSerializable("bowlers", bowlers);
                   extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                   extras.putString("teamid", teamuuid); //giati?
                   extras.putString("champuuid", champuuid);
                   extras.putSerializable("champ", championship);
                   extras.putSerializable("all_the_teams", all_the_teams);
                   extras.putInt("lanes",lanes);
                   i.putExtras(extras);
                   startActivity(i);
                   finish();
               }else {
                   System.out.println("champion sid " + championship.getSys_champID());
                   championship.setType(1);
                   bowlingViewModel.update(championship);
                   Intent i = new Intent(this, Pins1Activity.class);
                   Bundle extras = new Bundle();
                   extras.putSerializable("bowlers", bowlers);
                   extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                   extras.putString("teamid", teamuuid); //giati?
                   extras.putString("champuuid", champuuid);
                   extras.putSerializable("champ", championship);
                   extras.putInt("lanes",lanes);
                   extras.putSerializable("all_the_teams", all_the_teams);
                   extras.putSerializable("teammates", (Serializable) playersandteams);
                   i.putExtras(extras);
                   startActivity(i);
                   finish();
              /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                   finishAffinity();
               }*/
               }
            } else if (teamsvsteams.isChecked()) {
               if (editfixedcap.getText().toString().matches("")) {
                   Toast.makeText(
                           getApplicationContext(),
                           "You have to insert a fixed score for the blind",
                           Toast.LENGTH_LONG).show();
               } else {
                   System.out.println("champion sid " + championship.getSys_champID());
                   championship.setType(2);
                   championship.setFiexd_cap(Integer.parseInt(editfixedcap.getText().toString()));
                   bowlingViewModel.update(championship);
                   Intent i = new Intent(this, Teamsvsteams1Activity.class);
                   Bundle extras = new Bundle();
                   extras.putSerializable("bowlers", bowlers);
                   extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
                   extras.putSerializable("all_the_teams", all_the_teams);
                   extras.putSerializable("teammates", (Serializable) playersandteams);
                   extras.putString("champuuid", champuuid);
                   extras.putSerializable("champ", championship);
                   extras.putInt("lanes", lanes);
                   i.putExtras(extras);
                   startActivity(i);
                   finish();
             /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                   finishAffinity();
               }*/
               }
           }/*

           }*/else{
               Toast.makeText(
                       getApplicationContext(),
                       "You have to choose a type first",
                       Toast.LENGTH_LONG).show();
           }

        }
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    "You have to insert the lanes",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void OnDeleteClickListener(Team_detail myNote) {
       // bowlingViewModel.delete(myNote);
    }
}
