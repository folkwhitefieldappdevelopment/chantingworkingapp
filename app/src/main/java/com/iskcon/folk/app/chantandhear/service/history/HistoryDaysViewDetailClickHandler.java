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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HistoryDaysViewDetailClickHandler {

    public void showDaysViewDetails(View view, UserDetails userDetails, boolean reverseList) {

        LoaderAlertDialog loaderAlertDialog = new LoaderAlertDialog((Activity) view.getContext());

        loaderAlertDialog.show();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, List<RoundDataEntity>>> typeIndicator =
                        new GenericTypeIndicator<Map<String, List<RoundDataEntity>>>() {
                        };
                renderLayoutOverAlert(view, userDetails, reverseList, snapshot.getValue(typeIndicator));
                loaderAlertDialog.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loaderAlertDialog.close();
            }
        };

        new ChantingDataDao(userDetails).getAllDaysData(userDetails.getId(), valueEventListener);
    }

    private void renderLayoutOverAlert(View view, UserDetails userDetails, boolean reverseList,
                                       Map<String, List<RoundDataEntity>> roundDataEntityMap) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        LinearLayout dynamicLinearLayout = new LinearLayout(view.getContext());
        dynamicLinearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int margin = 30;
        layoutParams.setMargins(margin, 0, margin, margin);
        dynamicLinearLayout.setLayoutParams(layoutParams);

        int totalHeardCount = 0;

        if (roundDataEntityMap != null && !roundDataEntityMap.isEmpty()) {

            for (Map.Entry<String, List<RoundDataEntity>> entry : roundDataEntityMap.entrySet()) {

                List<RoundDataEntity> roundDataEntities = entry.getValue();

                if (reverseList) {
                    Collections.reverse(roundDataEntities);
                }

                int givenDayHeardCount = 0;
                int givenDayNoOfStars = 0;
                for (int i = 0; i < roundDataEntities.size(); i++) {
                    RoundDataEntity roundDataEntity = roundDataEntities.get(i);
                    int heardCount = roundDataEntity.getTotalHeardCount();
                    givenDayHeardCount = givenDayHeardCount + heardCount;
                    givenDayNoOfStars = givenDayNoOfStars + Math.round(
                            (float) heardCount / ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class) *
                                    ApplicationConstants.STAR_RATING_FOR_HEARD_COUNT.getConstantValue(Integer.class));
                }

                View mindfulJapaProgressListRowView =
                        View.inflate(view.getContext(), R.layout.mindful_japa_progress_list_row_view_activity, null);

                ((TextView) mindfulJapaProgressListRowView.findViewById(R.id.japaDate)).setText(entry.getKey());

                ((TextView) mindfulJapaProgressListRowView.findViewById(R.id.givenDayRoundsChanted)).setText(
                        String.valueOf(roundDataEntities.size()));

                ((TextView) mindfulJapaProgressListRowView.findViewById(R.id.givenDayHeardCount)).setText(
                        String.valueOf(givenDayHeardCount));

                ((TextView) mindfulJapaProgressListRowView.findViewById(R.id.givenDayStarCount)).setText(String.valueOf(
                        givenDayNoOfStars /
                                ApplicationConstants.STAR_RATING_FOR_HEARD_COUNT.getConstantValue(Integer.class)));

                dynamicLinearLayout.addView(mindfulJapaProgressListRowView);
            }

        } else {
            TextView textView = new TextView(view.getContext());
            textView.setText("Hare Krishna, no data found, please start your mala to see data.");
            textView.setTextColor(view.getResources().getColor(R.color.ch_light_color));
            textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
            textView.setTextSize(14);
            textView.setGravity(Gravity.CENTER);
            dynamicLinearLayout.addView(textView);
        }

        View mindfulJapaProgressListView =
                View.inflate(view.getContext(), R.layout.mindful_japa_progress_list_view_activity, null);

        LinearLayout mindfulProgressListViewLinearLayout =
                mindfulJapaProgressListView.findViewById(R.id.mindfulProgressListViewLinearLayout);

        ScrollView scrollView = new ScrollView(view.getContext());
        scrollView.addView(dynamicLinearLayout);
        mindfulProgressListViewLinearLayout.addView(scrollView);

        builder.setView(mindfulProgressListViewLinearLayout);

        AlertDialog alertDialog = builder.create();

        ((ImageView) mindfulProgressListViewLinearLayout.findViewById(R.id.closeButton)).setOnClickListener(
                new View.OnClickListener() {
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
}