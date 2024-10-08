package com.iskcon.folk.app.chantandhear.model;

import com.iskcon.folk.app.chantandhear.constant.Milestone;

public class RoundMilestoneDataModel {

    private Milestone milestone;

    private int milestoneHeardCount;

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public int getMilestoneHeardCount() {
        return milestoneHeardCount;
    }

    public void setMilestoneHeardCount(int milestoneHeardCount) {
        this.milestoneHeardCount = milestoneHeardCount;
    }
}