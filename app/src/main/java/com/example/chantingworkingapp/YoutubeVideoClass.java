package com.example.chantingworkingapp;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YoutubeVideoClass {
    private YouTubePlayerView youTubePlayerView;
    private String[] videoIds;

    public YoutubeVideoClass(String[] videoIds) {
        this.videoIds = videoIds;
    }

    public void showVideoPopup(Context context, LifecycleOwner lifecycleOwner) {
        // Create a Dialog with a custom layout
        Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);

        // Inflate the custom layout for the popup
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);

        // Set the custom layout for the dialog
        dialog.setContentView(popupView);

        // Find the YouTubePlayerView in the custom layout
        youTubePlayerView = popupView.findViewById(R.id.youtube_player_view);

        // Initialize YouTubePlayerView lifecycle
        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            // Load and play the video
            youTubePlayer.loadVideo(videoIds[0], 0);
            youTubePlayer.play();
        });

        // Observe the lifecycle owner
        lifecycleOwner.getLifecycle().addObserver(youTubePlayerView);

        // Show the dialog
        dialog.show();
    }
}
