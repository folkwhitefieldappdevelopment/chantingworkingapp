package com.example.chantingworkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.Locale;

public class Secondscreenlevelchose extends AppCompatActivity {
    private void vibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }
    TextView name,email;
    TextView text, mindtext,hrttext,soultext,mindcome,soulcome;
    ImageView mind,heart,soul,profile;
    DrawerLayout draw ;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    NavigationView nav ;
    ActionBarDrawerToggle action;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelchoose);
        text = findViewById(R.id.headertext);
        mindtext =findViewById(R.id.mindtext);
        MainActivity mains = new MainActivity();
        draw = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
        name = findViewById(R.id.nametext);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            name.setText(personName.toUpperCase(Locale.ROOT));
        }
        nav.bringToFront();
        action = new ActionBarDrawerToggle(this,draw,R.string.navigation_open,R.string.navigation_close);
        draw.addDrawerListener(action);
        action.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.home ) {
//                     Handle history option
                    return true;
                }
//                else if (id == R.id.history) {
////                     Handle history option
//                    Intent intent = new Intent(Secondscreenlevelchose.this, HistoryActivity.class);
//                    startActivity(intent);
//                    return true;
//                } else if (id == R.id.menu_speed) {
//                    mains.showCustomDialog(Secondscreenlevelchose.this);
//                    // Handle speed option
//                    return true;
//                } else if (id == R.id.menu_help) {
//
//                    mains.showHelpDialog(Secondscreenlevelchose.this);
////                    Toast.makeText(MainActivity.this,"helppp",Toast.LENGTH_SHORT).show();
//                    // Handle help option
//                    return true;
                else if (id == R.id.menu_quotes) {
                    // Handle quotes option
                    return true;
                } else if (id == R.id.menu_hearing) {
                    // Handle hearing option
                    return true;
                }
                return false;
            }
        });
        hrttext = findViewById(R.id.hearttext);
        soultext = findViewById(R.id.soultext);
        mind =findViewById(R.id.mind);
        heart = findViewById(R.id.heart);
        soul = findViewById(R.id.soul);
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                draw.setVisibility(View.VISIBLE);
                draw.getOverlay();
                draw.openDrawer(GravityCompat.START);
            }
        });

        mindcome = findViewById(R.id.comingheart);
        soulcome = findViewById(R.id.soulcoming);
        // Use a Handler to delay the start of the main activity
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Start the main activity after the splash delay
//                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_DELAY);

        // Set a click listener for the TextView
        mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                entermain();
            }
        });


    }
    private void entermain() {
        // Your code to handle the click event
        vibrate(50);
        Intent intent = new Intent(Secondscreenlevelchose.this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
