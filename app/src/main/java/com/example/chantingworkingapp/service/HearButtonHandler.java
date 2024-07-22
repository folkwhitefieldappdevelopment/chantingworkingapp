package com.example.chantingworkingapp.service;

import android.content.Context;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.constant.NamaPrabhuToasts;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HearButtonHandler extends AbstractEventHandler {

    public HearButtonHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {

        //TextView textView = super.getAppCompatActivity().findViewById();
        this.incrementHeardCount(japaMalaModel);
        this.sendToast(view);
    }

    private void incrementHeardCount(JapaMalaModel japaMalaModel){

        japaMalaModel.getJapaMapaRoundDataModels();
    }

    private void sendToast(View view) {
        int lowerLimit = 0;
        int upperLimit = 13;
        List<Integer> used = new ArrayList<>();

        View layout = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.custom_toast,(ViewGroup) super.getAppCompatActivity().findViewById(R.id.custom_toast_layout));
        TextView textHearQuote = layout.findViewById(R.id.text);
        textHearQuote.setText(String.valueOf(NamaPrabhuToasts.TOAST_MSGS[this.generateRandomNumber(lowerLimit,upperLimit,used)]));

        // Create and show the custom Toast
        Toast toast = new Toast(super.getAppCompatActivity().getApplicationContext());
        toast.setGravity(Gravity.START | Gravity.CENTER, 1100, 400);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        super.vibrate(50);
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