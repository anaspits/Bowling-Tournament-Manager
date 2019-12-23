package com.example.bowlingchampionshipmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.app.Activity;

import static com.example.bowlingchampionshipmanager.R.id.next_btn;
import android.widget.EditText;

public class Create1Activity extends AppCompatActivity {

    private static EditText textView;
    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE=41;
    private static final int SAVE_REQUEST_CODE = 42;

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
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }

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
                   // Cursor returnCursor = getContentResolver().query(currentUri,null,null,null,null);
                   // int fileTitle= returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                    String a = returnCursor.getString(fileTitle);
                   // System.out.println(a);

                    try {
                        String content =
                                readFileContent(currentUri);
                        textView.setText(content);
                       // textView.setText(returnCursor.getString(fileTitle));

                    } catch (IOException e) {
                        // Handle error here
                    }
                }
            }
        }
    }

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

    private String readFileContent(Uri uri) throws IOException {

        InputStream inputStream =
                getContentResolver().openInputStream(uri);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(
                        inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentline;
        while ((currentline = reader.readLine()) != null) {
            stringBuilder.append(currentline + "\n");
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    public void openNewActivity(View View) {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("Back"))
        {
            Intent goback = new Intent(this,MainActivity.class);
            startActivity(goback);
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
