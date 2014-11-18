package com.maximos.mobile.challengeapp.dao;

import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.data.Profile;
import com.maximos.mobile.challengeapp.data.UserActivity;
import com.maximos.mobile.challengeapp.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Henry on 11/11/2014.
 */
public class ProfileDao {
    private String TAG_NAME = ProfileDao.class.getName();
    private Logger logger = Logger.getLogger(TAG_NAME);
    private Profile profile = new Profile();


    public Profile getProfile (String userId){
        logger.log(Level.INFO,TAG_NAME + " : Inside get profile method");

        Connection conn = DBConnect.getConnection();
        try {
            logger.log(Level.INFO,TAG_NAME + " :inside db");
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.GET_PROFILE);
            stmt.setString(1, userId);
            logger.log(Level.INFO,TAG_NAME + " :ok");
           /* stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());*/
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                logger.log(Level.INFO,TAG_NAME + ": Working");
                profile.setUserId(userId);
                profile.setName(rs.getString("userid"));
                profile.setUserAvatar(rs.getString("avatar_link"));
            }

        } catch(SQLException e) {
            logger.log(Level.INFO,TAG_NAME + " : SQL Exception");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
            }
        }
        return profile;
    }


    public List <UserActivity> getUserActivity (String userId) {
        List userActivityList = new ArrayList();


        Connection conn = DBConnect.getConnection();
        try {
            logger.log(Level.INFO,TAG_NAME + " :inside db");
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.GET_USER_ACTIVITY);
            stmt.setString(1, userId);
            logger.log(Level.INFO,TAG_NAME + " :ok");
           /* stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());*/
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                UserActivity userActivity = new UserActivity();
                logger.log(Level.INFO,TAG_NAME + ": Working");
                userActivity.setUserId(userId);
                userActivity.setChallengeId(rs.getInt("challengeid"));
                userActivity.setChallengeStatus(rs.getInt("status"));
                userActivity.setChallengeCreatedTimeStamp(rs.getTimestamp("created_timestamp"));
                userActivity.setChallengeUpdatedTimeStamp(rs.getTimestamp("updated_timestamp"));
                userActivityList.add(userActivity);
            }

        } catch(SQLException e) {
            logger.log(Level.INFO,TAG_NAME + " : SQL Exception");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
            }
        }
        return userActivityList;
    }

    public List <UserActivity> getUserActivityToDo (String userId) {
        List userActivityList = new ArrayList();


        Connection conn = DBConnect.getConnection();
        try {
            logger.log(Level.INFO,TAG_NAME + " :inside db");
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.GET_USER_ACTIVITY_TODO);
            stmt.setString(1, userId);
            logger.log(Level.INFO,TAG_NAME + " :ok");
           /* stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());*/
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                UserActivity userActivity = new UserActivity();
                logger.log(Level.INFO,TAG_NAME + ": Working");
                userActivity.setUserId(userId);
                userActivity.setChallengeId(rs.getInt("challengeid"));
                userActivity.setChallengeStatus(rs.getInt("status"));
                userActivity.setChallengeCreatedTimeStamp(rs.getTimestamp("created_timestamp"));
                userActivity.setChallengeUpdatedTimeStamp(rs.getTimestamp("updated_timestamp"));
                userActivityList.add(userActivity);
            }

        } catch(SQLException e) {
            logger.log(Level.INFO,TAG_NAME + " : SQL Exception");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
            }
        }
        return userActivityList;
    }

}
