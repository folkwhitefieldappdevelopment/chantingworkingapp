package com.iskcon.folk.app.chantandhear.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChantingDataDao {

    private final UserDetails userDetails;
    private final FirebaseDatabase firebaseDatabase;
    private final String CHILD_USERS = "users";
    private final String CHILD_ROUND_DATA = "roundDetails";


    public ChantingDataDao(UserDetails userDetails) {
        this.userDetails = userDetails;
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void saveUser(UserDetails userDetails) {
        firebaseDatabase.getReference()
                .child(CHILD_USERS)
                .child(userDetails.getId())
                .child("userDetails")
                .setValue(userDetails);
    }

    public void saveRoundData(RoundDataEntity roundDataEntity, Date date) {

        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child(CHILD_USERS)
                .child(userDetails.getId())
                .child(CHILD_ROUND_DATA)
                .child(this.getCurrentDateAsString(date));

        final List<RoundDataEntity> roundDataEntities = new ArrayList<>();

        databaseReference
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        Object result = task.getResult().getValue();

                        if (result != null) {
                            roundDataEntities.addAll((List) result);
                        }

                        roundDataEntities.add(roundDataEntity);

                        databaseReference.setValue(roundDataEntities).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i(this.getClass().getSimpleName(), "onComplete: data got saved");
                            }
                        });
                    }
                });
    }

    public List<RoundDataEntity> get(Date date, String userId, ValueEventListener valueEventListener) {

        final List<RoundDataEntity> roundDataEntities = new ArrayList<>();

        firebaseDatabase.getReference()
                .child(CHILD_USERS)
                .child(userId)
                .child(CHILD_ROUND_DATA)
                .child(this.getCurrentDateAsString(date))
                .addListenerForSingleValueEvent(valueEventListener);

        return roundDataEntities;
    }

    private String getCurrentDateAsString(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(date);
    }
}