package com.example.chantingworkingapp.service;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;

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

    public void vibrate(long duration) {
        CommonUtils.vibrateFunction(duration, (Vibrator) appCompatActivity.getSystemService(Context.VIBRATOR_SERVICE));
    }

    public void animate(View view, int duration) {
        view.animate().setDuration(duration).scaleX(1.5f).scaleY(1.5f).withEndAction(() -> view.animate().setDuration(duration).scaleX(1f).scaleY(1f));
    }

    public void animateAndVibrate(View view, int vibrationDuration, int animationDuration) {
        this.vibrate(vibrationDuration);
        view.animate().setDuration(animationDuration).scaleX(1.5f).scaleY(1.5f).withEndAction(() -> view.animate().setDuration(animationDuration).scaleX(1f).scaleY(1f));
    }
}
