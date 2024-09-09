package com.iskcon.folk.app.chantandhear.service.beadcount;

import android.os.CountDownTimer;
import android.util.Log;

import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.HkMantraClickHandler;

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
            Log.i(this.getClass().getSimpleName(), "onTick: "+japaMalaViewModel.getBeadCounterLiveData().getValue());
        } else {
            this.onFinish();
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