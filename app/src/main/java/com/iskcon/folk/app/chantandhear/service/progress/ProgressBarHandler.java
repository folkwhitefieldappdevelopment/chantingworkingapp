package com.iskcon.folk.app.chantandhear.service.progress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Px;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.constant.Milestone;
import com.iskcon.folk.app.chantandhear.constant.UserAttentionSliderMessage;
import com.iskcon.folk.app.chantandhear.constant.VideoType;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;
import com.iskcon.folk.app.chantandhear.service.mediaplayer.HkMantraClickHandler;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;
import com.iskcon.folk.app.chantandhear.util.OpenAlertDialogRqModel;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
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
        ((TableLayout) super.getAppCompatActivity()
                .findViewById(R.id.progressBarHeaderTableLayout)).setVisibility(View.VISIBLE);
        this.addRow(Milestone.MILESTONE_1);
    }

    public void addProgressRow(int currentMalaCount) {

        if (currentMalaCount != 0) {

            if (currentMalaCount %
                    ApplicationConstants.USER_ATTENTION_SLIDER_SHOW_ON_EVERY.getConstantValue(
                            Integer.class) == 0) {

                this.showUserAttentionSlider();
            }

            Milestone calculatedMilestone = Milestone.beadInBetweenWhichMilestoe(currentMalaCount);

            if (!this.currentMilestone.equals(calculatedMilestone) &&
                    !this.milestoneWiseProgress.containsKey(calculatedMilestone)) {

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

        if (true) {
            //if (!this.checkAndShowNoHeardVideo(milestone)) {

            this.doNotificationOfMilestoneCompletion(milestone);

            this.currentMilestone = milestone;

            this.milestoneWiseProgress.put(milestone, 0);

            TableRow tableRow = new TableRow(super.getAppCompatActivity());
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setId(milestone.getMilestoneId());
            tableRow.setPadding(0, 0, 0, 15);
            tableRow.setGravity(Gravity.CENTER);

            tableRow.setAnimation(AnimationUtils.loadAnimation(super.getAppCompatActivity(),
                    R.anim.blink_5_times));

            TableRow.LayoutParams tableLayoutParams = new TableRow.LayoutParams(10, 40, 0f);

            // Column 1 :: Bead Division
            TextView beadDivisionTextView = new TextView(super.getAppCompatActivity());
            beadDivisionTextView.setTextSize(14);
            beadDivisionTextView.setTextColor(super.getAppCompatActivity().getResources()
                    .getColor(R.color.history_list_row_view_round_number));
            beadDivisionTextView.setText(MessageFormat.format("{0} - {1}", milestone.getStartBead(),
                    milestone.getEndBead()));
            beadDivisionTextView.setGravity(Gravity.CENTER);
            beadDivisionTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(beadDivisionTextView, tableLayoutParams);

            // Column 2 :: Heard count
            TextView heardCountTextView = new TextView(super.getAppCompatActivity());
            heardCountTextView.setTextSize(14);
            heardCountTextView.setTextColor(
                    super.getAppCompatActivity().getResources().getColor(R.color.ch_dark_color));
            heardCountTextView.setText(String.valueOf(0));
            heardCountTextView.setGravity(Gravity.CENTER);
            heardCountTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(heardCountTextView, tableLayoutParams);

            // Column 3 :: Progress bar
            ProgressBar progressBar = new ProgressBar(this.getAppCompatActivity(), null,
                    android.R.attr.progressBarStyleHorizontal);
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(5, 6));
            progressBar.setMax(16);
            tableRow.addView(progressBar, tableLayoutParams);

            // Adding row to the table
            TableLayout tableLayout =
                    super.getAppCompatActivity().findViewById(R.id.progressBarTableLayout);
            tableLayout.addView(tableRow);

            // Adding a row separator
        /*View rowSeparator = new View(super.getAppCompatActivity());
        rowSeparator.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        rowSeparator.setBackgroundResource(R.color.ch_counter_border_dark);
        tableLayout.addView(rowSeparator);*/

            this.changePreviousRowsAppearance(milestone);
        }
    }

    private void doNotificationOfMilestoneCompletion(Milestone milestone) {

        if (!milestone.equals(Milestone.MILESTONE_1)) {

            MediaPlayer milestoneMediaPlayer =
                    MediaPlayer.create(getAppCompatActivity(), R.raw.beat16);
            milestoneMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            });
            milestoneMediaPlayer.start();
        }
    }

    private void changePreviousRowsAppearance(Milestone currentMilestone) {

        if (!currentMilestone.equals(Milestone.MILESTONE_1)) {

            TableLayout tableLayout =
                    super.getAppCompatActivity().findViewById(R.id.progressBarTableLayout);

            for (int i = 0; i < tableLayout.getChildCount(); i++) {

                View view = tableLayout.getChildAt(i);

                if (view instanceof TableRow) {

                    TableRow tableRow = (TableRow) (view);

                    // Column 1: Beads division
                    TextView beadDivisionTextView = (TextView) tableRow.getChildAt(0);
                    beadDivisionTextView.setTextSize(12);
                    beadDivisionTextView.setTextColor(
                            super.getAppCompatActivity().getResources().getColor(R.color.green));

                    // Column 2: Heard Count
                    TextView heardCountTextView = (TextView) tableRow.getChildAt(1);
                    heardCountTextView.setTextSize(12);
                    heardCountTextView.setTextColor(
                            super.getAppCompatActivity().getResources().getColor(R.color.green));
                }
            }
        }
    }

    private void updateInProgressRow(int currentMilestoneHeardCount) {

        TableLayout tableLayout =
                super.getAppCompatActivity().findViewById(R.id.progressBarTableLayout);

        TableRow tableRow = (TableRow) (tableLayout.getChildAt(tableLayout.getChildCount() - 1));

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

        ((TableLayout) super.getAppCompatActivity()
                .findViewById(R.id.progressBarTableLayout)).removeAllViews();

        this.currentMilestone = null;

        this.milestoneWiseProgress = new HashMap<>();
    }

    private boolean checkAndShowNoHeardVideo(Milestone milestone) {
        boolean stopRowAdd = false;
        Integer milestoneWiseHeardCount = this.milestoneWiseProgress.get(milestone);
        if (!milestone.equals(Milestone.MILESTONE_1) && milestoneWiseHeardCount == null) {
            stopRowAdd = true;
            attentiveVideoShown = true;
            HkMantraClickHandler hkMantraClickHandler =
                    getAppCompatActivity().getHkMantraClickHandler();
            hkMantraClickHandler.pauseMediaPlayer();
            String message =
                    "The process is that you should chant and must hear the very sound. Please hear the sound and tap on the Heard Button promptly after every bead you have heard.";
            DialogInterface.OnClickListener positiveClickHandler =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            hkMantraClickHandler.resumeMediaPlayer();
                            //attentiveVideoShown = false;
                        }
                    };
            DialogInterface.OnClickListener negativeClickHandler =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //attentiveVideoShown = false;
                            dialogInterface.cancel();
                            getAppCompatActivity().getYoutubeVideoHandler()
                                    .showVideo(VideoType.ATTENTIVE);
                        }
                    };

            CommonUtils.showDialog(getAppCompatActivity(), new OpenAlertDialogRqModel(
                    message,
                    "I will be attentive", positiveClickHandler, "Enlighten me more",
                    negativeClickHandler));
        }

        return stopRowAdd;
    }

    public void setAttentiveVideoShown(boolean attentiveVideoShown) {
        this.attentiveVideoShown = attentiveVideoShown;
    }

    private void showUserAttentionSlider() {
        TextView textView =
                super.getAppCompatActivity().findViewById(R.id.userAttentionSliderMessage);
        textView.setText(MessageFormat.format("Hare Krishna, \n{0}",
                UserAttentionSliderMessage.getAttentionMessage()));
        textView.setAnimation(
                AnimationUtils.loadAnimation(getAppCompatActivity(), android.R.anim.fade_in));
        RelativeLayout relativeLayout =
                super.getAppCompatActivity().findViewById(R.id.userAttentionSliderRelativeLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout.setAnimation(
                AnimationUtils.loadAnimation(getAppCompatActivity(), android.R.anim.slide_in_left));
        relativeLayout.animate();
        textView.animate();

        super.vibrate(50);

        MediaPlayer milestoneMediaPlayer =
                MediaPlayer.create(getAppCompatActivity(), R.raw.copper_bell_ding);
        milestoneMediaPlayer.setVolume(0.5f, 0.5f);
        milestoneMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });
        milestoneMediaPlayer.start();

        Handler hideUserAttentionSliderHandler = new Handler();

        hideUserAttentionSliderHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(getAppCompatActivity(),
                        android.R.anim.slide_out_right);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        relativeLayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                if (relativeLayout.getVisibility() == View.VISIBLE) {
                    relativeLayout.setAnimation(animation);
                    relativeLayout.animate();
                }
            }
        }, ApplicationConstants.USER_ATTENTION_SLIDER_CLOSER_TIME.getConstantValue(Integer.class));
    }
}