package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.app.Activity;

import static com.example.bowlingchampionshipmanager.R.id.next_btn;
import android.widget.EditText;
//import com.opecsv.CSVReader;

public class Create1Activity extends AppCompatActivity {

    private static EditText textView;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE=41;
    private static final int SAVE_REQUEST_CODE = 42;
    private static ArrayList<Participant> bowlers = new ArrayList<Participant>();
    private static Participant s = new Participant(999,"instance", "instance", 999);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create1);

        textView = (EditText) findViewById(R.id.fileText);
       Button button_imp  = (Button) findViewById(R.id.button_import);
        button_imp.setOnClickListener(new View.OnClickListener() {
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
                    textView.setText("");
                }
            } else if (requestCode == SAVE_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();
                    writeFileContent(currentUri);
                }
            } else if (requestCode == OPEN_REQUEST_CODE) {

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
                        //textView.setText(ft);
                        //textView.setText("here");
                        //textView.setText(filepath);

                        returnCursor.close();
                       // participant(ft);
                       // textView.setText(returnCursor.getString(fileTitle));

                    } catch (IOException e) {
                        // Handle error here
                        e.getCause();
                        textView.setText("error");
                    }


                }
            }
        }
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void participant( String ft){


            //List to store all Participants
            ArrayList<Participant> bowlers = new ArrayList<Participant>();

            Participant s = new Participant(999,"instance", "instance", 999);

           //String csvFile="bowlers-list.csv";
            String csvFile=ft;
            String line = "";
            String cvsSplitBy = ",";

            //s.createParticipantList(bowlers, csvFile, line, cvsSplitBy);
            try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){

                String [] input = null;
                String fn = null;
                String ln = null;
                int ba = 0;

                int i = 0;
                while ((line = br.readLine()) != null){
                    // use comma as seperator

                    //get input
                    input = line.split(cvsSplitBy);
//                System.out.println(Arrays.toString(input));

                    //get first name
                    fn = input[0];

                    //get last name
                    ln = input[1];

                    //get avg
                    ba = Integer.parseInt(input[2]);

//                System.out.println("id: " + i + ", FN: " +  fn + ", LN: " + ln + ", Avg: " + ba);
                    Participant p = new Participant(i, fn, ln, ba);
                    bowlers.add(p);

                    i++;

                }

            } catch(IOException e){
                e.printStackTrace();
            }

            //return bowlers;

            //Logic for generating teams(pairs)

            //Sort by bowling average
            Collections.sort(bowlers, Participant.partBowlAvg);
            //add 5 points to pair in 2D table of poissible matchings?
            //Associate Participants with friends
            //2D table of friendships -- maybe remove from attributes
            //if [][] = 2, add 2 points to pair in 2D table of poissible matchings?
            int i;
            for(i = 0; i < bowlers.size(); i++){
                (bowlers.get(i)).setPartner(bowlers.get(bowlers.size() - i - 1));
                (bowlers.get(bowlers.size() - i - 1)).setPartner(bowlers.get(i));


            }

            for(i = 0; i < bowlers.size()/2;i++){

                //Print results in form of
                //Team No. , Participant 1 , Participant 2

                Participant p = bowlers.get(i);
                System.out.println("Team " + (i + 1) + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
                //String fn= p.getFN();
            }

        //textView.setText(fn);
        //textView.setText("ola kala");



    } */

    private void writeFileContent(Uri uri)
    {
        try{
            ParcelFileDescriptor pfd =
                    this.getContentResolver().
                            openFileDescriptor(uri, "w");

            FileOutputStream fileOutputStream =
                    new FileOutputStream(
                            pfd.getFileDescriptor());

            String textContent =
                    textView.getText().toString();

            fileOutputStream.write(textContent.getBytes());

            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

        s.createParticipantList(bowlers, inputStream, line, cvsSplitBy);
       /* //try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){

            String [] input = null;
            String fn = null;
            String ln = null;
            int ba = 0;

            int i = 0;
            while ((line = reader.readLine()) != null){
                // use comma as seperator

                //get input
                input = line.split(cvsSplitBy);
//                System.out.println(Arrays.toString(input));

                //get first name
                fn = input[0];

                //get last name
                ln = input[1];

                //get avg
                ba = Integer.parseInt(input[2]);

//                System.out.println("id: " + i + ", FN: " +  fn + ", LN: " + ln + ", Avg: " + ba);
                Participant p = new Participant(i, fn, ln, ba);
                bowlers.add(p);

                i++;

            }

       // } catch(IOException e){
       //     e.printStackTrace();
       // }

        //return bowlers; */

        s.generateTeams(bowlers);
       /* //Logic for generating teams(pairs)

        //Sort by bowling average
        Collections.sort(bowlers, Participant.partBowlAvg);
        //add 5 points to pair in 2D table of poissible matchings?
        //Associate Participants with friends
        //2D table of friendships -- maybe remove from attributes
        //if [][] = 2, add 2 points to pair in 2D table of poissible matchings?
        int j;
        for(j = 0; j < bowlers.size(); j++){
            (bowlers.get(j)).setPartner(bowlers.get(bowlers.size() - j - 1));
            (bowlers.get(bowlers.size() - j - 1)).setPartner(bowlers.get(j));


        }
        int i;
        for(i = 0; i < bowlers.size()/2;i++){

            //Print results in form of
            //Team No. , Participant 1 , Participant 2

            Participant p = bowlers.get(i);
            System.out.println("Team " + (i + 1) + ": " + p.getFN() + " " + p.getLN() + " (Avg: " + p.getBowlAvg() + " ) & " + p.getPartner().getFN() + " " + p.getPartner().getLN() + " (Avg: " + p.getPartner().getBowlAvg() + " )");
            //String fn= p.getFN();
        }
        Participant p = bowlers.get(1);
        String fnn =p.getFN();
        textView.setText(fnn);
        //textView.setText("ola kala");

        //// */
        Participant p = bowlers.get(0);
        String fnn =p.getFN();
        textView.setText(fnn);
        inputStream.close();
        //return stringBuilder.toString();
    }



    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Back"))
        {
            //Intent goback = new Intent(this,MainActivity.class);
            //startActivity(goback);
        }
        else if (button_text.equals("Next"))
        {
            Intent gonext = new Intent(this,Create2Activity.class);
            startActivity(gonext);

        }
       /* else if (button_text.equals("Import"))
        {


        } */
    }

}
