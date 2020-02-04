package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Teamsvsteams1Activity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs= new ArrayList<>(); //list me tis antipalles omades opou h thesi twn omadwn sti lista = einai o gyros opou paizoun antipales+1
    private static TextView details;
    private static int rounds=3;
    //public static Team[][] temp2; //dokimh disdiatastatos pinakas anti gia arraylist
    //public static ArrayList<Team> temp3 = new ArrayList<>(); //lista opou exei se seira th mia meta thn allh tis omades pou paizoun antipaloi (mod2), dld h omada sth thesi 0 paizei antipalh me thn omada sth thesh 1, klp


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
        }

        roundRobin(all_the_teams.size(),rounds);

    }

    static void roundRobin(int teams, int round) {
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
                    if (all_the_teams.get(j).getTeamID()== cycle[i]){
                        Team t1 = all_the_teams.get(j);

                        temp1.add(t1); //kai tin pernaw sthn lista temp1

                        //temp2[i][counter]=t1;
                        //counter++;

                        //temp3.add(t1);
                    }
                }
                for (int j=0; j<all_the_teams.size();j++){ //vriskw tin alli omada poy tha einai antipalos
                    if (all_the_teams.get(j).getTeamID()== cycle[teams - i - 1]){
                        Team t2 = all_the_teams.get(j);

                        temp1.add(t2); //tin pernaw kai afti sti lista gia na exw mia lista apo antipales omades

                        //temp2[i][counter]=t2;
                        //counter++;

                        //temp3.add(t2);
                    }
                }

                vs.add(temp1); //pernaw tin lista twn 2 antipalwn omadwn stin lista vs, opou h thesi tous einai o gyros ston opoio paizoun+1
                details.append(" Team "+temp1.get(0).getTeamID()+ " vs Team "+temp1.get(1).getTeamID()+"\n");

                //emfanish me temp2
                //details.append(" Team "+temp2[i][0].getTeamID()+ " vs Team "+temp2[i][1].getTeamID()+"\n");

                //emfanish me temp3
                //details.append(" Team "+temp3.get(counter2).getTeamID()+ " vs Team "+temp3.get(counter2+1).getTeamID()+"\n");
                //counter2=counter2+2;

                //details.append("Prepei Team "+cycle[i]+ " vs Team "+cycle[teams - i - 1]+"\n");
                //System.out.println(String.format("team %d - team %d",cycle[i],cycle[teams - i - 1]));
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
            extras.putSerializable("vs",vs);
            i.putExtras(extras);
            startActivity(i);

        } /*else if (button_text.equals("test")){
            Intent i = new Intent(this, Start1Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putSerializable("all_the_teams",all_the_teams);
            extras.putSerializable("vs",vs);
            i.putExtras(extras);
            startActivity(i);

        } */

    }
}
