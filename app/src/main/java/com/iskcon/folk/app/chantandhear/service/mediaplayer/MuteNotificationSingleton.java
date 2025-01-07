package com.iskcon.folk.app.chantandhear.service.mediaplayer;

public class MuteNotificationSingleton {

    private static boolean notificationMuted;

    private MuteNotificationSingleton() {

        // Utility class default constructor
    }

    public static void muteNotification(boolean mute) {
        notificationMuted = mute;
    }

    public static boolean isNotificationNotMuted() {
        return !notificationMuted;
    }
}