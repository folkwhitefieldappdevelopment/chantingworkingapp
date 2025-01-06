package com.iskcon.folk.app.chantandhear.service;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;

public class LevelIncreaseService {

    public void showDialog(MainActivity mainActivity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        View levelIncreasePopupView = View.inflate(mainActivity, R.layout.level_increase_popup, null);

        AlertDialog alertDialog = builder.create();

        alertDialog.setView(levelIncreasePopupView, 0, 100, 0, 0);

        alertDialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = 500;

        alertDialog.getWindow().setAttributes(layoutParams);

        alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        this.bindButtonClickEvents(mainActivity,alertDialog,levelIncreasePopupView);
    }

    private void bindButtonClickEvents(MainActivity mainActivity, AlertDialog alertDialog, View levelIncreasePopupView) {

        levelIncreasePopupView.findViewById(R.id.increaseLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getHearButtonHandler().handleLevelUp(view);
                alertDialog.dismiss();
            }
        });

        levelIncreasePopupView.findViewById(R.id.dontIncreaseLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}