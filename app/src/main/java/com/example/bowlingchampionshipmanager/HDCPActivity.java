package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;

public class HDCPActivity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    static ArrayList<String> hdcp_parameters=new ArrayList<>(); //axristo?
    static ArrayList<Integer> chh=new ArrayList<>();
    private static EditText begin;
    private static EditText adv;
    private static EditText tless;
    private static EditText factor;
    private static EditText tavani;
    private static int pressed=0; //an patithei to export tha ginei =1, alliws tha minei 0 gia na perastoun oi times tou hdcp me to save
    //enalaktika apla kanw 1 koumpi pou tha ta kanei kai ta 2: EXPORT-SAVE & NEXT

    private BowlingViewModel bViewModel;
    private LiveData<Championship> c;
    public Championship champ;

    private TextView textTitle;
    public String champuuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdcp);
        textTitle = (TextView) findViewById(R.id.textTitle);
        begin = (EditText) findViewById(R.id.editHDCP1);
        adv = (EditText) findViewById(R.id.editHDCP2);
        tless = (EditText) findViewById(R.id.editHDCP3);
        tavani = (EditText) findViewById(R.id.editHDCP4);
        factor = (EditText) findViewById(R.id.editHDCP5);


       // hdcp_parameters.add(adv.getText().toString());
  /*      hdcp_parameters.add(String.valueOf(tless.getText()));
        hdcp_parameters.add(String.valueOf(tavani.getText()));
        hdcp_parameters.add(String.valueOf(begin.getText()));
        hdcp_parameters.add(String.valueOf(factor.getText()));
*/
        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
            champuuid = bundleObject.getString("champuuid"); //an den kanei import tous players e3 arxis den tha leitourgisei profanws
            champ = (Championship) bundleObject.getSerializable("champ");
        }

        bViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class);
        //Auto mazi me th grammh 192 (bViewModel.update(champ);) kanonika douleve, twra 3afnika den douleuei alla telos pantwn. Exairetika ta nea mas!
        //twra doulevei mia xara pali! lol
      /*  c = bViewModel.getLastInsertChamp();
        c.observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship ch) {
                champ= ch;
               textTitle.setText(String.valueOf(ch.getSys_champID()));

            }
        }); */
      bViewModel.getChampUUID(champuuid).observe(this, new Observer<Championship>() {
          @Override
          public void onChanged(Championship ch) {
              champ= ch;
              textTitle.append(" No."+ch.getSys_champID());

          }
      });

//todo 1: getChamp(uuid) -> adapter -> HDCPActivity.champ H'
// todo 3: check teamsvsteams line 60 H'
//todo 4: check RoundListAdapter returnRounds
        System.out.println("champ id "+champ.getFchampID()+" uuid "+champ.getUuid()+ " to allo u "+ champuuid);

    }


    public void exportcsv(View view){
        //generate data
        StringBuilder data = new StringBuilder();
        data.append("Championship:"+champ.getSys_champID()+",UUID:"+champuuid);
        data.append("\nHDCP Parameters");
        data.append("\n1)Basis scores:,Beginners:," + begin.getText());
        data.append("\n,Advanced:," + adv.getText());
        data.append("\n,Teams with less players:," + tless.getText());
        data.append("\n2)Percentage Factor:," + factor.getText());
        data.append("\n3)Tavani:,"+ tavani.getText());


        /*  for(int i = 0; i < bowlers.size()/2;i++){
            Participant p = bowlers.get(i);
            data.append("\n"+"Team " + String.valueOf(p.getTeamid())+" "+String.valueOf(p.getFirstName())+ " " + String.valueOf(p.getLastName()) + " (Avg: " + String.valueOf(p.getBowlAvg()) + " ) & " + p.getPartner().getFirstName() + " " + String.valueOf(p.getPartner().getLastName()) + " (Avg: " + String.valueOf(p.getPartner().getBowlAvg()) + " )");
        }
        data.append("\n"+" HDCP Parameters"+ "\n");
        data.append("1) Basis scores:"+ "\n" );
        data.append("Beginners: "+ adv.getText()+"\n");
        data.append("Advanced: "+ tless.getText()+"\n");
        data.append("Teams with less players: "+ tavani.getText()+"\n");
        data.append("2) Percentage Factor: "+ begin.getText()+ "\n" );
        data.append("3) Tavani: "+ factor.getText()+ "\n" );

        hdcp_parameters.add(begin.getText().toString());
        hdcp_parameters.add(adv.getText().toString());
        hdcp_parameters.add(tless.getText().toString());
        hdcp_parameters.add(factor.getText().toString());
        hdcp_parameters.add(tavani.getText().toString());

        pressed=1;*/


        try{
            //saving the file into device
            FileOutputStream out = openFileOutput("bowling_championship_data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "bowling_championship_data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.bowlingchampionshipmanager.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Cancel"))
        {
            finish();
        }
        else if (button_text.equals("Save"))
        {
            //Intent gonext = new Intent(this,Create2Activity.class);
            //startActivity(gonext);

            /*axristo?
            HDCPparameters h = new HDCPparameters(0,0,0,0,0,0);
            h.setBegBS(Integer.parseInt(adv.getText().toString()));
            h.setAdvBS(Integer.parseInt(tless.getText().toString()));
            h.setLessBS(Integer.parseInt(tavani.getText().toString()));
            h.setFactor(Integer.parseInt(begin.getText().toString()));
            h.setTavani(Integer.parseInt(factor.getText().toString()));
            //na to kanw insert sth vash */

            String upar1 = begin.getText().toString();
            String upar2 = adv.getText().toString();
            String upar3 = tless.getText().toString();
            String upar4 = factor.getText().toString();
            String upar5 = tavani.getText().toString();

            if (upar1.matches("")){
                chh.add(null);
            } else {
                chh.add(Integer.parseInt(upar1));
                champ.setHdcp_beginners(Integer.parseInt(upar1));
            }
            if (upar2.matches("")){
                chh.add(null);
            } else {
                chh.add(Integer.parseInt(upar2));
                champ.setHdcp_adv(Integer.parseInt(upar2));
            }
            if (upar3.matches("")){
                chh.add(null);
            } else {
                chh.add(Integer.parseInt(upar3));
                champ.setHdcp_less(Integer.parseInt(upar3));
            }
            if (upar4.matches("")){
                chh.add(null);
            } else {
                chh.add(Integer.parseInt(upar4));
                champ.setHdcp_factor(Integer.parseInt(upar4));
            }
            if (upar5.matches("")){
                chh.add(null);
            } else {
                chh.add(Integer.parseInt(upar5));
                champ.setHdcp_tav(Integer.parseInt(upar5));
            }
            champ.setHdcp_parameters(chh);
            System.out.println("2 champ id "+champ.getFchampID()+" uuid "+champ.getUuid()+ " begin "+ champ.getHdcp_adv() );
            bViewModel.update(champ);

            /* if (pressed==0) {

                hdcp_parameters.add(adv.getText().toString());
                hdcp_parameters.add(tless.getText().toString());
                hdcp_parameters.add(tavani.getText().toString());
                hdcp_parameters.add(begin.getText().toString());
                hdcp_parameters.add(factor.getText().toString());

            }

            Intent i =  new Intent(this, Create3Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putSerializable("all_the_teams",all_the_teams);
            extras.putString("champuuid",champuuid);
            extras.putSerializable("hdcppar_object",h); //axristo?
            extras.putSerializable("champ",champ);
            i.putExtras(extras);
            startActivity(i); */
           finish();
            Toast.makeText(
                    getApplicationContext(),
                    R.string.save,
                    Toast.LENGTH_LONG).show();

        }

    }
}
