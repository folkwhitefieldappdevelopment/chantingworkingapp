package com.iskcon.folk.app.chantandhear.constant;

import java.util.Random;

public class UserAttentionSliderMessage {

    public final static String[] ATTENTION_MESSAGE = {
            "Try to hear and concentrate on Hare Krishna, Hare Rama string on every bead.",
            "Are you feeling the change of bead on your japa mala? Try to feel it every bead.",
            "Chant loudly when your concentration is getting diverted.",
            "Feeling sleepy? If so walk and chant. Still feeling sleepy? Go out of walls and chant",
            "Always chant complete mantra, say mantra as per your convenience speed, not too fast not too slow.",
            "Don't do anything else while chanting, concentrate on the sound very sound your chanting.",
            "Don’t keep your mind elsewhere. You keep your mind on the chanting, practice this on every bead.",
            "While chanting, you must hear. Hare Krishna-you must give attention to hear"};

    public static String getAttentionMessage() {

        return ATTENTION_MESSAGE[new Random().nextInt(ATTENTION_MESSAGE.length)];
    }
}