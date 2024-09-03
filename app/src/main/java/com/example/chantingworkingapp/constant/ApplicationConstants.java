package com.example.chantingworkingapp.constant;

public enum ApplicationConstants {
    TOTAL_BEADS(10),
    TOTAL_ROUND(16),
    TOTAL_BEADS_IN_A_MILESTONE(16),
    PANCHA_TATTVA_MANTRA_DURATION(6445L),
    HARE_KRISHNA_MANTRA_DURATION(3784L);

    private final Object constantValue;

    ApplicationConstants(Object constantValue) {
        this.constantValue = constantValue;
    }

    public <T> T getConstantValue(Class<T> typeReference) {
        return (T) constantValue;
    }
}
