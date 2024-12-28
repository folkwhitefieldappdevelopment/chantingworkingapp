package com.iskcon.folk.app.chantandhear.service.videoview;

import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.google.android.material.imageview.ShapeableImageView;
import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VideoViewManager extends AbstractEventHandler {

    public VideoViewManager(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
    }

    public void loadVideo() {

        List<File> videoFiles = new ArrayList<>();

        File baseLocation = new File(this.getAppCompatActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                ".chantAndHear_v1" + File.separator);

        if (baseLocation.exists()) {
            int noOfFiles = baseLocation.list().length;
            for (File file : baseLocation.listFiles()) {
                videoFiles.add(file);
            }

            VideoView videoView = getAppCompatActivity().findViewById(R.id.krishnaVideoView);
            videoView.setVideoPath(this.getNextFilePath(videoFiles));
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView.setVideoPath(getNextFilePath(videoFiles));
                    videoView.start();
                }
            });
        }
        /*videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });*/
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

    public void resumeVideo() {

        ((VideoView) getAppCompatActivity().findViewById(R.id.krishnaVideoView)).start();
    }

    public void stopVideo() {

        ((VideoView) getAppCompatActivity().findViewById(R.id.krishnaVideoView)).stopPlayback();
    }

    private String getNextFilePath(List<File> videoFiles) {
        return videoFiles.get(new Random().nextInt(videoFiles.size())).getPath();
    }
}