package com.example.chantingworkingapp.service;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.util.CommonUtils;

public abstract class AbstractEventHandler {

    private final MainActivity appCompatActivity;

    public AbstractEventHandler(MainActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public abstract void handle(JapaMalaModel japaMalaModel, View view);

    public MainActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void vibrate(long duration){
        CommonUtils.vibrateFunction(duration,(Vibrator) appCompatActivity.getSystemService(Context.VIBRATOR_SERVICE));
    }
}
