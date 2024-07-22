package com.example.chantingworkingapp.model;

import com.example.chantingworkingapp.constant.Milestone;

public class RoundMilestoneDataModel {

    private Milestone milestone;

    private int milestoneHeadCount;

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public int getMilestoneHeadCount() {
        return milestoneHeadCount;
    }

    public void setMilestoneHeadCount(int milestoneHeadCount) {
        this.milestoneHeadCount = milestoneHeadCount;
    }
}