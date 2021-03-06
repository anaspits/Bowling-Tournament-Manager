package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SelectStatforRoundActivity extends AppCompatActivity {

    public String champuuid, flag;
    public Championship championship;
    private List<PlayerandGames> rd;
    private List<Round> rounds;
    private List<TeamandRoundScore> teams;
    private BowlingViewModel bowlingViewModel;
    private List<TeammatesTuple> playersandteams;
    private ExportCSV ex= new ExportCSV();
    private List<Championship_detail> cds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_statfor_round);

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            championship= (Championship) bundleObject.getSerializable("champ");
            champuuid = championship.getUuid();
            flag = bundleObject.getString("flag");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs

        //pairnw ola ta rd
        bowlingViewModel.getAllPlayerScoreGamesofChamp(champuuid).observe(this, new Observer<List<PlayerandGames>>() {
            @Override
            public void onChanged(List<PlayerandGames> rds) {
            rd=rds;
            for (int i=0;i<rds.size();i++){
                //System.out.println("playerandgames rds updated_at " +rds.get(i).getRound_updated_date()+" r "+rds.get(i).getRound_uuid());
                    System.out.println( "roundid "+rd.get(i).getFroundid()+" rounduuid "+rd.get(i).getRound_uuid()+" r.score "+rd.get(i).getScore()+" avg "+rd.get(i).getBowlAvg()+" update "+rd.get(i).getRound_updated_date());
            }
            }
        });

        //pairnw ta rounds
        bowlingViewModel.getAllRoundofChamp(champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> rs) {
                rounds=rs;
            }
        });


        //pairnw oles tis omades me ta score tou ka8e gyrou tous
        //bowlingViewModel.geAllTeamsofChamp( champuuid ).observe(this, new Observer<List<TeamandRoundScore>>() {
        bowlingViewModel.geAllActiveTeamsofChamp( champuuid ).observe(this, new Observer<List<TeamandRoundScore>>() {
        @Override
            public void onChanged(List<TeamandRoundScore> t) {
        teams=t;
            }
        });

        //pairnw ta cd.score ths ka8e omadas
        bowlingViewModel.getChamp_detailofChamp(champuuid).observe(this, new Observer<List<Championship_detail>>() { //axristo-test
            @Override
            public void onChanged(List<Championship_detail> cs) {
                cds= cs;
            }
        });

        //pairnw tous paiktes ka8e omadas tou champ aftou
        bowlingViewModel.getAllTeamatesofAllTeamsofChamp(champuuid).observe(this, new Observer<List<TeammatesTuple>>() {
            @Override
            public void onChanged(List<TeammatesTuple> p1) {
                playersandteams = p1;
                for (int i = 0; i < p1.size(); i++) {
                    System.out.println("TEST1 team " + p1.get(i).getC().getFTeamID());
                    for (int j = 0; j < p1.get(i).getT().size(); j++) {
                        System.out.println("TEST1 team " + p1.get(i).getC().getFTeamID() + " pl "+p1.get(i).getT().get(j).getFullName());
                    }
                }
            }
        });


        //pairnw tous paiktes ka8e omadas
       /*svisto bowlingViewModel.getAllPlayersofTeam3(teams.get(0).getTeam_uuid() ).observe(this, new Observer<List<TeammatesTuple>>() {
            @Override
            public void onChanged(List<TeammatesTuple> t) { //leitourgei alla prpei na to kanw gia sugkekrimeno champ
                if(t.size()!=0) {
                    List<Participant> a = t.get(0).getT();
                }
                System.out.println("list obejct size "+t.size()+" list team size "+t.get(0).getT().size());
            }
        }); */
    }

    public void exportPlayerscsv(View view) {
        //generate data
        StringBuilder data = new StringBuilder();
        data= ex.exportRoundPlayersStat(championship,rounds,rd);

       /* data.append(" ,Name,"); //todo hdcp

            for (int i = 0; i < rounds.size(); i++) {
                if(i==0) {
                    data.append(rounds.get(i).getFroundid()+"H,");
                    data.append(rounds.get(i).getFroundid()+"H,");
                    data.append(rounds.get(i).getFroundid()+"H,");
                } else if (rounds.get(i).getFroundid()!=rounds.get(i-1).getFroundid()){
                    data.append(rounds.get(i).getFroundid()+"H,");
                    data.append(rounds.get(i).getFroundid()+"H,");
                    data.append(rounds.get(i).getFroundid()+"H,");
                } else if (rounds.get(i).getFroundid()==rounds.get(i-1).getFroundid()){
                    System.out.println("i "+i+" fr "+rounds.get(i).getFroundid());
                    //break() H continue();
                }
            }
        data.append("Avg,Games,Score");

        int counter=1;
        for (int i = 0; i < rd.size(); i++) {
            PlayerandGames p = rd.get(i);
            if(i==0) { //an einai to prwto, einai o prwtos paikths
                data.append("\n" + counter+"," + String.valueOf(p.getFirstName())+" "+p.getLastName());
                counter++;
                data.append(","+String.valueOf(p.getFirst()) + "," + String.valueOf(p.getSecond()) + "," + String.valueOf(p.getThird()));
            }else if (p.getParticipant_uuid().equals(rd.get(i-1).getParticipant_uuid())){ //an to uuid einai idio me to prohgoumeno ara einai o idios aikths se allo gyro
                data.append(","+String.valueOf(p.getFirst()) + "," + String.valueOf(p.getSecond()) + "," + String.valueOf(p.getThird()));
                if (i==(rd.size()-1)){
                    data.append(","+rd.get(i).getBowlAvg()+","+rd.get(i).getGames()+","+(rd.get(i).getBowlAvg()*rd.get(i).getGames()));
                }
            }else{
                data.append(","+rd.get(i-1).getBowlAvg()+","+rd.get(i-1).getGames()+","+(rd.get(i-1).getBowlAvg()*rd.get(i-1).getGames()));
                data.append("\n" + counter+"," + String.valueOf(p.getFirstName())+" "+p.getLastName()+ ",");
                counter++;
                data.append(String.valueOf(p.getFirst()) + "," + String.valueOf(p.getSecond()) + "," + String.valueOf(p.getThird()));
            }
        }*/

        try {
            //saving the file into device
            FileOutputStream out = openFileOutput("bowling_championship_playerRound_stat.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "bowling_championship_playerRound_stat.csv");
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

    public void exportTeamscsv(View view) {
        StringBuilder data = new StringBuilder();
        System.out.println("edw to cd size "+cds.size());
        data=ex.exportRoundTeamStat(championship,rounds,teams, playersandteams,cds);

        try {
            //saving the file into device
            FileOutputStream out = openFileOutput("bowling_championship_teamsRound_stat.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "bowling_championship_teamsRound_stat.csv");
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

        public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
       /* if(button_text.equals("Players")) {
        }*/
            Intent i = new Intent(this, SelectRoundActivity.class);
            Bundle extras = new Bundle();
            extras.putString("flag", "stat");
            extras.putSerializable("champ", championship);
            i.putExtras(extras);
            startActivity(i);


    }
}
