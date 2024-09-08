package com.example.chantingworkingapp.service;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.constant.ApplicationConstants;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlipperFocusSlideshowHandler extends AbstractEventHandler {

    private ViewFlipper focusViewFlipper;

    private List<View> flipperViews;

    public FlipperFocusSlideshowHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {

    }

    public void initializeFlipper() {
        focusViewFlipper = getAppCompatActivity().findViewById(R.id.focusSlideshowFlipper);
        flipperViews = this.getFlipperViews();
        this.addFlipperViews();
        focusViewFlipper.setAutoStart(true);
        focusViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(), android.R.anim.fade_in));
        focusViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(), android.R.anim.fade_out));
    }

    private List<View> getFlipperViews() {
        List<View> flipperViewDataModels = new ArrayList<>();
        for (int i = 0; i <= ApplicationConstants.NO_OF_IMAGE_VIEW_FOR_FLIPPER.getConstantValue(Integer.class); i++) {
            flipperViewDataModels.add(this.createShapeableImageView(
                    getAppCompatActivity().getResources().getIdentifier("krishna_slide_show_" + i, "drawable", "com.example.chantingworkingapp"))
            );
        }
        return flipperViewDataModels;
    }

    public void addFlipperViews() {
        for (int i = 0; i < flipperViews.size(); i++) {
            focusViewFlipper.addView(flipperViews.get(i));
        }
    }

    public void startFlipper(long flipInterval) {
        ((ShapeableImageView)getAppCompatActivity().findViewById(R.id.spMainActivityImage)).setVisibility(ViewFlipper.INVISIBLE);
        flipInterval = flipInterval != 0 ? flipInterval : ApplicationConstants.FLIP_VIEW_INTERVAL.getConstantValue(Integer.class);
        focusViewFlipper.setVisibility(View.VISIBLE);
        focusViewFlipper.setFlipInterval(Long.valueOf(flipInterval).intValue());
        focusViewFlipper.startFlipping();
    }

    public void pauseFlipper() {
        focusViewFlipper.stopFlipping();
    }

    public void stopFlipper() {
        ((ShapeableImageView)getAppCompatActivity().findViewById(R.id.spMainActivityImage)).setVisibility(ViewFlipper.VISIBLE);
        focusViewFlipper.stopFlipping();
        focusViewFlipper.setVisibility(View.INVISIBLE);
    }

    private ShapeableImageView createShapeableImageView(int resourceId) {

        ShapeableImageView shapeableImageView = new ShapeableImageView(getAppCompatActivity());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int MARGIN_SIZE = 5;
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE);
        shapeableImageView.setLayoutParams(layoutParams);

        // Create ShapeAppearanceModel with rounded corners
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, 20)
                .build();
        shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);

        shapeableImageView.setBackgroundResource(resourceId);

        return shapeableImageView;
    }
}