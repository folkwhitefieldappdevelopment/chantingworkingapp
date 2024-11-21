package com.iskcon.folk.app.chantandhear;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.iskcon.folk.app.chantandhear.constant.VideoType;
import com.iskcon.folk.app.chantandhear.service.AbstractEventHandler;
import com.iskcon.folk.app.chantandhear.service.HistoryViewClickHandler;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Random;

public class YoutubeVideoHandler extends AbstractEventHandler {

    private final String[] SOULFUL_JAPA_VIDEOS = {
            "q5fYKcRbaVM",
            "63Q-Y1GkWms",
            "e433GZzV42c",
            "CQkZbbYWp6c",
            "1cdvpwVG0vw",
            "NhqtvxDRYqA",
            "plwwgrbXjf8",
            "J992BbY-7JE",
            "GatUq_wbmyc",
            "MF-k_a9u5RU"
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
                    new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage("Looks like your are very eager to start next round of mala, lets proceed towards next round of chanting haribol!!").
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

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (VideoType.SOULFUL_JAPA.equals(videoType)) {
                    // Load and play the video
                    youTubePlayer.loadVideo(String.valueOf(SOULFUL_JAPA_VIDEOS[new Random().nextInt(10)]), 0);
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
                                new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage("You are spiritually energized after a wonderful video, lets proceed towards next round of chanting haribol!!.").
                                        setPositiveButton("Haribol (OK)", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                                dialog.cancel();
                                                onVideoCompleteOrSkip();
                                                new HistoryViewClickHandler().showHistoryPopup(popupView, getAppCompatActivity().getUserDetails(), true);
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
