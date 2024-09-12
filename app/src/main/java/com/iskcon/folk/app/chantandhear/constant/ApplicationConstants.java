package com.iskcon.folk.app.chantandhear.constant;

public enum ApplicationConstants {
    TOTAL_BEADS(108),
    TOTAL_ROUND(16),
    TOTAL_BEADS_IN_A_MILESTONE(16),
    PANCHA_TATTVA_MANTRA_DURATION(6445L),
    PANCHA_TATTVA_MANTRA_SPEED(2f),
    HARE_KRISHNA_MANTRA_SINGLE_BEAD_DURATION(4021L),
    FLIP_VIEW_INTERVAL(4000),
    FLIP_VIEW_BEAD_AFTER(2),
    NO_OF_IMAGE_VIEW_FOR_FLIPPER(42);

    private final Object constantValue;

    ApplicationConstants(Object constantValue) {
        this.constantValue = constantValue;
    }

    public <T> T getConstantValue(Class<T> typeReference) {
        return (T) constantValue;
    }
}
