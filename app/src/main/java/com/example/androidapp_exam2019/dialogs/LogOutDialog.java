package com.example.androidapp_exam2019.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.androidapp_exam2019.ConnectionActivity;
import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.constants.AppSharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class LogOutDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.logoutDialogTitle))
                .setMessage(getString(R.string.logoutDialogMessage))
                .setPositiveButton(getString(R.string.logoutDialogPositive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear().commit();
                        Intent intentReturnConnectionScreen = new Intent(getContext(), ConnectionActivity.class);
                        intentReturnConnectionScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentReturnConnectionScreen);
                    }
                })
                .setNegativeButton(getString(R.string.logoutDialogNegative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

}
