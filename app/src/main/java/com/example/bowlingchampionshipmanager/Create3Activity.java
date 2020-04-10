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
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Create3Activity extends AppCompatActivity implements DetailListAdapter.OnDeleteClickListener {
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    static ArrayList<String> hdcp_parameters;
    public String teamuuid;
    //private static TextView t;
    private static RadioButton pins;
    private static RadioButton teamsvsteams;
    private static Button start;

    private BowlingViewModel bowlingViewModel;
    private DetailListAdapter dlistAdapter;
    public String champuuid;
    public Championship championship;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create3);

       //t= (TextView) findViewById(R.id.textView22);

       pins= (RadioButton) findViewById(R.id.pins);
       teamsvsteams= (RadioButton) findViewById(R.id.teamsvsteams);
       start= (Button) findViewById(R.id.next_btn);
       //start.setEnabled(false);


        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            //t.setText(hdcp_parameters.get(0));
            teamuuid=bundleObject.getString("teamid");
            champuuid = bundleObject.getString("champuuid");
            championship= (Championship) bundleObject.getSerializable("champ");
        }
        System.out.println("all size 3 "+all_the_teams.size());
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        dlistAdapter = new DetailListAdapter(this, this);
        recyclerView.setAdapter(dlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getAllTeam_detail().observe(this, new Observer<List<Team_detail>>() {
            @Override
            public void onChanged(List<Team_detail> td) {
                dlistAdapter.setTeam_detail(td);
                int a= dlistAdapter.getItemCount();
                System.out.println("count ="+a);

            }
        });

    }


    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Back"))
        {
           // Intent goback = new Intent(this,Create2Activity.class);
           // startActivity(goback);
        }
        else if (button_text.equals("Start Championship"))
        {
            //Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);

           if (pins.isChecked()){
                Intent i = new Intent(this, Pins1Activity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers",bowlers);
                extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
               extras.putString("teamid",teamuuid);
               extras.putString("champuuid",champuuid);
               extras.putSerializable("all_the_teams",all_the_teams);
                i.putExtras(extras);
                startActivity(i);

            } else if (teamsvsteams.isChecked()) {
                Intent i = new Intent(this, Teamsvsteams1Activity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("bowlers",bowlers);
                extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
                extras.putSerializable("all_the_teams",all_the_teams);
                extras.putString("champuuid",champuuid);
                extras.putSerializable("champ",championship);
                i.putExtras(extras);
                startActivity(i);
            }


        }

    }
    @Override
    public void OnDeleteClickListener(Team_detail myNote) {
        bowlingViewModel.delete(myNote);
    }
}
