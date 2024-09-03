package com.example.chantingworkingapp.service.mediaplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.constant.ApplicationConstants;
import com.example.chantingworkingapp.factory.MediaPlayerInstanceCreatorFactory;
import com.example.chantingworkingapp.service.AbstractEventHandler;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.service.beadcount.JapaMalaViewModel;
import com.example.chantingworkingapp.service.beadcount.MalaBeadCounter;
import com.example.chantingworkingapp.service.progress.ProgressBarHandler;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HkMantraClickHandler extends AbstractEventHandler {

    private ImageView playButton = null;
    private final SpeedButtonHandler speedButton;
    private MediaPlayer hkMahaMantraMediaPlayer;
    private MediaPlayer panchTattvaMantraMediaPlayer;
    private boolean isMediaPaused;
    private Handler hkMahaMantraHandler;
    private Runnable hkMahaMantraRunnable;
    private Timer malaTimerClock;

    public HkMantraClickHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
        this.playButton = appCompatActivity.findViewById(R.id.startIconImageView);
        speedButton = new SpeedButtonHandler(appCompatActivity, null);
    }

    private void initializeMediaPlayers() {
        this.hkMahaMantraMediaPlayer = MediaPlayerInstanceCreatorFactory.createInstance(super.getAppCompatActivity(), R.raw.hkm);
        this.panchTattvaMantraMediaPlayer = MediaPlayerInstanceCreatorFactory.createInstance(super.getAppCompatActivity(), R.raw.iu_panch_tattva_mantra);
    }

    @Override
    public void handle(JapaMalaModel japaMalaModel, View view) {
        if (playButton.getVisibility() == View.VISIBLE) { //Resume
            playButton.setVisibility(View.INVISIBLE);
            super.vibrate(50);
            this.initializeMediaPlayers();
            if (isMediaPaused) {
                this.resumeMediaPlayer();
            } else {
                this.startPanchaTattvaMantraMediaPlayer();
            }
        } else if (playButton.getVisibility() == View.INVISIBLE) { //Pause
            this.pauseMediaPlayer(view);
        }
    }

    // Start Pancha Tattva Mantra Media player
    private void startPanchaTattvaMantraMediaPlayer() {
        Log.i("TAG = ", "getTotalMediaDuration = : " + this.getTotalMediaDuration());
        CountDownTimer countDownTimer = initiateMediaTimeCountDownTimer();
        panchTattvaMantraMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer panchtattvaMediaPlayer) {
                panchtattvaMediaPlayer.stop();
                panchtattvaMediaPlayer.release();
                panchTattvaMantraMediaPlayer = null;
                //startHkMahaMantraSingleMediaPlayer();
                startHkMahaMantraMultipleMediaPlayer();
            }
        });
        panchTattvaMantraMediaPlayer.start();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initiateMediaTimeCountDownTimer().start();
            }
        }, 500);*/

        initializeMalaTimerClock();
    }

    private CountDownTimer initiateMediaTimeCountDownTimer() {
        long totalMediaTime = this.getTotalMediaDuration();
        Log.i("TAG = ", "totalMediaTime = : " + totalMediaTime);
        TextView textView = getAppCompatActivity().findViewById(R.id.mediaTimerTextView);
        //TextView roundNoTextView = getAppCompatActivity().findViewById(R.id.roundNoTextView);
        String totalTime = String.format(Locale.ENGLISH, "0%d:%02d", TimeUnit.MILLISECONDS.toMinutes(totalMediaTime), TimeUnit.MILLISECONDS.toSeconds(totalMediaTime));
        textView.setText(totalTime);
        return new CountDownTimer(720000, 1000) {
            long timeElasped = 0;

            @Override
            public void onTick(long totalMediaDuration) {
                timeElasped = timeElasped + 1000;
                String elapsedTime = String.format(Locale.ENGLISH, "0%d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeElasped), TimeUnit.MILLISECONDS.toSeconds(timeElasped));
                //roundNoTextView.setText(MessageFormat.format("Round {0}  |  {1} ", getAppCompatActivity().getJapaMalaViewModel().getRoundNumberLiveData().getValue(), getAppCompatActivity().getJapaMalaViewModel().getBeadCounterLiveData().getValue()));
                //textView.setText(MessageFormat.format("{0} / {1}", elapsedTime, totalTime));
                textView.setText(MessageFormat.format("{0}", elapsedTime));
            }

            @Override
            public void onFinish() {
                timeElasped = 0;
            }
        };
    }

    private void initializeMalaTimerClock() {
        TextView mediaTimerTextView = getAppCompatActivity().findViewById(R.id.mediaTimerTextView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            long timeElasped = 0;

            @Override
            public void run() {
                timeElasped = timeElasped + 1000;
                String elapsedTime = String.format(Locale.ENGLISH, "0%d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeElasped), TimeUnit.MILLISECONDS.toSeconds(timeElasped));
                mediaTimerTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        mediaTimerTextView.setText(MessageFormat.format("{0}", elapsedTime));
                    }
                });
            }
        }, 50, 1000);
        this.malaTimerClock = timer;
    }

    private long getTotalMediaDuration() {
        return ApplicationConstants.PANCHA_TATTVA_MANTRA_DURATION.getConstantValue(Long.class) + (ApplicationConstants.HARE_KRISHNA_MANTRA_DURATION.getConstantValue(Long.class) * ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class));
    }

    // Start Hare Krishna Maha Mantra Media player
    private void startHkMahaMantraSingleMediaPlayer() {
        hkMahaMantraMediaPlayer.setPlaybackParams(hkMahaMantraMediaPlayer.getPlaybackParams().setSpeed(Float.parseFloat(speedButton.getSpeed())));
        this.hkMahaMantraHandler = new Handler();
        this.hkMahaMantraRunnable = new Runnable() {
            @Override
            public void run() {
                hkMahaMantraMediaPlayer.start();
                getAppCompatActivity().getJapaMalaViewModel().incrementBead();
            }
        };
        hkMahaMantraHandler.postDelayed(hkMahaMantraRunnable, 100);
        hkMahaMantraMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Integer beadCount = getAppCompatActivity().getJapaMalaViewModel().getBeadCounterLiveData().getValue();
                getAppCompatActivity().getJapaMalaViewModel().incrementBead();
                if (beadCount != null && beadCount < ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class)) {
                    TextView textView = getAppCompatActivity().findViewById(R.id.hareKrishnaMahaMantraTextView);
                    textView.animate().setDuration(250).scaleX(1.1f).scaleY(1.1f).withEndAction(() -> textView.animate().scaleX(1).scaleY(1));
                    mediaPlayer.start();
                } else {
                    onMalaCompleted();
                }
            }
        });
        Log.i(this.getClass().getSimpleName(), "mediaPlayer.getDuration() = " + hkMahaMantraMediaPlayer.getDuration());
    }

    private void startHkMahaMantraMultipleMediaPlayer() {
        hkMahaMantraMediaPlayer.setPlaybackParams(hkMahaMantraMediaPlayer.getPlaybackParams().setSpeed(Float.parseFloat(speedButton.getSpeed())));
        this.hkMahaMantraHandler = new Handler();
        this.hkMahaMantraRunnable = new Runnable() {
            @Override
            public void run() {
                hkMahaMantraMediaPlayer.start();
                getAppCompatActivity().getJapaMalaViewModel().incrementBead();
            }
        };
        hkMahaMantraHandler.postDelayed(hkMahaMantraRunnable, 100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long countDownInterval = 4035 / Float.valueOf(speedButton.getSpeed()).longValue();
                MalaBeadCounter  malaBeadCounter = new MalaBeadCounter(4035L * ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class), countDownInterval, getAppCompatActivity().getJapaMalaViewModel(),getAppCompatActivity().getHkMantraClickHandler());
                malaBeadCounter.start();
            }
        }, 4000);
    }

    // Pause either Hare Krishna Maha Mantra or Pancha Tattva Media player depending on which is being played currently.
    private void pauseMediaPlayer(View view) {
        MediaPlayer mediaPlayer = this.getCurrentMediaPlayer();
        playButton.setVisibility(View.VISIBLE);
        super.vibrate(50);
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        isMediaPaused = true;
    }

    // Reset Media Player play from the starting of the mala including Pancha Tattva mantra.
    public void resetMediaPlayer() {
        this.startPanchaTattvaMantraMediaPlayer();
    }

    // Pause either Hare Krishna Maha Mantra or Pancha Tattva Media player depending on which is being played currently.
    private void resumeMediaPlayer() {
        if (panchTattvaMantraMediaPlayer != null && panchTattvaMantraMediaPlayer.getCurrentPosition() > 0) {
            startPanchaTattvaMantraMediaPlayer();
        } else {
           // startHkMahaMantraSingleMediaPlayer();
            startHkMahaMantraMultipleMediaPlayer();
        }
        isMediaPaused = false;
        if (this.isHkMahaMantraPlaying()) {
            getAppCompatActivity().getJapaMalaViewModel().decrementBead();
        }
    }

    // Getting instance of either Hare Krishna Maha Mantra or Pancha Tattva Media player depending on which is being played currently.
    public MediaPlayer getCurrentMediaPlayer() {
        MediaPlayer mediaPlayer;
        try {
            if (hkMahaMantraMediaPlayer.isPlaying()) {
                mediaPlayer = hkMahaMantraMediaPlayer;
                Log.i("getCurrentMediaPlayer", "hkMahaMantraMediaPlayer.isPlaying() = " + hkMahaMantraMediaPlayer.isPlaying());
            } else {
                mediaPlayer = panchTattvaMantraMediaPlayer;
                Log.i("getCurrentMediaPlayer", "panchTattvaMantraMediaPlayer.isPlaying() = " + panchTattvaMantraMediaPlayer.isPlaying());
            }
        } catch (Exception e) {
            mediaPlayer = hkMahaMantraMediaPlayer;
        }
        return mediaPlayer;
    }

    private boolean isHkMahaMantraPlaying() {
        boolean isPlaying;
        try {
            isPlaying = hkMahaMantraMediaPlayer.isPlaying() || hkMahaMantraMediaPlayer.getCurrentPosition() > 1;
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
            isPlaying = false;
        }
        return isPlaying;
    }

    public void onMalaCompleted() {

        hkMahaMantraMediaPlayer.stop();
        hkMahaMantraMediaPlayer.release();
        hkMahaMantraHandler.removeCallbacks(hkMahaMantraRunnable);
        malaTimerClock.cancel();
        malaTimerClock.purge();

        TextView textView = getAppCompatActivity().findViewById(R.id.mediaTimerTextView);
        textView.setText(String.format(Locale.ENGLISH, "0%d:%02d", 0, 0));
        this.initializeMediaPlayers();

        playButton.setVisibility(View.VISIBLE);

        JapaMalaViewModel japaMalaViewModel = super.getAppCompatActivity().getJapaMalaViewModel();
        japaMalaViewModel.resetBead();

        int currentRoundNumber = japaMalaViewModel.getRoundNumberLiveData().getValue();
        japaMalaViewModel.incrementRound();
        japaMalaViewModel.resetHeard();
        ((TextView)getAppCompatActivity().findViewById(R.id.heardCountTextView)).setText("0");
        new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage(MessageFormat.format("Your {0} Round(s) of mala is completed successfully. Lets immerse into a wonderful video haribol!!", currentRoundNumber)).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        new ProgressBarHandler(getAppCompatActivity()).clearProgressBar();
                        getAppCompatActivity().getYoutubeVideoHandler().showVideo();
                        //showSoulFullJapaCardVideo();
                    }
                }).show();
    }

    public void destroy() {
        MediaPlayer mediaPlayer = getCurrentMediaPlayer();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        if (!isHkMahaMantraPlaying() && panchTattvaMantraMediaPlayer != null) {
            if (panchTattvaMantraMediaPlayer.isPlaying()) {
                panchTattvaMantraMediaPlayer.stop();
            }
            panchTattvaMantraMediaPlayer.release();
            panchTattvaMantraMediaPlayer = null;
        }
    }
}