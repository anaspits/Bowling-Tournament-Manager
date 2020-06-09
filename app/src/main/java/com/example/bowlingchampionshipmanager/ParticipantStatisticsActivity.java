package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

public class ParticipantStatisticsActivity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener{

    private BowlingViewModel bowlingViewModel;
    public String champuuid;
    public Participant p;
    private TextView textView,player, txvpl,txv2,txv3;
    private  Button addnew;
    private String flag;
    private SelectParticipantListAdapter blistAdapter;
    private List<Participant> participants;

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private BowlingListAdapter blistAdapter2;
    private int sum;

    //fixme fix UI k na to metaonomasw se Particpant list h kati tetoio
    //todo tha mporousa na valw kai ton ari8mo twn champs pou epai3e o ka8e paikths
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_statistics);
        player = findViewById(R.id.player);
        textView=findViewById(R.id.textView);
        addnew =findViewById(R.id.addnew);
        txvpl=findViewById(R.id.txvpl);
        txv2=findViewById(R.id.txv2);
        txv3=findViewById(R.id.txv3);

        //An 8elw na pros8esw enan paikth sth bash na energopoihsw to koumpi
        addnew.setVisibility(View.GONE);
        //pros to paron epeidh den xrhshmevei kapou to na ginei insert enos paikth mono tou sth bash, opote to koumpi to exw kryfo

        flag = "none";

        Bundle bundleObject = this.getIntent().getExtras();
        if (bundleObject != null) {
            p = (Participant) bundleObject.getSerializable("participant");
            flag = bundleObject.getString("flag");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new SelectParticipantListAdapter(this);
        blistAdapter2 = new BowlingListAdapter(this, this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (flag.equals("all")) {
            txv3.setVisibility(View.GONE);
            recyclerView.setAdapter(blistAdapter2);
            textView.setText("All the Players:");
            //addnew sto database
            Button addnew= findViewById(R.id.addnew);
            addnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ParticipantStatisticsActivity.this, AddNewActivity.class);
                    startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });
            bowlingViewModel.getAllActiveParticipants().observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> part) {

                    blistAdapter2.setBowls(part);
                    participants=part;
                    sum =blistAdapter2.getItemCount();
                }
            });
        }else if (flag.equals("all_stat")) {
            addnew.setVisibility(View.GONE);
            bowlingViewModel.getAllActiveParticipants().observe(this, new Observer<List<Participant>>() {
                @Override
                public void onChanged(List<Participant> part) {
                    blistAdapter.setSelected(part);
                    blistAdapter.setbtnSelFlag(1);
                    participants=part;
                }
            });
        } else {
            //todo fix UI
           // recyclerView.setVisibility(View.GONE);
            addnew.setVisibility(View.GONE);
            player.setText("Name: "+p.getFullName());
            player.append("\nHDCP: "+p.getHdcp());
            player.append("\nAverage: "+p.getBowlAvg());
            player.append("\nGames: "+p.getTotal_games());
        }
    }

    public void export(View view) {
        StringBuilder data = new StringBuilder();

        if (flag.equals("all")) {
            data.append(" ,Name,HDCP,Average,Games");


        for (int i = 0; i < participants.size(); i++) {
            Participant p =participants.get(i);
            data.append("\n" + (i+1)+"," + p.getFullName()+","+p.getHdcp()+","+p.getBowlAvg()+","+p.getTotal_games());
        }

            try {
                //saving the file into device
                FileOutputStream out = openFileOutput("bowling_championship_players_stat.csv", Context.MODE_PRIVATE);
                out.write((data.toString()).getBytes());
                out.close();

                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "bowling_championship_players_stat.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.bowlingchampionshipmanager.fileprovider", filelocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "Send mail"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data.append("Name,HDCP,Average,Games");
            data.append("\n" + p.getFullName()+","+p.getHdcp()+","+p.getBowlAvg()+","+p.getTotal_games());

            try {
                //saving the file into device
                FileOutputStream out = openFileOutput("bowling_championship_player_stat.csv", Context.MODE_PRIVATE);
                out.write((data.toString()).getBytes());
                out.close();

                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "bowling_championship_player_stat.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.bowlingchampionshipmanager.fileprovider", filelocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "Send mail"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;
        //Cursor returnCursor = getContentResolver().query(currentUri,null,null,null,null);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
            //final String note_id = UUID.randomUUID().toString();
            //int fakeid = bowlingViewModel.getAllPlayersofChamp(champ);
            // int fakeid = Integer.parseInt(resultData.getStringExtra("new_fid"));
            int fakeid = sum + 1;
            Bundle bundleObject = resultData.getExtras();
            if (bundleObject != null) {
                Participant t = (Participant) bundleObject.getSerializable("b_object");
                t.setFakeID(fakeid);
                bowlingViewModel.insert(t);

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update

            //update step 3
            // Code to update the note
            Bundle bundleObject = resultData.getExtras();
            if (bundleObject != null) {
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
    @Override
    public void OnDeleteClickListener(Participant myNote) {
        //bowlingViewModel.delete(myNote);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Player");
        alert.setMessage("Are you sure you want to delete this player?");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myNote.setDisable_flag(1);
                myNote.setDisabled_at_date( Calendar.getInstance().getTime());
                System.out.println(myNote.getDisable_flag());
                bowlingViewModel.update(myNote);
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}
