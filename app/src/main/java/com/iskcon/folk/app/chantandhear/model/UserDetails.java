package com.iskcon.folk.app.chantandhear.model;

import java.io.Serializable;

public class UserDetails implements Serializable {

    private String id;
    private String name;
    private String emailId;
    private String displayName;

    public UserDetails(String id, String name, String emailId, String displayName) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}