package com.example.dawnpeace.spota_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class ReviewAlertDialog extends AppCompatDialogFragment {
    private boolean positive;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hapus Komentar")
                .setMessage("Apakah Anda yakin ingin melanjutkan ?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positive = true;
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positive = false;
                    }
                });
        return builder.create();
    }

    public boolean isPositive() {
        return positive;
    }
}
