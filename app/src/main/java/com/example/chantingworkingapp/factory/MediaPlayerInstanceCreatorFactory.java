package com.example.chantingworkingapp.factory;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.chantingworkingapp.MainActivity;
import com.example.chantingworkingapp.R;
import com.example.chantingworkingapp.service.AbstractEventHandler;

public class MediaPlayerInstanceCreatorFactory {

    private MediaPlayerInstanceCreatorFactory(){
    }

    public static MediaPlayer createInstance(Activity activity, int rawId) {

        MediaPlayer mediaPlayer = MediaPlayer.create(activity, rawId);

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(activity, "Unexpected error occurred, please re-launch the app and try again and please report this error", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        return mediaPlayer;
    }
}
