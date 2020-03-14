package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewActivity extends AppCompatActivity {

    public static final String NEW_ADDED = "new_added";
    private EditText newadded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        newadded = findViewById(R.id.newadded);
        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(newadded.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                    String note = newadded.getText().toString();
                    resultIntent.putExtra(NEW_ADDED, note);
                    setResult(RESULT_OK, resultIntent);
                }

                finish();
            }
        });
    }
}
