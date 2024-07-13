package com.example.chantingworkingapp.service.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.util.CommonUtils;

public class UnMuteButtonHandler extends AbstractMediaPlayerEventHandler{

    public UnMuteButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {
        super.getMediaplayer().setVolume(1.0f, 1.0f);
        super.getAppCompatActivity().findViewById(R.id.muteIconImageView).setVisibility(View.VISIBLE);
        super.getAppCompatActivity().findViewById(R.id.unmuteIconImageView).setVisibility(View.INVISIBLE);
        CommonUtils.vibrateFunction(50,(Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE));
    }
}
