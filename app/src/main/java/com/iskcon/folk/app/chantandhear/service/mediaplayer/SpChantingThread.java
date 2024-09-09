package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.model.JapaMalaModel;
import com.iskcon.folk.app.chantandhear.model.RoundDataModel;

import java.text.MessageFormat;
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