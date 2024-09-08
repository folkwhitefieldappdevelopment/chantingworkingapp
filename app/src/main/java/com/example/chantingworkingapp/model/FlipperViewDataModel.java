package com.example.chantingworkingapp.model;

import com.example.chantingworkingapp.constant.FlipperViewType;

import java.io.Serializable;

public class FlipperViewDataModel implements Serializable {

    private FlipperViewType flipperViewType;
    private Object source;

    public FlipperViewDataModel(FlipperViewType flipperViewType, Object source) {
        this.flipperViewType = flipperViewType;
        this.source = source;
    }

    public FlipperViewType getFlipperViewType() {
        return flipperViewType;
    }

    public void setFlipperViewType(FlipperViewType flipperViewType) {
        this.flipperViewType = flipperViewType;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "FlipperViewDataModel{" +
                "flipperViewType=" + flipperViewType +
                ", source=" + source +
                '}';
    }
}