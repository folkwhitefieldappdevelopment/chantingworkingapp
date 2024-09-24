package com.iskcon.folk.app.chantandhear.service.progress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.constant.Milestone;
import com.iskcon.folk.app.chantandhear.constant.VideoType;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;
import com.iskcon.folk.app.chantandhear.service.HeardButtonHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.HkMantraClickHandler;

public class ProgressBarHandler extends AbstractEventHandler {

    private Milestone currentMilestone;
    private int progressData;
    private boolean attentiveVideoShown;

    public ProgressBarHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
    }

    public void initializeProgressBar() {
        LinearLayout linearLayout = super.getAppCompatActivity().findViewById(Milestone.MILESTONE_1.getLinearLayoutId());
        if (View.INVISIBLE == linearLayout.getVisibility()) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBar(int currentBeadCount) {

        this.checkAndShowNoHeardVideo(currentBeadCount);
        this.showProgressBar(currentBeadCount, true);
    }

    private void showProgressBar(int currentHeardCount, boolean updateCurrentMilestone) {
        Milestone milestone = Milestone.beadInBetweenWhichMilestoe(currentHeardCount);
        if (milestone != null) {
            if (updateCurrentMilestone) {
                currentMilestone = milestone;
            }
            LinearLayout linearLayout = super.getAppCompatActivity().findViewById(milestone.getLinearLayoutId());
            if (View.INVISIBLE == linearLayout.getVisibility()) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void incrementProgressBar(int currentHeardCount) {
        this.showProgressBar(currentHeardCount, false);
        if (!super.getAppCompatActivity().getHkMantraClickHandler().isMediaPaused()) {
            Milestone milestone = Milestone.beadInBetweenWhichMilestoe(currentHeardCount);
            if (currentMilestone == null) {
                currentMilestone = milestone;
            }
            if (milestone != null) {
                ProgressBar progressBar = super.getAppCompatActivity().findViewById(milestone.getProgressBarId());
                if (Milestone.MILESTONE_7.getMilestoneId() == milestone.getMilestoneId()) {
                    progressBar.setMax(12);
                } else {
                    progressBar.setMax(ApplicationConstants.TOTAL_BEADS_IN_A_MILESTONE.getConstantValue(Integer.class));
                }
                progressData = progressData + getAppCompatActivity().getHearButtonHandler().getLevelCountValue();
                progressBar.setProgress(progressData, true);
                TextView textView = getAppCompatActivity().findViewById(milestone.getHeardCountTextViewId());
                textView.setText(String.valueOf(progressData));
                if (progressData == 16 || (Milestone.MILESTONE_7 == milestone && progressData == 12)) {
                    MediaPlayer milestoneMediaPlayer = MediaPlayer.create(getAppCompatActivity(), R.raw.beat16);
                    milestoneMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                    });
                    milestoneMediaPlayer.start();
                    progressData = 0;
                    this.showProgressBar(currentHeardCount);
                    HeardButtonHandler heardButtonHandler = getAppCompatActivity().getHearButtonHandler();
                    //heardButtonHandler.handleLevelUp(null);
                    heardButtonHandler.animateLevelChange();
                }
            }
        }
    }

    public void clearProgressBar() {
        for (Milestone milestone : Milestone.values()) {
            ProgressBar progressBar = super.getAppCompatActivity().findViewById(milestone.getProgressBarId());
            progressBar.setProgress(0);
            LinearLayout linearLayout = super.getAppCompatActivity().findViewById(milestone.getLinearLayoutId());
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void checkAndShowNoHeardVideo(int currentBeadCount) {
        if (!attentiveVideoShown && (currentBeadCount > 16 && getAppCompatActivity().getJapaMalaViewModel().getHeardCounterLiveData().getValue() == 0)) {
            attentiveVideoShown = true;
            HkMantraClickHandler hkMantraClickHandler = getAppCompatActivity().getHkMantraClickHandler();
            hkMantraClickHandler.pauseMediaPlayer();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getAppCompatActivity());
            String message = "The process is that you chant and must hear the very sound. Your not clicking on heard button, please tap it promptly after every bead you have heard.";
            alertDialogBuilder.setTitle("Hare Krishna").setMessage(message);
            alertDialogBuilder.setPositiveButton("I will be attentive", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    hkMantraClickHandler.resumeMediaPlayer();
                    //attentiveVideoShown = false;
                }
            });
            alertDialogBuilder.setNegativeButton("Enlighten me more", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //attentiveVideoShown = false;
                    dialogInterface.cancel();
                    getAppCompatActivity().getYoutubeVideoHandler().showVideo(VideoType.ATTENTIVE);
                }
            });
            alertDialogBuilder.show();
        }
    }

    public void setAttentiveVideoShown(boolean attentiveVideoShown) {
        this.attentiveVideoShown = attentiveVideoShown;
    }
}