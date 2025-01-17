package com.iskcon.folk.app.chantandhear.service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.constant.UserAttentionSliderMessage;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;

import java.text.MessageFormat;

public class JapaHintsService {

    public void showDialog(MainActivity mainActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        View japaHintPopupView = View.inflate(mainActivity, R.layout.japa_hints_popup, null);

        ((TextView) japaHintPopupView.findViewById(R.id.hintMessage)).setText(
                MessageFormat.format("Hare Krishna, \n{0}", UserAttentionSliderMessage.getAttentionMessage()));

        AlertDialog alertDialog = builder.create();

        alertDialog.setView(japaHintPopupView, 0, 50, 0, 0);

        alertDialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = 500;

        alertDialog.getWindow().setAttributes(layoutParams);

        alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        this.bindButtonClickEvents(mainActivity, alertDialog, japaHintPopupView);

        this.registerAutoClose(alertDialog);
    }

    private void bindButtonClickEvents(MainActivity mainActivity, AlertDialog alertDialog, View japaHintsPopupView) {

        japaHintsPopupView.findViewById(R.id.encouraging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.vibrateFunction(50, (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE));
                alertDialog.dismiss();
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