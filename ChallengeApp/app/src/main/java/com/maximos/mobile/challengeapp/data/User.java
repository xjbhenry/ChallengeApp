package com.maximos.mobile.challengeapp.data;

import java.sql.Timestamp;

/**
 * Created by Henry on 10/4/2014.
 */
public class User {
    int userId;
    String name;
    String email;
    String address;
    String password;
    Profile profile;
    Timestamp created_timestamp;
    Timestamp updated_timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Timestamp getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCreated_timestamp(Timestamp created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public Timestamp getUpdated_timestamp() {
        return updated_timestamp;
    }

    public void setUpdated_timestamp(Timestamp updated_timestamp) {
        this.updated_timestamp = updated_timestamp;
    }
}