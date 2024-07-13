package com.example.chantingworkingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.chantingworkingapp.constant.NamaPrabhuToasts;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.service.HearButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.MuteButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.ResetButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.UnMuteButtonHandler;
import com.example.chantingworkingapp.util.CommonUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final HearButtonHandler hearButtonHandler = new HearButtonHandler(this);
    private ResetButtonHandler resetButtonHandler;
    private MuteButtonHandler muteButtonHandler;
    private UnMuteButtonHandler unMuteButtonHandler;

    private JapaMalaModel japaMalaModel;

    private String[] videoForUserListeningCheck = {
//            "zEtAgUtXvRU",
//            "SUbhkUd8c68",
//            "QnLevMrkQ_0",
//            "t--PFs-xxo4",
//            "m3QkR8qBgw8",
//            "6Fl0bGCVPCY",
//            "-csUVDrYhPg",
//            "nD_JQMmov8o",
//            "BMzyCEM5f8E"
            "MF-k_a9u5RU"
    };
    TextView spChantingText,hearingCountText, chantingTextDownToTheTextView, hareKrishnaMahaMantraText, soulfulJapaQuoteText, timerTextforTheMantra, currentRoundNumber, hearingTextDownHearTextView, levelCount;
    private static final int[] chantingProgressBar = {R.id.progress_bar_1
            , R.id.progress_bar_2
            , R.id.progress_bar_3
            , R.id.progress_bar_4
            , R.id.progress_bar_5
            , R.id.progress_bar_6
            , R.id.progress_bar_7

    };
    private static final int[] chantingProgressBarTextViews = {R.id.text_view_inside_frame1
            , R.id.text_view_inside_frame2
            , R.id.text_view_inside_frame3
            , R.id.text_view_inside_frame4
            , R.id.text_view_inside_frame5
            , R.id.text_view_inside_frame6
            , R.id.text_view_inside_frame7

    };

    ImageView srilaPrabhupadaImage, menuHemBurgerIcon, startButtonImage, pauseButtonImage, resetButtonImage, unmuteIconImage, muteIconImage, earlyDoneIconImage;
    //Navigation Menu
    DrawerLayout drawerLayoutForMenu;
    NavigationView navigationViewForMenu;
    ActionBarDrawerToggle action;
    //Handlers
    private Handler handlerForTimer;
    //MediaPlayers
    MediaPlayer spHkmMediaplayer, srilaPrabhupadaChantingWithOutPanchtattva;
    //Other Variables
    private long timeInMsForTimer, startingTimeForTimerRunnable, timeBuffForTimer, updatingTimeForTimerRunnable = 0;
    private  int secondsForTimer, minutesForTimer, timeToStopTimerForChangingText,hearingCountTextOfUser,chantingCountTextOfSP;
    boolean varForCheckingIfMantraStoppedInBetween ;
    private  FloatingActionButton hearingMovableButton;
    private ProgressBar[] chantingAndHearingProgressBar = new ProgressBar[7];
    private TextView[] chantingAndHearingProgressBarTextViews = new TextView[7];
    private PowerManager.WakeLock wakeLock;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyApp::WakeLockTag");
        wakeLock.acquire();
        //TextViews  on the Screen
        spChantingText = findViewById(R.id.spChanting);
        chantingCountTextOfSP= Integer.parseInt(String.valueOf(spChantingText.getText()));
        hearingCountText = findViewById(R.id.hearingCount);
        hearingCountTextOfUser = Integer.parseInt(String.valueOf(hearingCountText.getText()));
        chantingTextDownToTheTextView = findViewById(R.id.chanting_text);

        hearingTextDownHearTextView = findViewById(R.id.Hearing_text);

        hareKrishnaMahaMantraText = findViewById(R.id.HKM);
        hareKrishnaMahaMantraText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAndResumeJapaFunction(v);
            }
        });
        soulfulJapaQuoteText = findViewById(R.id.goldencommand);
        timerTextforTheMantra = findViewById(R.id.timetext);
        currentRoundNumber = findViewById(R.id.rounds_text);
        levelCount = findViewById(R.id.levelCount);
        // Initialize your buttons here
        hearingMovableButton = findViewById(R.id.hear);
        hearingMovableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               hearButtonHandler.handle(japaMalaModel,view);
            }
        });
        //Initialization of Progress Bars
        chantingAndHearingProgressBar[0] = findViewById(chantingProgressBar[0]);
        chantingAndHearingProgressBar[1] = findViewById(chantingProgressBar[1]);
        chantingAndHearingProgressBar[2] = findViewById(chantingProgressBar[2]);
        chantingAndHearingProgressBar[3] = findViewById(chantingProgressBar[3]);
        chantingAndHearingProgressBar[4] = findViewById(chantingProgressBar[4]);
        chantingAndHearingProgressBar[5] = findViewById(chantingProgressBar[5]);
        chantingAndHearingProgressBar[6] = findViewById(chantingProgressBar[6]);
       chantingAndHearingProgressBarTextViews[0] = findViewById(chantingProgressBarTextViews[0]);
       chantingAndHearingProgressBarTextViews[1] = findViewById(chantingProgressBarTextViews[1]);
       chantingAndHearingProgressBarTextViews[2] = findViewById(chantingProgressBarTextViews[2]);
       chantingAndHearingProgressBarTextViews[3] = findViewById(chantingProgressBarTextViews[3]);
       chantingAndHearingProgressBarTextViews[4] = findViewById(chantingProgressBarTextViews[4]);
       chantingAndHearingProgressBarTextViews[5] = findViewById(chantingProgressBarTextViews[5]);
       chantingAndHearingProgressBarTextViews[6] = findViewById(chantingProgressBarTextViews[6]);
        //ImageViews
        srilaPrabhupadaImage = findViewById(R.id.SpImage);
        menuHemBurgerIcon = findViewById(R.id.hamburgerIcon);
        startButtonImage = findViewById(R.id.startIconImageView);

        resetButtonImage = findViewById(R.id.resetIconImageView);
        resetButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButtonHandler.handle(japaMalaModel,view);
            }
        });

        muteIconImage = findViewById(R.id.muteIconImageView);
        muteIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  muteButtonHandler.handle(japaMalaModel,view);
            }
        });

        unmuteIconImage = findViewById(R.id.unmuteIconImageView);
        unmuteIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   unMuteButtonHandler.handle(japaMalaModel,view);
            }
        });

        earlyDoneIconImage = findViewById(R.id.quickDoneImage);
        //Menu Navigation
        drawerLayoutForMenu = findViewById(R.id.drawer_layout);
        navigationViewForMenu = findViewById(R.id.nav_view);
        //Handlers
        handlerForTimer = new Handler(Looper.getMainLooper());
        //MediaPlayers
        spHkmMediaplayer = MediaPlayer.create(MainActivity.this, R.raw.sp_hkm);
        resetButtonHandler = new ResetButtonHandler(this,spHkmMediaplayer);
        muteButtonHandler = new MuteButtonHandler(this,spHkmMediaplayer);
        unMuteButtonHandler = new UnMuteButtonHandler(this,spHkmMediaplayer);
        srilaPrabhupadaChantingWithOutPanchtattva = MediaPlayer.create(MainActivity.this, R.raw.hkm);
        // Further initialization or event handling can be done here
        listeningCheck();
    }

    private void pauseAndResumeJapaFunction(View v) {
        if(startButtonImage.getVisibility() == View.VISIBLE){//Resume
            startButtonFunction(v);
        }
        else if(startButtonImage.getVisibility() == View.INVISIBLE){//Pause
            pauseButtonFunction(v);
        }
    }

    private void listeningCheck() {
        int notHearingTime = 1;
        boolean userListeningCheck = true;

        if(chantingCountTextOfSP - hearingCountTextOfUser>=15*notHearingTime){
            userListeningCheck = false;
        }
        if (!userListeningCheck){
            notHearingTime = 0;
            notHearingTime += 1;
            showVideoPopup(videoForUserListeningCheck);
            userListeningCheck = true;
        }
    }

    public void showVideoPopup(String[] videoToBePlay) {
        // Create a Dialog with a custom layout
        Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);

        // Inflate the custom layout for the popup
        View popupView = super.getLayoutInflater().inflate(R.layout.popup_layout, null);

        // Set the custom layout for the dialog
        dialog.setContentView(popupView);

        // Find the YouTubePlayerView in the custom layout
        youTubePlayerView = popupView.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // Load and play the video
                youTubePlayer.loadVideo(String.valueOf(videoToBePlay[0]), 0);
                youTubePlayer.play();// Enter fullscreen mode
            }
        });

        // Show the dialog
        dialog.show();
    }

    //Runnable
    private final Runnable runnableForTimer = new Runnable() {
        @Override
        public void run() {
//            Log.i(TAG, "Thread Name 1: " + Thread.currentThread().getName());
            timeInMsForTimer = SystemClock.uptimeMillis() - startingTimeForTimerRunnable;
            updatingTimeForTimerRunnable = timeBuffForTimer + timeInMsForTimer;
            secondsForTimer = (int) (updatingTimeForTimerRunnable / 1000);
            minutesForTimer = secondsForTimer / 60;
            secondsForTimer = secondsForTimer % 60;
            // Update UI
            timerTextforTheMantra.setText(MessageFormat.format("{0}:{1}", String.format(Locale.getDefault(), "%02d", minutesForTimer), String.format(Locale.getDefault(), "%02d", secondsForTimer)));
            // Schedule the next update
            handlerForTimer.postDelayed(this, 0);
        }
    };

    //Start Button Function
    public void startButtonFunction(View view) {
        startButtonImage.setVisibility(View.INVISIBLE);
        startingTimeForTimerRunnable = SystemClock.uptimeMillis();
        CommonUtils.vibrateFunction(50,(Vibrator) getSystemService(VIBRATOR_SERVICE));
        handlerForTimer.postDelayed(runnableForTimer, 0);
        if (spHkmMediaplayer != null && spHkmMediaplayer.getCurrentPosition() == 0 && !varForCheckingIfMantraStoppedInBetween) {
            spHkmMediaplayer.setPlaybackParams(spHkmMediaplayer.getPlaybackParams().setSpeed(1));
            spHkmMediaplayer.start();
            timeToStopTimerForChangingText = 4030;
            Log.e("MainActivity", "Successfully started MediaPlayer");
            handlerForTimer.postDelayed(runnableForTimer, (long) (6435));
        } else if (varForCheckingIfMantraStoppedInBetween) {
            timeToStopTimerForChangingText = 4030;
            //updateStartButtonText();
            spHkmMediaplayer.start();
            Log.e("MainActivity", "Failed to initialize MediaPlayer");
        }
    }

    //Pause Button Function
    public void pauseButtonFunction(View view) {
        startButtonImage.setVisibility(View.VISIBLE);
        handlerForTimer.removeCallbacks(runnableForTimer);
        timeBuffForTimer += timeInMsForTimer;
        CommonUtils.vibrateFunction(50,(Vibrator) getSystemService(VIBRATOR_SERVICE));
        if (spHkmMediaplayer.getCurrentPosition() < 6435 && !varForCheckingIfMantraStoppedInBetween) {
            spHkmMediaplayer.pause();
            spHkmMediaplayer.seekTo(0);
        } else {
            varForCheckingIfMantraStoppedInBetween = true;
            spHkmMediaplayer.release();
            spHkmMediaplayer = MediaPlayer.create(MainActivity.this, R.raw.hkm);
            Log.e("MainActivity", "Successfully hkm started MediaPlayer");
        }
    }
    //Reset Button function
    public void resetButtonFunction() {
        startButtonImage.setVisibility(View.VISIBLE);
        handlerForTimer.removeCallbacks(runnableForTimer);
        timeBuffForTimer += timeInMsForTimer;
        CommonUtils.vibrateFunction(50,(Vibrator) getSystemService(VIBRATOR_SERVICE));
        if (spHkmMediaplayer != null) {
            spHkmMediaplayer.stop();
            spHkmMediaplayer.release();
            spHkmMediaplayer = MediaPlayer.create(MainActivity.this, R.raw.sp_hkm);
        }
        timeInMsForTimer = 0L;
        startingTimeForTimerRunnable = 0L;
        timeBuffForTimer = 0L;
        timerTextforTheMantra.setText("00:00");
    }
}
