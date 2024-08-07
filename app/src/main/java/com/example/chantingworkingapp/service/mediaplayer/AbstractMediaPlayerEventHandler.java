package com.example.chantingworkingapp.service.mediaplayer;

import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.service.AbstractEventHandler;

public abstract class AbstractMediaPlayerEventHandler extends AbstractEventHandler {

    private MediaPlayer mediaplayer;

    public AbstractMediaPlayerEventHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity);
        this.mediaplayer = mediaplayer;
    }

    public MediaPlayer getMediaplayer() {
        return mediaplayer;
    }

    public void setMediaplayer(MediaPlayer mediaplayer) {
        this.mediaplayer = mediaplayer;
    }



}