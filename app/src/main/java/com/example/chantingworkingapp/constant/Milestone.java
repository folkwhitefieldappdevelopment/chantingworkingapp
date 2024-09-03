package com.example.chantingworkingapp.constant;

import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.chantingworkingapp.R;

public enum Milestone {

    MILESTONE_1(1, 1, 16, R.id.layoutProgressBar_1, R.id.progressBar_1, R.id.tvProgressBarHeardCount_1),
    MILESTONE_2(2, 17, 32, R.id.layoutProgressBar_2, R.id.progressBar_2, R.id.tvProgressBarHeardCount_2),
    MILESTONE_3(3, 33, 48, R.id.layoutProgressBar_3, R.id.progressBar_3, R.id.tvProgressBarHeardCount_3),
    MILESTONE_4(4, 49, 64, R.id.layoutProgressBar_4, R.id.progressBar_4, R.id.tvProgressBarHeardCount_4),
    MILESTONE_5(5, 65, 80, R.id.layoutProgressBar_5, R.id.progressBar_5, R.id.tvProgressBarHeardCount_5),
    MILESTONE_6(6, 81, 96, R.id.layoutProgressBar_6, R.id.progressBar_6, R.id.tvProgressBarHeardCount_6),
    MILESTONE_7(7, 97, 108, R.id.layoutProgressBar_7, R.id.progressBar_7, R.id.tvProgressBarHeardCount_7);

    private final int milestoneId;
    private final int startBead;
    private final int endBead;
    private final int linearLayoutId;
    private final int progressBarId;
    private final int heardCountTextViewId;

    Milestone(int milestoneId, int startBead, int endBead, int linearLayoutId, int progressBarId, int heardCountTextViewId) {
        this.milestoneId = milestoneId;
        this.startBead = startBead;
        this.endBead = endBead;
        this.linearLayoutId = linearLayoutId;
        this.progressBarId = progressBarId;
        this.heardCountTextViewId = heardCountTextViewId;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public int getStartBead() {
        return startBead;
    }

    public int getEndBead() {
        return endBead;
    }

    public int getLinearLayoutId() {
        return linearLayoutId;
    }

    public int getProgressBarId() {
        return progressBarId;
    }

    public int getHeardCountTextViewId() {
        return heardCountTextViewId;
    }

    public static Milestone beadInBetweenWhichMilestoe(int currentBead) {
        Milestone returnMilestone = null;
        for (Milestone milestone : values()) {
            if (currentBead >= milestone.getStartBead() && currentBead <= milestone.getEndBead()) {
                returnMilestone = milestone;
                break;
            }
        }
        return returnMilestone;
    }
}