package com.iskcon.folk.app.chantandhear.service;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HareKrishnaMantraTextManager {

    private final List<String> HARE_KRISHNA_MAHA_MANTRA =
            Arrays.asList("Hare", "Krishna", "Hare", "Krishna\n", "Krishna", "Krishna", "Hare", "Hare\n", "Hare",
                    "Rama", "Hare", "Rama\n", "Rama", "Rama", "Hare", "Hare");
    private final float PROPORTION = 1.5f;
    private Timer timer;
    private final TextView textView;
    private final StringBuffer DISPLAYED_MANTRA_BUFFER = new StringBuffer();
    private int i = 1;

    public HareKrishnaMantraTextManager(TextView textView) {
        this.textView = textView;
    }

    public void initState(TextView text) {
        //text.setTextColor(Color.parseColor("#666666"));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
                String.join(" ", HARE_KRISHNA_MAHA_MANTRA));
        /*spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#F1E8EB")), 0, 5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(PROPORTION), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        text.setText(spannableStringBuilder);
        DISPLAYED_MANTRA_BUFFER.append(HARE_KRISHNA_MAHA_MANTRA.get(0));
        //this.registerTimer();
    }

    public void nextState(TextView text, int startPosition, int endPosition, int i) {
        Log.i("nextState", "<<<<<<<<<<<<<<<<<<<<<<<<<<<<< nextState >>>>>>>>>>>>>>>>>>>>>>>>>>" +
                startPosition + ":" + endPosition + ":" + i);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(String.join("\b", HARE_KRISHNA_MAHA_MANTRA));
        this.reverse(spannableStringBuilder, startPosition, endPosition);
        this.forward(spannableStringBuilder, startPosition, endPosition);
        text.setText(spannableStringBuilder);
        DISPLAYED_MANTRA_BUFFER.append(HARE_KRISHNA_MAHA_MANTRA.get(i));
    }

    private void reverse(SpannableStringBuilder spannableStringBuilder, int startPosition, int endPosition) {
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, startPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, startPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(0.0f), 0, startPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void forward(SpannableStringBuilder spannableStringBuilder, int startPosition, int endPosition) {
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), startPosition, endPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#F1E8EB")), startPosition, endPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(PROPORTION), startPosition, endPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void registerTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int displayedMantraLength = DISPLAYED_MANTRA_BUFFER.length() + 1;
                nextState(textView, displayedMantraLength, displayedMantraLength + HARE_KRISHNA_MAHA_MANTRA.get(i).length(), i);
                i = i + 1;
            }
        }, 300, 3000);
    }

    private void reinitializeTimer() {
        timer = new Timer();
    }
}