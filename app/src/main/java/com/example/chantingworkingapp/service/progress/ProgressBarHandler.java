package com.example.chantingworkingapp.service.progress;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.constant.ApplicationConstants;
import com.example.chantingworkingapp.constant.Milestone;
import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.service.AbstractEventHandler;

public class ProgressBarHandler extends AbstractEventHandler {

    public ProgressBarHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
    }

    public void initializeProgressBar(){
        LinearLayout linearLayout = super.getAppCompatActivity().findViewById(Milestone.MILESTONE_1.getLinearLayoutId());
        if (View.INVISIBLE == linearLayout.getVisibility()) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    public void manageProgress(int currentHeardCount) {
        int currentBeadCount = super.getAppCompatActivity().getJapaMalaViewModel().getBeadCounterLiveData().getValue();
        Milestone milestone = Milestone.beadInBetweenWhichMilestoe(currentBeadCount);
        LinearLayout linearLayout = super.getAppCompatActivity().findViewById(milestone.getLinearLayoutId());
        if (View.INVISIBLE == linearLayout.getVisibility()) {
            linearLayout.setVisibility(View.VISIBLE);
        }
        Log.i(this.getClass().getName(), "milestone = " + milestone);

        ProgressBar progressBar = super.getAppCompatActivity().findViewById(milestone.getProgressBarId());
        if (Milestone.MILESTONE_7.getMilestoneId() == milestone.getMilestoneId()) {
            progressBar.setMax(12);
        } else {
            progressBar.setMax(ApplicationConstants.TOTAL_BEADS_IN_A_MILESTONE.getConstantValue(Integer.class));
        }
        int progressData = (Milestone.MILESTONE_1.equals(milestone) ? currentHeardCount : (currentHeardCount + (currentBeadCount - 1)) % progressBar.getMax());
        progressData = progressData == 0 ? milestone.getEndBead() : progressData;
        progressBar.setProgress(progressData, true);
        TextView textView = getAppCompatActivity().findViewById(milestone.getHeardCountTextViewId());
        textView.setText(String.valueOf(progressData));
    }

    public void setCurrentMilestone(int currentBeadCount) {

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
