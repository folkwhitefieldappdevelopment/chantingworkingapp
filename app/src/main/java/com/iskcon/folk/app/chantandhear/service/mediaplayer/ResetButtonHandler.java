package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;
import com.iskcon.folk.app.chantandhear.util.OpenAlertDialogRqModel;

public class ResetButtonHandler extends AbstractMediaPlayerEventHandler {


    public ResetButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(View view) {
        super.animateAndVibrate(view, 50, 200);
        MediaPlayer mediaPlayer =
                getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
        if (mediaPlayer != null && mediaPlayer.isPlaying() &&
                mediaPlayer.getCurrentPosition() > 0) {
            getAppCompatActivity().getHkMantraClickHandler().pauseMediaPlayer();
            this.showConfirmationDialog(mediaPlayer);
        } else {
            Toast.makeText(getAppCompatActivity(),
                    "Hare Krishna, nothing to reset, round has not yet started.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showConfirmationDialog(MediaPlayer mediaPlayer) {
        Vibrator vibrator =
                (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);
        MainActivity mainActivity = (MainActivity) super.getAppCompatActivity();


        DialogInterface.OnClickListener positiveClickHandler =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CommonUtils.vibrateFunction(50, vibrator);
                        dialogInterface.cancel();
                        getAppCompatActivity().getHkMantraClickHandler().resetActivity();
                        getAppCompatActivity().getProgressBarHandler()
                                .setAttentiveVideoShown(false);
                    }
                };
        DialogInterface.OnClickListener negativeClickHandler =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CommonUtils.vibrateFunction(50, vibrator);
                        getAppCompatActivity().getHkMantraClickHandler().resumeMediaPlayer();
                        CommonUtils.vibrateFunction(50, vibrator);
                        dialogInterface.cancel();
                    }
                };

        CommonUtils.showDialog(getAppCompatActivity(), new OpenAlertDialogRqModel(
                "Are you sure you want to reset!! On your confirmation, current round will be started again freshly.?",
                "Reset", positiveClickHandler, "Cancel", negativeClickHandler));
    }
}