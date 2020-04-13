package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectTeamActivity extends AppCompatActivity {

    public static final int SELECT_TEAM_ACTIVITY_REQUEST_CODE = 6;
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs;//list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
    private BowlingViewModel bowlingViewModel;
    private SelectListAdapter blistAdapter;
    public String champuuid, teamuuid;
    public Championship championship;
    public static List<Round> rofTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers"); //axreiasto
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            vs = (ArrayList<ArrayList>) bundleObject.getSerializable("vs");
            champuuid = bundleObject.getString("champuuid");
            championship= (Championship) bundleObject.getSerializable("champ");
            rofTeam = (List<Round>) bundleObject.getSerializable("listround"); //axristo
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new SelectListAdapter(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getAllTeamsofChamp2(champuuid).observe(this, new Observer<List<ActiveChampsTuple>>() {
            @Override
            public void onChanged(List<ActiveChampsTuple> t) {
                List<Team> a = t.get(0).getT();
                System.out.println("list obejct size "+t.size()+" list team size "+t.get(0).getT().size());
                blistAdapter.setSelected(a);
                blistAdapter.setChamp(championship);
            }
        });
    }

//axristo
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;

        if (requestCode == SELECT_TEAM_ACTIVITY_REQUEST_CODE) { ////////gia edit kai update
            Bundle bundleObject = resultData.getExtras();
            if (bundleObject != null) {
                Team t;
                t = (Team) bundleObject.getSerializable("b_object");
                teamuuid = t.getUuid();

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        }
    }



/*    public void openNewActivity( Team t) {
        String button_text;

            //Intent gonext = new Intent(this,Create2Activity.class);
            //startActivity(gonext);
            Intent i =  new Intent(SelectTeamActivity.this, RoundActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bowlers",bowlers);
            bundle.putSerializable("all_the_teams",all_the_teams);
            bundle.putSerializable("champ",championship);
            bundle.putString("champuuid",champuuid); //na perasw auto h to object championship oloklhro?
            i.putExtras(bundle);
            startActivity(i);
    }*/
}
