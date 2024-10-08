package com.iskcon.folk.app.chantandhear.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iskcon.folk.app.chantandhear.LevelSelectionActivity;
import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;

import java.util.Date;
import java.util.List;

public class HomePageLevelSelectionFragment extends Fragment {
    private int completedRounds;
    private UserDetails userDetails;
    private boolean gotData = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.level_selection, container, false);
        ImageView mind = view.findViewById(R.id.level1MindFullJapa);
        userDetails = (UserDetails) getActivity().getIntent().getExtras().get("userDetails");
        mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gotData) {
                    view.animate().setDuration(200).scaleX(1.2f).scaleY(1.2f).withEndAction(() -> view.animate().setDuration(300).scaleX(1f).scaleY(1f));
                    enterMain(userDetails, getCompletedRounds());
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        this.getUserRoundDetails(userDetails);
        super.onStart();
    }

    private void getUserRoundDetails(UserDetails userDetails) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<RoundDataEntity>> typeIndicator = new GenericTypeIndicator<List<RoundDataEntity>>() {
                };
                if (snapshot != null && snapshot.exists()) {
                    setCompletedRounds(snapshot.getValue(typeIndicator).size());
                }
                gotData = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        new ChantingDataDao(userDetails).get(new Date(), userDetails.getId(), valueEventListener);
    }

    private void enterMain(UserDetails userDetails, int completedRounds) {
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        intent.putExtra("userDetails", userDetails);
        intent.putExtra("completedRounds", completedRounds);
        startActivity(intent);
    }

    public int getCompletedRounds() {
        return completedRounds;
    }

    public void setCompletedRounds(int completedRounds) {
        this.completedRounds = completedRounds;
    }


}