package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;

public class SpeedButtonHandler extends AbstractMediaPlayerEventHandler {

    private float speed = 1.0f;

    private int speedIndex;

    private final String[] SPEED_DROPDOWN_DATA = {"0.25x", "0.5x", "0.75x", "Normal", "1.2x", "1.25x", "1.3x", "1.4x", "1.5x", "1.75x"};

    public float getSpeed() {
        return speed;
    }

    public SpeedButtonHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
    }

    @Override
    public void handle(View view) {
        super.animateAndVibrate(view, 50, 200);
        HkMantraClickHandler hkMantraClickHandler = super.getAppCompatActivity().getHkMantraClickHandler();
        MediaPlayer mediaPlayer = super.getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
        if (mediaPlayer != null && mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0) {
            hkMantraClickHandler.pauseMediaPlayer();
        }
        this.showConfirmationDialog();
    }

    private void showConfirmationDialog() {
        final Vibrator vibrator = (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);
        final MainActivity mainActivity = (MainActivity) super.getAppCompatActivity();
        final int selectedItem = speedIndex != 0 ? speedIndex : 3; // Default selected item
        AlertDialog.Builder builder = new AlertDialog.Builder(super.getAppCompatActivity());
        builder.setTitle("Choose Speed")
                .setSingleChoiceItems(SPEED_DROPDOWN_DATA, selectedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.vibrateFunction(50, vibrator);
                        speedIndex = which;
                        switch (which) {
                            case 0:
                                speed = 0.25f;
                                break;
                            case 1:
                                speed = 0.5f;
                                break;
                            case 2:
                                speed = 0.75f;
                                break;
                            case 3:
                                speed = 1.0f;
                                break;
                            case 4:
                                speed = 1.2f;
                                break;
                            case 5:
                                speed = 1.25f;
                                break;
                            case 6:
                                speed = 1.3f;
                                break;
                            case 7:
                                speed = 1.4f;
                                break;
                            case 8:
                                speed = 1.5f;
                                break;
                            case 9:
                                speed = 1.75f;
                                break;
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((TextView) getAppCompatActivity().findViewById(R.id.speedMenu)).setText(SPEED_DROPDOWN_DATA[speedIndex]);
                        getAppCompatActivity().getHkMantraClickHandler().resumeMediaPlayer();
                    }
                }).show();
    }
}