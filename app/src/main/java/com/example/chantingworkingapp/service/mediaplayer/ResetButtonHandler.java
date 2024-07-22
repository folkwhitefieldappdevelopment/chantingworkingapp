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

        final MediaPlayer mediaPlayer = super.getMediaplayer();

        mediaPlayer.pause();

        this.showConfirmationDialog(mediaPlayer);
    }

    private void showConfirmationDialog(MediaPlayer mediaPlayer){

        final Vibrator vibrator = (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);

        final MainActivity mainActivity = (MainActivity) super.getAppCompatActivity();

        super.vibrate(50);

        CommonUtils.showWarningDialog(
                super.getAppCompatActivity(),
                "Warning",
                "Are you sure you will lose your Progress for the current round?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.vibrateFunction(50,vibrator);
                        mainActivity.getHkMantraClickHandler().resetButtonFunction();
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
