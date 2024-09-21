package com.iskcon.folk.app.chantandhear.service.progress;

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
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;

public class ProgressBarHandler extends AbstractEventHandler {

    private Milestone currentMilestone;
    private int progressData;

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

    public void showProgressBar(int currentHeardCount) {
        this.showProgressBar(currentHeardCount, true);
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
                progressData = progressData + 1;
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
}