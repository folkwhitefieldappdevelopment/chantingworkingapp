package com.iskcon.folk.app.chantandhear.history.model;

import java.io.Serializable;

public class BeadDataEntity implements Serializable {

    private String rowId;
    private int roundNumber;
    private int hearingLevel;
    private int startBead;
    private int endBead;
    private int heardCount;

    public BeadDataEntity() {
    }

    public BeadDataEntity(String rowId, int roundNumber, int hearingLevel, int startBead, int endBead, int heardCount) {
        this.rowId = rowId;
        this.roundNumber = roundNumber;
        this.hearingLevel = hearingLevel;
        this.startBead = startBead;
        this.endBead = endBead;
        this.heardCount = heardCount;
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

    public int getHearingLevel() {
        return hearingLevel;
    }

    public void setHearingLevel(int hearingLevel) {
        this.hearingLevel = hearingLevel;
    }

    public int getStartBead() {
        return startBead;
    }

    public void setStartBead(int startBead) {
        this.startBead = startBead;
    }

    public int getEndBead() {
        return endBead;
    }

    public void setEndBead(int endBead) {
        this.endBead = endBead;
    }

    public int getHeardCount() {
        return heardCount;
    }

    public void setHeardCount(int heardCount) {
        this.heardCount = heardCount;
    }
}