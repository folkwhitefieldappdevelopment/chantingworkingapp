package com.example.chantingworkingapp.service.mediaplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.model.JapaMalaModel;

import java.text.MessageFormat;
import java.util.Locale;

public class HkMantraClickHandler extends AbstractMediaPlayerEventHandler{

    private ImageView startButtonImage = null;
    private TextView timerTextforTheMantra = null;
    private SpeedButtonHandler speedButton;
    boolean varForCheckingIfMantraStoppedInBetween ;
    private final Handler handlerForTimer = new Handler(Looper.getMainLooper());
    private long timeInMsForTimer = 0;
    private long startingTimeForTimerRunnable = 0;
    private long timeBuffForTimer = 0;
    private long updatingTimeForTimerRunnable = 0;
    private int secondsForTimer;
    private int minutesForTimer;
    private SpChantingThread spChantingThread;
    private int timeToStopTimerForChangingText;
    private int hearingCountTextOfUser;
    private int chantingCountTextOfSP;
    private boolean start;

    public HkMantraClickHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
        this.startButtonImage = super.getAppCompatActivity().findViewById(R.id.startIconImageView);
        this.timerTextforTheMantra = super.getAppCompatActivity().findViewById(R.id.timetext);
        speedButton = new SpeedButtonHandler(appCompatActivity,mediaplayer);

    }

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {
        spChantingThread = new SpChantingThread(getAppCompatActivity(),japaMalaModel, start,super.getMediaplayer());
        if(startButtonImage.getVisibility() == View.VISIBLE){ //Resume
            startButtonFunction(view,spChantingThread);
        } else if(startButtonImage.getVisibility() == View.INVISIBLE){ //Pause
            pauseButtonFunction(view,spChantingThread);
        }
    }

    //Start Button Function
    private void startButtonFunction(View view,SpChantingThread spChantingThread) {
        MediaPlayer mediaPlayer = super.getMediaplayer();
        startButtonImage.setVisibility(View.INVISIBLE);
        startingTimeForTimerRunnable = SystemClock.uptimeMillis();
        super.vibrate(50);
        handlerForTimer.postDelayed(spChantingThread, 0);
        if (mediaPlayer != null && mediaPlayer.getCurrentPosition() == 0 && !varForCheckingIfMantraStoppedInBetween) {
            mediaPlayer.setPlaybackParams(super.getMediaplayer().getPlaybackParams().setSpeed(Float.parseFloat(speedButton.getSpeed())));
            mediaPlayer.start();
            timeToStopTimerForChangingText = 4030;
            Log.e("MainActivity", "Successfully started MediaPlayer");
            handlerForTimer.postDelayed(spChantingThread, (long) (6435));
        } else if (varForCheckingIfMantraStoppedInBetween) {
            timeToStopTimerForChangingText = 4030;
            //updateStartButtonText();
            super.getMediaplayer().start();
            Log.e("MainActivity", "Failed to initialize MediaPlayer");
        }
    }

    //Pause Button Function
    private void pauseButtonFunction(View view,SpChantingThread spChantingThread) {
        MediaPlayer mediaPlayer = super.getMediaplayer();
        startButtonImage.setVisibility(View.VISIBLE);
        handlerForTimer.removeCallbacks(spChantingThread);
        timeBuffForTimer += timeInMsForTimer;
        super.vibrate(50);
        if (mediaPlayer.getCurrentPosition() < 6435 && !varForCheckingIfMantraStoppedInBetween) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        } else {
            varForCheckingIfMantraStoppedInBetween = true;
            mediaPlayer.release();
            super.setMediaplayer(MediaPlayer.create(super.getAppCompatActivity(), R.raw.hkm));
            Log.e("MainActivity", "Successfully hkm started MediaPlayer");
        }
    }
    //Reset Button function
    public void resetButtonFunction(SpChantingThread spChantingThread) {
        MediaPlayer mediaPlayer = super.getMediaplayer();
        startButtonImage.setVisibility(View.VISIBLE);
        handlerForTimer.removeCallbacks(spChantingThread);
        timeBuffForTimer += timeInMsForTimer;
        super.vibrate(50);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            super.setMediaplayer(MediaPlayer.create(super.getAppCompatActivity(), R.raw.sp_hkm));
        }
        timeInMsForTimer = 0L;
        startingTimeForTimerRunnable = 0L;
        timeBuffForTimer = 0L;
        timerTextforTheMantra.setText("00:00");
    }
}
