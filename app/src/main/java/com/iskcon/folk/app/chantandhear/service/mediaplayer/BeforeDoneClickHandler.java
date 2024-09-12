package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;
import com.iskcon.folk.app.chantandhear.service.progress.ProgressBarHandler;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;

public class BeforeDoneClickHandler extends AbstractEventHandler {

    public BeforeDoneClickHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {

        MediaPlayer mediaPlayer = getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();

        if (getAppCompatActivity().getHkMantraClickHandler().isHkMahaMantraPlaying()) {

            getAppCompatActivity().getHkMantraClickHandler().pauseMediaPlayer();

            Vibrator vibrator = (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);

            new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage("Are you really sure you have completed your round?").
                    setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CommonUtils.vibrateFunction(50, vibrator);
                            dialogInterface.cancel();
                            getAppCompatActivity().getHkMantraClickHandler().onMalaCompleted(true);
                        }
                    }).setNegativeButton("Resume", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CommonUtils.vibrateFunction(50, vibrator);
                            dialogInterface.cancel();
                            getAppCompatActivity().getHkMantraClickHandler().resumeMediaPlayer();
                        }
                    }).show();
        } else {

            Toast.makeText(getAppCompatActivity(), "Hare Krishna, round has not yet started, start round to mark it as completed.", Toast.LENGTH_SHORT).show();
        }
    }
}