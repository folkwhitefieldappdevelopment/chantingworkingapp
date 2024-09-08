package com.example.chantingworkingapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.chantingworkingapp.constant.ApplicationConstants;
import com.example.chantingworkingapp.databinding.ActivityMainBinding;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.service.FlipperFocusSlideshowHandler;
import com.example.chantingworkingapp.service.HearButtonHandler;
import com.example.chantingworkingapp.service.beadcount.JapaMalaViewModel;
import com.example.chantingworkingapp.service.mediaplayer.HkMantraClickHandler;
import com.example.chantingworkingapp.service.mediaplayer.MuteButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.ResetButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.SpeedButtonHandler;
import com.example.chantingworkingapp.service.mediaplayer.UnmuteButtonHandler;
import com.example.chantingworkingapp.service.progress.ProgressBarHandler;
import com.google.android.material.navigation.NavigationView;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private HearButtonHandler hearButtonHandler;
    private ResetButtonHandler resetButtonHandler;
    private MuteButtonHandler muteButtonHandler;
    private UnmuteButtonHandler unMuteButtonHandler;
    private HkMantraClickHandler hkMantraClickHandler;
    private SpeedButtonHandler speedClickHandler;
    private JapaMalaViewModel japaMalaViewModel;
    private YoutubeVideoHandler youtubeVideoHandler;
    private FlipperFocusSlideshowHandler flipperFocusSlideshowHandler;

    private JapaMalaModel japaMalaModel = null;
    private float dx;

    private float dy;
    private int lastAction;
    TextView spChantingText;
    TextView hearingCountText;

    ImageView menuHemBurgerIcon;
    ImageView startButtonImage;
    ImageView resetButtonImage;
    ImageView unmuteIconImage;
    ImageView muteIconImage;

    //Navigation Menu
    DrawerLayout drawerLayoutForMenu;
    NavigationView navigationViewForMenu;

    //MediaPlayers
    MediaPlayer spHkmMediaplayer, srilaPrabhupadaChantingWithOutPanchtattva;

    //Other Variables
    private PowerManager.WakeLock wakeLock;
    private ActivityMainBinding binding;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.japaMalaViewModel = this.initializeJapaMalaViewModel();

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
        // Initialize your buttons here

        //MediaPlayers
        spHkmMediaplayer = MediaPlayer.create(MainActivity.this, R.raw.iu_maha_mantra_one_bead_1);

        resetButtonHandler = new ResetButtonHandler(this, spHkmMediaplayer);
        muteButtonHandler = new MuteButtonHandler(this, spHkmMediaplayer);
        unMuteButtonHandler = new UnmuteButtonHandler(this, spHkmMediaplayer);
        hkMantraClickHandler = new HkMantraClickHandler(this);
        youtubeVideoHandler = new YoutubeVideoHandler(this);
        speedClickHandler = new SpeedButtonHandler(this, spHkmMediaplayer);
        hearButtonHandler = new HearButtonHandler(this);
        flipperFocusSlideshowHandler = new FlipperFocusSlideshowHandler(this);

        srilaPrabhupadaChantingWithOutPanchtattva = MediaPlayer.create(MainActivity.this, R.raw.hkm);

        //ImageViews
        menuHemBurgerIcon = findViewById(R.id.hamburgerIcon);
        startButtonImage = findViewById(R.id.startIconImageView);

        findViewById(R.id.speedMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedClickHandler.handle(v);
            }
        });
        TextView hareKrishnaMahaMantraTextView = findViewById(R.id.hareKrishnaMahaMantraTextView);
        hareKrishnaMahaMantraTextView.setText(R.string.main_activity_tap_to_start_your_mala_hare_krishna);
        hareKrishnaMahaMantraTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hkMantraClickHandler.handle(v);
            }
        });
        resetButtonImage = findViewById(R.id.resetIconImageView);
        resetButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButtonHandler.handle(view);
            }
        });
        muteIconImage = findViewById(R.id.muteIconImageView);
        muteIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muteButtonHandler.handle(view);
            }
        });
        unmuteIconImage = findViewById(R.id.unmuteIconImageView);
        unmuteIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unMuteButtonHandler.handle(view);
            }
        });
        binding.hearButton.setOnTouchListener(this);

        findViewById(R.id.hearingLevelUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hearButtonHandler.handleLevelUp(v);
            }
        });
        findViewById(R.id.hearingLevelDown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hearButtonHandler.handleLevelDown(v);
            }
        });

        flipperFocusSlideshowHandler.initializeFlipper();

        // Further initialization or event handling can be done here
        listeningCheck();
    }

    private JapaMalaModel initializeJapaMalaModel() {
        return new JapaMalaModel();
    }

    private void listeningCheck() {
        int notHearingTime = 1;
        boolean userListeningCheck = true;
        //if (chantingCountTextOfSP - hearingCountTextOfUser >= 15 * notHearingTime) {
        userListeningCheck = false;
        //}
        if (userListeningCheck) {
            notHearingTime = 0;
            notHearingTime += 1;
            youtubeVideoHandler.showVideo();
            userListeningCheck = true;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Log.d("TouchEvent", "triggered: " + event.getAction());
        gestureDetector.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dx = view.getX() - event.getRawX();
                dy = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                Log.d("TouchEvent", "touch1: " + event.getAction());
                break;
            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dy);
                view.setX(event.getRawX() + dx);
                lastAction = MotionEvent.ACTION_MOVE;
                Log.d("TouchEvent", "touch2: " + event.getAction());
                break;
            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN) {
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
            hearButtonHandler.handle(null);

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

    private JapaMalaViewModel initializeJapaMalaViewModel() {
        JapaMalaViewModel japaMalaViewModel = new ViewModelProvider(this).get(JapaMalaViewModel.class);
        ProgressBarHandler progressBarManager = new ProgressBarHandler(this);
        Observer<Integer> beadCountIncrementObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentMalaBeadCount) {
                if (currentMalaBeadCount != null && currentMalaBeadCount <= ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class)) {
                    if (currentMalaBeadCount == 2) {
                        progressBarManager.initializeProgressBar();
                    }
                    TextView beadCountTextView = findViewById(R.id.beadCountTextView);
                    beadCountTextView.setText(String.valueOf(currentMalaBeadCount));
                    TextView textView = findViewById(R.id.hareKrishnaMahaMantraTextView);
                    textView.animate().setDuration(250).scaleX(1.1f).scaleY(1.1f).withEndAction(() -> textView.animate().scaleX(1).scaleY(1));
                } else {
                    hkMantraClickHandler.onMalaCompleted();
                }
                Log.i(this.getClass().getSimpleName(), "beadCountIncrementObserver.onChanged: " + currentMalaBeadCount);
            }
        };
        japaMalaViewModel.getBeadCounterLiveData().observe(this, beadCountIncrementObserver);

        Observer<Integer> roundIncrementObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentRoundNumber) {
                if (currentRoundNumber != null && currentRoundNumber <= ApplicationConstants.TOTAL_ROUND.getConstantValue(Integer.class)) {
                    TextView roundNoTextView = findViewById(R.id.roundNoTextView);
                    roundNoTextView.setText(String.valueOf(MessageFormat.format("Round {0}", currentRoundNumber)));
                }
                Log.i(this.getClass().getSimpleName(), "roundIncrementObserver.onChanged: " + currentRoundNumber);
            }
        };
        japaMalaViewModel.getRoundNumberLiveData().observe(this, roundIncrementObserver);

        Observer<Integer> heardCountIncrementObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentHeardCount) {
                if (currentHeardCount != null && currentHeardCount <= ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class)) {
                    if (currentHeardCount != 0) {
                        progressBarManager.manageProgress(currentHeardCount);
                        TextView heardCountTextView = findViewById(R.id.heardCountTextView);
                        heardCountTextView.setText(String.valueOf(currentHeardCount));
                    }
                }
                Log.i(this.getClass().getSimpleName(), "heardCountIncrementObserver.onChanged: " + currentHeardCount);
            }
        };
        japaMalaViewModel.getHeardCounterLiveData().observe(this, heardCountIncrementObserver);

        return japaMalaViewModel;
    }

    public JapaMalaViewModel getJapaMalaViewModel() {
        return japaMalaViewModel;
    }

    public HkMantraClickHandler getHkMantraClickHandler() {
        return hkMantraClickHandler;
    }

    public YoutubeVideoHandler getYoutubeVideoHandler() {
        return youtubeVideoHandler;
    }

    public SpeedButtonHandler getSpeedClickHandler() {
        return speedClickHandler;
    }

    public FlipperFocusSlideshowHandler getFlipperFocusSlideshowHandler() {
        return flipperFocusSlideshowHandler;
    }

    @Override
    protected void onDestroy() {
        hkMantraClickHandler.destroy();
        super.onDestroy();
    }
}