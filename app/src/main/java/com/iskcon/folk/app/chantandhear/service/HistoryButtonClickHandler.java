package com.iskcon.folk.app.chantandhear.service;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
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

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<RoundDataEntity>> typeIndicator = new GenericTypeIndicator<List<RoundDataEntity>>() {
                };
                renderLayoutOverAlert(snapshot.getValue(typeIndicator));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        new ChantingDataDao(getAppCompatActivity().getUserDetails())
                .get(new Date(), getAppCompatActivity().getUserDetails().getId(), valueEventListener);
    }

    private void renderLayoutOverAlert(List<RoundDataEntity> roundDataEntities) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getAppCompatActivity());

        View historyListView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.history_list_view_activity, null);

        LinearLayout historyListViewLinearLayout = historyListView.findViewById(R.id.historyListViewLinearLayout);

        LinearLayout linearLayout = new LinearLayout(getAppCompatActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = 90;
        layoutParams.setMargins(margin, 0, margin, margin);
        linearLayout.setLayoutParams(layoutParams);

        if (roundDataEntities != null && !roundDataEntities.isEmpty()) {
            roundDataEntities.forEach(roundDataEntity -> {
                View historyView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.history_list_row_view_activity, null);
                ((TextView) historyView.findViewById(R.id.roundIdTextView)).setText(
                        String.format(
                                Locale.ENGLISH,
                                "0%d",
                                roundDataEntity.getRoundNumber()
                        )
                );
                ((TextView) historyView.findViewById(R.id.heardCountTextView)).setText(roundDataEntity.getTotalHeardCount() + "");
                ((TextView) historyView.findViewById(R.id.timeTakenTextView)).setText(
                        String.format(
                                Locale.ENGLISH,
                                "0%d:%02d min",
                                TimeUnit.MILLISECONDS.toMinutes(roundDataEntity.getTimeTaken()) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(roundDataEntity.getTimeTaken()) % 60
                        )
                );
                LinearLayout.LayoutParams historyRowViewLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                historyRowViewLayoutParams.setMargins(0, 0, 0, 50);
                historyView.setLayoutParams(historyRowViewLayoutParams);

                linearLayout.addView(historyView);
            });
        }

        ScrollView scrollView = new ScrollView(getAppCompatActivity());
        scrollView.addView(linearLayout);
        historyListViewLinearLayout.addView(scrollView);
        builder.setView(historyListViewLinearLayout);
        builder.show();
    }
}