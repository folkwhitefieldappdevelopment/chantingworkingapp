package com.iskcon.folk.app.chantandhear.service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;

import java.text.MessageFormat;
import java.util.List;

public class ChantingGuideHandlerService {

    public void showMarqueeTextView(MainActivity mainActivity) {
        TextView textView = mainActivity.findViewById(R.id.chantingGuideMarqueeTextView);
        textView.setText(MessageFormat.format("Tips -> 1. {0} || 2. {1} || 3. {2} <- Tips", mainActivity.getString(R.string.chanting_guide_1_title),
                mainActivity.getString(R.string.chanting_guide_2_title),
                mainActivity.getString(R.string.chanting_guide_3_title)));
        textView.setVisibility(View.VISIBLE);
        textView.setSelected(true);
    }

    public void hideMarqueeTextView(MainActivity mainActivity) {
        TextView textView = mainActivity.findViewById(R.id.chantingGuideMarqueeTextView);
        textView.setVisibility(View.INVISIBLE);
    }

    public void showDialog(MainActivity mainActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        View chantingGuideView = View.inflate(mainActivity, R.layout.chanting_guidline, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(chantingGuideView, 0, 50, 0, 0);
        alertDialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.getWindow().setAttributes(layoutParams);
        this.bindButtonClickEvents(mainActivity, alertDialog, chantingGuideView);
    }

    private void bindButtonClickEvents(MainActivity mainActivity, AlertDialog alertDialog, View chantingGuideView) {

        chantingGuideView.findViewById(R.id.acceptButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.vibrateFunction(50, (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE));
                alertDialog.dismiss();
                mainActivity.getHkMantraClickHandler().startPanchaTattvaMantraMediaPlayer();
            }
        });
    }

    private void registerAutoClose(AlertDialog alertDialog) {

        Handler autoClosehandler = new Handler();

        autoClosehandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, ApplicationConstants.LEVEL_INCREASE_POPUP_AUTO_CLOSE_DELAY.getConstantValue(Long.class));
    }
}