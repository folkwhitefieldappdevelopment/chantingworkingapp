package com.iskcon.folk.app.chantandhear.service.progress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.Milestone;
import com.iskcon.folk.app.chantandhear.constant.VideoType;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.HkMantraClickHandler;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ProgressBarHandler extends AbstractEventHandler {

    private Milestone currentMilestone;
    private Map<Milestone, Integer> milestoneWiseProgress = new HashMap<>();
    private int progressData;
    private boolean attentiveVideoShown;

    public ProgressBarHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
    }

    public void initializeProgressBar() {
        this.addRow(Milestone.MILESTONE_1);
    }

    public void addProgressRow(int currentMalaCount) {

        if (currentMalaCount != 0) {

            Milestone calculatedMilestone = Milestone.beadInBetweenWhichMilestoe(currentMalaCount);

            if (!this.currentMilestone.equals(calculatedMilestone) && !this.milestoneWiseProgress.containsKey(calculatedMilestone)) {

                this.addRow(calculatedMilestone);
            }
        }
    }

    public void incrementProgress(int heardCount) {

        if (heardCount != 0) {

            int currentMilestoneHeardCount = this.milestoneWiseProgress.get(this.currentMilestone);

            if (currentMilestoneHeardCount < 16) {

                this.updateInProgressRow(currentMilestoneHeardCount);

            } else {

                this.addRow(Milestone.getNextMilestone(currentMilestone));
            }
        }
    }

    private void addRow(Milestone milestone) {

        this.currentMilestone = milestone;

        this.milestoneWiseProgress.put(milestone, 0);

        TableRow tableRow = new TableRow(super.getAppCompatActivity());
        tableRow.setId(milestone.getMilestoneId());
        tableRow.setPadding(0, 8, 0, 8);
        tableRow.setGravity(Gravity.CENTER);

        tableRow.setAnimation(AnimationUtils.loadAnimation(super.getAppCompatActivity(), R.anim.blink_5_times));

        // Column 1 :: Bead Division
        TextView beadDivisionTextView = new TextView(super.getAppCompatActivity());
        beadDivisionTextView.setTextSize(20);
        beadDivisionTextView.setTextColor(super.getAppCompatActivity().getResources().getColor(R.color.ch_dark_color));
        beadDivisionTextView.setText(MessageFormat.format("{0} - {1}", milestone.getStartBead(), milestone.getEndBead()));
        beadDivisionTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableRow.addView(beadDivisionTextView);

        // Column 2 :: Heard count
        TextView heardCountTextView = new TextView(super.getAppCompatActivity());
        heardCountTextView.setTextSize(20);
        heardCountTextView.setTextColor(super.getAppCompatActivity().getResources().getColor(R.color.ch_dark_color));
        heardCountTextView.setText(String.valueOf(0));
        heardCountTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableRow.addView(heardCountTextView);

        // Column 3 :: Progress bar
        ProgressBar progressBar = new ProgressBar(this.getAppCompatActivity(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setPadding(4, 0, 4, 0);
        progressBar.setMax(16);
        tableRow.addView(progressBar);

        // Adding row to the table
        TableLayout tableLayout = super.getAppCompatActivity().findViewById(R.id.progressBarTableLayout);
        tableLayout.addView(tableRow);

        // Adding a row separator
        View rowSeparator = new View(super.getAppCompatActivity());
        rowSeparator.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        rowSeparator.setBackgroundResource(R.color.ch_counter_border_dark);
        tableLayout.addView(rowSeparator);

        this.changePreviousRowsAppearance(milestone);
    }

    private void changePreviousRowsAppearance(Milestone currentMilestone) {

        if (!currentMilestone.equals(Milestone.MILESTONE_1)) {

            TableLayout tableLayout = super.getAppCompatActivity().findViewById(R.id.progressBarTableLayout);

            for (int i = 0; i < tableLayout.getChildCount() - 2; i++) {

                View view = tableLayout.getChildAt(i);

                if (view instanceof TableRow) {

                    TableRow tableRow = (TableRow) (view);

                    // Column 1: Beads division
                    TextView beadDivisionTextView = (TextView) tableRow.getChildAt(0);
                    beadDivisionTextView.setTextSize(12);
                    beadDivisionTextView.setTextColor(super.getAppCompatActivity().getResources().getColor(R.color.green));

                    // Column 2: Heard Count
                    TextView heardCountTextView = (TextView) tableRow.getChildAt(1);
                    heardCountTextView.setTextSize(12);
                    heardCountTextView.setTextColor(super.getAppCompatActivity().getResources().getColor(R.color.green));
                }
            }
        }
    }

    private void updateInProgressRow(int currentMilestoneHeardCount) {

        TableLayout tableLayout = super.getAppCompatActivity().findViewById(R.id.progressBarTableLayout);

        TableRow tableRow = (TableRow) (tableLayout.getChildAt(tableLayout.getChildCount() - 2));

        // Column 2: Heard Count
        int levelValue = super.getAppCompatActivity().getHearButtonHandler().getLevelCountValue();

        int toBeUpdatedProgressData = currentMilestoneHeardCount + levelValue;
        TextView heardCountTextView = (TextView) tableRow.getChildAt(1);
        heardCountTextView.setText(String.valueOf(toBeUpdatedProgressData));

        // Column 3: Progress bar
        ProgressBar progressBar = (ProgressBar) tableRow.getChildAt(2);
        progressBar.setProgress(toBeUpdatedProgressData, true);

        this.milestoneWiseProgress.put(currentMilestone, toBeUpdatedProgressData);

        Log.e("progress bar handler", "this.progressData: " + toBeUpdatedProgressData);
    }

    public void clearProgressBar() {

        ((TableLayout) super.getAppCompatActivity().findViewById(R.id.progressBarTableLayout)).removeAllViews();

        this.currentMilestone = null;

        this.milestoneWiseProgress = new HashMap<>();
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