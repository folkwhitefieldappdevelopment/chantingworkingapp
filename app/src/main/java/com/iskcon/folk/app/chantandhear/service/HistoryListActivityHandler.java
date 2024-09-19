package com.iskcon.folk.app.chantandhear.service;

import android.view.View;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HistoryListActivityHandler extends AbstractEventHandler {
    public HistoryListActivityHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
        List<RoundDataEntity> roundDataEntities = new ChantingDataDao().get(new Date(), super.getAppCompatActivity().getUserDetails().getEmailId());
        if (roundDataEntities != null && !roundDataEntities.isEmpty()) {
            roundDataEntities.forEach(roundDataEntity -> {
                View historyView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.history_list_row_view_activity, null);
                ((TextView) historyView.findViewById(R.id.roundIdTextView)).setText(roundDataEntity.getRoundNumber());
                ((TextView) historyView.findViewById(R.id.heardCountTextView)).setText(roundDataEntity.getTotalHeardCount());
                ((TextView) historyView.findViewById(R.id.timeTakenTextView)).setText(String.format(Locale.ENGLISH, "0%d:%02d min", TimeUnit.MILLISECONDS.toMinutes(roundDataEntity.getTimeTaken()) % 60, TimeUnit.MILLISECONDS.toSeconds(roundDataEntity.getTimeTaken()) % 60));
            });
        }
    }
}