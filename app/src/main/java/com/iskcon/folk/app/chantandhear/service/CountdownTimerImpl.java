package com.iskcon.folk.app.chantandhear.service;

import android.os.CountDownTimer;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class CountdownTimerImpl extends CountDownTimer {

    private long timeElapsed = 0;

    public CountdownTimerImpl(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }
}