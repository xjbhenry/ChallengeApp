package com.maximos.mobile.challengeapp.model;

/**
 * Created by Henry on 10/4/2014.
 */
public class Post {
    int type;
    int creatorId;
    int postId;
    int challengeId;
    String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

}
