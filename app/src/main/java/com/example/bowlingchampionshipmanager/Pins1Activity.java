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

import java.util.ArrayList;
import java.util.List;

public class Pins1Activity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener{

    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    static ArrayList<String> hdcp_parameters;
    public String teamuuid;
    private BowlingViewModel bowlingViewModel;
    private BowlingListAdapter blistAdapter;
    public String champuuid;

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
        }

 System.out.println("all size "+all_the_teams.size());
        Button button_imp  = (Button) findViewById(R.id.button_import);

        //na svisw
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        blistAdapter = new BowlingListAdapter(this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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

            Intent i = new Intent(this, Pins1Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putString("teamid",teamuuid);
            extras.putString("champuuid",champuuid);
            i.putExtras(extras);
            startActivity(i);
        }

    }

    @Override
    public void OnDeleteClickListener(Participant myNote) {
        bowlingViewModel.delete(myNote);
    }

}
