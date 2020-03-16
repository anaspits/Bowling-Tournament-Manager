package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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

public class Create2Activity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener{
    private static TextView textView;
    private static TextView display_teams;
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    private int playersPerTeam=2;

    private String TAG = this.getClass().getSimpleName();
    private BowlingListAdapter blistAdapter;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private BowlingViewModel bowlingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        ///recyclerview
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new BowlingListAdapter(this, this);
        recyclerView.setAdapter(blistAdapter);
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

        bowlingViewModel.getAllBowls().observe(this, new Observer<List<Test_table>>() {
            @Override
            public void onChanged(List<Test_table> test_tables) {
                blistAdapter.setBowls(test_tables);
            }
        });
////////////

        //textView = (TextView) findViewById(R.id.row11);
        display_teams = (TextView) findViewById(R.id.teams);

        Bundle bundleObject = this.getIntent().getExtras();
       if(bundleObject!=null){

            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
           all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            }


       /*Participant p = bowlers.get(0);
        String fnn =p.getFN();
        textView.setText(fnn); */
       /*
       //palia emfanish, xwris to teams
       int i;
        for(i = 0; i < bowlers.size()/2;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            //System.out.println("Team " + (i + 1) + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
            display_teams.append("Team " + p.getTeamid() + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )" + "\n");
        } */

       //nea emfanish me th xrhsh tou arraylist teams pou exei mesa tou arraylist me Participant
        int i;
       /* for (i=0; i<teams.size();i++) {
            ArrayList<Participant> temp = teams.get(i);

            display_teams.append("\n"+"Team " + (i+1) +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                display_teams.append(temp.get(j).getFN() +"  ");
            }

        } */

        //nea nea emfanish me xrhsh tou arraylist all_the_teams pou exei mesa Team
        for (i=0; i<all_the_teams.size();i++) {
            Team t = all_the_teams.get(i);
            ArrayList<Participant> temp =  t.getTeamates();

            display_teams.append("\n"+"Team " + t.getTeamID() +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                display_teams.append(temp.get(j).getFN() +"  ");
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
////////////////gia room insert
        /*final int t_id = 4;
        Test_table t= new Test_table(t_id, "lol4");
        bowlingViewModel.insert(t); */
        /////////////// telos room insert
        ////////gia insert sto database tou room
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
            //final String note_id = UUID.randomUUID().toString();
            final int id = 2;
            Test_table t = new Test_table( resultData.getStringExtra(AddNewActivity.NEW_ADDED));
            bowlingViewModel.insert(t);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.save,
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c = findViewById(R.id.back_btn);
            //c.setText("Here");

            //check dao for the update()
            //update step 3
            // Code to update the note
            Bundle bundleObject = resultData.getExtras();
            if (bundleObject != null) {
                int bowlId;
                String up;
                Test_table t;

                bowlId = bundleObject.getInt("bowlId");
                up =  bundleObject.getString(EditActivity.UPDATED_NOTE);
                t= (Test_table) bundleObject.getSerializable("b_object");
                t.setName(up);
                bowlingViewModel.update(t);

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
            i.putExtras(bundle);
            startActivity(i);

        }
    }

    @Override
    public void OnDeleteClickListener(Test_table myNote) {
        bowlingViewModel.delete(myNote);
    }
}

