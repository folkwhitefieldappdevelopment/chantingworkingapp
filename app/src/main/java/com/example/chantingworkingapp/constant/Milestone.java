package com.example.chantingworkingapp.constant;

public enum Milestone {

    MILESTONE_1(1,1,16),
    MILESTONE_2(2,17,32),
    MILESTONE_3(3,33,48),
    MILESTONE_4(4,49,64),
    MILESTONE_5(5,65,80),
    MILESTONE_6(6,81,96),
    MILESTONE_7(7,97,108);

    private final int milestoneId;
    private final int startBead;
    private final int endBead;

    Milestone(int milestoneId, int startBead, int endBead) {
        this.milestoneId = milestoneId;
        this.startBead = startBead;
        this.endBead = endBead;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public int getStartBead() {
        return startBead;
    }

    public int getEndBead() {
        return endBead;
    }
}
