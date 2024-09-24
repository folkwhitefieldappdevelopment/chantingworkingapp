package com.iskcon.folk.app.chantandhear.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.iskcon.folk.app.chantandhear.RegisterActivity;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.service.HistoryViewClickHandler;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomePageProgressCardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_progress_card_fragment, container, false);
        UserDetails userDetails = (UserDetails) getActivity().getIntent().getExtras().get("userDetails");
        ((TextView) view.findViewById(R.id.todayLegend)).setText(MessageFormat.format("Todays ({0})", new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date())));
        getUserRoundDetails(view, userDetails);
        ((LinearLayout) view.findViewById(R.id.heardLinearLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HistoryViewClickHandler().showHistoryPopup(view, userDetails, false);
            }
        });

        TextView letsFinishTextView = view.findViewById(R.id.letsFinishChanting);
        SpannableString spannableString = new SpannableString("Lets finish today's chanting!!");
        spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
        letsFinishTextView.setText(spannableString);
        letsFinishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("userDetails", userDetails);
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
                    int todaysHeardCount = 0;
                    int todaysNoOfStars = 0;
                    for (int i = 0; i < roundDataEntities.size(); i++) {
                        int heardCount = roundDataEntities.get(i).getTotalHeardCount();
                        todaysHeardCount = todaysHeardCount + heardCount;
                        todaysNoOfStars = todaysNoOfStars + Math.round((float) heardCount / ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class) * ApplicationConstants.STAR_RATING_FOR_HEARD_COUNT.getConstantValue(Integer.class));
                    }
                    ((TextView) view.findViewById(R.id.todaysRound)).setText(String.valueOf(roundDataEntities.size()));
                    ((TextView) view.findViewById(R.id.todaysHeardCount)).setText(String.valueOf(todaysHeardCount));
                    ((TextView) view.findViewById(R.id.todaysStarCount)).setText(String.valueOf(todaysNoOfStars));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        new ChantingDataDao(userDetails).get(new Date(), userDetails.getId(), valueEventListener);
    }
}