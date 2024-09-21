package com.iskcon.folk.app.chantandhear.service;

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
import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;

import java.text.SimpleDateFormat;
import java.util.Collections;
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

        int totalHeardCount = 0;

        if (roundDataEntities != null && !roundDataEntities.isEmpty()) {

            Collections.reverse(roundDataEntities);

            for (int i = 0; i < roundDataEntities.size(); i++) {
                RoundDataEntity roundDataEntity = roundDataEntities.get(i);
                View historyRowView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.history_list_row_view_activity, null);
                int roundNumber = roundDataEntity.getRoundNumber();
                String roundNumberStr = roundNumber > 9 ? String.valueOf(roundNumber) : String.format(Locale.ENGLISH, "0%d", roundNumber);
                ((TextView) historyRowView.findViewById(R.id.roundIdTextView)).setText(roundNumberStr);
                ((TextView) historyRowView.findViewById(R.id.heardCountTextView)).setText(roundDataEntity.getTotalHeardCount() + "");
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
            TextView textView = new TextView(getAppCompatActivity());
            textView.setText("Hare Krishna, no data found, please start your mala to see data in history. ");
            textView.setTextColor(getAppCompatActivity().getResources().getColor(R.color.ch_light_color));
            textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
            textView.setTextSize(14);
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
        }

        ScrollView scrollView = new ScrollView(getAppCompatActivity());
        scrollView.addView(linearLayout);
        historyListViewLinearLayout.addView(scrollView);

        ((TextView) historyListViewLinearLayout.findViewById(R.id.currentDateTextView))
                .setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date()));

        ((TextView) historyListViewLinearLayout.findViewById(R.id.userNameTextView))
                .setText(super.getAppCompatActivity().getUserDetails().getDisplayName());

        ((TextView) historyListViewLinearLayout.findViewById(R.id.totalHeardCountTextView))
                .setText(String.valueOf(totalHeardCount));

        builder.setView(historyListViewLinearLayout);

        builder.show();
    }

    private void addStars(View historyRowView, int heardCount) {
        LinearLayout starContainerLinearLayout = historyRowView.findViewById(R.id.starContainerLinearLayout);
        if (heardCount > 0) {
            int noOfStars = Math.round((float) heardCount / ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class) * ApplicationConstants.STAR_RATING_FOR_HEARD_COUNT.getConstantValue(Integer.class));
            if (noOfStars > 0) {
                for (int i = 0; i < noOfStars; i++) {
                    ImageView imageView = new ImageView(getAppCompatActivity());
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

        if(starContainerLinearLayout.getChildCount() == 0){
            TextView noStarsTextView = new TextView(getAppCompatActivity());
            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            noStarsTextView.setLayoutParams(textViewLayoutParams);
            noStarsTextView.setText("No stars, you must hear while chanting.");
            noStarsTextView.setTextSize(11);
            noStarsTextView.setTextColor(getAppCompatActivity().getResources().getColor(R.color.history_list_row_view_round_number));
            noStarsTextView.setTypeface(noStarsTextView.getTypeface(),Typeface.ITALIC);
            starContainerLinearLayout.addView(noStarsTextView);
        }
    }
}