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

    private final String[] SPEED_DROPDOWN_DATA = {"0.25x", "0.5x", "1x", "1.1x", "1.2x", "1.5x", "2x", "4x"};

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
        if (hkMantraClickHandler.isHkMahaMantraPlaying()) {
            MediaPlayer mediaPlayer = super.getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
            if (mediaPlayer != null && mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0) {
                mediaPlayer.pause();
                this.showConfirmationDialog(getMediaplayer());
            } else {
                Toast.makeText(getAppCompatActivity(), "Hare Krishna, Speed cannot be adjusted for Pancha Tattva.", Toast.LENGTH_SHORT).show();
            }
        } else {
            this.showConfirmationDialog(getMediaplayer());
        }
    }

    private void showConfirmationDialog(MediaPlayer mediaPlayer) {
        final Vibrator vibrator = (Vibrator) super.getAppCompatActivity().getSystemService(Context.VIBRATOR_SERVICE);
        final MainActivity mainActivity = (MainActivity) super.getAppCompatActivity();
        final int[] selectedItem = {2}; // Default selected item
        AlertDialog.Builder builder = new AlertDialog.Builder(super.getAppCompatActivity());
        builder.setTitle("Choose Speed")
                .setSingleChoiceItems(SPEED_DROPDOWN_DATA, selectedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.vibrateFunction(50, vibrator);
                        switch (which) {
                            case 0:
                                speed = 0.25f;
                                break;
                            case 1:
                                speed = 0.5f;
                                break;
                            case 2:
                                speed = 1.0f;
                                break;
                            case 3:
                                speed = 1.1f;
                                break;
                            case 4:
                                speed = 1.2f;
                                break;
                            case 5:
                                speed = 1.5f;
                                break;
                            case 6:
                                speed = 2.0f;
                                break;
                            case 7:
                                speed = 4.0f;
                                break;
                        }
                        ((TextView)getAppCompatActivity().findViewById(R.id.speedMenu)).setText(SPEED_DROPDOWN_DATA[which]);
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MediaPlayer mediaPlayer = getAppCompatActivity().getHkMantraClickHandler().getCurrentMediaPlayer();
                        if (mediaPlayer != null && mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0) {
                            getAppCompatActivity().getHkMantraClickHandler().startHkMahaMantraMultipleMediaPlayer();
                        }
                    }
                }).show();
    }
}