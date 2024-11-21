package com.iskcon.folk.app.chantandhear.constant;

import com.iskcon.folk.app.chantandhear.R;

public enum Milestone {

    MILESTONE_1(1, 1, 16),
    MILESTONE_2(2, 17, 32),
    MILESTONE_3(3, 33, 48),
    MILESTONE_4(4, 49, 64),
    MILESTONE_5(5, 65, 80),
    MILESTONE_6(6, 81, 96),
    MILESTONE_7(7, 97, 108);

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

    public static Milestone beadInBetweenWhichMilestoe(int currentBead) {
        Milestone returnMilestone = null;
        for (Milestone milestone : values()) {
            if (currentBead >= milestone.getStartBead() && currentBead <= milestone.getEndBead()) {
                returnMilestone = milestone;
                break;
            }
        }
        return returnMilestone;
    }

    public static Milestone getMilestoneById(int milestoneId) {

        Milestone retMilestone = null;

        for (Milestone locMilestone : values()) {

            if (milestoneId == locMilestone.getMilestoneId()) {

                retMilestone = locMilestone;

                break;
            }
        }

        return retMilestone;
    }

    public static Milestone getNextMilestone(Milestone currentMilestone) {

        return getMilestoneById(currentMilestone.getMilestoneId() + 1);
    }
}