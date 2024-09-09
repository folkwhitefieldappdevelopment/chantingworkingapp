package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.media.MediaPlayer;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;

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