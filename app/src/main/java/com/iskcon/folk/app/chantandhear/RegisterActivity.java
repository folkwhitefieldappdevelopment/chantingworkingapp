package com.iskcon.folk.app.chantandhear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.homepage.HomePageActivity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;

public class RegisterActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Button loginButton;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        this.animate();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                ((TextView) findViewById(R.id.welcomeMessage)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
            }
        });
        ((LinearLayout) findViewById(R.id.loginButtonLL)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        if (auth.getCurrentUser() != null) {
            userDetails = new UserDetails(
                    auth.getCurrentUser().getUid(),
                    auth.getCurrentUser().getDisplayName(),
                    auth.getCurrentUser().getEmail(),
                    auth.getCurrentUser().getDisplayName()
            );
            navigateToSecondActivity();
        }
        super.onStart();
    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Unable to signup at this moment, please contact system administrator.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("task", "onComplete: task");
                        if (task.isSuccessful()) {
                            userDetails = new UserDetails(
                                    task.getResult().getUser().getUid(),
                                    task.getResult().getUser().getDisplayName(),
                                    task.getResult().getUser().getEmail(),
                                    task.getResult().getUser().getDisplayName()
                            );
                            new ChantingDataDao(userDetails).saveUser(userDetails);
                            navigateToSecondActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void navigateToSecondActivity() {
        Intent intent = new Intent(RegisterActivity.this, HomePageActivity.class);
        intent.putExtra("userDetails", userDetails);
        startActivity(intent);
    }

    private void animate() {

        RelativeLayout spQuoteRelativeLayout = findViewById(R.id.spQuoteRelativeLayout);
        spQuoteRelativeLayout.setVisibility(View.VISIBLE);
        spQuoteRelativeLayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_message_fade_in));
        spQuoteRelativeLayout.startLayoutAnimation();

        spQuoteRelativeLayout.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.titleDivider).setVisibility(View.VISIBLE);
                RelativeLayout welcomeMessageRelativeLayout = findViewById(R.id.welcomeMessageRelativeLayout);
                welcomeMessageRelativeLayout.setVisibility(View.VISIBLE);
                welcomeMessageRelativeLayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_message_fade_in));
                welcomeMessageRelativeLayout.startLayoutAnimation();
                welcomeMessageRelativeLayout.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        LinearLayout loginButtonLinearLayout = findViewById(R.id.loginButtonLL);
                        loginButtonLinearLayout.setVisibility(View.VISIBLE);
                        loginButtonLinearLayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_message_fade_in));
                        loginButtonLinearLayout.startLayoutAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}