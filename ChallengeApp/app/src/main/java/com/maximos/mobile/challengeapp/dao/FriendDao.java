package com.maximos.mobile.challengeapp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.data.FriendInfo;
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
 * Created by Henry on 11/17/2014.
 */
public class FriendDao {
    private String TAG_NAME = FriendDao.class.getName();
    private Logger logger = Logger.getLogger(TAG_NAME);

    public List<FriendInfo> getFriends(String userId) {
        List mFriends = new ArrayList();

        Connection conn = DBConnect.getConnection();

        try {
            logger.log(Level.INFO,TAG_NAME + " :inside db");
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.GET_FRIEND);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                logger.log(Level.INFO,TAG_NAME + ": Working");
                String result = rs.getString(DB_Constants.TAG_FRIENDID);
                Gson gson = new Gson();
                mFriends = gson.fromJson(result, new TypeToken<List<FriendInfo>>(){}.getType());
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
        return mFriends;
    }

    public Boolean storeFriend (String userId, List<FriendInfo> friend) {
        Connection conn = DBConnect.getConnection();
        Boolean result = false;
        try {
            logger.log(Level.INFO,TAG_NAME + " :inside db");
            Gson gson = new Gson();
            String friendString = gson.toJson(friend,new TypeToken<List<FriendInfo>>(){}.getType());
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.GET_FRIEND);
            stmt.setString(1,friendString);
            stmt.setString(2, userId);
            int rowCounts = stmt.executeUpdate();
            while(rowCounts >= 1) {
                logger.log(Level.INFO, TAG_NAME + "updated" + rowCounts +"lines");
                result = true;
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
        return result;
    }
}
