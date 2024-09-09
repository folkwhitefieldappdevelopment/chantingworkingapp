package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;

public class UnmuteButtonHandler extends AbstractMediaPlayerEventHandler {

    public UnmuteButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(View view) {
        super.animateAndVibrate(view, 50, 200);
        MediaPlayer mediaPlayer = super.getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
        if (mediaPlayer != null && mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0) {
            super.getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer().setVolume(1.0f, 1.0f);
            super.getAppCompatActivity().findViewById(R.id.muteIconImageView).setVisibility(View.VISIBLE);
            super.getAppCompatActivity().findViewById(R.id.unmuteIconImageView).setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(getAppCompatActivity(), "Hare Krishna, nothing to un-mute, round has not yet started.", Toast.LENGTH_SHORT).show();
        }
    }
}