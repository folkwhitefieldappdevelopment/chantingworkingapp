package com.example.chantingworkingapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

public class CommonUtils {

    public static void vibrateFunction(long milliseconds,Vibrator vibrator) {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

    public static void showWarningDialog(Context context, String title, String textForDialog, DialogInterface.OnClickListener OkHandler, DialogInterface.OnClickListener CancelHandler) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(textForDialog);
        builder.setPositiveButton("OK", OkHandler);
        builder.setNegativeButton("Cancel", CancelHandler);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
