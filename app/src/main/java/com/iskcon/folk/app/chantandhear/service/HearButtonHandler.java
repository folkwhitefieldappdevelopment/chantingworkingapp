package com.iskcon.folk.app.chantandhear.service;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.NamaPrabhuToasts;
import com.iskcon.folk.app.chantandhear.service.beadcount.JapaMalaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HearButtonHandler extends AbstractEventHandler {

    private final TextView levelCountTextView;
    private int levelCountValue = 1;

    public HearButtonHandler(MainActivity appCompatActivity) {
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

        if(super.getAppCompatActivity().getHkMantraClickHandler().isHkMahaMantraPlaying()) {
            getAppCompatActivity().getJapaMalaViewModel().incrementHeardBy(levelCountValue);
        }
        this.sendToast();
    }

    public void handleLevelUp(View view) {
        super.animateAndVibrate(view, 50, 100);
        levelCountValue = Integer.parseInt(levelCountTextView.getText().toString());
        if (levelCountValue < 8) {
            levelCountValue++;
            levelCountTextView.setText(String.valueOf(levelCountValue));
        }
    }

    public void handleLevelDown(View view) {
        super.animateAndVibrate(view, 50, 100);
        levelCountValue = Integer.parseInt(levelCountTextView.getText().toString());
        if (levelCountValue > 1) {
            levelCountValue--;
            levelCountTextView.setText(String.valueOf(levelCountValue));
        }
    }

    private void sendToast() {
        int lowerLimit = 0;
        int upperLimit = 13;
        List<Integer> used = new ArrayList<>();

        View layout = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) super.getAppCompatActivity().findViewById(R.id.custom_toast_layout));
        TextView textHearQuote = layout.findViewById(R.id.text);
        textHearQuote.setText(String.valueOf(NamaPrabhuToasts.TOAST_MSGS[this.generateRandomNumber(lowerLimit, upperLimit, used)]));

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
}