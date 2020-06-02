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
    //public static List<Round> rofTeam;
    private TextView textView;
    private Button btn;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);

        textView =  findViewById(R.id.textView);
btn=findViewById(R.id.exitRound_btn);
flag="none";

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers"); //axreiasto
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            vs = (ArrayList<ArrayList>) bundleObject.getSerializable("vs");
            championship= (Championship) bundleObject.getSerializable("champ");
            champuuid = championship.getUuid();
            flag = bundleObject.getString("flag");
            //rofTeam = (List<Round>) bundleObject.getSerializable("listround"); //axristo
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new SelectListAdapter(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //PAS ROUND PART 1
       /* bowlingViewModel.getAllTeamsofChamp2(champuuid).observe(this, new Observer<List<ActiveChampsTuple>>() {
            @Override
            public void onChanged(List<ActiveChampsTuple> t) {
                List<Team> a = t.get(0).getT();
                System.out.println("list obejct size "+t.size()+" list team size "+t.get(0).getT().size());
                blistAdapter.setSelected(a);
                blistAdapter.setChamp(championship);
            }
        });
    } doulevei*/

       if(flag!=null) {
           System.out.println("flag "+flag+ " champuuid "+champuuid);
           if (flag.equals("stat")) { //fixme
               textView.setText("Select a Team to view its Statistics");
               bowlingViewModel.getAllTeamsofChamp3(champuuid).observe(this, new Observer<List<Team>>() { //todo mono tis omades pou exoun teleiwsei ta rounds???
                               @Override
                               public void onChanged(List<Team> t) {
                                   System.out.println("t size "+t.size());
                                   blistAdapter.setSelected(t);
                                   blistAdapter.setChamp(championship);
                                   blistAdapter.setFlag(flag);
                                   if(t.size()==0){
                                       textView.setText("There are no Teams available");
                                   }
                               }
                           });
           }else {
               //bowlingViewModel.getAllTeamsofChamp3(champuuid).observe(this, new Observer<List<Team>>() {
               bowlingViewModel.getActiveTeamsofChamp(champuuid).observe(this, new Observer<List<Team>>() { //todo na emfanize mhows oles tis omades kai an einai active h' oxi kai aftes pou teleiwsan na tis stelnei sto finnishteam
                   @Override
                   public void onChanged(List<Team> t) {
                       blistAdapter.setSelected(t);
                       blistAdapter.setChamp(championship);
                       //  if (flag != null) {
                       blistAdapter.setFlag(flag);
                       // }
                /*if (t.size()==0){ //fixme
                    textView.setText("All the teams have finished their games");
                    btn.setText("Main Menu");
                } */
                   }
               });
           }
       } else {
           System.out.println("flag proble"); //svhsto
       }
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

    public void exitActivity(View view) {
        //todo H' finnish(); apla?
        Intent i = new Intent(this, MainActivity.class);
        //axrista?
        Bundle extras = new Bundle();
        extras.putSerializable("bowlers", bowlers);
        extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
        extras.putSerializable("all_the_teams", all_the_teams);
        extras.putSerializable("vs", vs);
        extras.putSerializable("champ", championship);
        i.putExtras(extras); //
        startActivity(i);
        finish();
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
