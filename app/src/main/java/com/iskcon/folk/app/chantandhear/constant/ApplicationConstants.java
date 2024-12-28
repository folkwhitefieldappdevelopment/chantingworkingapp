package com.iskcon.folk.app.chantandhear.constant;

public enum ApplicationConstants {
    TOTAL_BEADS(108),
    TOTAL_ROUND(16),
    TOTAL_BEADS_IN_A_MILESTONE(16),
    PANCHA_TATTVA_MANTRA_DURATION(6445L),
    PANCHA_TATTVA_MANTRA_SPEED(1f),
    HARE_KRISHNA_MANTRA_SINGLE_BEAD_DURATION(4021L),
    FLIP_VIEW_INTERVAL(20000),
    FLIP_VIEW_BEAD_AFTER(2),
    NO_OF_IMAGE_VIEW_FOR_FLIPPER(30),
    STAR_RATING_FOR_HEARD_COUNT(5),
    USER_ATTENTION_SLIDER_CLOSER_TIME(15000),
    USER_ATTENTION_SLIDER_SHOW_ON_EVERY(10),
    FAKE_HEARING_TIME_DIFFERENCE(2L);

    private final Object constantValue;

    ApplicationConstants(Object constantValue) {
        this.constantValue = constantValue;
    }

    public <T> T getConstantValue(Class<T> typeReference) {
        return (T) constantValue;
    }
}