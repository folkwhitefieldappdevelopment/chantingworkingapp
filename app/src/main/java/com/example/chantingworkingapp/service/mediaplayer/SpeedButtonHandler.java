package com.example.chantingworkingapp.service.mediaplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.util.CommonUtils;

public class SpeedButtonHandler extends AbstractMediaPlayerEventHandler {

    private String speed = "1.0f";

    public String getSpeed() {
        return speed;
    }

    public SpeedButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {
        super.animateAndVibrate(view,50,200);
        MediaPlayer mediaPlayer = super.getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
        mediaPlayer.pause();
        this.showConfirmationDialog(getMediaplayer());
    }

    private void showConfirmationDialog(MediaPlayer mediaPlayer) {
        final Vibrator vibrator = (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);
        final MainActivity mainActivity = (MainActivity) super.getAppCompatActivity();
        final String[] speeds = {"1x", "1.5x", "2x"};
        final int[] selectedItem = {0}; // Default selected item
        AlertDialog.Builder builder = new AlertDialog.Builder(super.getAppCompatActivity());
        builder.setTitle("Choose Speed")
                .setSingleChoiceItems(speeds, selectedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedItem[0] = which;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.vibrateFunction(50, vibrator);
                        //mainActivity.resetButtonFunction();
                        // Handle the selection

                        switch (speed) {
                            case "0":
                                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.0f));
                                speed = "1.0f";
                                break;
                            case "1":
                                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.5f));
                                speed = "1.5f";
                                break;
                            case "2":
                                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(2.0f));
                                speed = "2.0f";
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.start();
                        CommonUtils.vibrateFunction(50, vibrator);
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
