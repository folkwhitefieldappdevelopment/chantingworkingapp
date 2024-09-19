package com.iskcon.folk.app.chantandhear.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChantingDataDao {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;


    public ChantingDataDao() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void save(Serializable serializable) {

        firebaseDatabase.getReference().child("roundData").setValue(serializable).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(this.getClass().getSimpleName(), "onComplete: data got saved");
            }
        });
    }

    public List<RoundDataEntity> get(Date date, String userId) {

        final List<RoundDataEntity> roundDataEntities = new ArrayList<>();

        firebaseDatabase.getReference().child("roundData")
                .child(userId)
                .equalTo(new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(date))
                .orderByChild("roundId").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            roundDataEntities.add(dataSnapshot.getValue(RoundDataEntity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return roundDataEntities;
    }
}