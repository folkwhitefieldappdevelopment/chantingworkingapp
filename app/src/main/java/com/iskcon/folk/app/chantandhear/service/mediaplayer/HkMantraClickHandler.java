package com.iskcon.folk.app.chantandhear.service.mediaplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.factory.MediaPlayerInstanceCreatorFactory;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;
import com.iskcon.folk.app.chantandhear.service.beadcount.JapaMalaViewModel;
import com.iskcon.folk.app.chantandhear.service.beadcount.MalaBeadCounter;
import com.iskcon.folk.app.chantandhear.service.progress.ProgressBarHandler;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HkMantraClickHandler extends AbstractEventHandler {

    private ImageView playButton = null;
    private SpeedButtonHandler speedButtonHandler;
    private MediaPlayer hkMahaMantraMediaPlayer;
    private MediaPlayer panchTattvaMantraMediaPlayer;
    private boolean isMediaPaused;
    private Handler hkMahaMantraHandler;
    private Runnable hkMahaMantraRunnable;
    private Timer malaTimerClock;
    private Handler hkMahaMantraMalaCounterHandler;
    private Runnable hkMahaMantraMalaCounterRunnable;
    private CountDownTimer malaBeadCounter;
    private TextView hareKrishnaMahaMantraTextView;

    public HkMantraClickHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
        this.playButton = appCompatActivity.findViewById(R.id.startIconImageView);
    }

    private void initializeMediaPlayers() {
        this.speedButtonHandler = getAppCompatActivity().getSpeedClickHandler();
        this.hkMahaMantraMediaPlayer = MediaPlayerInstanceCreatorFactory.createInstance(super.getAppCompatActivity(), R.raw.hkm);
        this.panchTattvaMantraMediaPlayer = MediaPlayerInstanceCreatorFactory.createInstance(super.getAppCompatActivity(), R.raw.iu_panch_tattva_mantra);
    }

    @Override
    public void handle(View view) {
        hareKrishnaMahaMantraTextView = getAppCompatActivity().findViewById(R.id.hareKrishnaMahaMantraTextView);
        if (playButton.getVisibility() == View.VISIBLE) { // Start or Resume
            playButton.setVisibility(View.INVISIBLE);
            super.vibrate(50);
            this.initializeMediaPlayers();
            if (isMediaPaused) {
                this.resumeMediaPlayer();
            } else {
                this.startPanchaTattvaMantraMediaPlayer();
            }
        } else if (playButton.getVisibility() == View.INVISIBLE) { // Pause
            hareKrishnaMahaMantraTextView.setText(R.string.main_activity_tap_to_start_your_mala_hare_krishna);
            this.pauseMediaPlayer(view);
        }
    }

    // Start Pancha Tattva Mantra Media player
    private void startPanchaTattvaMantraMediaPlayer() {
        hareKrishnaMahaMantraTextView.setAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(),android.R.anim.fade_in));
        hareKrishnaMahaMantraTextView.setText(R.string.main_activity_pancha_tattva_mantra);
        CountDownTimer countDownTimer = initiateMediaTimeCountDownTimer();
        this.panchTattvaMantraMediaPlayer.setPlaybackParams(this.panchTattvaMantraMediaPlayer.getPlaybackParams().setSpeed(speedButtonHandler.getSpeed()));
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
            long timeElapsed = 0;

            @Override
            public void onTick(long totalMediaDuration) {
                timeElapsed = timeElapsed + 1000;
                String elapsedTime = String.format(Locale.ENGLISH, "0%d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeElapsed), TimeUnit.MILLISECONDS.toSeconds(timeElapsed));
                //roundNoTextView.setText(MessageFormat.format("Round {0}  |  {1} ", getAppCompatActivity().getJapaMalaViewModel().getRoundNumberLiveData().getValue(), getAppCompatActivity().getJapaMalaViewModel().getBeadCounterLiveData().getValue()));
                //textView.setText(MessageFormat.format("{0} / {1}", elapsedTime, totalTime));
                textView.setText(MessageFormat.format("{0}", elapsedTime));
            }

            @Override
            public void onFinish() {
                timeElapsed = 0;
            }
        };
    }

    private void initializeMalaTimerClock() {
        TextView mediaTimerTextView = getAppCompatActivity().findViewById(R.id.mediaTimerTextView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            long timeElapsed = 0;

            @Override
            public void run() {
                timeElapsed = timeElapsed + 1000;
                String elapsedTime = String.format(Locale.ENGLISH, "0%d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeElapsed), TimeUnit.MILLISECONDS.toSeconds(timeElapsed));
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
        return ApplicationConstants.PANCHA_TATTVA_MANTRA_DURATION.getConstantValue(Long.class) + (ApplicationConstants.HARE_KRISHNA_MANTRA_SINGLE_BEAD_DURATION.getConstantValue(Long.class) * ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class));
    }

    // Start Hare Krishna Maha Mantra Media player
    private void startHkMahaMantraSingleMediaPlayer() {
        hkMahaMantraMediaPlayer.setPlaybackParams(hkMahaMantraMediaPlayer.getPlaybackParams().setSpeed(speedButtonHandler.getSpeed()));
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

    public void startHkMahaMantraMultipleMediaPlayer() {
        hareKrishnaMahaMantraTextView.setAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(),android.R.anim.fade_out));
        hareKrishnaMahaMantraTextView.setAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(),android.R.anim.fade_in));
        hareKrishnaMahaMantraTextView.setText(R.string.main_activity_hk_maha_mantra);
        hkMahaMantraMediaPlayer.setPlaybackParams(hkMahaMantraMediaPlayer.getPlaybackParams().setSpeed(speedButtonHandler.getSpeed()));
        final int CURRENT_BEAD_COUNT = getAppCompatActivity().getJapaMalaViewModel().getBeadCounterLiveData().getValue();
        final long COUNT_DOWN_INTERVAL = ApplicationConstants.HARE_KRISHNA_MANTRA_SINGLE_BEAD_DURATION.getConstantValue(Long.class) / Float.valueOf(speedButtonHandler.getSpeed()).longValue();
        this.hkMahaMantraHandler = new Handler();
        this.hkMahaMantraRunnable = new Runnable() {
            @Override
            public void run() {
                hkMahaMantraMediaPlayer.start();
                if (CURRENT_BEAD_COUNT == 0) {
                    getAppCompatActivity().getJapaMalaViewModel().incrementBead();
                    getAppCompatActivity().getFlipperFocusSlideshowHandler().startFlipper(COUNT_DOWN_INTERVAL * ApplicationConstants.FLIP_VIEW_BEAD_AFTER.getConstantValue(Integer.class));
                }
            }
        };
        hkMahaMantraHandler.post(hkMahaMantraRunnable);
        this.hkMahaMantraMalaCounterHandler = new Handler();
        this.hkMahaMantraMalaCounterRunnable = new Runnable() {
            @Override
            public void run() {
                int finalBeadCount = 0;
                if (CURRENT_BEAD_COUNT != 0) {
                    finalBeadCount = CURRENT_BEAD_COUNT;
                    finalBeadCount = finalBeadCount == 1 ? finalBeadCount : finalBeadCount - 1;
                    finalBeadCount = ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class) - finalBeadCount;
                } else {
                    finalBeadCount = ApplicationConstants.TOTAL_BEADS.getConstantValue(Integer.class);
                }
                long millisInFuture = ApplicationConstants.HARE_KRISHNA_MANTRA_SINGLE_BEAD_DURATION.getConstantValue(Long.class) * finalBeadCount;
                malaBeadCounter = new MalaBeadCounter(millisInFuture, COUNT_DOWN_INTERVAL, getAppCompatActivity().getJapaMalaViewModel(), getAppCompatActivity().getHkMantraClickHandler()).start();
            }
        };

        hkMahaMantraMalaCounterHandler.postDelayed(hkMahaMantraMalaCounterRunnable, COUNT_DOWN_INTERVAL);
    }

    // Pause either Hare Krishna Maha Mantra or Pancha Tattva Media player depending on which is being played currently.
    private void pauseMediaPlayer(View view) {
        MediaPlayer mediaPlayer = this.getCurrentMediaPlayer();
        playButton.setVisibility(View.VISIBLE);
        super.vibrate(50);
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        isMediaPaused = true;
        this.destroyResources();
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

    public boolean isHkMahaMantraPlaying() {
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

        this.destroyResources();

        TextView textView = getAppCompatActivity().findViewById(R.id.mediaTimerTextView);
        textView.setText(String.format(Locale.ENGLISH, "0%d:%02d", 0, 0));
        this.initializeMediaPlayers();

        playButton.setVisibility(View.VISIBLE);

        JapaMalaViewModel japaMalaViewModel = super.getAppCompatActivity().getJapaMalaViewModel();
        japaMalaViewModel.resetBead();
        japaMalaViewModel.resetHeard();

        ((TextView) getAppCompatActivity().findViewById(R.id.heardCountTextView)).setText("0");

        new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage(MessageFormat.format("Your {0} Round(s) of mala is completed successfully. Lets immerse into a wonderful video haribol!!", japaMalaViewModel.getRoundNumberLiveData().getValue())).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        new ProgressBarHandler(getAppCompatActivity()).clearProgressBar();
                        getAppCompatActivity().getYoutubeVideoHandler().showVideo();
                        //showSoulFullJapaCardVideo();
                    }
                }).show();

        getAppCompatActivity().getFlipperFocusSlideshowHandler().stopFlipper();
    }

    private void destroyResources() {
        if (hkMahaMantraHandler != null) {
            hkMahaMantraHandler.removeCallbacks(hkMahaMantraRunnable);
            hkMahaMantraMalaCounterHandler.removeCallbacks(hkMahaMantraMalaCounterRunnable);
            if (malaBeadCounter != null) {
                malaBeadCounter.cancel();
            }
            malaTimerClock.cancel();
            malaTimerClock.purge();
            getAppCompatActivity().getFlipperFocusSlideshowHandler().pauseFlipper();
        }
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
            if (panchTattvaMantraMediaPlayer.getCurrentPosition() > 0) {
                panchTattvaMantraMediaPlayer.stop();
                panchTattvaMantraMediaPlayer.release();
                panchTattvaMantraMediaPlayer = null;
            }
        }
    }
}