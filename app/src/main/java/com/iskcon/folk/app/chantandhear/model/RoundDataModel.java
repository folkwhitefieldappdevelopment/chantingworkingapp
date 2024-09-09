package com.iskcon.folk.app.chantandhear.model;

import com.iskcon.folk.app.chantandhear.constant.Milestone;

import java.util.Date;
import java.util.Map;

public class RoundDataModel {

    private Map<Milestone, RoundMilestoneDataModel> roundMilestoneDataModelMap;

    private int roundId;// number of the respective round

    private Date startTime;

    private Date endTime;

    private int totalHeardCount;

    public Map<Milestone, RoundMilestoneDataModel> getRoundMilestoneDataModelMap() {
        return roundMilestoneDataModelMap;
    }

    public void setRoundMilestoneDataModelMap(Map<Milestone, RoundMilestoneDataModel> roundMilestoneDataModelMap) {
        this.roundMilestoneDataModelMap = roundMilestoneDataModelMap;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTotalHeardCount() {
        return totalHeardCount;
    }

    public void setTotalHeardCount(int totalHeardCount) {
        this.totalHeardCount = totalHeardCount;
    }
}