package com.example.chantingworkingapp.model;

import com.example.chantingworkingapp.constant.Milestone;

import java.util.Map;

public class RoundDataModel {

    private Map<Milestone, RoundMilestoneDataModel> japaMalaRoundMilestoneDataModelMap;

    private int roundId;// number of the respective round

    private int startTime;

    private int endTime;

    private int totalHeardCount;

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Map<Milestone, RoundMilestoneDataModel> getJapaMalaRoundMilestoneDataModelMap() {
        return japaMalaRoundMilestoneDataModelMap;
    }

    public void setJapaMalaRoundMilestoneDataModelMap(Map<Milestone, RoundMilestoneDataModel> japaMalaRoundMilestoneDataModelMap) {
        this.japaMalaRoundMilestoneDataModelMap = japaMalaRoundMilestoneDataModelMap;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getTotalHeadCount() {
        return totalHeardCount;
    }

    public void setTotalHeadCount(int totalHeadCount) {
        this.totalHeardCount = totalHeadCount;
    }
}
