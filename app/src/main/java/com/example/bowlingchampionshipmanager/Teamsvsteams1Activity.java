package com.example.bowlingchampionshipmanager;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Teamsvsteams1Activity extends AppCompatActivity  implements RoundListAdapter.OnDeleteClickListener{
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs= new ArrayList<>(); //list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
    private static TextView details;
    private static int rounds=3; //todo : na to vazei o user? all_the_teams.size*2
    //public static Team[][] temp2; //dokimh disdiatastatos pinakas anti gia arraylist
    //public static ArrayList<Team> temp3 = new ArrayList<>(); //lista opou exei se seira th mia meta thn allh tis omades pou paizoun antipaloi (mod2), dld h omada sth thesi 0 paizei antipalh me thn omada sth thesh 1, klp
    private BowlingViewModel bowlingViewModel;
    private RoundListAdapter blistAdapter;
    public static String champuuid;
    public String teamuuid;
    public Championship championship;
    public static List<Round> rofTeam; //axristo
    public static List<Round> test;
    private static String firstRounduud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamsvsteams1);
        Bundle bundleObject = this.getIntent().getExtras();


        details=(TextView) findViewById(R.id.textView1);

        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            hdcp_parameters= (ArrayList<String>) bundleObject.getStringArrayList("hdcp_parameters");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            champuuid = bundleObject.getString("champuuid");
            championship= (Championship) bundleObject.getSerializable("champ");
        }

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new RoundListAdapter(this, this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

 /*       //todo: na kanw to championship update to status se "started" h "in progress" h kati allo analogo
        bowlingViewModel.getChampUUID(champuuid).observe(this, new Observer<Championship>() { //fixme: leitourgei perierga
            @Override
            public void onChanged(Championship c) {
                System.out.println(" edw to ch einai "+c.getStatus());
                System.out.println(" edw to ch to 8etw ws started");
                c.setStatus("started");
                bowlingViewModel.update(c);
                System.out.println(" edw to ch to 8etw egine "+c.getStatus());
            }
        }); */

        if (all_the_teams.size()!=0) {
            roundRobin(all_the_teams.size(), rounds);

            test();
        }
    }

    void roundRobin(int teams, int round) {
      //temp2=new Team[round][teams];
        if (((teams%2 != 0) && (round != teams - 1))||(teams <= 0))
            throw new IllegalArgumentException();
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
System.out.println("t1 teammates " + t1.getTeammates().size());
                        temp1.add(t1); //kai tin pernaw sthn lista temp1

                        //temp2[i][counter]=t1;
                        //counter++;

                        //temp3.add(t1);
                    }
                }
                for (int j=0; j<all_the_teams.size();j++){ //vriskw tin alli omada poy tha einai antipalos
                    if (all_the_teams.get(j).getFTeamID()== cycle[teams - i - 1]){
                        Team t2 = all_the_teams.get(j);
                        System.out.println("t2 teammates " + t2.getTeammates().size());
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
                    for (int p = 0; p < pa.size(); p++) { //gia kathe paikth ths omadas auths
                        Round_detail rd = new Round_detail(ruuid,pa.get(p).getUuid(), 0, 0, 0,pa.get(p).getHdcp() ); //ftiaxnw to rd //todo na kanw setScore to score tou paikth
                        bowlingViewModel.insert(rd);
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
                if(t!=null) {
                    //int a = t.get(0).getSys_teamID();
                    //blistAdapter.setRounds(t);
                    details.append("size of round "+String.valueOf(t.size()));
                } else{
                    details.append("wtf");
                }

            }
        });
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
            extras.putSerializable("champ",championship);
            extras.putSerializable("listround", (Serializable) rofTeam); //axristo
            extras.putSerializable("vs",vs);
            i.putExtras(extras);
            startActivity(i);

        } else if (button_text.equals("test")){
            Intent i = new Intent(this, Start1Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putSerializable("all_the_teams",all_the_teams);
            extras.putString("teamid",teamuuid);
            extras.putString("champuuid",champuuid);
            extras.putSerializable("vs",vs);
            i.putExtras(extras);
            startActivity(i);

        }

    }

    @Override
    public void OnDeleteClickListener(Round myNote) {
        bowlingViewModel.delete(myNote);
    }
}
