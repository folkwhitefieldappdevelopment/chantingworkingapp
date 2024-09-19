package com.iskcon.folk.app.chantandhear.history.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoundDataEntity implements Serializable {
    private String userId;
    private Date chantingDate;
    private int roundNumber;
    private Date startTime;
    private Date endTime;
    private long timeTaken;
    private int totalHeardCount;
    private Float playbackSpeed;
    private int status;
    private int earlyDone;
    private List<BeadDataEntity> chantingBeadDataEntities;

    public RoundDataEntity() {
    }

    public RoundDataEntity(String userId, Date chantingDate, int roundNumber, Date startTime, Date endTime, long timeTaken, int totalHeardCount, Float playbackSpeed, int status, int earlyDone, List<BeadDataEntity> chantingBeadDataEntities) {
        this.userId = userId;
        this.chantingDate = chantingDate;
        this.roundNumber = roundNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeTaken = timeTaken;
        this.totalHeardCount = totalHeardCount;
        this.playbackSpeed = playbackSpeed;
        this.status = status;
        this.earlyDone = earlyDone;
        this.chantingBeadDataEntities = chantingBeadDataEntities;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getChantingDate() {
        return chantingDate;
    }

    public void setChantingDate(Date chantingDate) {
        this.chantingDate = chantingDate;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
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

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getTotalHeardCount() {
        return totalHeardCount;
    }

    public void setTotalHeardCount(int totalHeardCount) {
        this.totalHeardCount = totalHeardCount;
    }

    public Float getPlaybackSpeed() {
        return playbackSpeed;
    }

    public void setPlaybackSpeed(Float playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEarlyDone() {
        return earlyDone;
    }

    public void setEarlyDone(int earlyDone) {
        this.earlyDone = earlyDone;
    }

    public List<BeadDataEntity> getChantingBeadDataEntities() {
        return chantingBeadDataEntities;
    }

    public void setChantingBeadDataEntities(List<BeadDataEntity> chantingBeadDataEntities) {
        this.chantingBeadDataEntities = chantingBeadDataEntities;
    }
}