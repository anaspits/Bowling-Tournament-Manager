package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.app.Activity;

import android.widget.EditText;
import android.widget.Toast;

/*import javax.persistence.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence; */
//import com.opecsv.CSVReader;

public class Create1Activity extends AppCompatActivity implements BowlingListAdapter.OnDeleteClickListener {

    private static EditText textView;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE=41;
    private static final int SAVE_REQUEST_CODE = 42;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    public static final int UPDATE_TEAM_ACTIVITY_REQUEST_CODE = 3;
    public static final int UPDATE_CHAMP_ACTIVITY_REQUEST_CODE = 4;
    public static ArrayList<Participant> bowlers = new ArrayList<Participant>();
    //public static ArrayList<ArrayList> teamates = new ArrayList<>();
    public static ArrayList<ArrayList> teams = new ArrayList<>();
    public static ArrayList<ArrayList> teamsplayersid = new ArrayList<>();
    public static ArrayList<Team> all_the_teams = new ArrayList<>();
    public static ArrayList<Integer> teamsid = new ArrayList<>();
   // private static Participant s = new Participant(999,"instance", "instance", 999, 0);
   private static Participant s = new Participant(999,"instance", "instance", 999, 0);
    public static int t_id=1;
    public static int allbowls=0;
    private static Test_table test= new Test_table("instance");
    public static ArrayList<Test_table> testbowlers = new ArrayList<Test_table>();

    private String TAG = this.getClass().getSimpleName();
    private BowlingViewModel bowlingViewModel;
    private BowlingListAdapter blistAdapter;
    private BowlingListAdapter blistAdapter2;
    private TeamListAdapter tlistAdapter;
    private int sum =0;
    private int champinsertID;
    public int fchampID=1;
    public static String ok= "fail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create1);

        textView = (EditText) findViewById(R.id.fileText);
       Button button_imp  = (Button) findViewById(R.id.button_import);

       //na svisw
       /* EntityManagerFactory emf=Persistence.createEntityManagerFactory("Participant_details");
        EntityManager em=emf.createEntityManager();
        em.getTransaction().begin();
        //Test_table s1 = new Test_table(0, "test");
        //em.persist(s1);
        //Test_table s=em.find(Test_table.class,1);
        //s.set_name("yes");
        em.getTransaction().commit();
        emf.close();
        em.close(); */

        ///recyclerview
        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        blistAdapter = new BowlingListAdapter(this, this);
        blistAdapter2 = new BowlingListAdapter(this, this);
        recyclerView.setAdapter(blistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //addnew sto database
        Button addnew= findViewById(R.id.addnew);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create1Activity.this, AddNewActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });
        ////////////////gia room insert
        /*final int t_id = 1;
        Test_table t= new Test_table(t_id, "lol1");
        bowlingViewModel.insert(t);*/
        /////////////// telos room insert

        bowlingViewModel.getAllBowls().observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> participants) {
                blistAdapter.setBowls(participants);
            }
        });
        bowlingViewModel.getAllPlayersofChamp(0).observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(List<Participant> participants) {
                blistAdapter2.setBowls(participants);
                sum =blistAdapter2.getItemCount();
                addnew.setText(String.valueOf(sum));
            }
        });
        bowlingViewModel.getLastInsertChamp().observe(this, new Observer<Championship>() {
            @Override
            public void onChanged(Championship c) {
               if(c!=null) {
                   fchampID = c.getFchampID()+1;
               }
            }
        });
////////////

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

        ////////////////gia room insert
        /*final int t_id = 4;
        Test_table t= new Test_table(t_id, "lol4");
        bowlingViewModel.insert(t); */
        /////////////// telos room insert
        ////////gia insert sto database tou room
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
            //final String note_id = UUID.randomUUID().toString();
           /*axrista String nam =resultData.getStringExtra(AddNewActivity.NEW_ADDED);
            int avg = Integer.parseInt(resultData.getStringExtra("new_avg"));
            int team = Integer.parseInt(resultData.getStringExtra("new_avg"));
            int hdcp = Integer.parseInt(resultData.getStringExtra("new_avg")); //na to valw ston constructor
            int champ = Integer.parseInt(resultData.getStringExtra("new_avg"));*/
            //int fakeid = bowlingViewModel.getAllPlayersofChamp(champ);
           // int fakeid = Integer.parseInt(resultData.getStringExtra("new_fid"));
            int fakeid = sum+1;
            //Test_table t= new Test_table( resultData.getStringExtra(AddNewActivity.NEW_ADDED));
            //na ftia3w to name
            //Participant t = new Participant(fakeid,resultData.getStringExtra(AddNewActivity.NEW_ADDED), "",avg,team);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null) {
                Participant t = (Participant) bundleObject.getSerializable("b_object");
                t.setFakeID(fakeid);
                bowlingViewModel.insert(t);
                t_id++; //axristo

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            //c.setText("Here");

            //check dao for the update()
            //update step 3
            // Code to update the note
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
             /*axrista   int bowlId;
                String nam;
                int avg,team,hdcp,fid; */
                //Test_table t;
                Participant t;

               /* axrista bowlId = bundleObject.getInt("bowlId");
                nam = bundleObject.getString(EditActivity.UPDATED_NOTE);
                avg = Integer.parseInt( bundleObject.getString("updatedAvg")); //alliws  avg = Integer.valueOf(bundleObject.getString("updatedAvg"));
                //team = Integer.parseInt( bundleObject.getString("updatedTeam"));
                hdcp = Integer.parseInt( bundleObject.getString("updatedHdcp"));
                fid = Integer.parseInt( bundleObject.getString("updatedfid")); */
              // t= (Test_table) bundleObject.getSerializable("b_object");
                t = (Participant) bundleObject.getSerializable("b_object");
              /*axrista  t.setFirstName(nam); t.setLastName("");
                t.setBowlAvg(avg);
               // t.setTeamid(team);
                t.setHdcp(hdcp);
                t.setFchampID(fid); */
               // Test_table t1 = new Test_table(bowlId,up);
                bowlingViewModel.update(t);

                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == UPDATE_TEAM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Team t;
                t = (Team) bundleObject.getSerializable("b_object");
                bowlingViewModel.update(t);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        }
///////////////////////////

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
              //  System.out.println(Arrays.toString(input));

                    //get first name
                    fn = input[0];

                    //get last name
                    ln = input[1];

                    //get avg
                    ba = Integer.parseInt(input[2]);

              //  System.out.println("id: " + i + ", FN: " +  fn + ", LN: " + ln + ", Avg: " + ba);
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


        Championship ch = new Championship(fchampID,0,0, "created"); ////vash 3
        bowlingViewModel.insert(ch);
        System.out.println("chid " +ch.getSys_champID());
        bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
            @Override
            public void onChanged(List<Championship> ch) {
                champinsertID =ch.size();
            }
        });
        System.out.println("chid 2" +champinsertID);

         //test.testcreateParticipantList(bowlingViewModel,inputStream, line, cvsSplitBy,testbowlers);
       s.createParticipantList(bowlingViewModel, inputStream, line, cvsSplitBy,bowlers);


        //createParticipantList: (na svisw ta apo katw)
      /* //try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
        BufferedReader br =
                new BufferedReader(new InputStreamReader(
                        inputStream));
            String [] input = null;
            String fn = null;
            String ln = null;
            int ba = 0;

            int i = 0;
            while ((line = br.readLine()) != null){
                // use comma as seperator

                //get input
                input = line.split(cvsSplitBy);
              //  System.out.println(Arrays.toString(input));

                //get first name
                fn = input[0];

                //get last name
                ln = input[1];

                //get avg
                ba = Integer.parseInt(input[2]);

              //  System.out.println("id: " + i + ", FN: " +  fn + ", LN: " + ln + ", Avg: " + ba);
                Participant p = new Participant(i, fn, ln, ba,0);
                bowlers.add(p);
                i++;
                String name = fn + " " +ln;
             Test_table t= new Test_table(name);
                bowlingViewModel.insert(t);
                t_id++;

            } */

       // } catch(IOException e){
       //     e.printStackTrace();
       // }

        //return bowlers;



       //create the teams
       int playersPerTeam=2;
        s.generateTeams(bowlers,playersPerTeam,bowlingViewModel,champinsertID);

        int i;
       /* //teamates mallon axristo
        for (i=0; i<bowlers.size();i++){
            teamates.add(bowlers.get(i).getTeammates());
        } */
        for (i=0; i<bowlers.size()/playersPerTeam;i++){
            teams.add(bowlers.get(i).getTeamates());
            teamsplayersid.add(bowlers.get(i).getTeamatesid());
        }

        //to teams pou einai arraylist me participants

        for (i=0; i<teams.size();i++) {
            ArrayList<Participant> temp = teams.get(i);
            ArrayList<Integer> tempid = teamsplayersid.get(i);
            Team t = new Team((i+1),null,0); /////////////////////////////////////////vash 1
            t.setTeammates(temp);
            t.setTeammatesid(tempid);
           // t.setChampid(fchampID);
            all_the_teams.add(t);
           // bowlingViewModel.insert(t);
            teamsid.add(t.getSys_teamID());
            //textView.append("Team " + t.getFTeamID() + ", teamid name " + t.getTeamName()+ " players: "+"\n");
          /*  int j;
            for (j=0; j<temp.size();j++) {
               // textView.append(temp.get(j).getFN());
            } */
        }

   /*     Championship ch = new Championship(fchampID,0,0, "created"); ////vash 1
        ch.setTeamsid(teamsid);
        bowlingViewModel.insert(ch); */
        //emfanish test
        for (i=0; i<all_the_teams.size();i++) {
            Team t = all_the_teams.get(i);
            ArrayList<Participant> temp =  t.getTeammates();

            textView.append("\n"+"Team " + t.getFTeamID() +": " );
            int j;
            for (j=0; j<temp.size();j++) {
                textView.append(temp.get(j).getFN() +"  ");
            }
        }

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
        //test
        //emfanizei tous sympaiktes kathe paikth
        /*for (i=0; i<teamates.size();i++) {
            ArrayList<Participant> temp = teamates.get(i);

            textView.append("gia ton paikth " + temp.get(0) + "\n");
            int j;
            for (j=0; j<temp.size();j++) {
               textView.append(temp.get(j).getFN());
            }

        }
        */ //test
 // na ksesxoliasw      Participant p = bowlers.get(0);
 // na ksesxoliasw       String fnn =p.getFN();
        //textView.setText(fq);
        inputStream.close();
        //return stringBuilder.toString();
System.out.println("Telos snart");
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
            //Intent gonext = new Intent(this,Create2Activity.class);
            //startActivity(gonext);
            Intent i =  new Intent(Create1Activity.this, Create2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bowlers",bowlers);
                bundle.putSerializable("all_the_teams",all_the_teams);
                i.putExtras(bundle);
            startActivity(i);

        }
       /* else if (button_text.equals("Import"))
        {


        } */
    }

    @Override
    public void OnDeleteClickListener(Participant myNote) {
        bowlingViewModel.delete(myNote);
    }


}
