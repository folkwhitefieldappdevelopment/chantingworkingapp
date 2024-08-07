package com.example.chantingworkingapp;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.chantingworkingapp.databinding.ActivityMainBinding;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.service.HearButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.HkMantraClickHandler;
import com.example.chantingworkingapp.service.mediaplayer.MuteButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.ResetButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.SpeedButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.UnmuteButtonHandler;
import com.example.chantingworkingapp.util.CommonUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private Button LevelAddButton,LevelMinusButton;
    private final HearButtonHandler hearButtonHandler = new HearButtonHandler(this);
    private ResetButtonHandler resetButtonHandler;
    private MuteButtonHandler muteButtonHandler;
    private UnmuteButtonHandler unMuteButtonHandler;
    private HkMantraClickHandler hkMantraClickHandler;
    private SpeedButtonHandler speedClickhandler;

    private JapaMalaModel japaMalaModel = null;
    private float dx;
    private float dy;
    private int lastAction;
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
    private ActivityMainBinding binding;
    private GestureDetector gestureDetector;
    private ImageView speedMenuButton;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        japaMalaModel = this.initializeJapaMalaModel();
        gestureDetector = new GestureDetector(this, new GestureListener());

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //binding.HearingText.setOnTouchListener(this);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyApp::WakeLockTag");
        wakeLock.acquire();
        //TextViews  on the Screen
        spChantingText = findViewById(R.id.spChanting);
        //chantingCountTextOfSP= Integer.parseInt(String.valueOf(spChantingText.getText()));
        hearingCountText = findViewById(R.id.hearingCount);
        //hearingCountTextOfUser = Integer.parseInt(String.valueOf(hearingCountText.getText()));
        chantingTextDownToTheTextView = findViewById(R.id.chanting_text);

        hearingTextDownHearTextView = findViewById(R.id.Hearing_text);

        soulfulJapaQuoteText = findViewById(R.id.goldencommand);
        timerTextforTheMantra = findViewById(R.id.timetext);
        currentRoundNumber = findViewById(R.id.rounds_text);
        levelCount = findViewById(R.id.levelCount);
        // Initialize your buttons here

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

        //MediaPlayers
        spHkmMediaplayer = MediaPlayer.create(MainActivity.this, R.raw.sp_hkm);
        resetButtonHandler = new ResetButtonHandler(this,spHkmMediaplayer);
        muteButtonHandler = new MuteButtonHandler(this,spHkmMediaplayer);
        unMuteButtonHandler = new UnmuteButtonHandler(this,spHkmMediaplayer);
        hkMantraClickHandler = new HkMantraClickHandler(this,spHkmMediaplayer);
        speedClickhandler = new SpeedButtonHandler(this,spHkmMediaplayer);
        srilaPrabhupadaChantingWithOutPanchtattva = MediaPlayer.create(MainActivity.this, R.raw.hkm);
        //ImageViews
        srilaPrabhupadaImage = findViewById(R.id.SpImage);
        menuHemBurgerIcon = findViewById(R.id.hamburgerIcon);
        startButtonImage = findViewById(R.id.startIconImageView);
        speedMenuButton = findViewById(R.id.speedMenuImage);
        speedMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedClickhandler.handle(japaMalaModel,v);
            }
        });
        hareKrishnaMahaMantraText = findViewById(R.id.HKM);
        hareKrishnaMahaMantraText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hkMantraClickHandler.handle(japaMalaModel,v);
            }
        });

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

        hearingMovableButton = findViewById(R.id.hear);
        binding.hear.setOnTouchListener(this);

        LevelAddButton = findViewById(R.id.LevelAdd);
        LevelAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int test = Integer.parseInt(levelCount.getText().toString());
                if(test<8){
                    test = test +1;
                    levelCount.setText(String.valueOf(test));
                }
            }
        });
        LevelMinusButton = findViewById(R.id.LevelMinus);
        LevelMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int test = Integer.parseInt(levelCount.getText().toString());
                if(test>2){
                    test = test -1;
                    levelCount.setText(String.valueOf(test));
                }
            }
        });

        earlyDoneIconImage = findViewById(R.id.quickDoneImage);
        //Menu Navigation
        drawerLayoutForMenu = findViewById(R.id.drawer_layout);
        navigationViewForMenu = findViewById(R.id.nav_view);
        //Handlers
        handlerForTimer = new Handler(Looper.getMainLooper());
        // Further initialization or event handling can be done here
        listeningCheck();
    }

    private JapaMalaModel initializeJapaMalaModel(){
        return new JapaMalaModel();
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
   @Override
   public boolean onTouch(View view , MotionEvent event){
       Log.d("TouchEvent", "triggered: " + event.getAction());
       gestureDetector.onTouchEvent(event);

       switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                dx = view.getX()-event.getRawX();
                dy = view.getY()-event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                Log.d("TouchEvent", "touch1: " + event.getAction());

                break;
            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY()+dy);
                view.setX(event.getRawX()+dx);
                lastAction = MotionEvent.ACTION_MOVE;
                Log.d("TouchEvent", "touch2: " + event.getAction());

                break;
            case MotionEvent.ACTION_UP:
                if(lastAction== MotionEvent.ACTION_DOWN) {
                    lastAction = event.getAction();
                    Log.d("TouchEvent", "touch: " + event.getAction());

                }
                break;
            default:
                return false;
        }
        return true;
   }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // Handle single tap (similar to click)
            hearButtonHandler.handle(japaMalaModel, binding.HearingText);

            Log.d("GestureDetector", "Single Tap Up");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // Handle long press
            Log.d("GestureDetector", "Long Press");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Handle scroll
            Log.d("GestureDetector", "Scroll");
            return true;
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

    public HkMantraClickHandler getHkMantraClickHandler() {
        return hkMantraClickHandler;
    }
}