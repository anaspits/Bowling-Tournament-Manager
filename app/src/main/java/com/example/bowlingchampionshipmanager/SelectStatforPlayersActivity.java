package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectStatforPlayersActivity extends AppCompatActivity {

    private BowlingViewModel bowlingViewModel;
    private SelectParticipantListAdapter blistAdapter;
    public String champuuid;
    public Championship championship;
    private TextView textView;
   // private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectplayers);

       // flag="none";

       /* Bundle bundleObject = this.getIntent().getExtras();
        if(bundleObject!=null){
            championship= (Championship) bundleObject.getSerializable("champ");
          //  flag = bundleObject.getString("flag");
            champuuid=championship.getUuid();
        }
*/

    }

    public void openNewActivity(View View) {
        String button_text;
        button_text = ((Button) View).getText().toString();
        /*if(button_text.equals("Back"))
        {
            Intent goback = new Intent(this,Create1Activity.class);
            startActivity(goback);
        }
        else */
        if (button_text.equals("All Players")) {
            Intent i = new Intent(SelectStatforPlayersActivity.this, ParticipantStatisticsActivity.class);
            Bundle bundle = new Bundle();
           bundle.putString("flag", "all");
           // bundle.putSerializable("champ", championship);
            i.putExtras(bundle);
            startActivity(i);

        } else if (button_text.equals("Specific Player")) {
            Intent i = new Intent(SelectStatforPlayersActivity.this, SelectParticipantActivity.class);
            Bundle bundle = new Bundle();
           // bundle.putSerializable("champ", championship);
            i.putExtras(bundle);
            startActivity(i);
        }
    }
}
