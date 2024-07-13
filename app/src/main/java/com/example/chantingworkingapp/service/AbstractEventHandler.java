package com.example.chantingworkingapp.service;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.model.JapaMalaModel;

public abstract class AbstractEventHandler {

    private final MainActivity appCompatActivity;

    public AbstractEventHandler(MainActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public abstract void handle(JapaMalaModel japaMalaModel, View view);

    public MainActivity getAppCompatActivity() {
        return appCompatActivity;
    }
}
