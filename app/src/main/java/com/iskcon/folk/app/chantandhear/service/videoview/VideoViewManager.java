package com.iskcon.folk.app.chantandhear.service.videoview;

import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.google.android.material.imageview.ShapeableImageView;
import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;

import java.io.File;

public class VideoViewManager extends AbstractEventHandler {

    public VideoViewManager(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
    }

    public void loadVideo() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ".chantAndHear_v1" + File.separator + "sample_kirshna_images.mp4");
        VideoView videoView = getAppCompatActivity().findViewById(R.id.krishnaVideoView);
        videoView.setVideoPath(file.getPath());
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    public void startVideo(long flipInterval) {
        ((ShapeableImageView) getAppCompatActivity().findViewById(R.id.spMainActivityImage)).setVisibility(ViewFlipper.INVISIBLE);
        VideoView videoView = getAppCompatActivity().findViewById(R.id.krishnaVideoView);
        videoView.setVisibility(View.VISIBLE);
        videoView.start();
    }

    public void pauseVideo() {
        ((VideoView) getAppCompatActivity().findViewById(R.id.krishnaVideoView)).pause();
    }

    public void reVideo() {
        ((VideoView) getAppCompatActivity().findViewById(R.id.krishnaVideoView)).resume();
    }

    public void stopVideo() {
        ((VideoView) getAppCompatActivity().findViewById(R.id.krishnaVideoView)).stopPlayback();
    }
}