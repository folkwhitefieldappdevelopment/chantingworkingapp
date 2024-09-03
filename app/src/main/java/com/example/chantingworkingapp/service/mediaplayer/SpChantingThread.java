package com.example.chantingworkingapp.service.mediaplayer;

import android.media.MediaPlayer;
import android.os.Build;
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
import com.example.chantingworkingapp.model.RoundDataModel;
import com.google.android.play.core.integrity.r;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SpChantingThread implements Runnable {

    private final JapaMalaModel japaMalaModel;
    private int currentRoundId;
    private int currentBeadNumber = 1;
    private final MediaPlayer mediaPlayer;

    private final Handler handlerForTimer = new Handler(Looper.getMainLooper());

    public SpChantingThread(JapaMalaModel japaMalaModel, int currentRoundId, MediaPlayer mediaPlayer) {
        this.japaMalaModel = japaMalaModel;
        this.currentRoundId = currentRoundId;
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void run() {
        int mediaCurrentPosition = mediaPlayer.getCurrentPosition();

        Log.e(this.getClass().getSimpleName(), MessageFormat.format("{0}:{1}", String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toMinutes(mediaCurrentPosition), String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toSeconds(mediaCurrentPosition)))));
        RoundDataModel roundDataModel = null;

        if (japaMalaModel.getRoundDataModels() == null) {
            japaMalaModel.setRoundDataModels(new ArrayList<>());
        } else {
            roundDataModel = japaMalaModel.getRoundDataModels().get(currentRoundId - 1);
        }

        if (roundDataModel == null) {
            roundDataModel = new RoundDataModel();
            roundDataModel.setRoundId(currentRoundId);
            roundDataModel.setStartTime(new Date());
            roundDataModel.setRoundMilestoneDataModelMap(new HashMap<>());
        }

        if (currentBeadNumber != 108) {
            currentBeadNumber++;
        }
    }

    public JapaMalaModel getJapaMalaModel() {
        return japaMalaModel;
    }
}