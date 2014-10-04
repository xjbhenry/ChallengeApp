package com.maximos.mobile.challengeapp;

/**
 * Created by Henry on 10/4/2014.
 */
public class Profile {
    int userId;
    int[] challengeIdCompleted;
    String challengeIdCompletedCat;
    int[] challengeIdOngiong;
    String challengeIdOngoingCat;
    int[] challengeIdCreated;
    String challengeIdCreatedCat;
    int credits;
    int[] friendsId;
    String friendsIdCat;
    int[] affiliationId;
    String affiliationIdCat;
    int[] visitedPlaceId;
    String visitedPlaceIdCat;
    int[] interestId;
    String interestIdCat;
    String description;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInterestIdCat() {
        interestIdCat = catenate(interestId);
        return interestIdCat;
    }

    public String getVisitedPlaceIdCat() {
        visitedPlaceIdCat = catenate(visitedPlaceId);
        return visitedPlaceIdCat;
    }

    public String getAffiliationIdCat() {
        affiliationIdCat = catenate(affiliationId);
        return affiliationIdCat;
    }

    public String getFriendsIdCat() {
        friendsIdCat = catenate(friendsId);
        return friendsIdCat;
    }

    public String getChallengeIdCreatedCat() {
        challengeIdCreatedCat = catenate(challengeIdCreated);
        return challengeIdCreatedCat;
    }

    public String getChallengeIdOngoingCat() {
        challengeIdOngoingCat = catenate(challengeIdOngiong);
        return challengeIdOngoingCat;
    }

    private String catenate(int[] array) {
        String result = null;
        for (int i = 0; i < array.length; i++) {
            result = result + array[i] + " ";
        }
        return result;
    }

    public String getChallengeIdCompletedCat() {
        challengeIdCompletedCat = catenate(challengeIdCompleted);
        return challengeIdCompletedCat;
    }

    public int[] getChallengeIdCompleted() {
        return challengeIdCompleted;
    }

    public int[] getChallengeIdOngiong() {
        return challengeIdOngiong;
    }

    public int[] getChallengeIdCreated() {
        return challengeIdCreated;
    }

    public int getCredits() {
        return credits;
    }

    public void setChallengeIdCompleted(int[] challengeIdCompleted) {
        this.challengeIdCompleted = challengeIdCompleted;
    }

    public void setChallengeIdCompletedCat(String challengeIdCompletedCat) {
        this.challengeIdCompletedCat = challengeIdCompletedCat;
    }

    public void setChallengeIdOngiong(int[] challengeIdOngiong) {
        this.challengeIdOngiong = challengeIdOngiong;
    }

    public void setChallengeIdOngoingCat(String challengeIdOngoingCat) {
        this.challengeIdOngoingCat = challengeIdOngoingCat;
    }

    public void setChallengeIdCreated(int[] challengeIdCreated) {
        this.challengeIdCreated = challengeIdCreated;
    }

    public void setChallengeIdCreatedCat(String challengeIdCreatedCat) {
        this.challengeIdCreatedCat = challengeIdCreatedCat;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setFriendsId(int[] friendsId) {
        this.friendsId = friendsId;
    }

    public void setFriendsIdCat(String friendsIdCat) {
        this.friendsIdCat = friendsIdCat;
    }

    public void setAffiliationId(int[] affiliationId) {
        this.affiliationId = affiliationId;
    }

    public void setAffiliationIdCat(String affiliationIdCat) {
        this.affiliationIdCat = affiliationIdCat;
    }

    public void setVisitedPlaceId(int[] visitedPlaceId) {
        this.visitedPlaceId = visitedPlaceId;
    }

    public void setVisitedPlaceIdCat(String visitedPlaceIdCat) {
        this.visitedPlaceIdCat = visitedPlaceIdCat;
    }

    public void setInterestId(int[] interestId) {
        this.interestId = interestId;
    }

    public void setInterestIdCat(String interestIdCat) {
        this.interestIdCat = interestIdCat;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}