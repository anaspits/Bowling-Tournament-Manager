package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayList;

public class HDCPActivity extends AppCompatActivity {
    static ArrayList<Participant> bowlers;
    public static ArrayList<Team> all_the_teams;
    static ArrayList<String> hdcp_parameters=new ArrayList<>();
    private static EditText par;
    private static EditText par2;
    private static EditText par3;
    private static EditText par4;
    private static EditText par5;
    private static int pressed=0; //an patithei to export tha ginei =1, alliws tha minei 0 gia na perastoun oi times tou hdcp me to save
    //enalaktika apla kanw 1 koumpi pou tha ta kanei kai ta 2: EXPORT-SAVE & NEXT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdcp);

        par = (EditText) findViewById(R.id.editHDCPparameters);
        par2 = (EditText) findViewById(R.id.editHDCPparameters2);
        par3 = (EditText) findViewById(R.id.editHDCPparameters3);
        par5 = (EditText) findViewById(R.id.editHDCPparameters5);
        par4= (EditText) findViewById(R.id.editHDCPparameters4);


       // hdcp_parameters.add(par2.getText().toString());
  /*      hdcp_parameters.add(String.valueOf(par3.getText()));
        hdcp_parameters.add(String.valueOf(par5.getText()));
        hdcp_parameters.add(String.valueOf(par.getText()));
        hdcp_parameters.add(String.valueOf(par4.getText()));
*/
        Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            bowlers = (ArrayList<Participant>) bundleObject.getSerializable("bowlers");
            all_the_teams = (ArrayList<Team>) bundleObject.getSerializable("all_the_teams");
        }
    }

    public void exportcsv(View view){
        //generate data
        StringBuilder data = new StringBuilder();
        data.append("Teams");
        for(int i = 0; i < bowlers.size()/2;i++){
            Participant p = bowlers.get(i);
            data.append("\n"+"Team " + String.valueOf(p.getTeamid())+" "+String.valueOf(p.getFN())+ " " + String.valueOf(p.getLN()) + " (Avg: " + String.valueOf(p.getBowlAvg()) + " ) & " + p.getPartner().getFN() + " " + String.valueOf(p.getPartner().getLN()) + " (Avg: " + String.valueOf(p.getPartner().getBowlAvg()) + " )");
        }
        data.append("\n"+" HDCP Parameters"+ "\n");
        data.append("1) Basis scores:"+ "\n" );
        data.append("Beginners: "+ par2.getText()+"\n");
        data.append("Advanced: "+ par3.getText()+"\n");
        data.append("Teams with less players: "+ par5.getText()+"\n");
        data.append("2) Percentage Factor: "+ par.getText()+ "\n" );
        data.append("3) Tavani: "+ par4.getText()+ "\n" );

        hdcp_parameters.add(par2.getText().toString());
        hdcp_parameters.add(par3.getText().toString());
        hdcp_parameters.add(par5.getText().toString());
        hdcp_parameters.add(par.getText().toString());
        hdcp_parameters.add(par4.getText().toString());
        pressed=1;


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
            HDCPparameters h = new HDCPparameters(0,0,0,0,0,0);
            if (pressed==0) {
                //axrista
                hdcp_parameters.add(par2.getText().toString());
                hdcp_parameters.add(par3.getText().toString());
                hdcp_parameters.add(par5.getText().toString());
                hdcp_parameters.add(par.getText().toString());
                hdcp_parameters.add(par4.getText().toString()); //

                h.setBegBS(Integer.parseInt(par2.getText().toString()));
                h.setAdvBS(Integer.parseInt(par3.getText().toString()));
                h.setLessBS(Integer.parseInt(par5.getText().toString()));
                h.setFactor(Integer.parseInt(par.getText().toString()));
                h.setTavani(Integer.parseInt(par4.getText().toString()));
                //na to kanw insert sth vash
            }
            Intent i =  new Intent(this, Create3Activity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("bowlers",bowlers);
            extras.putStringArrayList("hdcp_parameters",hdcp_parameters);
            extras.putSerializable("all_the_teams",all_the_teams);
            extras.putSerializable("hdcppar_object",h);
            i.putExtras(extras);
            startActivity(i);

        }

    }
}
