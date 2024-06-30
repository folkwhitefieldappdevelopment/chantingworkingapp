package com.example.chantingworkingapp;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
    private String[] namaPrabhuStatements = {
            "I am drinking Nama Prabhu",
            "I love Nama Prabhu",
            "I am embracing Nama Prabhu",
            "I am hearing Nama Prabhu",
            "I soulfully chant Nama Prabhu",
            "I am addicted to Nama Prabhu",
            "I am Nama sevaka",
            "I am Nama premi",
            "I am Nama Sadhaka",
            "I am greedy for Nama Prabhu",
            "I will not leave Nama Prabhu",
            "I trust Nama Prabhu",
            "My only hope is Nama Prabhu",
            "Nama is only my life and soul",
            "I am enveloped in the grace of Nama Prabhu",
            "I cherish every syllable of Nama Prabhu",
            "My heart dances with the vibrations of Nama Prabhu",
            "I find solace in the embrace of Nama Prabhu",
            "I am uplifted by the divine sound of Nama Prabhu",
            "I am immersed in the nectar of Nama Prabhu",
            "I am revitalized by the chanting of Nama Prabhu",
            "I draw strength from the holy name, Nama Prabhu",
            "I am illuminated by the light of Nama Prabhu",
            "I am fulfilled by the eternal joy of Nama Prabhu",
            "I surrender to the love of Nama Prabhu",
            "I thrive in the presence of Nama Prabhu",
            "I find my true self in Nama Prabhu",
            "I am guided by the wisdom of Nama Prabhu",
            "I am purified by the chanting of Nama Prabhu",
            "I am embraced by the mercy of Nama Prabhu",
            "I seek refuge in the shelter of Nama Prabhu",
            "I resonate with the divine energy of Nama Prabhu",
            "I am constantly nourished by Nama Prabhu",
            "I find peace in the eternal name, Nama Prabhu",
            "I am connected to the divine through Nama Prabhu",
            "I am absorbed in the divine rhythm of Nama Prabhu",
            "I awaken to the divine presence in Nama Prabhu",
            "I am consoled by the sacred chant of Nama Prabhu",
            "I bloom in the light of Nama Prabhu",
            "I harmonize my life with the essence of Nama Prabhu",
            "I dwell in the sanctuary of Nama Prabhu",
            "I am inspired by the divine call of Nama Prabhu",
            "I flow with the divine currents of Nama Prabhu",
            "I find eternal bliss in the name of Nama Prabhu",
            "I am drawn to the magnetic charm of Nama Prabhu",
            "I transcend worldly bounds through Nama Prabhu",
            "I blossom in the love of Nama Prabhu",
            "I feel the divine embrace in Nama Prabhu",
            "I am constantly rejuvenated by Nama Prabhu",
            "I reach spiritual heights with Nama Prabhu",
            "I bask in the eternal sunshine of Nama Prabhu",
            "I align my soul with the vibrations of Nama Prabhu",
            "I am nourished by the divine melody of Nama Prabhu",
            "I feel divine joy in every chant of Nama Prabhu",
            "I am anchored in the timeless essence of Nama Prabhu",
            "I discover my true path with Nama Prabhu",
            "I am lifted by the pure essence of Nama Prabhu",
            "I am comforted by the sweet sound of Nama Prabhu",
            "I am intertwined with the divine grace of Nama Prabhu",
            "I find boundless joy in the recitation of Nama Prabhu",
            "I radiate the love of Nama Prabhu",
            "I am in tune with the divine name, Nama Prabhu",
            "I am cradled by the serenity of Nama Prabhu",
            "I am sanctified by the holy name, Nama Prabhu"
    };
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

    ImageView srilaPrabhupadaImage, menuHemBurgerIcon, startButtonImage, stopButtonImage, resetButtonImage, unmuteIconImage, muteIconImage, earlyDoneIconImage;
    //Navigation Menu
    DrawerLayout drawerLayoutForMenu;
    NavigationView navigationViewForMenu;
    ActionBarDrawerToggle action;
    //Handlers
    private Handler handlerForTimer;
    //MediaPlayers
    MediaPlayer srilaPrabhupadaChantingWithPanchtattva, srilaPrabhupadaChantingWithOutPanchtattva;
    //Other Variables
    private long timeInMsForTimer, startingTimeForTimerRunnable, timeBuffForTimer, updatingTimeForTimerRunnable = 0;
    private  int secondsForTimer, minutesForTimer, timeToStopTimerForChangingText,hearingCountTextOfUser,chantingCountTextOfSP;
    Boolean varForCheckingIfMantraStoppedInBetween;
    private  FloatingActionButton hearingMovableButton;
    private ProgressBar[] chantingAndHearingProgressBar = new ProgressBar[7];
    private TextView[] chantingAndHearingProgressBarTextViews = new TextView[7];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //TextViews  on the Screen
        spChantingText = findViewById(R.id.spChanting);
        chantingCountTextOfSP= Integer.parseInt(String.valueOf(spChantingText.getText()));
        hearingCountText = findViewById(R.id.hearingCount);
        hearingCountTextOfUser = Integer.parseInt(String.valueOf(hearingCountText.getText()));
        chantingTextDownToTheTextView = findViewById(R.id.chanting_text);

        hearingTextDownHearTextView = findViewById(R.id.Hearing_text);

        hareKrishnaMahaMantraText = findViewById(R.id.HKM);
        soulfulJapaQuoteText = findViewById(R.id.goldencommand);
        timerTextforTheMantra = findViewById(R.id.timetext);
        currentRoundNumber = findViewById(R.id.rounds_text);
        levelCount = findViewById(R.id.levelCount);
        // Initialize your buttons here
        hearingMovableButton = findViewById(R.id.hear);
        hearingMovableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hearingTextviewfunction(v);
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
        unmuteIconImage = findViewById(R.id.unmuteIconImageView);
        muteIconImage = findViewById(R.id.muteIconImageView);
        earlyDoneIconImage = findViewById(R.id.quickDoneImage);
        //Menu Navigation
        drawerLayoutForMenu = findViewById(R.id.drawer_layout);
        navigationViewForMenu = findViewById(R.id.nav_view);
        //Handlers
        handlerForTimer = new Handler(Looper.getMainLooper());
        //MediaPlayers
        srilaPrabhupadaChantingWithPanchtattva = MediaPlayer.create(MainActivity.this, R.raw.sp_hkm);
        srilaPrabhupadaChantingWithOutPanchtattva = MediaPlayer.create(MainActivity.this, R.raw.hkm);
        // Further initialization or event handling can be done here
        listeningCheck();

    }

    private void hearingTextviewfunction(View v) {
        int lowerLimit = 0;
        int upperLimit = 13;
        List<Integer> used = new ArrayList<>();
//
        int randomNumber = generateRandomNumber(lowerLimit, upperLimit,used);
        View layout = getLayoutInflater().inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        // Set your custom message
        TextView textHearQuote = layout.findViewById(R.id.text);

        textHearQuote.setText(String.valueOf(namaPrabhuStatements[randomNumber]));

        // Create and show the custom Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.START | Gravity.CENTER, 1100, 400);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        vibrateFunction(100);
        toast.show();



        hearingCountTextOfUser += 1;
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
    YouTubePlayerView youTubePlayerView;

    public void showVideoPopup(String[] videoToBePlay) {
        // Create a Dialog with a custom layout
        Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);

        // Inflate the custom layout for the popup
        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

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
    public static int generateRandomNumber(int lowerLimit, int upperLimit, List<Integer> usedNumbers) {
        if (lowerLimit > upperLimit) {
            throw new IllegalArgumentException("Lower limit should be less than or equal to upper limit");
        }

        List<Integer> availableNumbers = new ArrayList<>();
        for (int i = lowerLimit; i <= upperLimit; i++) {
            if (!usedNumbers.contains(i)) {
                availableNumbers.add(i);
            }
        }
        Random random = new Random();
        if (availableNumbers.isEmpty()) {
            throw new IllegalStateException("All numbers in the range have been used");
        }

        int randomIndex = random.nextInt(availableNumbers.size());
        int randomNumber = availableNumbers.get(randomIndex);
        usedNumbers.add(randomNumber);

        return randomNumber;
    }


    //Function For Vibration
    private void vibrateFunction(long milliseconds) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

    //Runnables
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
            timerTextforTheMantra.setText(MessageFormat.format("{0}:{1}", minutesForTimer, String.format(Locale.getDefault(), "%02d", secondsForTimer)));
            // Schedule the next update
            handlerForTimer.postDelayed(this, 0);

        }
    };

    //Start Button Function
    public void startButtonFunction(View view) {
        startButtonImage.setVisibility(View.INVISIBLE);
        stopButtonImage.setVisibility(View.VISIBLE);
        startingTimeForTimerRunnable = SystemClock.uptimeMillis();
        handlerForTimer.postDelayed(runnableForTimer, 0);
        if (srilaPrabhupadaChantingWithPanchtattva != null && srilaPrabhupadaChantingWithPanchtattva.getCurrentPosition() == 0 && !varForCheckingIfMantraStoppedInBetween) {
            srilaPrabhupadaChantingWithPanchtattva.setPlaybackParams(srilaPrabhupadaChantingWithPanchtattva.getPlaybackParams().setSpeed(playbackSpeed));
            srilaPrabhupadaChantingWithPanchtattva.start();
            timeToStopTimerForChangingText = 4030;
            Log.e("MainActivity", "Successfully started MediaPlayer");
            handlerForTimer.postDelayed(runnableForTimer, (long) (6435 / playbackSpeed));
        } else if (varForCheckingIfMantraStoppedInBetween) {
            timeToStopTimerForChangingText = 4030;
            //updateStartButtonText();
            Log.e("MainActivity", "Failed to initialize MediaPlayer");
        }

    }

    //Stop Button Function
    public void stopButtonFunction(View view) {
        startButtonImage.setVisibility(View.VISIBLE);
        stopButtonImage.setVisibility(View.INVISIBLE);
        timeBuffForTimer += timeInMsForTimer;


    }
}
