package com.iskcon.folk.app.chantandhear.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.service.history.HistoryDaysViewDetailClickHandler;
import com.iskcon.folk.app.chantandhear.service.history.HistoryRoundDetailsViewClickHandler;
import com.iskcon.folk.app.chantandhear.util.LoaderAlertDialog;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomePageProgressCardFragment extends Fragment {
    private int completedRounds = 0;
    LoaderAlertDialog loaderAlertDialog = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loaderAlertDialog = new LoaderAlertDialog(this.getActivity());
        loaderAlertDialog.show();
        View view = inflater.inflate(R.layout.home_page_progress_card_fragment, container, false);
        UserDetails userDetails = (UserDetails) getActivity().getIntent().getExtras().get("userDetails");
        ((TextView) view.findViewById(R.id.todayLegend)).setText(
                MessageFormat.format("Todays ({0})", new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date())));
        this.getUserRoundDetails(view, userDetails);
        ((LinearLayout) view.findViewById(R.id.heardLinearLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HistoryRoundDetailsViewClickHandler().showHistoryRoundWiseDetailPopup(view, userDetails, false);
            }
        });
        this.paintMindfulJapaProgress(view, userDetails);
        ((LinearLayout) view.findViewById(R.id.openMindfulProgressNewWindow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HistoryDaysViewDetailClickHandler().showDaysViewDetails(view, userDetails, false);
            }
        });
        TextView letsFinishTextView = view.findViewById(R.id.letsFinishChanting);
        SpannableString spannableString = new SpannableString("Continue chanting");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        letsFinishTextView.setText(spannableString);
        letsFinishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("userDetails", userDetails);
                intent.putExtra("completedRounds", completedRounds);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getUserRoundDetails(View view, UserDetails userDetails) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<RoundDataEntity>> typeIndicator = new GenericTypeIndicator<List<RoundDataEntity>>() {
                };
                if (snapshot != null && snapshot.exists()) {
                    List<RoundDataEntity> roundDataEntities = snapshot.getValue(typeIndicator);
                    completedRounds = roundDataEntities.size();
                    int todaysHeardCount = 0;
                    int todaysNoOfStars = 0;
                    for (int i = 0; i < roundDataEntities.size(); i++) {
                        int heardCount = roundDataEntities.get(i).getTotalHeardCount();
                        todaysHeardCount = todaysHeardCount + heardCount;
                        todaysNoOfStars = todaysNoOfStars + Math.round(
                                (float) heardCount / ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class) *
                                        ApplicationConstants.STAR_RATING_FOR_HEARD_COUNT.getConstantValue(Integer.class));
                    }
                    ((TextView) view.findViewById(R.id.todaysRound)).setText(String.valueOf(roundDataEntities.size()));
                    ((TextView) view.findViewById(R.id.todaysHeardCount)).setText(String.valueOf(todaysHeardCount));
                    ((TextView) view.findViewById(R.id.todaysStarCount)).setText(String.valueOf(
                            todaysNoOfStars / ApplicationConstants.STAR_RATING_FOR_HEARD_COUNT.getConstantValue(Integer.class)));

                    TextView textView = view.findViewById(R.id.letsFinishChanting);
                    textView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.wiggle_3));
                    if (completedRounds == 16) {
                        textView.setVisibility(View.INVISIBLE);
                    }
                }
                loaderAlertDialog.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loaderAlertDialog.close();
            }
        };
        new ChantingDataDao(userDetails).getCurrentDayData(new Date(), userDetails.getId(), valueEventListener);
    }

    private void paintMindfulJapaProgress(View view, UserDetails userDetails) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<Map<String, List<RoundDataEntity>>> typeIndicator =
                        new GenericTypeIndicator<Map<String, List<RoundDataEntity>>>() {
                        };
                if (snapshot != null && snapshot.exists()) {
                    Map<String, List<RoundDataEntity>> roundDataEntityMap = snapshot.getValue(typeIndicator);
                    int acrossDays = roundDataEntityMap.size();
                    int totallyHeard = 0;
                    for (Map.Entry<String, List<RoundDataEntity>> entryMap : roundDataEntityMap.entrySet()) {
                        List<RoundDataEntity> roundDataEntities1 = entryMap.getValue();
                        for (int i = 0; i < roundDataEntities1.size(); i++) {
                            totallyHeard = totallyHeard + roundDataEntities1.get(i).getTotalHeardCount();
                        }
                    }
                    ((TextView) view.findViewById(R.id.mindfulTotallyHeard)).setText(String.valueOf(totallyHeard));
                    ((TextView) view.findViewById(R.id.mindfulAcrossDays)).setText(String.valueOf(acrossDays));
                }
                loaderAlertDialog.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loaderAlertDialog.close();
            }
        };
        new ChantingDataDao(userDetails).getAllDaysData(userDetails.getId(), valueEventListener);
    }
}