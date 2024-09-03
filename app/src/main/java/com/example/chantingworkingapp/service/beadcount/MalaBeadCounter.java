package com.example.chantingworkingapp.service.beadcount;

import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.constant.ApplicationConstants;
import com.example.chantingworkingapp.service.mediaplayer.HkMantraClickHandler;

public class MalaBeadCounter extends CountDownTimer {

    private final JapaMalaViewModel japaMalaViewModel;
    private final HkMantraClickHandler hkMantraClickHandler;

    public MalaBeadCounter(long millisInFuture, long countDownInterval, JapaMalaViewModel japaMalaViewModel, HkMantraClickHandler hkMantraClickHandler) {
        super(millisInFuture, countDownInterval);
        this.japaMalaViewModel = japaMalaViewModel;
        this.hkMantraClickHandler = hkMantraClickHandler;
    }

    @Override
    public void onTick(long l) {
        Integer beadCount = japaMalaViewModel.getBeadCounterLiveData().getValue();
        if (beadCount != null && beadCount < ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class)) {
            japaMalaViewModel.incrementBead();
        }
    }

    @Override
    public void onFinish() {
        hkMantraClickHandler.onMalaCompleted();
    }

    public JapaMalaViewModel getJapaMalaViewModel() {
        return japaMalaViewModel;
    }
}