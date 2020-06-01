package com.example.bowlingchampionshipmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class WarningDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning!") .setMessage("If you go Back you will loose your progress").setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("cancel");
                dialogInterface.cancel();
            } })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    } });
        return builder.create(); }
}