package com.iskcon.folk.app.chantandhear.history.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ChantingRoundDataEntity implements Serializable{
    private String rowId;
    private int roundNumber;
    private Date startTime;
    private Date endTime;
    private long timeTaken;
    private int totalHeardCount;
    private Float playbackSpeed;
    private int status;
    private int earlyDone;
    private List<ChantingBeadDataEntity> chantingBeadDataEntities;

    public ChantingRoundDataEntity(){
    };

    public ChantingRoundDataEntity(String rowId, int roundNumber, Date startTime, Date endTime, long timeTaken, int totalHeardCount, Float playbackSpeed, int status, int earlyDone, List<ChantingBeadDataEntity> chantingBeadDataEntities) {
        this.rowId = rowId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
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

    public int getEarlyDone() {
        return earlyDone;
    }

    public void setEarlyDone(int earlyDone) {
        this.earlyDone = earlyDone;
    }

    public List<ChantingBeadDataEntity> getChantingBeadDataEntities() {
        return chantingBeadDataEntities;
    }

    public void setChantingBeadDataEntities(List<ChantingBeadDataEntity> chantingBeadDataEntities) {
        this.chantingBeadDataEntities = chantingBeadDataEntities;
    }
}
