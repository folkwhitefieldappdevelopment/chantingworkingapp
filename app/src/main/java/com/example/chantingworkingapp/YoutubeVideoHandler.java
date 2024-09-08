package com.example.chantingworkingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.chantingworkingapp.model.JapaMalaModel;
import com.example.chantingworkingapp.service.AbstractEventHandler;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Random;

public class YoutubeVideoHandler extends AbstractEventHandler {

    public YoutubeVideoHandler(MainActivity appCompatActivity) {
        super(appCompatActivity);
    }

    @Override
    public void handle(View view) {

    }

    public void showVideo() {

        String[] videoToBePlay = {
                "zEtAgUtXvRU", "SUbhkUd8c68", "QnLevMrkQ_0",
                "t--PFs-xxo4", "m3QkR8qBgw8", "6Fl0bGCVPCY",
                "-csUVDrYhPg", "nD_JQMmov8o", "BMzyCEM5f8E",
                "MF-k_a9u5RU"
        };

        // Create a Dialog with a custom layout
        Dialog dialog = new Dialog(super.getAppCompatActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        // Inflate the custom layout for the popup
        View popupView = super.getAppCompatActivity().getLayoutInflater().inflate(R.layout.popup_layout, null);

        ImageButton closeButton = popupView.findViewById(R.id.dialog_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage("Looks like your are very eager to start next round of mala, lets proceed towards next round of chanting haribol!!").
                        setPositiveButton("Haribol (OK)", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onVideoCompleteOrSkip();
                                dialogInterface.cancel();
                                dialog.dismiss();
                            }
                        }).show();
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
                // Load and play the video
                youTubePlayer.loadVideo(String.valueOf(videoToBePlay[new Random().nextInt(10)]), 0);
                youTubePlayer.play();// Enter fullscreen mode
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                if (PlayerConstants.PlayerState.ENDED == state) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(getAppCompatActivity()).setTitle("Hare Krishna").setMessage("You are more spiritually energized after a wonderful video, lets proceed towards next round of chanting haribol!!.").
                                    setPositiveButton("Haribol (OK)", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            dialog.cancel();
                                            onVideoCompleteOrSkip();
                                        }
                                    }).show();
                        }
                    }, 1000);
                }
                super.onStateChange(youTubePlayer, state);
            }
        });
        dialog.show();
    }

    public void onVideoCompleteOrSkip(){
        getAppCompatActivity().getJapaMalaViewModel().incrementRound();
    }
}
