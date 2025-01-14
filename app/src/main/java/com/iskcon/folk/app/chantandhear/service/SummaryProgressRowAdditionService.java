package com.iskcon.folk.app.chantandhear.service;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
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
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.util.LoaderAlertDialog;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SummaryProgressRowAdditionService {

    public void addSummaryRow(MainActivity mainActivity) {

        this.getSummaryDataFromDatabase(mainActivity, mainActivity.getUserDetails(), true);
    }

    private void renderView(MainActivity mainActivity, List<RoundDataEntity> roundDataEntities) {

        View summayView = View.inflate(mainActivity, R.layout.summary_progress_layout, null);

        LinearLayout linearLayout = summayView.findViewById(R.id.summaryProgressRowLayoutId);

        for (RoundDataEntity roundDataEntity : roundDataEntities) {

            View summaryProgressRowView =
                    View.inflate(mainActivity.getApplicationContext(), R.layout.summary_progress_row_layout, null);

            ((TextView) summaryProgressRowView.findViewById(R.id.summaryRoundHeaderTextView)).setText(
                    String.valueOf(roundDataEntity.getRoundNumber()));

            ((TextView) summaryProgressRowView.findViewById(R.id.summaryHeardHeaderTextView)).setText(
                    String.valueOf(roundDataEntity.getTotalHeardCount()));

            linearLayout.addView(summaryProgressRowView);
        }

        LinearLayout summaryProgressLayout = mainActivity.findViewById(R.id.summaryProgressLayoutId);

        summaryProgressLayout.addView(summayView);
    }

    private void getSummaryDataFromDatabase(MainActivity mainActivity, UserDetails userDetails, boolean reverseList) {

        LoaderAlertDialog loaderAlertDialog = new LoaderAlertDialog(mainActivity);

        loaderAlertDialog.show();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<RoundDataEntity>> typeIndicator =
                        new GenericTypeIndicator<List<RoundDataEntity>>() {
                        };
                renderView(mainActivity, snapshot.getValue(typeIndicator));
                loaderAlertDialog.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loaderAlertDialog.close();
            }
        };

        new ChantingDataDao(userDetails).getCurrentDayData(new Date(), userDetails.getId(), valueEventListener);
    }
}