package com.iskcon.folk.app.chantandhear.service;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.constant.NamaPrabhuToasts;
import com.iskcon.folk.app.chantandhear.service.beadcount.JapaMalaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HeardButtonHandler extends AbstractEventHandler {

    private final TextView levelCountTextView;
    private int levelCountValue = ApplicationConstants.HEARING_LEVEL_DEFAULT_VALUE.getConstantValue(Integer.class);
    private Date lastHeard = new Date();

    public HeardButtonHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
        levelCountTextView = getAppCompatActivity().findViewById(R.id.levelCount);
    }

    @Override
    public void handle(View view) {
        super.vibrate(50);
        FloatingActionButton floatingActionButton = super.getAppCompatActivity().findViewById(R.id.hearButton);
        floatingActionButton.animate().setDuration(100).scaleX(1.1f).scaleY(1.1f).withEndAction(() ->
                floatingActionButton.animate().setDuration(100).scaleX(1.0f).scaleY(1.0f));

        JapaMalaViewModel japaMalaViewModel = getAppCompatActivity().getJapaMalaViewModel();

        if (super.getAppCompatActivity().getHkMantraClickHandler().isHkMahaMantraPlaying() &&
                japaMalaViewModel.getHeardCounterLiveData().getValue() <
                        ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class)) {

            if (!this.isThisFakeHearing(japaMalaViewModel.getHeardCounterLiveData().getValue())) {
                japaMalaViewModel.incrementHeardBy(levelCountValue);
                super.getAppCompatActivity().getProgressBarHandler()
                        .incrementProgress(japaMalaViewModel.getHeardCounterLiveData().getValue());
            }
        }
        //this.showToast();
    }

    public void handleLevelUp(View view) {
        if (view != null) {
            super.animateAndVibrate(view, 50, 100);
        }
        levelCountValue = Integer.parseInt(levelCountTextView.getText().toString());
        if (levelCountValue < 8) {
            levelCountValue++;
            levelCountTextView.setText(String.valueOf(levelCountValue));
        } else {
            Toast.makeText(getAppCompatActivity(), "Maximum level reached.", Toast.LENGTH_SHORT).show();
        }
    }

    public void handleLevelDown(View view) {
        super.animateAndVibrate(view, 50, 100);
        levelCountValue = Integer.parseInt(levelCountTextView.getText().toString());
        if (levelCountValue > 1) {
            levelCountValue--;
            levelCountTextView.setText(String.valueOf(levelCountValue));
        } else {
            Toast.makeText(getAppCompatActivity(), "Minimum level reached.", Toast.LENGTH_SHORT).show();
        }
    }

    public void animateLevelChange() {
        getAppCompatActivity().findViewById(R.id.hearLevelChangeLinearLayout)
                .startAnimation(
                        AnimationUtils.loadAnimation(
                                getAppCompatActivity(),
                                R.anim.blink_5_times)
                );
    }

    private void showToast() {
        int lowerLimit = 0;
        int upperLimit = 13;
        List<Integer> used = new ArrayList<>();

        View layout = super.getAppCompatActivity().getLayoutInflater()
                .inflate(R.layout.custom_toast, (ViewGroup) super.getAppCompatActivity().findViewById(R.id.custom_toast_layout));
        TextView textHearQuote = layout.findViewById(R.id.text);
        textHearQuote.setText(
                String.valueOf(NamaPrabhuToasts.TOAST_MSGS[this.generateRandomNumber(lowerLimit, upperLimit, used)]));

        // Create and show the custom Toast
        Toast toast = new Toast(super.getAppCompatActivity().getApplicationContext());
        toast.setGravity(Gravity.START | Gravity.CENTER, 1100, 400);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private int generateRandomNumber(int lowerLimit, int upperLimit, List<Integer> usedNumbers) {
        if (lowerLimit > upperLimit) {
            throw new IllegalArgumentException("Lower limit should be less than or equal to upper limit");
        }
        List<Integer> availableNumbers = new ArrayList<>();
        for (int i = lowerLimit; i <= upperLimit; i++) {
            if (!usedNumbers.contains(i)) {
                availableNumbers.add(i);
            }
        }
        Random random = new Random();
        if (availableNumbers.isEmpty()) {
            throw new IllegalStateException("All numbers in the range have been used");
        }
        int randomIndex = random.nextInt(availableNumbers.size());
        int randomNumber = availableNumbers.get(randomIndex);
        usedNumbers.add(randomNumber);
        return randomNumber;
    }

    public int getLevelCountValue() {
        return levelCountValue;
    }

    private boolean isThisFakeHearing(int currentHeardCount) {

        boolean isThisFakeHearing = false;

        if (currentHeardCount == 0) {

            this.lastHeard = new Date();

        } else {

            Date currentDate = new Date();

            long heardTimeDifference = TimeUnit.MILLISECONDS.toMillis(currentDate.getTime() - this.lastHeard.getTime());

            if (heardTimeDifference > ApplicationConstants.HARE_KRISHNA_MANTRA_SINGLE_BEAD_DURATION.getConstantValue(Long.class) /
                    super.getAppCompatActivity().getSpeedClickHandler().getSpeed()) {

                this.lastHeard = currentDate;

            } else {

                Toast.makeText(getAppCompatActivity(),
                                "Haribol, this hearing won't be counted, hear and then tap. Do Prompt hearing.", Toast.LENGTH_SHORT)
                        .show();

                isThisFakeHearing = true;
            }
        }

        return isThisFakeHearing;
    }

    public void setLevelCountValue(int levelCountValue) {
        this.levelCountValue = levelCountValue;
        ((TextView)super.getAppCompatActivity().findViewById(R.id.levelCount)).setText(String.valueOf(levelCountValue));
    }
}