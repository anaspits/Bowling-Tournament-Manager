package com.example.bowlingchampionshipmanager;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Teamsvsteams1Activity extends AppCompatActivity  implements RoundListAdapter.OnDeleteClickListener{
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    private List<TeammatesTuple> playersandteams;
    public static ArrayList<ArrayList> vs= new ArrayList<>(); //list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
    private static TextView details,textTitle;
    private static int rounds;
    //public static Team[][] temp2; //dokimh disdiatastatos pinakas anti gia arraylist
    //public static ArrayList<Team> temp3 = new ArrayList<>(); //lista opou exei se seira th mia meta thn allh tis omades pou paizoun antipaloi (mod2), dld h omada sth thesi 0 paizei antipalh me thn omada sth thesh 1, klp
    private BowlingViewModel bowlingViewModel;
    private RoundListAdapter blistAdapter;
    public static String champuuid;
    public String teamuuid;
    public Championship championship;
    public List<Championship_detail> cd;
    public static List<Round> rofTeam; //axristo
    public static List<Round> test;
    private static String firstRounduud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamsvsteams1);
        Bundle bundleObject = this.getIntent().getExtras();

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new RoundListAdapter(this, this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        details=(TextView) findViewById(R.id.textView1);
        textTitle=findViewById(R.id.textTitle);

        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            playersandteams= (List<TeammatesTuple>) bundleObject.getSerializable("teammates");
            champuuid = bundleObject.getString("champuuid");
            championship= (Championship) bundleObject.getSerializable("champ");
            textTitle.append(" No."+championship.getSys_champID());
        }



        OnBackPressedCallback cb =new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed(){
                championship.setStatus("started");
                championship.setStart_date(Calendar.getInstance().getTime());
                bowlingViewModel.update(championship);
                for(int i=0;i<cd.size();i++){
                    bowlingViewModel.update(cd.get(i));
                    System.out.println("cd flag="+cd.get(i).getActive_flag()+" me size "+cd.size());
                }
                openDialog();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this,cb);


        bowlingViewModel.getChampUUID(champuuid).observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship c) {
                System.out.println(" edw to ch einai "+c.getStatus());
                System.out.println(" edw to ch to 8etw ws started");
                championship=c;
                championship.setStatus("started");
                //championship.setStart_date(Calendar.getInstance().getTime());
                //bowlingViewModel.update(c);
                System.out.println(" edw to ch to 8etw egine "+c.getStatus());
            }
        });


 //pairnaw sto flag ka8e omadas tou champ ton arithom twn rounds kai se ka8e round aftos 8a meiwnetai mexris otou na ginei 0
        bowlingViewModel.getChamp_detailofChamp(champuuid).observe(this, new Observer<List<Championship_detail>>() {
            @Override
            public void onChanged(List<Championship_detail> c) {
                cd=c;
                for(int i=0;i<c.size();i++){
                    c.get(i).setActive_flag(rounds);
                }
            }
        });

        rounds=(all_the_teams.size()-1)*2;
        if (all_the_teams.size()!=0) {
            roundRobin(all_the_teams.size(), rounds);

            test();
        }
    }

    void roundRobin(int teams, int round) {
      //temp2=new Team[round][teams];
        if (((teams%2 != 0) && (round != teams - 1))||(teams <= 0))
            throw new IllegalArgumentException(); //todo na dw ti ginetai me to roundrobin monwn omadwn px 3 omades
        int[] cycle = new int[teams];
        int n = teams /2;
        for (int i = 0; i < n; i++) {
            cycle[i] = i + 1;
            cycle[teams - i - 1] = cycle[i] + n;

        }

        //int counter2=0; //gia thn swsth emfanisei twn antipalwn omadwn gia thn temp3
        for(int d = 1; d <= round; d++) {
            details.append(" Round "+ d +": \n");

            //System.out.println(String.format("Round %d", d));
            for (int i = 0; i < n; i++) {
                ArrayList<Team> temp1 = new ArrayList<>(); //voithitiki lista gia thn vs
                //int counter=0; //gia thn 2h omada gia ton temp2
                for (int j=0; j<all_the_teams.size();j++){ //vriskw poia omada exetazoume
                    if (all_the_teams.get(j).getFTeamID()== cycle[i]){
                        Team t1 = all_the_teams.get(j);
//System.out.println("t1 teammates " + t1.getTeammates().size());
                        temp1.add(t1); //kai tin pernaw sthn lista temp1

                        //temp2[i][counter]=t1;
                        //counter++;

                        //temp3.add(t1);
                    }
                }
                for (int j=0; j<all_the_teams.size();j++){ //vriskw tin alli omada poy tha einai antipalos
                    if (all_the_teams.get(j).getFTeamID()== cycle[teams - i - 1]){
                        Team t2 = all_the_teams.get(j);
                        //System.out.println("t2 teammates " + t2.getTeammates().size());
                        temp1.add(t2); //tin pernaw kai afti sti lista gia na exw mia lista apo antipales omades

                        //temp2[i][counter]=t2;
                        //counter++;

                        //temp3.add(t2);
                    }
                }

                vs.add(temp1); //pernaw tin lista twn 2 antipalwn omadwn stin lista vs, opou h thesi tous einai o gyros ston opoio paizoun+1
                details.append(" Team "+temp1.get(0).getFTeamID()+ " vs Team "+temp1.get(1).getFTeamID()+"\n");

                String ruuid= UUID.randomUUID().toString();
                Round r = new Round (ruuid,d,temp1.get(0).getFTeamID(), temp1.get(1).getFTeamID() , champuuid, temp1.get(0).getUuid(),  temp1.get(1).getUuid(),temp1.get(0).getScore(),temp1.get(1).getScore(), "");
                if (d==1){
                   // r.setStatus("current");
                    r.setStatus("next");
                    System.out.println("Round d= "+r.getFroundid()+" t1: "+r.getTeam1ID()+" t2: " + r.getTeam2ID()+" stat "+r.getStatus() +" chid "+ champuuid);
                } else if (d==round){
                    r.setStatus("last");
                    System.out.println("Round d= "+r.getFroundid()+" t1: "+r.getTeam1ID()+" t2: " + r.getTeam2ID()+" stat "+r.getStatus());
                } else {
                    r.setStatus("next");
                    System.out.println("Round d= "+r.getFroundid()+" t1: "+r.getTeam1ID()+" t2: " + r.getTeam2ID()+" stat "+r.getStatus());
                }
                bowlingViewModel.insert(r);
                //gia ka8e paikth ths ka8e omadas vazw to rd
                for(int t=0;t<temp1.size();t++) { //gia ka8e omada autou tou gurou
                    ArrayList<Participant> pa =temp1.get(t).getTeammates(); //pairnw tous paiktes ths omadas auths
                    if(pa!=null) { //an o user ekopse kai sunexise to create
                        for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                            System.out.println("me pa.teamates");
                            Round_detail rd = new Round_detail(ruuid, pa.get(p).getUuid(), 0, 0, 0, pa.get(p).getHdcp(), 0, champuuid, r.getFroundid(), Calendar.getInstance().getTime()); //ftiaxnw to rd
                            bowlingViewModel.insert(rd);
                            System.out.println("Rd round" + r.getFroundid() + " partici " + pa.get(p).getFirstName() + " " + pa.get(p).getUuid());

                        }
                    }else {
                        for (int tm = 0; tm < playersandteams.size();tm++) { //psaxnw na vrw thn omada pou meletame sto playersandteams
                            if (playersandteams.get(tm).getC().getUuid().equals(temp1.get(t).getUuid())) { //gia na parw tous paiktes ths omadas afths
                                List<Participant> pa2 = playersandteams.get(tm).getT();
                                for (int p = 0; p < pa2.size(); p++) {
                                    System.out.println("me pa2 k playersandteams");
                                    Round_detail rd = new Round_detail(ruuid, pa2.get(p).getUuid(), 0, 0, 0, pa2.get(p).getHdcp(), 0, champuuid, r.getFroundid(), Calendar.getInstance().getTime()); //ftiaxnw to rd
                                    //rd.setScore(pa.get(p).getBowlAvg());
                                    bowlingViewModel.insert(rd);
                                    System.out.println("Rd round" + r.getFroundid() + " partici " + pa2.get(p).getFirstName() + " " + pa2.get(p).getUuid());
                                }
                                break;
                            }
                        }
                    }
                }
                //emfanish me temp2
                //details.append(" Team "+temp2[i][0].getFTeamID()+ " vs Team "+temp2[i][1].getFTeamID()+"\n");

                //emfanish me temp3
                //details.append(" Team "+temp3.get(counter2).getFTeamID()+ " vs Team "+temp3.get(counter2+1).getFTeamID()+"\n");
                //counter2=counter2+2;

                //details.append("Prepei Team "+cycle[i]+ " vs Team "+cycle[teams - i - 1]+"\n");
                //System.out.println(String.format("teamid %d - teamid %d",cycle[i],cycle[teams - i - 1]));
            }
            details.append("\n");
            int temp = cycle[1];
            for (int i = 1; i < teams - 1; i++) {
                int pr = cycle[i+1];
                cycle[i+1] = temp;
                temp = pr;
            }
            cycle[1] = temp;
        }
        bowlingViewModel.getAllRound().observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> t) {
System.out.println("r size "+t.size());

            }
        });
    }

    public void openDialog() {
        SaveDialog exampleDialog = new SaveDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

    }


    public static void test2(List<Round> r){
        rofTeam = r;
        System.out.println("rofteam "+rofTeam.size());
    }

    public void test(){
        //test rd
        bowlingViewModel.getAllRound_detail().observe(this, new Observer<List<Round_detail>>() {
            @Override
            public void onChanged(List<Round_detail> t) {
                if(t!=null) {
                    //int a = t.get(0).getSys_teamID();
                    //details.append(" rd size= "+String.valueOf(t.size())+"\n");
                    System.out.println("here rd size ="+t.size());
                    System.out.println(" td 1 " +t.get(0).getParticipant_uuid()+" + "+t.get(0).getRound_uuid());
                } else{
                    details.append("wtf");
                }

            }
        });
        Team t = all_the_teams.get(0);
        bowlingViewModel.getRoundsofTeam(t.getUuid(), champuuid).observe(this, new Observer<List<Round>>() {
            @Override
            public void onChanged(List<Round> r) {
                if(t!=null) {
                    blistAdapter.setRounds(r);
                    blistAdapter.returnRounds(r);
                   // details.append("size of round of team 1: "+String.valueOf(r.size())+"\n");
                    System.out.println("size of round of team 1: "+r.size() );
                    test = r;
                    System.out.println("test1 size of round of team 1: "+test.size() );
                } else{
                    details.append("wtf");
                }

            }
        });

        //test
        if(test!=null) { //edw to test.size=0 omws sto openActivity...
            System.out.println("test2 size of round of team 1: "+test.size() );
        } else{
            System.out.println("test2 wtf");
        } //

       /*axrhsto
        bowlingViewModel.getFirstRoundofTeamofChamp(t.getUuid(), champuuid).observe(this, new Observer<Round>() {
            @Override
            public void onChanged(Round r) {
                details.append("Team 1:"+r.getTeam1ID()+" or "+r.getTeam2ID()+", first round: "+r.getFroundid()+"\n");
                System.out.println("Team 1:"+r.getTeam1ID()+" or "+r.getTeam2ID()+", first round: "+r.getFroundid());

            }
        }); */

    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();


       // championship.setType(1);
        championship.setStart_date(Calendar.getInstance().getTime());
        bowlingViewModel.update(championship);
        System.out.println("ch status ="+championship.getStatus()+" type "+championship.getType()+" startdate "+championship.getStart_date());
        for(int i=0;i<cd.size();i++){
            bowlingViewModel.update(cd.get(i));
            System.out.println("cd flag="+cd.get(i).getActive_flag()+" me size "+cd.size());
        }

        System.out.println("rofteam 2 ="+rofTeam.size()); //rofTeam=3
        if(test!=null) { //edw test.size = 3
            System.out.println("final test size of round of team 1: "+test.size() );
        } else{
            System.out.println("final test wtf");
        } //

        if (button_text.equals("Start Championship"))
        {
            // Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);
            Intent i = new Intent(this, SelectTeamActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putSerializable("all_the_teams",all_the_teams);
            extras.putString("teamid",teamuuid); //?
            extras.putString("champuuid",champuuid);
            extras.putString("flag", "start");
            extras.putSerializable("champ",championship);
            extras.putSerializable("listround", (Serializable) rofTeam); //axristo
            extras.putSerializable("vs",vs);
            i.putExtras(extras);
            startActivity(i);
            finish();

        }

    }

    @Override
    public void OnDeleteClickListener(Round myNote) {
        bowlingViewModel.delete(myNote);
    }
}
