package com.maximos.mobile.challengeapp.data;

import java.sql.Timestamp;

/**
 * Created by Henry on 11/16/2014.
 */
public class Challenge {
    private int challengeId;
    private String challengeTitle;
    private String challengeDescription;
    private int challengeCategory;
    private String challengeDemonstration;
    private Timestamp challengeCreatedTimeStamp;
    private Timestamp challengeUpdatedTimeStamp;
    private String challengeCreatorId;

    public int getChallengeCategory() {
        return challengeCategory;
    }

    public void setChallengeCategory(int challengeCategory) {
        this.challengeCategory = challengeCategory;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public String getChallengeTitle() {
        return challengeTitle;
    }

    public void setChallengeTitle(String challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    public String getChallengeDescription() {
        return challengeDescription;
    }

    public void setChallengeDescription(String challengeDescription) {
        this.challengeDescription = challengeDescription;
    }

    public String getChallengeDemonstration() {
        return challengeDemonstration;
    }

    public void setChallengeDemonstration(String challengeDemonstration) {
        this.challengeDemonstration = challengeDemonstration;
    }

    public Timestamp getChallengeCreatedTimeStamp() {
        return challengeCreatedTimeStamp;
    }

    public void setChallengeCreatedTimeStamp(Timestamp challengeTimeStamp) {
        this.challengeCreatedTimeStamp = challengeTimeStamp;
    }

    public Timestamp getChallengeUpdatedTimeStamp() {
        return challengeUpdatedTimeStamp;
    }

    public void setChallengeUpdatedTimeStamp(Timestamp challengeUpdatedTimeStamp) {
        this.challengeUpdatedTimeStamp = challengeUpdatedTimeStamp;
    }

    public String getChallengeCreatorId() {
        return challengeCreatorId;
    }

    public void setChallengeCreatorId(String challengeCreatorId) {
        this.challengeCreatorId = challengeCreatorId;
    }
}
