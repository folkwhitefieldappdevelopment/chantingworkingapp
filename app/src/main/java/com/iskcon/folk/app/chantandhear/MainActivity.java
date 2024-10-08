package com.iskcon.folk.app.chantandhear;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.databinding.ActivityMainBinding;
import com.iskcon.folk.app.chantandhear.model.JapaMalaModel;
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.service.FlipperFocusSlideshowHandler;
import com.iskcon.folk.app.chantandhear.service.HeardButtonHandler;
import com.iskcon.folk.app.chantandhear.service.HistoryViewClickHandler;
import com.iskcon.folk.app.chantandhear.service.beadcount.JapaMalaViewModel;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.BeforeDoneClickHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.HkMantraClickHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.MuteButtonHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.ResetButtonHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.SpeedButtonHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.UnmuteButtonHandler;
import com.iskcon.folk.app.chantandhear.service.progress.ProgressBarHandler;
import com.google.android.material.navigation.NavigationView;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private HeardButtonHandler hearButtonHandler;
    private ResetButtonHandler resetButtonHandler;
    private MuteButtonHandler muteButtonHandler;
    private UnmuteButtonHandler unMuteButtonHandler;
    private HkMantraClickHandler hkMantraClickHandler;
    private SpeedButtonHandler speedClickHandler;
    private JapaMalaViewModel japaMalaViewModel;
    private YoutubeVideoHandler youtubeVideoHandler;
    private FlipperFocusSlideshowHandler flipperFocusSlideshowHandler;
    private BeforeDoneClickHandler beforeDoneClickHandler;
    private ProgressBarHandler progressBarHandler;
    private HistoryViewClickHandler historyViewOpenHandler;
    private JapaMalaModel japaMalaModel = null;
    private UserDetails userDetails;
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

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int initialRoundNumber = Integer.valueOf(getIntent().getExtras().get("completedRounds").toString());

        initialRoundNumber++;
        ((TextView) findViewById(R.id.roundNoTextView)).setText(MessageFormat.format("Round {0}", initialRoundNumber));

        this.japaMalaViewModel = this.initializeJapaMalaViewModel(initialRoundNumber);

        japaMalaModel = this.initializeJapaMalaModel();
        gestureDetector = new GestureDetector(this, new GestureListener());

        userDetails = (UserDetails) getIntent().getExtras().get("userDetails");

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
        hearButtonHandler = new HeardButtonHandler(this);
        flipperFocusSlideshowHandler = new FlipperFocusSlideshowHandler(this);
        beforeDoneClickHandler = new BeforeDoneClickHandler(this);
        progressBarHandler = new ProgressBarHandler(this);
        historyViewOpenHandler = new HistoryViewClickHandler();
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

        ((ImageView) findViewById(R.id.beforeDoneImageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beforeDoneClickHandler.handle(view);
            }
        });

        ((LinearLayout) findViewById(R.id.heardLinearLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyViewOpenHandler.showHistoryPopup(view,userDetails,false);
            }
        });

        ((LinearLayout) findViewById(R.id.heardLinearLayout)).setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_10_times));
    }

    private JapaMalaModel initializeJapaMalaModel() {
        return new JapaMalaModel();
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

    private JapaMalaViewModel initializeJapaMalaViewModel(int initialRoundNoValue) {
        JapaMalaViewModel japaMalaViewModel = new ViewModelProvider(this).get(JapaMalaViewModel.class);
        japaMalaViewModel.setInitialRoundNoValue(initialRoundNoValue);
        Observer<Integer> beadCountIncrementObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentMalaBeadCount) {
                if (currentMalaBeadCount != null && currentMalaBeadCount <= ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class)) {
                    TextView beadCountTextView = findViewById(R.id.beadCountTextView);
                    beadCountTextView.setText(String.valueOf(currentMalaBeadCount));
                    TextView textView = findViewById(R.id.hareKrishnaMahaMantraTextView);
                    textView.animate().setDuration(500).scaleX(1.1f).scaleY(1.1f).withEndAction(() -> textView.animate().scaleX(1).scaleY(1));
                    progressBarHandler.showProgressBar(currentMalaBeadCount);
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

    public HeardButtonHandler getHearButtonHandler() {
        return hearButtonHandler;
    }

    public ProgressBarHandler getProgressBarHandler() {
        return progressBarHandler;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public HistoryViewClickHandler getHistoryViewOpenHandler() {
        return historyViewOpenHandler;
    }

    @Override
    protected void onDestroy() {
        hkMantraClickHandler.destroy();
        super.onDestroy();
    }
}