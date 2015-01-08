package com.maximos.mobile.challengeapp.data;

import java.sql.Timestamp;

/**
 * Created by Henry on 11/16/2014.
 */
public class UserActivity {
    private String userId;
    private int challengeId;
    private int challengeStatus;
    private Timestamp challengeCreatedTimeStamp;
    private Timestamp challengeUpdatedTimeStamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public int getChallengeStatus() {
        return challengeStatus;
    }

    public void setChallengeStatus(int challengeStatus) {
        this.challengeStatus = challengeStatus;
    }

    public Timestamp getChallengeCreatedTimeStamp() {
        return challengeCreatedTimeStamp;
    }

    public void setChallengeCreatedTimeStamp(Timestamp challengeCreatedTimeStamp) {
        this.challengeCreatedTimeStamp = challengeCreatedTimeStamp;
    }

    public Timestamp getChallengeUpdatedTimeStamp() {
        return challengeUpdatedTimeStamp;
    }

    public void setChallengeUpdatedTimeStamp(Timestamp challengeUpdatedTimeStamp) {
        this.challengeUpdatedTimeStamp = challengeUpdatedTimeStamp;
    }
}
