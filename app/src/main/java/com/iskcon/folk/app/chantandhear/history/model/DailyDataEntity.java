package com.iskcon.folk.app.chantandhear.history.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DailyDataEntity implements Serializable {

    private String userId;
    private Date chantingDate;
    private String rowId;
    private int numberOfRoundsDone;
    private int numberOfRoundsSet;
    private List<RoundDataEntity> chantingRoundDataEntities;

    public DailyDataEntity(){
    }

    public DailyDataEntity(String userId, Date chantingDate, String rowId, int numberOfRoundsDone, int numberOfRoundsSet, List<RoundDataEntity> chantingRoundDataEntities) {
        this.userId = userId;
        this.chantingDate = chantingDate;
        this.rowId = rowId;
        this.numberOfRoundsDone = numberOfRoundsDone;
        this.numberOfRoundsSet = numberOfRoundsSet;
        this.chantingRoundDataEntities = chantingRoundDataEntities;
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

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public int getNumberOfRoundsDone() {
        return numberOfRoundsDone;
    }

    public void setNumberOfRoundsDone(int numberOfRoundsDone) {
        this.numberOfRoundsDone = numberOfRoundsDone;
    }

    public int getNumberOfRoundsSet() {
        return numberOfRoundsSet;
    }

    public void setNumberOfRoundsSet(int numberOfRoundsSet) {
        this.numberOfRoundsSet = numberOfRoundsSet;
    }

    public List<RoundDataEntity> getChantingRoundDataEntities() {
        return chantingRoundDataEntities;
    }

    public void setChantingRoundDataEntities(List<RoundDataEntity> chantingRoundDataEntities) {
        this.chantingRoundDataEntities = chantingRoundDataEntities;
    }
}
