package com.iskcon.folk.app.chantandhear.service.beadcount;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JapaMalaViewModel extends ViewModel {

    private MutableLiveData<Integer> beadCounterLiveData;

    private MutableLiveData<Integer> roundNumberLiveData;

    private MutableLiveData<Integer> heardCounterLiveData;

    private final int INITIAL_BEAD_VALUE = 0;

    private int initialRoundNoValue = 1;

    private final int INITIAL_HEARD_VALUE = 0;

    public MutableLiveData<Integer> getBeadCounterLiveData() {
        if (beadCounterLiveData == null) {
            beadCounterLiveData = new MutableLiveData<>(INITIAL_BEAD_VALUE);
        }
        return beadCounterLiveData;
    }

    public MutableLiveData<Integer> getRoundNumberLiveData() {
        if (roundNumberLiveData == null) {
            roundNumberLiveData = new MutableLiveData<>(initialRoundNoValue);
        }
        return roundNumberLiveData;
    }

    public MutableLiveData<Integer> getHeardCounterLiveData() {
        if (heardCounterLiveData == null) {
            heardCounterLiveData = new MutableLiveData<>(INITIAL_HEARD_VALUE);
        }
        return heardCounterLiveData;
    }

    public void incrementBead() {
        if (beadCounterLiveData != null && beadCounterLiveData.isInitialized()) {
            int incrementBead = beadCounterLiveData.getValue() + 1;
            beadCounterLiveData.setValue(incrementBead);
            beadCounterLiveData.postValue(incrementBead);
        } else {
            beadCounterLiveData = new MutableLiveData<>(INITIAL_BEAD_VALUE);
        }
    }

    public void incrementRound() {
        if (roundNumberLiveData != null && roundNumberLiveData.isInitialized()) {
            int incrementBead = roundNumberLiveData.getValue() + 1;
            roundNumberLiveData.setValue(incrementBead);
            roundNumberLiveData.postValue(incrementBead);
        } else {
            roundNumberLiveData = new MutableLiveData<>(initialRoundNoValue);
        }
    }

    public void incrementHeardBy(int incrementBy) {
        if (heardCounterLiveData != null && heardCounterLiveData.isInitialized()) {
            int incrementHeardCount = heardCounterLiveData.getValue() + incrementBy;
            heardCounterLiveData.setValue(incrementHeardCount);
            heardCounterLiveData.postValue(incrementHeardCount);
        } else {
            heardCounterLiveData = new MutableLiveData<>(INITIAL_HEARD_VALUE);
        }
    }

    public void decrementBead() {
        if (beadCounterLiveData != null && beadCounterLiveData.isInitialized()) {
            int currentBeadCount = beadCounterLiveData.getValue();
            if (currentBeadCount != 1) {
                beadCounterLiveData.setValue(currentBeadCount - 1);
                beadCounterLiveData.postValue(currentBeadCount - 1);
            }
        } else {
            beadCounterLiveData = new MutableLiveData<>(INITIAL_BEAD_VALUE);
        }
    }

    public void resetBead() {
        if (beadCounterLiveData != null && beadCounterLiveData.isInitialized()) {
            beadCounterLiveData.setValue(INITIAL_BEAD_VALUE);
            beadCounterLiveData.postValue(INITIAL_BEAD_VALUE);
        } else {
            beadCounterLiveData = new MutableLiveData<>(INITIAL_BEAD_VALUE);
        }
    }

    public void resetHeard() {
        if (heardCounterLiveData != null && heardCounterLiveData.isInitialized()) {
            heardCounterLiveData.setValue(INITIAL_HEARD_VALUE);
            heardCounterLiveData.postValue(INITIAL_HEARD_VALUE);
        } else {
            heardCounterLiveData = new MutableLiveData<>(INITIAL_HEARD_VALUE);
        }
    }

    public int getInitialRoundNoValue() {
        return initialRoundNoValue;
    }

    public void setInitialRoundNoValue(int initialRoundNoValue) {
        this.initialRoundNoValue = initialRoundNoValue;
    }
}