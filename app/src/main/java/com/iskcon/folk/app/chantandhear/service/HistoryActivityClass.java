package com.iskcon.folk.app.chantandhear.service;

import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;

public class HistoryActivityClass extends AbstractEventHandler {
    public HistoryActivityClass(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
        // call fire base by passing user id
        for (int i = 0; i < 16; i++) {
            View historyView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.activity_cardview_today, null);
            ((TextView) historyView.findViewById(R.id.roundIdTextView)).setText("01");
            ((TextView) historyView.findViewById(R.id.heardCountTextView)).setText("001");
            ((TextView) historyView.findViewById(R.id.timeTakenTextView)).setText("01:60min");
        }
    }
}
