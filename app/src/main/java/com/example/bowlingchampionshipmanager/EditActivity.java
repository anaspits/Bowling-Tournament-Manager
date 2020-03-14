package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    public static final String BOWL_ID="bowlId";
    static final String UPDATED_NOTE = "bowl_text";
    private EditText editdb;
    private Bundle bundle;
    private int bowlId;
    private LiveData<Test_table> test_table;

    EditViewModel editViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editdb = findViewById(R.id.editdb);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            bowlId = bundle.getInt("bowlId");
        }

        editViewModel = ViewModelProviders.of(this).get(EditViewModel.class);

        //fetch step 3
        test_table = editViewModel.getBowl(bowlId);
        test_table.observe(this, new Observer<Test_table>() {
            @Override
            public void onChanged(Test_table test_table) {
                editdb.setText(test_table.getName());
            }
        });
    }

    public void updateDB (View view) {
        String updatedNote = editdb.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("bowlId", bowlId);
        resultIntent.putExtra(UPDATED_NOTE, updatedNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate (View view) {
        finish();
    }
}
