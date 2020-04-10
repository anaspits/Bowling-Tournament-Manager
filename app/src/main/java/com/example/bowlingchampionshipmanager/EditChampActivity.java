package com.example.bowlingchampionshipmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditChampActivity extends AppCompatActivity implements TeamListAdapter.OnDeleteClickListener{

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    public static final int UPDATE_TEAM_ACTIVITY_REQUEST_CODE = 3;
    public static final int UPDATE_CHAMP_ACTIVITY_REQUEST_CODE = 4;
    public static final String BOWL_ID="bowlId";
    static final String UPDATED_NOTE = "bowl_text";
    private EditText editstatus, editround,par1,par2,par3,par4,par5;
    private Bundle bundle;
    private int bowlId;
    public String champuuid;
    private LiveData<Championship> champ;
    private Championship t;
    private TextView cname,param;
    static ArrayList<Integer> hdcp_parameters=new ArrayList<>();
    private ArrayList<Integer> tid = new ArrayList<>();

    EditViewModel editViewModel;
    private TeamListAdapter tlistAdapter;
    private BowlingViewModel bowlingViewModel;
    private BowlingViewModel bowlingViewModel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_champ);

        editstatus = findViewById(R.id.editstatus);
        editround = findViewById(R.id.editround);
        cname = findViewById(R.id.cname);
        par1 = (EditText) findViewById(R.id.editHDCP1);
        par2 = (EditText) findViewById(R.id.editHDCP2);
        par3 = (EditText) findViewById(R.id.editHDCP3);
        par5 = (EditText) findViewById(R.id.editHDCP4);
        par4= (EditText) findViewById(R.id.editHDCP5);
        param= (TextView)  findViewById(R.id.param);

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        bowlingViewModel2 = ViewModelProviders.of(this).get(BowlingViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.crecyclerView);
        tlistAdapter = new TeamListAdapter(this, this);
        recyclerView.setAdapter(tlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editViewModel = ViewModelProviders.of(this).get(EditViewModel.class);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            bowlId = bundle.getInt("bowlId");
            //cname.setText(String.valueOf(bowlId));
            // t = (Test_table) bundle.getSerializable("b_object");
            t =  (Championship) bundle.getSerializable("b_object");
            champuuid = t.getUuid();
        }

        /*bowlingViewModel.getAllTeamsofChamp(bowlId).observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> team) {
                tlistAdapter.setTeams(team);
            }
        });

        bowlingViewModel.getTeamsid(bowlId).observe(this, new Observer<TeammatesTuple>() {
            @Override
            public void onChanged(TeammatesTuple teamsid) {
              tid = teamsid.getListids();
            }
        });*/
      /*  param.setText(String.valueOf(tid.get(1))); //den 3erw
        ArrayList<Team> te = new ArrayList<>();
        for (int i=0; i<tid.size();i++) {
            bowlingViewModel.getTeam(i).observe(this, new Observer<Team>() {
                @Override
                public void onChanged(Team team) {

                    te.add(team);
                    //param.setText(String.valueOf(team.getSys_teamID()));
                    tlistAdapter.setTeams(te);
                }
            });
        } */

       // ArrayList<Team> te = new ArrayList<>();

       // for (int i=0; i<tid.size();i++) {
            bowlingViewModel.getAllTeamsofChamp2(champuuid).observe(this, new Observer<List<ActiveChampsTuple>>() {
                @Override
                public void onChanged(List<ActiveChampsTuple> t) {
List<Team> a = t.get(0).getT();
System.out.println("list obejct size "+t.size()+" list team size "+t.get(0).getT().size());
                    tlistAdapter.setTeams(a);
                    if(t!=null) {
                        //int a = t.get(0).getSys_teamID();
                       // param.setText(String.valueOf(a.size()));
                    } else{
                        param.setText("wtf");
                    }

                     }
                       });
        bowlingViewModel.getAllChamp_detail().observe(this, new Observer<List<Championship_detail>>() {
            @Override
            public void onChanged(List<Championship_detail> t) {
               if(t!=null) {
                    //int a = t.get(0).getSys_teamID();
                    param.setText(String.valueOf(t.size()));
                } else{
                    param.setText("wtf");
                }

            }
        });


       // ArrayList<Integer> te = new ArrayList<>();

        //fetch step 3
       /* champ = editViewModel.getChamp(bowlId);
        champ*/
       editViewModel.getChamp(bowlId).observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship champ) {
                /*for (int i = 0; i < c.size(); i++) {
                    te.add(c.get(i).getTeamID());
                } */
                //Championship champ = c.get(0); //vash 2
                cname.append(" No. "+ String.valueOf(bowlId));
                editstatus.setText(String.valueOf(champ.getStatus()));
                editround.setText(String.valueOf(champ.getRound())); //axristo
              /*   ArrayList<Integer> h = t.getHdcp_parameters(); //prin
               par1.setText(String.valueOf(h.get(0)));
                par2.setText(String.valueOf(h.get(1)));
                par3.setText(String.valueOf(h.get(2)));
                par5.setText(String.valueOf(h.get(3)));
                par4.setText(String.valueOf(h.get(4))); */

                if (String.valueOf(champ.getHdcp_beginners())!= null){ //meta
                    par1.setText(String.valueOf(champ.getHdcp_beginners()));
                }
                if (String.valueOf(champ.getHdcp_adv())!= null){ //meta
                    par2.setText(String.valueOf(champ.getHdcp_adv()));
                }
                if (String.valueOf(champ.getHdcp_less())!= null){ //meta
                    par3.setText(String.valueOf(champ.getHdcp_beginners()));
                }
                if (String.valueOf(champ.getHdcp_factor())!= null){ //meta
                    par4.setText(String.valueOf(champ.getHdcp_factor()));
                }
                if (String.valueOf(champ.getHdcp_tav())!= null){ //meta
                    par5.setText(String.valueOf(champ.getHdcp_tav()));
                }
            }
        });
    }


    public void updateDB (View view) {
        String updatedst = editstatus.getText().toString();
        String updatedr = editround.getText().toString();
        String upar1 = par1.getText().toString();
        String upar2 = par2.getText().toString();
        String upar3 = par3.getText().toString();
        String upar4 = par4.getText().toString();
        String upar5 = par5.getText().toString();

       if (upar1.matches("")){
            hdcp_parameters.add(null);
        } else {
            hdcp_parameters.add(Integer.parseInt(upar1));
        }
        if (upar2.matches("")){
            hdcp_parameters.add(null);
        } else {
            hdcp_parameters.add(Integer.parseInt(upar2));
        }
        if (upar3.matches("")){
            hdcp_parameters.add(null);
        } else {
            hdcp_parameters.add(Integer.parseInt(upar3));
        }
        if (upar4.matches("")){
            hdcp_parameters.add(null);
        } else {
            hdcp_parameters.add(Integer.parseInt(upar4));
        }
        if (upar5.matches("")){
            hdcp_parameters.add(null);
        } else {
            hdcp_parameters.add(Integer.parseInt(upar5));
        }

        t.setStatus(updatedst);
        t.setRound(Integer.parseInt(updatedr));
        t.setHdcp_parameters(hdcp_parameters);

        Intent resultIntent = new Intent();
        // resultIntent.putExtra("bowlId", bowlId);
        resultIntent.putExtra("b_object", (Serializable) t);
      /*axrista  resultIntent.putExtra(UPDATED_NOTE, updatedName);
        resultIntent.putExtra("updatedAvg", updatedAvg);
        resultIntent.putExtra("updatedTeam", updatedTeam);
        resultIntent.putExtra("updatedHdcp", updatedHdcp);
        resultIntent.putExtra("updatedfid", updatedfid); */
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate (View view) {
        finish();
    }

    @Override
    public void OnDeleteClickListener(Team myNote) {
        bowlingViewModel.delete(myNote);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;

        if (requestCode == UPDATE_CHAMP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Championship t;
                t = (Championship) bundleObject.getSerializable("b_object"); //todo: prepei na ta kanei update ola ta champ me auto to champid
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
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            //c.setText("Here");

            //check dao for the update()
            //update step 3
            // Code to update the note
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Participant t;
                t = (Participant) bundleObject.getSerializable("b_object");

                bowlingViewModel.update(t);

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        }
    }
}
