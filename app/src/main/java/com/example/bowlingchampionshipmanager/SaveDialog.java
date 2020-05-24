package com.example.bowlingchampionshipmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SaveDialog  extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Progress Saved!").setMessage("Your Championship is ready to Start!")
                .setPositiveButton("OK, back to Main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                R.string.save,
                                Toast.LENGTH_LONG).show();
                    }
                });
        return builder.create();
    }
}

