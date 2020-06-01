package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

//todo scrollable
public class AddNewActivity extends AppCompatActivity {

    public static final String NEW_ADDED = "new_added";
    private EditText newfirstname,newavg,newteam,newchamp,newhdcp, newlastname;
    private Participant participant;
    private BowlingViewModel bowlingViewModel;
    private static RadioButton male,female;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        newfirstname = findViewById(R.id.newfirstname);
        newlastname = findViewById(R.id.newlastname);
        newavg = findViewById(R.id.newavg);
        newhdcp= findViewById(R.id.newhdcp);
        newteam = findViewById(R.id.newteam);
        newchamp = findViewById(R.id.newchamp);
        male= findViewById(R.id.male);
        female= findViewById(R.id.female);

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);

        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(newfirstname.getText()) || TextUtils.isEmpty(newlastname.getText()) || TextUtils.isEmpty(newavg.getText()) || TextUtils.isEmpty(newhdcp.getText())) {
                    //setResult(RESULT_CANCELED, resultIntent);
                    Toast.makeText(
                            getApplicationContext(),
                            "Please fill all the fields",
                            Toast.LENGTH_LONG).show();

                } else {
                    String fname = newfirstname.getText().toString().trim();
                    String lname = newlastname.getText().toString().trim();
                    String avg = newavg.getText().toString().trim();
                    String hdcp = newhdcp.getText().toString().trim();
                    String team = newteam.getText().toString().trim();
                    String champ = newchamp.getText().toString().trim(); //todo ti na kanw me afta?
                    String uuid = UUID.randomUUID().toString().trim();
                    if (male.isChecked()){
                        Participant t = new Participant(0,uuid,fname, lname,Integer.parseInt(avg),Integer.parseInt(team), Calendar.getInstance().getTime(),Integer.parseInt(hdcp),"m", 0);
                        resultIntent.putExtra("b_object", (Serializable) t);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else if (female.isChecked()){
                        Participant t = new Participant(0,uuid,fname, lname,Integer.parseInt(avg),Integer.parseInt(team), Calendar.getInstance().getTime(),Integer.parseInt(hdcp),"f", 0);
                        resultIntent.putExtra("b_object", (Serializable) t);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Choose male/female",
                                Toast.LENGTH_LONG).show();
                    }
                    /*axrista resultIntent.putExtra(NEW_ADDED, fname);
                    resultIntent.putExtra("new_avg", avg);
                    resultIntent.putExtra("new_team", team);
                    resultIntent.putExtra("new_champ", champ); */
                    //resultIntent.putExtra("new_fid", sfid);

                   // resultIntent.putExtra("b_object", (Serializable) t);
                    //setResult(RESULT_OK, resultIntent);
                }

            }
        });


    }
}
