package com.iskcon.folk.app.chantandhear.service;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlipperFocusSlideshowHandler extends AbstractEventHandler {

    private ViewFlipper focusViewFlipper;

    private List<View> flipperViews;

    private final List<String> KRISHNA_QUOTES = List.of("I am coming bead after bead.",
            "I want to have relationship with you.",
            "I will take you out of material world.",
            "No difference between me and my nama.",
            "Trust me , I'm the Sound.",
            "I have changed many people's heart, they are now pure devotees.",
            "I will remove all your kama sankalpas.",
            "I am with you bead after bead.",
            "Prayer is the hot line between me and you.",
            "Love is the only way to achieve me.",
            "I love to be loved by you.",
            "I am coming bead after bead.",
            "I am always thinking about me, you too start to think about me, is it ok?",
            "I am hear for you no need to fear.",
            "I will never leave your side.",
            "I am there with you and within you for eternal.",
            "Common hold my hands with complete faith and trust.",
            "Neither me nor you cant go away of each other.",
            "Every nama you chanting is bringing you close to me.",
            "All I know is I will never leave your hand.",
            "Do you want me in your life?",
            "Can you take me inside your heart !!",
            "I am coming in your life, may I do so?"
    );

    public FlipperFocusSlideshowHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {
    }

    public void initializeFlipper() {
        focusViewFlipper = getAppCompatActivity().findViewById(R.id.focusSlideshowFlipper);
        flipperViews = this.getFlipperViews();
        focusViewFlipper.setAutoStart(true);
        this.addFlipperViews();
        focusViewFlipper.stopFlipping();
        focusViewFlipper.setFlipInterval(ApplicationConstants.FLIP_VIEW_INTERVAL.getConstantValue(Integer.class));
        focusViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(), R.anim.slide_in_bottom));
        focusViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(), R.anim.slide_out_bottom));
    }

    private List<View> getFlipperViews() {
        List<View> flipperViewDataModels = new ArrayList<>();
        int krishanQuoteIndex = 0;
        for (int i = 0; i <= ApplicationConstants.NO_OF_IMAGE_VIEW_FOR_FLIPPER.getConstantValue(Integer.class); i++) {
            if (krishanQuoteIndex == KRISHNA_QUOTES.size() - 1) {
                krishanQuoteIndex = 0;
            }
            String krishnaQuote = KRISHNA_QUOTES.get(krishanQuoteIndex);
            krishanQuoteIndex++;
            flipperViewDataModels.add(this.createImageView(
                    getAppCompatActivity().getResources().getIdentifier("krishna_slide_show_" + i, "drawable", "com.iskcon.folk.app.chantandhear"),
                    krishnaQuote)
            );
        }
        return flipperViewDataModels;
    }

    private void addFlipperViews() {
        for (int i = 0; i < flipperViews.size(); i++) {
            focusViewFlipper.addView(flipperViews.get(i));
        }
    }

    public void startFlipper(long flipInterval) {
        ((ShapeableImageView) getAppCompatActivity().findViewById(R.id.spMainActivityImage)).setVisibility(ViewFlipper.INVISIBLE);
        flipInterval = flipInterval != 0 ? flipInterval : ApplicationConstants.FLIP_VIEW_INTERVAL.getConstantValue(Integer.class);
        focusViewFlipper.setVisibility(View.VISIBLE);
        focusViewFlipper.showNext();
        focusViewFlipper.setFlipInterval(Long.valueOf(flipInterval).intValue());
    }

    public void showNextFlipper() {
        focusViewFlipper.showNext();
    }

    public void pauseFlipper() {
        focusViewFlipper.stopFlipping();
    }

    public void stopFlipper() {
        ((ShapeableImageView) getAppCompatActivity().findViewById(R.id.spMainActivityImage)).setVisibility(ViewFlipper.VISIBLE);
        focusViewFlipper.stopFlipping();
        focusViewFlipper.setVisibility(View.INVISIBLE);
    }

    private FrameLayout createImageView(int resourceId, String krishnaQuote) {

        ShapeableImageView shapeableImageView = new ShapeableImageView(getAppCompatActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        final int MARGIN_SIZE = 5;
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE);
        shapeableImageView.setLayoutParams(layoutParams);
        // Create ShapeAppearanceModel with rounded corners
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, 20)
                .build();
        shapeableImageView.setShapeAppearanceModel(shapeAppearanceModel);
        shapeableImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        shapeableImageView.setBackgroundResource(resourceId);
        shapeableImageView.setAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(), R.anim.flipper_zoom_in));

        TextView textView = new TextView(getAppCompatActivity());
        textView.setPadding(20, 20, 20, 20);
        textView.setLayoutParams(new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER | Gravity.BOTTOM)
        );
        textView.setText(krishnaQuote);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        textView.setMaxLines(3);
        textView.setMinHeight(150);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(getAppCompatActivity().getResources().getColor(R.color.ch_light_color));
        textView.setBackgroundResource(R.drawable.flipper_krishna_quotes_bg);
        textView.setLetterSpacing(0.15f);
        textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
        textView.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);
        textView.setAnimation(AnimationUtils.loadAnimation(getAppCompatActivity(), R.anim.slide_in_bottom));

        FrameLayout frameLayout = new FrameLayout(getAppCompatActivity());
        LinearLayout.LayoutParams frameLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        frameLayoutParams.gravity = Gravity.CENTER;
        frameLayout.setLayoutParams(frameLayoutParams);
        frameLayout.addView(shapeableImageView);
        frameLayout.addView(textView);

        return frameLayout;
    }
}