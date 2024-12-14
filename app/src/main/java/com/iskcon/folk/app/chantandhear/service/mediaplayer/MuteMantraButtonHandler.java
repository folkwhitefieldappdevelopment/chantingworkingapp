package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;

public class MuteMantraButtonHandler extends AbstractMediaPlayerEventHandler{

    public MuteMantraButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(View view) {
        super.animateAndVibrate(view,50,200);
        MediaPlayer mediaPlayer = super.getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
        TextView textView = getAppCompatActivity().findViewById(R.id.muteIconTextView);
        if(textView.getText().equals("Mute mantra")){ // Process of muting the audio
            if (mediaPlayer != null && mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0) {
                mediaPlayer.setVolume(0.0f, 0.0f);
                ImageView muteMantraImageView = (ImageView) view;
                muteMantraImageView.setImageResource(R.drawable.baseline_volume_off_24);
                textView.setText("Unmute mantra");
            } else {
                Toast.makeText(getAppCompatActivity(), "Hare Krishna, nothing to mute, round has not yet started.", Toast.LENGTH_SHORT).show();
            }
        } else { // Process of unmuting the audio
            if (mediaPlayer != null && mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0) {
                mediaPlayer.setVolume(1.0f, 1.0f);
                ImageView muteMantraImageView = (ImageView) view;
                muteMantraImageView.setImageResource(R.drawable.baseline_volume_up_24);
                textView.setText("Mute mantra");
            } else {
                Toast.makeText(getAppCompatActivity(), "Hare Krishna, nothing to mute, round has not yet started.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}