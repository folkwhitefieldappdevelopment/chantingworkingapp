package com.example.chantingworkingapp.service.mediaplayer;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.util.CommonUtils;

public class ResetButtonHandler extends AbstractMediaPlayerEventHandler {


    public ResetButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {
        super.animateAndVibrate(view,50,200);
        final MediaPlayer mediaPlayer = getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
        mediaPlayer.pause();
        this.showConfirmationDialog(mediaPlayer);
    }

    private void showConfirmationDialog(MediaPlayer mediaPlayer){
        Vibrator vibrator = (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);
        MainActivity mainActivity = (MainActivity) super.getAppCompatActivity();
        CommonUtils.showWarningDialog(
                super.getAppCompatActivity(),
                "Hare Krishna",
                "Are you sure to reset!! On your confirmation, current round will be started again freshly.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.vibrateFunction(50,vibrator);
                        //mainActivity.resetButtonFunction();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle Cancel button click
                        mediaPlayer.start();
                        CommonUtils.vibrateFunction(50,vibrator);
                        dialog.dismiss();
                    }
                }
        );
    }
}
