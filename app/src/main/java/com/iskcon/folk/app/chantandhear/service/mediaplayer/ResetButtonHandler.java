package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;

public class ResetButtonHandler extends AbstractMediaPlayerEventHandler {


    public ResetButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(View view) {
        super.animateAndVibrate(view, 50, 200);
        final MediaPlayer mediaPlayer = getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
        if (mediaPlayer != null && mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0) {
            mediaPlayer.pause();
            this.showConfirmationDialog(mediaPlayer);
        } else {
            Toast.makeText(getAppCompatActivity(), "Hare Krishna, nothing to reset, round has not yet started.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showConfirmationDialog(MediaPlayer mediaPlayer) {
        Vibrator vibrator = (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);
        MainActivity mainActivity = (MainActivity) super.getAppCompatActivity();
        CommonUtils.showWarningDialog(
                super.getAppCompatActivity(),
                "Hare Krishna",
                "Are you sure to reset!! On your confirmation, current round will be started again freshly.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.vibrateFunction(50, vibrator);
                        mainActivity.getHkMantraClickHandler().destroy();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle Cancel button click
                        mediaPlayer.start();
                        CommonUtils.vibrateFunction(50, vibrator);
                        dialog.dismiss();
                    }
                }
        );
    }
}