package com.maximos.mobile.challengeapp.model;

import java.sql.Timestamp;

/**
 * Created by Maximos on 11/17/2014.
 */
public class Challenge {
    int challengeId;
    String title;
    String description;
    int category;
    String demonstration;
    Timestamp createdTimestamp;
    Timestamp updatedTimestamp;
    String creatorId;

    public Challenge(String title, String description, int category, String demonstration, Timestamp createdTimestamp, Timestamp updatedTimestamp, String creatorId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.demonstration = demonstration;
        this.creatorId = creatorId;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDemonstration() {
        return demonstration;
    }

    public void setDemonstration(String demonstration) {
        this.demonstration = demonstration;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Timestamp getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
