package com.example.chantingworkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ImageView icon,backgrpund;
    FrameLayout frame;
    TextView welcome;
    Button googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomscreen);

        googleBtn = findViewById(R.id.loginButton);
        welcome = findViewById(R.id.welcometext);
        icon = findViewById(R.id.icon);
        frame=findViewById(R.id.frame);
        backgrpund = findViewById(R.id.backimage);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);



        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null){
            Toast.makeText(registerActivity.this,"ALready Logged in",Toast.LENGTH_SHORT).show();
            navigateToSecondActivity();
        }
        else{
            Toast.makeText(registerActivity.this,"You can log in now",Toast.LENGTH_SHORT).show();
        }
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 20){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
                Toast.makeText(getApplicationContext(), "Some---"+account.getIdToken(), Toast.LENGTH_SHORT).show();

                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Loo Now", Toast.LENGTH_SHORT).show();

                            FirebaseUser user  = auth.getCurrentUser();
                            ReadWriteContactDetails read = new ReadWriteContactDetails();
                            read.name = user.getDisplayName();
                            database.getReference().child("users").child(user.getUid()).setValue(read).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "L in Now", Toast.LENGTH_SHORT).show();

                                }
                            });

                            navigateToSecondActivity();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Something went wrong2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void navigateToSecondActivity(){

        Intent intent = new Intent(registerActivity.this,Secondscreenlevelchose.class);
        startActivity(intent);
    }
}