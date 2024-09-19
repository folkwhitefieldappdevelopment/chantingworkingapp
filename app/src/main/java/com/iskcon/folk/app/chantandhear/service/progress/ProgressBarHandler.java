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
        Milestone milestone = Milestone.beadInBetweenWhichMilestoe(currentHeardCount);
        if (milestone != null) {
            LinearLayout linearLayout = super.getAppCompatActivity().findViewById(milestone.getLinearLayoutId());
            if (View.INVISIBLE == linearLayout.getVisibility()) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void incrementProgressBar(int currentHeardCount) {
        if (!super.getAppCompatActivity().getHkMantraClickHandler().isMediaPaused()) {
            Milestone milestone = Milestone.beadInBetweenWhichMilestoe(currentHeardCount);
            if (currentMilestone == null) {
                currentMilestone = milestone;
            }
            if (milestone != null) {
                this.showProgressBar(currentHeardCount);
                ProgressBar progressBar = super.getAppCompatActivity().findViewById(milestone.getProgressBarId());
                if (Milestone.MILESTONE_7.getMilestoneId() == milestone.getMilestoneId()) {
                    progressBar.setMax(12);
                } else {
                    progressBar.setMax(ApplicationConstants.TOTAL_BEADS_IN_A_MILESTONE.getConstantValue(Integer.class));
                }
                int progressData = (Milestone.MILESTONE_1.equals(milestone) ? currentHeardCount : (currentHeardCount - milestone.getStartBead()) + 1);
                progressData = progressData == 0 ? 1 : progressData;
                progressBar.setProgress(progressData, true);
                TextView textView = getAppCompatActivity().findViewById(milestone.getHeardCountTextViewId());
                textView.setText(String.valueOf(progressData));

                if (currentMilestone.getMilestoneId() != milestone.getMilestoneId()) {
                    MediaPlayer milestoneMediaPlayer = MediaPlayer.create(getAppCompatActivity(), R.raw.beat16);
                    milestoneMediaPlayer.start();
                    milestoneMediaPlayer.stop();
                    milestoneMediaPlayer.release();
                    currentMilestone = milestone;
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