package com.iskcon.folk.app.chantandhear.service;

import android.view.View;

import com.iskcon.folk.app.chantandhear.model.JapaMalaModel;

public interface EventHandler {

    public abstract void handle(JapaMalaModel japaMalaModel, View view);
}
