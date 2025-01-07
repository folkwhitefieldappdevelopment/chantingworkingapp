package com.iskcon.folk.app.chantandhear.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Vibrator;

import com.iskcon.folk.app.chantandhear.service.mediaplayer.MuteNotificationSingleton;

public class CommonUtils {

    public static void vibrateFunction(long milliseconds, Vibrator vibrator) {
        if (MuteNotificationSingleton.isNotificationNotMuted() && (vibrator != null && vibrator.hasVibrator())) {
            vibrator.vibrate(milliseconds);
        }
    }

    public static void showDialog(Context context, OpenAlertDialogRqModel openAlertDialogRqModel) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // 1. Setting title.
        String title = openAlertDialogRqModel.getTitle();
        alertDialogBuilder.setTitle((title != null && !title.isEmpty() ? title : "Hare Krishna"));

        // 2. Setting message
        alertDialogBuilder.setMessage(openAlertDialogRqModel.getMessage());

        // 3. Setting positive button name and handler.
        alertDialogBuilder.setPositiveButton(openAlertDialogRqModel.getPositiveButtonName(),
                openAlertDialogRqModel.getPositiveClickHandler());

        // 4. Setting negative button name and handler.
        if (openAlertDialogRqModel.getNegativeButtonName() != null && openAlertDialogRqModel.getNegativeClickHandler() != null) {
            alertDialogBuilder.setNegativeButton(openAlertDialogRqModel.getNegativeButtonName(),
                    openAlertDialogRqModel.getNegativeClickHandler());
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}