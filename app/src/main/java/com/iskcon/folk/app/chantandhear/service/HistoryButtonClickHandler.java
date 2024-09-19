package com.iskcon.folk.app.chantandhear.service;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HistoryButtonClickHandler extends AbstractEventHandler {

    public HistoryButtonClickHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getAppCompatActivity());

        //LinearLayout historyListViewLinearLayout = super.getAppCompatActivity().findViewById(R.id.historyListViewLinearLayout);

        //List<RoundDataEntity> roundDataEntities = new ChantingDataDao().get(new Date(), super.getAppCompatActivity().getUserDetails().getEmailId());

        List<RoundDataEntity> roundDataEntities = new ArrayList<>();
        RoundDataEntity roundDataEntity1 = new RoundDataEntity();
        roundDataEntity1.setRoundNumber(1);
        roundDataEntity1.setTimeTaken(56988);
        roundDataEntity1.setTotalHeardCount(45);

        RoundDataEntity roundDataEntity2 = new RoundDataEntity();
        roundDataEntity2.setRoundNumber(2);
        roundDataEntity2.setTimeTaken(6789323);
        roundDataEntity2.setTotalHeardCount(56);

        RoundDataEntity roundDataEntity3 = new RoundDataEntity();
        roundDataEntity3.setRoundNumber(3);
        roundDataEntity3.setTimeTaken(890909);
        roundDataEntity3.setTotalHeardCount(98);

        roundDataEntities.add(roundDataEntity1);
        roundDataEntities.add(roundDataEntity2);
        roundDataEntities.add(roundDataEntity3);

        LinearLayout historyListViewLinearLayout = new LinearLayout(getAppCompatActivity());

        if (roundDataEntities != null && !roundDataEntities.isEmpty()) {
            roundDataEntities.forEach(roundDataEntity -> {
                View historyView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.history_list_row_view_activity, null);
                ((TextView) historyView.findViewById(R.id.roundIdTextView)).setText(roundDataEntity.getRoundNumber() + "");
                ((TextView) historyView.findViewById(R.id.heardCountTextView)).setText(roundDataEntity.getTotalHeardCount() + "");
                ((TextView) historyView.findViewById(R.id.timeTakenTextView)).setText(String.format(Locale.ENGLISH, "0%d:%02d min", TimeUnit.MILLISECONDS.toMinutes(roundDataEntity.getTimeTaken()) % 60, TimeUnit.MILLISECONDS.toSeconds(roundDataEntity.getTimeTaken()) % 60));
                historyListViewLinearLayout.addView(historyView);
            });
        }
        builder.setView(historyListViewLinearLayout);
        builder.create();
        builder.show();
    }
}