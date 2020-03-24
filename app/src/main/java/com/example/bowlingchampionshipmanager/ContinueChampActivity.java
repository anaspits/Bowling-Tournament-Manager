package com.example.bowlingchampionshipmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class ContinueChampActivity extends AppCompatActivity implements ChampListAdapter.OnDeleteClickListener{

    public static final int UPDATE_CHAMP_ACTIVITY_REQUEST_CODE = 2;
    private BowlingViewModel bowlingViewModel;
    private ChampListAdapter clistAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_champ);

        bowlingViewModel = ViewModelProviders.of(this).get(BowlingViewModel.class); //dimiourgia tou antikeimenou ViewModel gia tin diaxeirhshs ths vashs
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        clistAdapter = new ChampListAdapter(this, this);
        recyclerView.setAdapter(clistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bowlingViewModel.getAllChamp().observe(this, new Observer<List<Championship>>() {
            @Override
            public void onChanged(List<Championship> c) {
                clistAdapter.setChamp(c);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;

        if (requestCode == UPDATE_CHAMP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { ////////gia edit kai update
            Button c= findViewById(R.id.back_btn);
            Bundle bundleObject =resultData.getExtras();
            if(bundleObject!=null){
                Championship t;
                t = (Championship) bundleObject.getSerializable("b_object");
                bowlingViewModel.update(t);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.save,
                        Toast.LENGTH_LONG).show();

            }
        }
    }
    @Override
    public void OnDeleteClickListener(Championship myNote) {
        bowlingViewModel.delete(myNote);
    }
}
