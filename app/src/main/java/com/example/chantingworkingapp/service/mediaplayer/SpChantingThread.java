package com.example.chantingworkingapp.service.mediaplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.constant.Milestone;
import com.example.chantingworkingapp.model.JapaMalaModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SpChantingThread extends AbstractMediaPlayerEventHandler implements Runnable{
    private JapaMalaModel japaMalaModel;
    private  SpeedButtonHandler speedButtonHandler;
    private TextView timerTextforTheMantra = null, chantingText = null;
    boolean varForCheckingIfMantraStoppedInBetween ;
    private final Handler handlerForTimer = new Handler(Looper.getMainLooper());
    private long timeInMsForTimer = 0;
    private long startingTimeForTimerRunnable = 0;
    private long timeBuffForTimer = 0;
    private long updatingTimeForTimerRunnable = 0;
    private int secondsForTimer;
    private int minutesForTimer;
    private boolean start = true;
    private ProgressBar progressBar16;
    private Milestone milestone;
    public SpChantingThread(MainActivity appCompatActivity, JapaMalaModel japaMalaModel, Boolean start, MediaPlayer mediaPlayer) {
        super(appCompatActivity,mediaPlayer);
        this.japaMalaModel = japaMalaModel;
        this.start = true;
        timerTextforTheMantra = super.getAppCompatActivity().findViewById(R.id.timetext);
        chantingText = super.getAppCompatActivity().findViewById(R.id.chanting_text);
        progressBar16 = super.getAppCompatActivity().findViewById(R.id.progress_bar_1);
        speedButtonHandler = new SpeedButtonHandler(appCompatActivity,mediaPlayer);
    }

    @Override
    public void run() {
        if(japaMalaModel.getJapaMalaRoundDataModels() == null){
            japaMalaModel.setJapaMalaRoundDataModels(new ArrayList<>());
        }

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

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {
        run();


    }

    private void runSpChanting() {

            float spSpeed = Float.parseFloat(speedButtonHandler.getSpeed());


            int waitTimeForChanting = 0;
            int spChantingCount = Integer.parseInt(chantingText.getText().toString());
            if( spChantingCount <108){
                waitTimeForChanting = 4030;
                spChantingCount ++;
                chantingText.setText(String.valueOf(spChantingCount));


            }


    }
}
