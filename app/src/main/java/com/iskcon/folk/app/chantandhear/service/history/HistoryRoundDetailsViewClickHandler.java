package com.iskcon.folk.app.chantandhear.service.history;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.util.LoaderAlertDialog;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HistoryRoundDetailsViewClickHandler {

    public void showHistoryRoundWiseDetailPopup(View view, UserDetails userDetails, boolean reverseList) {

        LoaderAlertDialog loaderAlertDialog = new LoaderAlertDialog((Activity) view.getContext());

        loaderAlertDialog.show();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<RoundDataEntity>> typeIndicator = new GenericTypeIndicator<List<RoundDataEntity>>() {
                };
                renderLayoutOverAlert(view, userDetails, reverseList, snapshot.getValue(typeIndicator));
                loaderAlertDialog.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loaderAlertDialog.close();
            }
        };

        new ChantingDataDao(userDetails).getCurrentDayData(new Date(), userDetails.getId(), valueEventListener);
    }

    private void renderLayoutOverAlert(View view, UserDetails userDetails, boolean reverseList,
                                       List<RoundDataEntity> roundDataEntities) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        LinearLayout linearLayout = new LinearLayout(view.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = 90;
        layoutParams.setMargins(margin, 0, margin, margin);
        linearLayout.setLayoutParams(layoutParams);

        int totalHeardCount = 0;

        if (roundDataEntities != null && !roundDataEntities.isEmpty()) {

            if (reverseList) {
                Collections.reverse(roundDataEntities);
            }

            for (int i = 0; i < roundDataEntities.size(); i++) {
                RoundDataEntity roundDataEntity = roundDataEntities.get(i);
                View historyRowView = View.inflate(view.getContext(), R.layout.history_list_row_view_activity, null);
                int roundNumber = roundDataEntity.getRoundNumber();
                String roundNumberStr =
                        roundNumber > 9 ? String.valueOf(roundNumber) : String.format(Locale.ENGLISH, "0%d", roundNumber);
                ((TextView) historyRowView.findViewById(R.id.roundIdTextView)).setText(roundNumberStr);
                ((TextView) historyRowView.findViewById(R.id.heardCountTextView)).setText(
                        roundDataEntity.getTotalHeardCount() + "");
                ((TextView) historyRowView.findViewById(R.id.timeTakenTextView)).setText(
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
                historyRowView.setLayoutParams(historyRowViewLayoutParams);

                totalHeardCount = totalHeardCount + roundDataEntity.getTotalHeardCount();

                this.addStars(historyRowView, roundDataEntity.getTotalHeardCount());

                linearLayout.addView(historyRowView);
            }
        } else {
            TextView textView = new TextView(view.getContext());
            textView.setText("Hare Krishna, no data found, please start your mala to see data.");
            textView.setTextColor(view.getResources().getColor(R.color.ch_light_color));
            textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
            textView.setTextSize(14);
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
        }

        View historyListView = View.inflate(view.getContext(), R.layout.history_list_view_activity, null);

        LinearLayout historyListViewLinearLayout = historyListView.findViewById(R.id.historyListViewLinearLayout);

        ScrollView scrollView = new ScrollView(view.getContext());
        scrollView.addView(linearLayout);
        historyListViewLinearLayout.addView(scrollView);

        ((TextView) historyListViewLinearLayout.findViewById(R.id.currentDateTextView))
                .setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date()));

        ((TextView) historyListViewLinearLayout.findViewById(R.id.userNameTextView))
                .setText(userDetails.getDisplayName());

        ((TextView) historyListViewLinearLayout.findViewById(R.id.totalHeardCountTextView))
                .setText(String.valueOf(totalHeardCount));

        builder.setView(historyListViewLinearLayout);

        AlertDialog alertDialog = builder.create();

        ((ImageView) historyListView.findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.animate()
                        .setDuration(200)
                        .scaleX(1.5f)
                        .scaleY(1.5f)
                        .withEndAction(() ->
                                view.animate()
                                        .setDuration(200)
                                        .scaleX(1f)
                                        .scaleY(1f)
                        );

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void addStars(View historyRowView, int heardCount) {
        LinearLayout starContainerLinearLayout = historyRowView.findViewById(R.id.starContainerLinearLayout);
        if (heardCount > 0) {
            int noOfStars = Math.round((float) heardCount / ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class) *
                    ApplicationConstants.STAR_RATING_FOR_HEARD_COUNT.getConstantValue(Integer.class));
            if (noOfStars > 0) {
                for (int i = 0; i < noOfStars; i++) {
                    ImageView imageView = new ImageView(historyRowView.getContext());
                    LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    imageView.setLayoutParams(imageViewLayoutParams);
                    imageView.setBackgroundResource(R.drawable.baseline_star_24);
                    starContainerLinearLayout.addView(imageView);
                }
            }
        }

        if (starContainerLinearLayout.getChildCount() == 0) {
            TextView noStarsTextView = new TextView(historyRowView.getContext());
            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            noStarsTextView.setLayoutParams(textViewLayoutParams);
            noStarsTextView.setText("Hear while chanting to get stars");
            noStarsTextView.setTextSize(12);
            noStarsTextView.setTextColor(historyRowView.getContext().getResources().getColor(R.color.red));
            noStarsTextView.setTypeface(noStarsTextView.getTypeface(), Typeface.ITALIC);
            starContainerLinearLayout.addView(noStarsTextView);
        }
    }
}