package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Teamsvsteams1Activity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    static ArrayList<String> hdcp_parameters;
    public static ArrayList<Team> all_the_teams;
    public static ArrayList<ArrayList> vs= new ArrayList<>(); //list me tis antipalles omades
    private static TextView details;
    private static int rounds=3;


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
        if (((teams%2 != 0) && (round != teams - 1))||(teams <= 0))
            throw new IllegalArgumentException();
        int[] cycle = new int[teams];
        int n = teams /2;
        for (int i = 0; i < n; i++) {
            cycle[i] = i + 1;
            cycle[teams - i - 1] = cycle[i] + n;
        }

        for(int d = 1; d <= round; d++) {
             details.append(" Round "+ d +": \n");
            //System.out.println(String.format("Round %d", d));
            for (int i = 0; i < n; i++) {
               /* Team t1 = all_the_teams.get(i);
                Team t2 = all_the_teams.get(teams-i-1);
                ArrayList<Team> temp = new ArrayList<>();
                temp.add(t1);
                temp.add(t2);
                vs.add(temp);
                details.append(" Team "+(t1.getTeamID()+1)+ " vs Team "+(t2.getTeamID()+1)+"\n"); */
                details.append(" Team "+cycle[i]+ " vs Team "+cycle[teams - i - 1]+"\n");
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

        }

    }
}
