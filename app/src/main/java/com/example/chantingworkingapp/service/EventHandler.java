package com.example.chantingworkingapp.service;

import android.view.View;

import com.example.chantingworkingapp.model.JapaMalaModel;

public interface EventHandler {

    public abstract void handle(JapaMalaModel japaMalaModel, View view);
}
