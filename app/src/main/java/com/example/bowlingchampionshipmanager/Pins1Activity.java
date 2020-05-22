package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Pins1Activity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener{

    static ArrayList<Participant> bowlers;
    static ArrayList<Pins_points> pins_points= new ArrayList<>();
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE=41;
    public static ArrayList<Team> all_the_teams; //xrhsimo
    private List<Team> all_the_teams_live;
    static ArrayList<String> hdcp_parameters;
    public String teamuuid; //axristo
    private BowlingViewModel bowlingViewModel;
    private BowlingListAdapter blistAdapter;
    public String champuuid;
    private Championship ch;
    public List<Championship_detail> cd;
    private static Pins_points pp = new Pins_points("","",0,0);
    private int imp_pressed=0;
    private int round; //todo na rwthsw
private EditText editNorounds;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins1);

        imp_pressed=0;

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            //t.setText(hdcp_parameters.get(0));
            teamuuid=bundleObject.getString("teamid"); //axristo
            champuuid = bundleObject.getString("champuuid");
            ch= (Championship) bundleObject.getSerializable("champ");

        }


        Button button_imp  = (Button) findViewById(R.id.button_import);
        editNorounds=findViewById(R.id.editNorounds);
        textView=findViewById(R.id.textView);

        //na svisw
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        /*blistAdapter = new BowlingListAdapter(this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); */

        //todo na kanw insert ta round k round_detail kai na rwthsw posa rounds ginontai
      /*  for(int d = 1; d <= round; d++) {
            System.out.println(String.format("Round %d", d));
            for (int i = 0; i < all_the_teams.size(); i++) {
                System.out.println(" Team " + all_the_teams.get(i).getFTeamID());
                String ruuid = UUID.randomUUID().toString();
                Round r = new Round(ruuid, d, all_the_teams.get(i).getFTeamID(), 0, champuuid, all_the_teams.get(i).getUuid(), null, all_the_teams.get(i).getScore(), 0, "");
                if (d == round) {
                    r.setStatus("last");
                    System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus());
                } else {
                    r.setStatus("next");
                    System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus() + " chid " + champuuid);
                }

                bowlingViewModel.insert(r);
                //gia ka8e paikth ths ka8e omadas vazw to rd
                ArrayList<Participant> pa = all_the_teams.get(i).getTeammates(); //pairnw tous paiktes ths omadas auths
                for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                    Round_detail rd = new Round_detail(ruuid, pa.get(p).getUuid(), 0, 0, 0, pa.get(p).getHdcp()); //ftiaxnw to rd
                    rd.setScore(pa.get(p).getBowlAvg());
                    bowlingViewModel.insert(rd);
                    System.out.println("Rd round"+r.getFroundid()+" partici "+pa.get(p).getFirstName()+" "+pa.get(p).getUuid());
                }
            }
        } */

      //allazw to status tou champ
        bowlingViewModel.getChampUUID(champuuid).observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship c) {
                System.out.println(" edw to ch einai "+c.getStatus());
                System.out.println(" edw to ch to 8etw ws started");
                ch=c;
                ch.setStatus("started");
                ch.setStart_date(Calendar.getInstance().getTime());
                //bowlingViewModel.update(c);
                System.out.println(" edw to ch to 8etw egine "+c.getStatus());
            }
        });

  /*      //pairnaw sto flag ka8e omadas tou champ ton arithom twn rounds kai se ka8e round aftos 8a meiwnetai mexris otou na ginei 0
        bowlingViewModel.getChamp_detailofChamp(champuuid).observe(this, new Observer<List<Championship_detail>>() {
            @Override
            public void onChanged(List<Championship_detail> c) {
                cd=c;
                for(int i=0;i<c.size();i++){
                    c.get(i).setActive_flag(round);
                    System.out.println("cd of champ "+ch.getFchampID()+" team "+cd.get(i).getSys_teamID());
                }
            }
        });*/

        System.out.println("all size "+all_the_teams.size());
        //pairnw to sunolo twn omadwn pou 8a xreiatsw parakatw
        bowlingViewModel.getAllTeamsofChamp3(champuuid).observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> t) {
                all_the_teams_live=t;
            }
        });

        button_imp.setOnClickListener(new View.OnClickListener() { //TODO: na kanw na mhn 3anapatietai kai na emfanizei ton titlo tou arxeiou
            @Override
            public void onClick(View v) {
                openFile();
            }
        });
    }

    public void openFile(){

        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        //intent.setType("gagt/sdf");
        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;
        //Cursor returnCursor = getContentResolver().query(currentUri,null,null,null,null);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == CREATE_REQUEST_CODE) {
                if (resultData != null) {

                }
            }  else if (requestCode == OPEN_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();

                    // File file = new File (currentUri.getPath());
                    //String a = file.getName();

                    Cursor returnCursor = getContentResolver().query(currentUri,null,null,null,null);
                    assert returnCursor != null;
                    int fileTitle= returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    String ft = returnCursor.getString(fileTitle);
                    //String filepath= resultData.getData().getPath();


                    try {
                        //String content =readFileContent(currentUri);
                        readFileContent(currentUri);
                        textView.setText("Imported file: "+ft);
                        //textView.setText("here");
                        //textView.setText(filepath);

                        returnCursor.close();
                        // participant(ft);
                        // textView.setText(returnCursor.getString(fileTitle));

                    } catch (IOException e) {
                        // Handle error here
                        e.getCause();
                    }


                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void readFileContent(Uri uri) throws IOException {

        InputStream inputStream =
                getContentResolver().openInputStream(uri);
       /* BufferedReader reader =
                new BufferedReader(new InputStreamReader(
                        inputStream));
       /* StringBuilder stringBuilder = new StringBuilder();
        String currentline;
        while ((currentline = reader.readLine()) != null) {
           stringBuilder.append(currentline + "\n");
        } */

        ////
        //String csvFile="bowlers-list.csv";
        //String csvFile=ft;

        String line = "";
        String cvsSplitBy = ",";
        pp.createPins_pointsList(bowlingViewModel, inputStream, line, cvsSplitBy,ch.getUuid(),pins_points);
        inputStream.close();
        //return stringBuilder.toString();
        textView.append("\n");
        textView.append("Pins - Points\n");
        for (int i=0;i<pins_points.size();i++) {
            textView.append(pins_points.get(i).getPins()+" "+pins_points.get(i).getPoints()); //fixme den emfanizetai kai na ginei scrollable
            System.out.println("pins "+pins_points.get(i).getPins()+" points "+pins_points.get(i).getPoints());
        }
        imp_pressed=1;
    }


        public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();

            System.out.println(" all_the_teams_live size "+all_the_teams_live.size()); //todo
            System.out.println(" all_the_teams_live  "+all_the_teams_live.get(0).getTeamName()+ " score "+all_the_teams_live.get(0).getScore() );
        System.out.println(" rounds edit "+editNorounds.getText().toString());


      if(editNorounds.getText().toString().equals("0")){
          Toast.makeText(
                  getApplicationContext(),
                  "You can't have 0 rounds",
                  Toast.LENGTH_LONG).show();
      } else if (editNorounds.getText().toString().equals("")  || editNorounds.getText().toString().equals("e.g. 4")){
          Toast.makeText(
                  getApplicationContext(),
                  "Please insert the number of rounds",
                  Toast.LENGTH_LONG).show();
        } else {
          if (imp_pressed == 1){
              round = Integer.parseInt(editNorounds.getText().toString());
          System.out.println("rounds " + round);
          //kanw ta rounds kai ta round-detail
          for (int d = 1; d <= round; d++) {
              System.out.println(String.format("Round %d", d));
              for (int i = 0; i < all_the_teams_live.size(); i++) {
                  System.out.println(" Team " + all_the_teams_live.get(i).getFTeamID());
                  String ruuid = UUID.randomUUID().toString();
                  Round r = new Round(ruuid, d, all_the_teams_live.get(i).getFTeamID(), 0, champuuid, all_the_teams_live.get(i).getUuid(), null, all_the_teams_live.get(i).getScore(), 0, "");
                  if (d == round) {
                      r.setStatus("last");
                      System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus());
                  } else {
                      r.setStatus("next");
                      System.out.println("Round d= " + r.getFroundid() + " t1: " + r.getTeam1ID() + " t2: " + r.getTeam2ID() + " stat " + r.getStatus() + " chid " + champuuid);
                  }

                  bowlingViewModel.insert(r);

                  //gia ka8e paikth ths ka8e omadas vazw to rd
                  /*/meta fixme
                  bowlingViewModel.getAllPlayersofTeam3(all_the_teams_live.get(i).getUuid(), champuuid).observe(this, new Observer<List<Participant>>() {
                      @Override
                      public void onChanged(List<Participant> pa) {
                          for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                              Round_detail rd = new Round_detail(ruuid, pa.get(p).getUuid(), 0, 0, 0, pa.get(p).getHdcp(), 0,champuuid ); //ftiaxnw to rd
                             // rd.setScore(pa.get(p).getBowlAvg());
                              bowlingViewModel.insert(rd); //den mporw na kanw insert edw!!
                              System.out.println("Rd round" + r.getFroundid() + " partici " + pa.get(p).getFirstName() + " " + pa.get(p).getUuid());
                          }

                      }
                  }); */
                  // Prin
                  ArrayList<Participant> pa = all_the_teams.get(i).getTeammates(); //pairnw tous paiktes ths omadas auths //todo na to kanw me livedata apo to view model
                  for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                      Round_detail rd = new Round_detail(ruuid, pa.get(p).getUuid(), 0, 0, 0, pa.get(p).getHdcp(), 0,champuuid,r.getFroundid(), Calendar.getInstance().getTime() ); //ftiaxnw to rd
                      //rd.setScore(pa.get(p).getBowlAvg());
                      bowlingViewModel.insert(rd);
                      System.out.println("Rd round" + r.getFroundid() + " partici " + pa.get(p).getFirstName() + " " + pa.get(p).getUuid());
                  } //
              }
          }

          bowlingViewModel.update(ch);
          System.out.println("ch status =" + ch.getStatus() + " type " + ch.getType()+" start_date "+ch.getStart_date()+" cr date"+ch.getCreated_at());

          //pairnaw sto flag ka8e omadas tou champ ton aritho twn rounds kai se ka8e round aftos 8a meiwnetai mexris otou na ginei 0
          bowlingViewModel.getChamp_detailofChamp(champuuid).observe(this, new Observer<List<Championship_detail>>() {
              @Override
              public void onChanged(List<Championship_detail> c) {
                  cd = c;
                  for (int i = 0; i < c.size(); i++) {
                      c.get(i).setActive_flag(round);
                      System.out.println("cd of champ " + ch.getFchampID() + " team " + cd.get(i).getSys_teamID());
                      bowlingViewModel.update(cd.get(i));
                  }
              }
          });

          if (button_text.equals("Back")) {
              // Intent goback = new Intent(this,Create2Activity.class);
              // startActivity(goback);
          } else if (button_text.equals("Start Championship")) {
              //Intent gonext = new Intent(this,Create3Activity.class);
              //startActivity(gonext);

              Intent i = new Intent(this, SelectTeamActivity.class);
              Bundle extras = new Bundle();
              extras.putSerializable("bowlers", bowlers);
              extras.putStringArrayList("hdcp_parameters", hdcp_parameters);
              extras.putString("teamid", teamuuid); //axristo
              extras.putString("champuuid", champuuid);
              extras.putString("flag", "start");
              extras.putSerializable("champ", ch);
              i.putExtras(extras);
              startActivity(i);
          }
      } else {
              Toast.makeText(
                      getApplicationContext(),
                      "Please import a file",
                      Toast.LENGTH_LONG).show();
          }
        }
    }

    @Override
    public void OnDeleteClickListener(Participant myNote) {
        bowlingViewModel.delete(myNote);
    }

}
