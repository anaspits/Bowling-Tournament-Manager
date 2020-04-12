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

import java.util.ArrayList;
import java.util.List;

public class Teamsvsteams1Activity extends AppCompatActivity  implements RoundListAdapter.OnDeleteClickListener{
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs= new ArrayList<>(); //list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
    private static TextView details;
    private static int rounds=3; //fixme
    //public static Team[][] temp2; //dokimh disdiatastatos pinakas anti gia arraylist
    //public static ArrayList<Team> temp3 = new ArrayList<>(); //lista opou exei se seira th mia meta thn allh tis omades pou paizoun antipaloi (mod2), dld h omada sth thesi 0 paizei antipalh me thn omada sth thesh 1, klp
    private BowlingViewModel bowlingViewModel;
    private RoundListAdapter blistAdapter;
    public static String champuuid;
    public String teamuuid;
    public Championship championship;


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



        roundRobin(all_the_teams.size(),rounds);

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

                        temp1.add(t1); //kai tin pernaw sthn lista temp1

                        //temp2[i][counter]=t1;
                        //counter++;

                        //temp3.add(t1);
                    }
                }
                for (int j=0; j<all_the_teams.size();j++){ //vriskw tin alli omada poy tha einai antipalos
                    if (all_the_teams.get(j).getFTeamID()== cycle[teams - i - 1]){
                        Team t2 = all_the_teams.get(j);

                        temp1.add(t2); //tin pernaw kai afti sti lista gia na exw mia lista apo antipales omades

                        //temp2[i][counter]=t2;
                        //counter++;

                        //temp3.add(t2);
                    }
                }

                vs.add(temp1); //pernaw tin lista twn 2 antipalwn omadwn stin lista vs, opou h thesi tous einai o gyros ston opoio paizoun+1
                details.append(" Team "+temp1.get(0).getFTeamID()+ " vs Team "+temp1.get(1).getFTeamID()+"\n");

                Round r = new Round (d,1, temp1.get(0).getFTeamID(), temp1.get(1).getFTeamID() , champuuid, temp1.get(0).getUuid(),  temp1.get(1).getUuid());
                System.out.println("t1: "+temp1.get(0).getFTeamID()+" t2: " + temp1.get(1).getFTeamID());
                bowlingViewModel.insert(r);
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
                    blistAdapter.setRounds(t);
                    details.append(String.valueOf(t.size()));
                } else{
                    details.append("wtf");
                }

            }
        });
    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();

        if (button_text.equals("Start Championship"))
        {
            // Intent gonext = new Intent(this,Create3Activity.class);
            //startActivity(gonext);
            Intent i = new Intent(this, RoundActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putSerializable("all_the_teams",all_the_teams);
            extras.putString("teamid",teamuuid);
            extras.putString("champuuid",champuuid);
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
