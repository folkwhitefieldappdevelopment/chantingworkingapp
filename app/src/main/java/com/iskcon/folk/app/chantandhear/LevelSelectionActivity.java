package com.iskcon.folk.app.chantandhear;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LevelSelectionActivity extends AppCompatActivity {

    private UserDetails userDetails;

    private void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

    TextView name, email;
    ImageView mind, heart, soul, profile;
    DrawerLayout draw;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    NavigationView nav;
    ActionBarDrawerToggle action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_selection);

        Object userDetailsObj = getIntent().getExtras().get("userDetails");

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (userDetailsObj != null) {
            userDetails = (UserDetails) userDetailsObj;
        } else {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            userDetails = new UserDetails(googleSignInAccount.getId(), googleSignInAccount.getDisplayName(), googleSignInAccount.getEmail(), googleSignInAccount.getDisplayName());
        }

        MainActivity mains = new MainActivity();
        draw = findViewById(R.id.drawer_layout);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        /*((TextView) findViewById(R.id.levelSelectionWelcomeTextView))
                .setText(MessageFormat.format(
                        "Hare Krishna {0}, feel the presence of Krishna with every bead",
                        googleSignInAccount.getDisplayName().toUpperCase(Locale.ENGLISH)));*/
        action = new ActionBarDrawerToggle(this, draw, R.string.navigation_open, R.string.navigation_close);
        draw.addDrawerListener(action);
        action.syncState();
        mind = findViewById(R.id.level1MindFullJapa);
        mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate(50);
                view.animate().setDuration(200).scaleX(1.2f).scaleY(1.2f).withEndAction(() -> view.animate().setDuration(300).scaleX(1f).scaleY(1f));
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<List<RoundDataEntity>> typeIndicator = new GenericTypeIndicator<List<RoundDataEntity>>() {
                        };
                        int completedRounds = 0;
                        if (snapshot != null && snapshot.exists()) {
                            completedRounds = snapshot.getValue(typeIndicator).size();
                        }

                        enterMain(userDetails, completedRounds);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                new ChantingDataDao(userDetails).get(new Date(), userDetails.getId(), valueEventListener);
            }
        });
    }

    private void enterMain(UserDetails userDetails, int completedRounds) {
        Intent intent = new Intent(LevelSelectionActivity.this, MainActivity.class);
        intent.putExtra("userDetails", userDetails);
        intent.putExtra("completedRounds", completedRounds);
        startActivity(intent);
        finish();
    }
}