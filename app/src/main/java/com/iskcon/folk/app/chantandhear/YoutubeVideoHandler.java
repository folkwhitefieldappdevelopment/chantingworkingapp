package com.iskcon.folk.app.chantandhear;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.iskcon.folk.app.chantandhear.constant.VideoType;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;
import com.iskcon.folk.app.chantandhear.service.history.HistoryRoundDetailsViewClickHandler;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Random;

public class YoutubeVideoHandler extends AbstractEventHandler {

    private final String[] SOULFUL_JAPA_LANDSCAPE_VIDEOS = {
            "lzAZ0FFRBvY",
            "bqOXgSRbQgE",
            "WSWOi0kulX8",
            "4bV_iKWbUZA",
            "K3UuBIVmvAk",
            "DwjBzvn3xNM",
            "E7ewlFncBfQ",
            "TARIiT70uUc",
            "ZNejoYVQAI",
            "MDHu6ThofRM",
            "4JZM2xrj2Is",
            "H2qvBcr47n8",
            "y_aR42SteoA",
            "Fb1kS_oeHgY",
            "z7wg1SmLr9w",
            "S6wOnfukMzk",
            "7A8OulTIOWA",
            "i3X9Z06dfK4",
            "j1N3S3VWOas",
            "_XoReZE3zJs"
    };

    private final String[] SOULFUL_JAPA_PORTRAIT_VIDEOS = {
            "vIrvGY9STTo",
            "0fLhnEHzt44",
            "P1r05OAcA-c",
            "tOmpHVkTHB4",
            "YeFCFeb1Hd8",
            "lQ7F4S89bPI",
            "BfaoQWNtO2Y",
            "sBjMOGuUhDc",
            "2gjiVdx3eLY",
            "nN4yELBq5ac",
            "IOiWQjq4UiE",
            "6x1CmduML9M",
            "bwhym85xUc8",
            "BZV5ktnpKk0",
            "OBfhazD6LvI",
            "CTlxwP52zFM",
            "KVAARjfdxKQ",
            "8jagUE9EeIg",
            "OYxjHHAqx5U",
            "UfS9WSetnNs",
            "zoAYbahTQf0",
            "0mRkWk-3YVI",
            "J7w5cjxvMvo",
            "1aj99PNZ_cg",
            "M9gknS7W22k",
            "ZguaKfpJoLI",
            "MZ6Pn3XEEac",
            "1hU06szYAAU",
            "5ylMcB7ZOtk",
            "a4MGy8r_FSo",
            "YIYSEG--jIs",
            "Y-K7b6UCB-0",
            "9UPB72kYrOw",
            "iMQpF2LXYQU",
            "hpDuLbETZxY",
            "fUZ8ndAakMk",
            "w65C5ML0Xdc",
            "qrlMNLW7-xU",
            "8rgNZROtd2A",
            "O9qYCpsfKYg",
            "e5FpexFxlcE",
            "cepn7iigT60",
            "3cvhOtXBIR4",
            "55SEvDlUAqQ",
            "hJgMnhb0V0Q",
            "BivdZ72Hj4g",
            "nl0kQAZoM1w",
            "Qn2iP9TeNa4",
            "RyFh_I_LO-o",
            "o74zFd4Z0pk",
            "wPANIRhkimo",
            "zaJLBPY1cGw",
            "yxDDREZ6HTQ"
    };

    private final String[] ATTENTIVE_VIDEOS = {"E7dJinaofqU", "JzFjHo8OKPI"};

    public YoutubeVideoHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {

    }

    public void showVideo(VideoType videoType) {

        // Create a Dialog with a custom layout
        Dialog dialog = new Dialog(super.getAppCompatActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        // Inflate the custom layout for the popup
        View popupView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.popup_layout, null);

        ImageButton closeButton = popupView.findViewById(R.id.dialog_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VideoType.SOULFUL_JAPA.equals(videoType)) {
                    new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage(
                                    "Looks like your are very eager to start next round of mala, lets proceed towards next round of chanting haribol!!").
                            setPositiveButton("Haribol (OK)", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onVideoCompleteOrSkip();
                                    dialogInterface.cancel();
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    dialog.dismiss();
                }
            }
        });

        // Set the custom layout for the dialog
        dialog.setContentView(popupView);
        dialog.closeOptionsMenu();

        // Find the YouTubePlayerView in the custom layout
        YouTubePlayerView youTubePlayerView = popupView.findViewById(R.id.youtube_player_view);
        super.getAppCompatActivity().getLifecycle().addObserver(youTubePlayerView);

        int orientation = getAppCompatActivity().getResources().getConfiguration().orientation;

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = null;
                if (Configuration.ORIENTATION_PORTRAIT == orientation) {
                    videoId = String.valueOf(
                            SOULFUL_JAPA_PORTRAIT_VIDEOS[new Random().nextInt(SOULFUL_JAPA_PORTRAIT_VIDEOS.length)]);
                } else {
                    videoId = String.valueOf(
                            SOULFUL_JAPA_LANDSCAPE_VIDEOS[new Random().nextInt(SOULFUL_JAPA_LANDSCAPE_VIDEOS.length)]);
                }
                if (VideoType.SOULFUL_JAPA.equals(videoType)) {
                    // Load and play the video
                    youTubePlayer.loadVideo(videoId, 0);
                } else {
                    youTubePlayer.loadVideo(String.valueOf(ATTENTIVE_VIDEOS[new Random().nextInt(2)]), 0);
                }
                youTubePlayer.play();// Enter fullscreen mode
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                if (PlayerConstants.PlayerState.ENDED == state) {
                    if (VideoType.SOULFUL_JAPA.equals(videoType)) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage(
                                                "You are spiritually energized after a wonderful video, lets proceed towards next round of chanting haribol!!.").
                                        setPositiveButton("Haribol (OK)", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                                dialog.cancel();
                                                onVideoCompleteOrSkip();
                                                new HistoryRoundDetailsViewClickHandler().showHistoryRoundWiseDetailPopup(
                                                        popupView, getAppCompatActivity().getUserDetails(), true);
                                            }
                                        }).show();
                            }
                        }, 10);
                    } else {
                        dialog.cancel();
                    }
                }
                super.onStateChange(youTubePlayer, state);
            }
        });
        dialog.show();
    }

    public void onVideoCompleteOrSkip() {
        getAppCompatActivity().getJapaMalaViewModel().incrementRound();
    }
}