package com.example.chantingworkingapp.service.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.util.CommonUtils;

import java.text.MessageFormat;
import java.util.Locale;

public class HkMantraClickHandler extends AbstractMediaPlayerEventHandler{

    private ImageView startButtonImage = null;
    private TextView timerTextforTheMantra = null;
    boolean varForCheckingIfMantraStoppedInBetween ;
    private final Handler handlerForTimer = new Handler(Looper.getMainLooper());
    private long timeInMsForTimer = 0;
    private long startingTimeForTimerRunnable = 0;
    private long timeBuffForTimer = 0;
    private long updatingTimeForTimerRunnable = 0;
    private int secondsForTimer;
    private int minutesForTimer;
    private int timeToStopTimerForChangingText;
    private int hearingCountTextOfUser;
    private int chantingCountTextOfSP;

    public HkMantraClickHandler(MainActivity appCompatActivity, MediaPlayer mediaplayer) {
        super(appCompatActivity, mediaplayer);
        this.startButtonImage = super.getAppCompatActivity().findViewById(R.id.startIconImageView);
        this.timerTextforTheMantra = super.getAppCompatActivity().findViewById(R.id.timetext);
    }

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {
        if(startButtonImage.getVisibility() == View.VISIBLE){ //Resume
            startButtonFunction(view);
        } else if(startButtonImage.getVisibility() == View.INVISIBLE){ //Pause
            pauseButtonFunction(view);
        }
    }

    //Start Button Function
    private void startButtonFunction(View view) {
        MediaPlayer mediaPlayer = super.getMediaplayer();
        startButtonImage.setVisibility(View.INVISIBLE);
        startingTimeForTimerRunnable = SystemClock.uptimeMillis();
        super.vibrate(50);
        handlerForTimer.postDelayed(runnableForTimer, 0);
        if (mediaPlayer != null && mediaPlayer.getCurrentPosition() == 0 && !varForCheckingIfMantraStoppedInBetween) {
            mediaPlayer.setPlaybackParams(super.getMediaplayer().getPlaybackParams().setSpeed(1));
            mediaPlayer.start();
            timeToStopTimerForChangingText = 4030;
            Log.e("MainActivity", "Successfully started MediaPlayer");
            handlerForTimer.postDelayed(runnableForTimer, (long) (6435));
        } else if (varForCheckingIfMantraStoppedInBetween) {
            timeToStopTimerForChangingText = 4030;
            //updateStartButtonText();
            super.getMediaplayer().start();
            Log.e("MainActivity", "Failed to initialize MediaPlayer");
        }
    }

    //Pause Button Function
    private void pauseButtonFunction(View view) {
        MediaPlayer mediaPlayer = super.getMediaplayer();
        startButtonImage.setVisibility(View.VISIBLE);
        handlerForTimer.removeCallbacks(runnableForTimer);
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
    public void resetButtonFunction() {
        MediaPlayer mediaPlayer = super.getMediaplayer();
        startButtonImage.setVisibility(View.VISIBLE);
        handlerForTimer.removeCallbacks(runnableForTimer);
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

    //Runnable
    private final Runnable runnableForTimer = new Runnable() {
        @Override
        public void run() {
            timeInMsForTimer = SystemClock.uptimeMillis() - startingTimeForTimerRunnable;
            updatingTimeForTimerRunnable = timeBuffForTimer + timeInMsForTimer;
            secondsForTimer = (int) (updatingTimeForTimerRunnable / 1000);
            minutesForTimer = secondsForTimer / 60;
            secondsForTimer = secondsForTimer % 60;
            // Update UI
            timerTextforTheMantra.setText(MessageFormat.format("{0}:{1}", String.format(Locale.getDefault(), "%02d", minutesForTimer), String.format(Locale.getDefault(), "%02d", secondsForTimer)));
            // Schedule the next update
            handlerForTimer.postDelayed(this, 0);
        }
    };
}
