package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//version 2
public class PinsRoundEditActivity extends AppCompatActivity {

    private static TextView textTitle;
    private static TextView sumHDCP,sum1st,sum2nd,sum3rd,txtscore1, totalSumtxt,  pointstxt,team1txt ;
    public static Team team1;
    public static Round r;
    public static String tuuid1;
    private int score1;
    private BowlingViewModel bowlingViewModel;
    private RoundScoreListAdapter2 blistAdapter;
    public String champuuid;
    public Championship championship;
    static List<Pins_points> pp;
    public int calc_pressed=0;
    private Championship_detail cd1;
    int first_sum1=0;
    int second_sum1=0;
    int third_sum1=0;
    int sum_hdcp1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins_round_edit);

        textTitle= findViewById(R.id.textTitle);
        team1txt=(TextView) findViewById(R.id.team1);
        sumHDCP =findViewById(R.id.sumHDCP);
        sum1st=findViewById(R.id.sum1st);
        sum2nd=findViewById(R.id.sum2nd);
        sum3rd=findViewById(R.id.sum3rd);
        totalSumtxt =findViewById(R.id.txvTotalSum);
        txtscore1=findViewById(R.id.score1);
        pointstxt =  findViewById(R.id.points);
        calc_pressed=0;

        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){

            team1 = (Team) bundleObject.getSerializable("selTeam");
            tuuid1= team1.getUuid();

            championship = (Championship) bundleObject.getSerializable("champ");
            System.out.println("Champ in round = "+championship.getFchampID()+" "+ championship.getUuid());
            champuuid = championship.getUuid();
            r = (Round) bundleObject.getSerializable("round");
            if (r!= null) {
                System.out.println("got round from sel " + r.getFroundid() + " uuid "+ r.getRounduuid()+ " stat " + r.getStatus() +  " with t1: " + r.getTeam1ID() + " and t2: " + r.getTeam2ID() + " and sysID: " + r.getRoundid());
            } else {
                System.out.println("got round from sel ERROR");
            }

        }
        System.out.println("Team selected: "+team1.getFTeamID()+" sys "+team1.getSys_teamID()+" uuid "+team1.getUuid());
        team1txt.setText("Team "+team1.getTeamName() );
        textTitle.setText("Round "+r.getFroundid());
        textTitle.append("\nLanes:"+r.getLanes());
        System.out.println("Round " + r.getFroundid()+" uuid "+r.getRounduuid() +" team: "+r.getTeam1ID()+" vs team: "+ r.getTeam2ID()+"  lanes "+r.getLanes());


        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new RoundScoreListAdapter2(this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getAllPlayersofTeam3(tuuid1, champuuid).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> part) {
                blistAdapter.setBowls(part);
                blistAdapter.setRound(r);
                blistAdapter.setChamp(championship);
                ArrayList<Round_detail> rd = new ArrayList<>(); //fixme na ta pairnw live apo to viewmodel
                for (int i = 0; i < part.size(); i++) {
                    Round_detail round_detail= new Round_detail(r.getRounduuid(), part.get(i).getUuid(), 0, 0, 0,part.get(i).getHdcp(), 0, champuuid,r.getFroundid(), Calendar.getInstance().getTime() );
                    System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ part.get(i).getFirstName());
                    rd.add(round_detail);
                }
                blistAdapter.setRound_detail(rd);

            }
        });

        bowlingViewModel.getPins_pointsofChamp(champuuid).observe(this, new Observer<List<Pins_points>>() {
            @Override
            public void onChanged(List<Pins_points> rds) {
                System.out.println("pp size " + rds.size());
                pp=rds;
                for (int i = 0; i < rds.size(); i++) {
                    System.out.println("pp " + i + " pins " + pp.get(i).getPins() + " pointstxt " + pp.get(i).getPoints() + " uuid " + pp.get(i).getPins_uuid() + " champ " + pp.get(i).getChamp_uuid() );
                    System.out.println("pp " + i + " pins " + rds.get(i).getPins() + " pointstxt " + rds.get(i).getPoints() + " uuid " + rds.get(i).getPins_uuid() + " champ " + rds.get(i).getChamp_uuid() );
                }
            }
        });

        //pairnw to champ_detail ths omadas gia na dw to score ths
        bowlingViewModel.getChamp_detailofTeamandChamp(tuuid1, champuuid).observe(this, new Observer<Championship_detail>() {
            @Override
            public void onChanged(Championship_detail c1) {
                cd1 = c1;
                System.out.println("1 exei score=" + c1.getScore());
            }
        });
    }

    public void calculateScore(View View) {
        //score1=team1.getScore(); prin
        score1=cd1.getScore(); //meta
         first_sum1=0;
         second_sum1=0;
         third_sum1=0;
         sum_hdcp1=0;
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++){
            // team1.setText(team1.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() +System.getProperty("line.separator"));
            System.out.println(" emda player "  +" id "+RoundScoreListAdapter2.editModelArrayList.get(i).getUuid()+" hdcp "+RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());
            System.out.println(" rd size " +RoundScoreListAdapter2.rd.size()+" rid "+RoundScoreListAdapter2.rd.get(i).getRound_uuid()+" pid "+RoundScoreListAdapter2.rd.get(i).getParticipant_uuid()+" h "+ RoundScoreListAdapter2.rd.get(i).getHdcp() +" 1st "+ RoundScoreListAdapter2.rd.get(i).getFirst()+" 2nd "+ RoundScoreListAdapter2.rd.get(i).getSecond()+" 3rd "+ RoundScoreListAdapter2.rd.get(i).getThird());

            first_sum1 += RoundScoreListAdapter2.rd.get(i).getFirst();
            second_sum1 +=  RoundScoreListAdapter2.rd.get(i).getSecond();
            third_sum1 +=  RoundScoreListAdapter2.rd.get(i).getThird();

            //todo: na emfanizetai to neo hdcp sto activity
            if(r.getFroundid()==1 && RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp()==0){ //an einai o prwtos gyros kai to HDCP tou paikth einai 0 tote upologizw to hdcp apo tis korines pou ekane twra se afton ton gyro
                float first_avg = (RoundScoreListAdapter2.rd.get(i).getFirst() + RoundScoreListAdapter2.rd.get(i).getSecond() + RoundScoreListAdapter2.rd.get(i).getThird());
                int first_hdcp=RoundScoreListAdapter2.editModelArrayList.get(i).calculateHDCPofPlayer(RoundScoreListAdapter2.editModelArrayList.get(i), first_avg, championship, bowlingViewModel);
                RoundScoreListAdapter2.editModelArrayList.get(i).setHdcp(first_hdcp);
                RoundScoreListAdapter2.rd.get(i).setHdcp(first_hdcp);
                //bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i)); //fixme: kakws to vazw edw giati meta na pathsei cancel 8a meinei sth vash afto to hdcp, episis otan pataei calculate xanontai ta rd pou evale prin o paikths
                System.out.println("First hdcp ="+first_hdcp);
            }
            sum_hdcp1 +=RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp();

            //upologizw to score tou paikth gia ola ta rounds mexri twra autou tou champ (pou den htan blind)
            if(RoundScoreListAdapter2.rd.get(i).getBlind()==0 && !RoundScoreListAdapter2.editModelArrayList.get(i).getUuid().equals("blind")) {
                int i2 = i;
                bowlingViewModel.getAllDoneRound_detailofplayerofChamp(RoundScoreListAdapter2.editModelArrayList.get(i).getUuid(), champuuid).observe(this, new Observer<List<Round_detail>>() {
                    @Override
                    public void onChanged(List<Round_detail> allrdchamp) {
                        float avg = 0;
                        //int games = (3 * allrdchamp.size()) + 3;//+3 gia ta paixnidia aftou tou round, afou den epistrefetai apo to query giati den exei ginei upadate(roud), ginetai sto roundActivity
                        int games =3; //aftou tou round
                        System.out.println("1games a " + games + " pl " + RoundScoreListAdapter2.editModelArrayList.get(i2).getUuid());
                        for (int j = 0; j < allrdchamp.size(); j++) {
                            if (allrdchamp.get(j).getBlind() == 0) {
                                avg += allrdchamp.get(j).getScore(); //ta score twn prohgoumenwn rounds
                                games+=3; //ka8e round pou epai3e (dld den htan blind)
                            }
                        }
                        System.out.println("1 prohgoumena score " + avg);
                        avg += (RoundScoreListAdapter2.rd.get(i2).getFirst() + RoundScoreListAdapter2.rd.get(i2).getSecond() + RoundScoreListAdapter2.rd.get(i2).getThird()); //ta score aftou tou round
                        System.out.println("1 prohgoume+afto to score " + avg);
                        if (games != 0) {
                            avg = avg / games;
                        }

//ypologismos hdcp
                        System.out.println("1 hdcp champ.tav " + championship.getHdcp_tav());
                        if(RoundScoreListAdapter2.rd.get(i2).getChecked_auto_calc_hdcp()!=null && RoundScoreListAdapter2.rd.get(i2).getChecked_auto_calc_hdcp().equals("yes") ) {
                            int hdcp = RoundScoreListAdapter2.editModelArrayList.get(i2).calculateHDCPofPlayer(RoundScoreListAdapter2.editModelArrayList.get(i2), avg, championship, bowlingViewModel);
                            //RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                            //RoundScoreListAdapter2.editModelArrayList.get(i2).setHdcp(hdcp);
                            RoundScoreListAdapter2.rd.get(i2).setAuto_calc_newHdcp(hdcp);
                       /* if (RoundScoreListAdapter2.editModelArrayList.get(i2).getUuid().equals("blind") && championship.getHdcp_less()!=0) { //an einai omada me ligoterous paiktes //todo na rwthsw
                            int hdcp = (int) ((championship.getHdcp_less() - avg) * championship.getHdcp_factor());
                            RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                            System.out.println("1 hdcp less" + hdcp);
                        } else if (championship.getHdcp_tav() != 0) {
                            int hdcp = (int) ((championship.getHdcp_tav() - avg) * championship.getHdcp_factor());
                            RoundScoreListAdapter2.rd.get(i2).setHdcp(hdcp);
                            System.out.println("1 hdcp tav" + hdcp);
                        } */
                        }
                        System.out.println("1games b " + games + " avg " + avg);
                        RoundScoreListAdapter2.rd.get(i2).setAvg(avg);
                        RoundScoreListAdapter2.rd.get(i2).setGames(games);
                        RoundScoreListAdapter2.editModelArrayList.get(i2).setTotal_games(3+RoundScoreListAdapter2.editModelArrayList.get(i2).getTotal_games()); //ola ta prohgoumena games ever pou eixe o paikths + ta 3 aftou tou gyrou
                    }
                });
            }
        }
        first_sum1 += sum_hdcp1;
        second_sum1 += sum_hdcp1;
        third_sum1 += sum_hdcp1;
        sumHDCP.setText(String.valueOf(sum_hdcp1));
        sum1st.setText(String.valueOf(first_sum1));
        sum2nd.setText(String.valueOf(second_sum1));
        sum3rd.setText(String.valueOf(third_sum1 ));

        int totalsum =  first_sum1+second_sum1+third_sum1;
        totalSumtxt.setText("Total sum: "+totalsum);

//calculate pins score from points
            int point=0;
            System.out.println("2 pins " + pp.get(0).getPins() + " pointstxt " + pp.get(0).getPoints() + " uuid " + pp.get(0).getPins_uuid() + " champ " + pp.get(0).getChamp_uuid() );
//ta points gia to first_sum1
            for (int i = 0; i < pp.size(); i++) {
                if (i==0 && first_sum1 <= pp.get(i).getPins()){
                    point+=pp.get(i).getPoints();
                } else if (i==(pp.size()-1) && first_sum1 >= pp.get(i).getPins()){
                    point+=pp.get(i).getPoints();
                } else if (first_sum1 > pp.get(i).getPins() && first_sum1 < pp.get(i+1).getPins()){ //mexri kai ta x pins = n pointstxt
                    System.out.println(first_sum1+">" + pp.get(i).getPins() + " && " +first_sum1+"<"+ pp.get(i+1).getPins() +" pointstxt "+pp.get(i+1).getPoints());
                    point+=pp.get(i+1).getPoints();
                }
            }
            System.out.println("points1 "+point);
        //ta points gia to second_sum1
        for (int i = 0; i < pp.size(); i++) {
            if (i==0 && second_sum1 <= pp.get(i).getPins()){
                point+=pp.get(i).getPoints();
            } else if (i==(pp.size()-1) && second_sum1 >= pp.get(i).getPins()){
                point+=pp.get(i).getPoints();
            } else if (second_sum1 > pp.get(i).getPins() && second_sum1 < pp.get(i+1).getPins()){ //mexri kai ta x pins = n pointstxt
                System.out.println(second_sum1+"> " + pp.get(i).getPins() + " && " +second_sum1+"<"+ pp.get(i+1).getPins() +" pointstxt "+pp.get(i+1).getPoints());
                point+=pp.get(i+1).getPoints();
            }
        }
        System.out.println("points2 "+point);
        //ta points gia to third_sum1
        for (int i = 0; i < pp.size(); i++) {
            if (i==0 && third_sum1 <= pp.get(i).getPins()){
                point+=pp.get(i).getPoints();
            } else if (i==(pp.size()-1) && third_sum1 >= pp.get(i).getPins()){
                point+=pp.get(i).getPoints();
            } else if (third_sum1 > pp.get(i).getPins() && third_sum1 < pp.get(i+1).getPins()){ //mexri kai ta x pins = n pointstxt
                System.out.println(third_sum1+"> " + pp.get(i).getPins() + " && " +third_sum1+"<"+ pp.get(i+1).getPins() +" pointstxt "+pp.get(i+1).getPoints());
                point+=pp.get(i+1).getPoints();
            }
        }
        System.out.println("points3 "+point);
            score1+=point;
            //t.setScore(score1);
            r.setPoints1(point);
            txtscore1.setText("Score: "+score1);
            pointstxt.setText("Points: "+point);
            calc_pressed = 1;

        if(r.getFroundid()==1) {//gia na emfanisei to neo prwto HDCP
            blistAdapter.setBowls(RoundScoreListAdapter2.editModelArrayList);
        }
        }
    public void openNewActivity(View View) {
        //String button_text;
        // button_text =((Button)View).getText().toString();
        System.out.println("calc_pressed=" + calc_pressed);
        if (calc_pressed == 1) {
            team1.setScore(score1);
            r.setScore1(score1);
            System.out.println("team1 score " + team1.getScore() + " sid " + team1.getSys_teamID());

            for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                if(!RoundScoreListAdapter2.editModelArrayList.get(i).getUuid().equals("blind")) {
                    System.out.println(team1txt.getText() + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp() + System.getProperty("line.separator"));
                    System.out.println(" paikths " + i + " " + RoundScoreListAdapter2.editModelArrayList.get(i).getFullName() + " id " + RoundScoreListAdapter2.editModelArrayList.get(i).getUuid() + " hdcp " + RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp());

                    if(RoundScoreListAdapter2.rd.get(i).getBlind()==1){ //an htan blind exei score 0
                        RoundScoreListAdapter2.rd.get(i).setScore(0);
                        RoundScoreListAdapter2.rd.get(i).setFirst(0);
                        RoundScoreListAdapter2.rd.get(i).setSecond(0);
                        RoundScoreListAdapter2.rd.get(i).setThird(0);
                    }else {
                        RoundScoreListAdapter2.rd.get(i).setScore((RoundScoreListAdapter2.rd.get(i).getFirst() + RoundScoreListAdapter2.rd.get(i).getSecond() + RoundScoreListAdapter2.rd.get(i).getThird()));
                        if(RoundScoreListAdapter2.rd.get(i).getChecked_auto_calc_hdcp()!=null && RoundScoreListAdapter2.rd.get(i).getChecked_auto_calc_hdcp().equals("yes") ) { //an to hdcp htan auto-calculated tote
                            RoundScoreListAdapter2.rd.get(i).setHdcp(RoundScoreListAdapter2.rd.get(i).getAuto_calc_newHdcp()); //8etw ws hdcp tou rd to neo hdcp
                            RoundScoreListAdapter2.editModelArrayList.get(i).setHdcp(RoundScoreListAdapter2.rd.get(i).getAuto_calc_newHdcp());
                        }
                    }

                    bowlingViewModel.update(RoundScoreListAdapter2.editModelArrayList.get(i));

                    //RoundScoreListAdapter2.rd.get(i).setScore( (RoundScoreListAdapter2.rd.get(i).getFirst()+ RoundScoreListAdapter2.rd.get(i).getSecond()+ RoundScoreListAdapter2.rd.get(i).getThird())/3);
                    RoundScoreListAdapter2.rd.get(i).setUpdated_at(Calendar.getInstance().getTime());
                    bowlingViewModel.update(RoundScoreListAdapter2.rd.get(i));
                    System.out.println(" rd: " + i + " rid " + RoundScoreListAdapter2.rd.get(i).getRound_uuid() + " pid " + RoundScoreListAdapter2.rd.get(i).getParticipant_uuid() + " h " + RoundScoreListAdapter2.rd.get(i).getHdcp() + " 1st " + RoundScoreListAdapter2.rd.get(i).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(i).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(i).getThird() + RoundScoreListAdapter2.rd.get(i).getScore());
                }
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("selTeam", (Serializable) team1);
            resultIntent.putExtra("round2", (Serializable) r);
            resultIntent.putExtra("score1", score1);
            setResult(RESULT_OK, resultIntent);
            finish();

        }else{
            Toast.makeText(
                    getApplicationContext(),
                    "You have to calculate the score first",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void exitActivity(View View) {
        finish();
    }

    public void export(View view) {
        if(calc_pressed==1){
            ExportCSV exp= new ExportCSV();
           /* if(checkboxhdcp.isChecked()){
                sum_hdcp1=0;
                for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) {
                    sum_hdcp1+=RoundScoreListAdapter2.editModelArrayList.get(i).getHdcp();
                }
            } */
            StringBuilder data=  exp.exportRoundEditScore(championship,r,team1,null,RoundScoreListAdapter2.editModelArrayList,RoundScoreListAdapterTeam2.editModelArrayList,RoundScoreListAdapter2.rd,RoundScoreListAdapterTeam2.rd,first_sum1,second_sum1,third_sum1,sum_hdcp1,0,0,0,0);
            try {
                //saving the file into device
                FileOutputStream out = openFileOutput("bowling_championship_finishedChamp_stat.csv", Context.MODE_PRIVATE);
                out.write((data.toString()).getBytes());
                out.close();

                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "bowling_championship_finishedChamp_stat.csv");
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
        }else{
            Toast.makeText(
                    getApplicationContext(),
                    "You have to calculate the score first",
                    Toast.LENGTH_LONG).show();
        }
    }
}
