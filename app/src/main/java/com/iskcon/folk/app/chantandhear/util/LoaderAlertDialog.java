package com.iskcon.folk.app.chantandhear.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.iskcon.folk.app.chantandhear.R;

public class LoaderAlertDialog {

    private final Activity activity;
    private AlertDialog dialog;

    public LoaderAlertDialog(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("InflateParams")
    public LoaderAlertDialog show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loader_alert_dialog, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return this;
    }

    public void close() {
        dialog.dismiss();
    }
}