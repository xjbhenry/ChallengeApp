package com.maximos.mobile.challengeapp.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.maximos.mobile.challengeapp.constants.App_Constants;
import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.model.User;
import com.maximos.mobile.challengeapp.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maximos on 11/10/2014.
 */
public class UserDao {

    private String TAG_NAME = UserDao.class.getName();

    private Logger logger = Logger.getLogger(TAG_NAME);

    public boolean findUser(String userId) {
        logger.log(Level.INFO,TAG_NAME+": Inside find User method with userid :" +userId);
        boolean result = false;
        Connection conn = DBConnect.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.FIND_USER);
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int count = rs.getInt(DB_Constants.USER_COUNT_FIELD);
                if(count>=1) {
                    result = true;
                }
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE,TAG_NAME + " : SqlException");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
                e.printStackTrace();
            }
        }
        logger.log(Level.INFO,"result :" + result);
        return result;
    }

    public User fetchUser(User user) {
        logger.log(Level.INFO,TAG_NAME + " :fetching user");
        User userDetails = new User();
        Connection conn = DBConnect.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.FETCH_USER);
            stmt.setString(1,user.getUserId());
            stmt.setString(2,user.getPassword());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                userDetails.setUserId(rs.getString(DB_Constants.USER_ID_FIELD));
                userDetails.setEmail(rs.getString(DB_Constants.USER_EMAIL_FIELD));
                userDetails.setName(rs.getString(DB_Constants.USER_NAME_FIELD));
                userDetails.setPassword(rs.getString(DB_Constants.USER_PASSWORD_FIELD));
                userDetails.setAddress(rs.getString(DB_Constants.USER_ADDRESS_FIELD));
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE, TAG_NAME + " : SQL Exception");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
                e.printStackTrace();
            }
        }
        return userDetails;
    }

    public boolean validateUser(String userId,String password) {
        logger.log(Level.INFO,TAG_NAME+": Inside Validate User method");
        boolean result = false;
        Connection conn = DBConnect.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.FIND_USER);
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int count = rs.getInt(1);
                if(count>=1) {
                    result = true;
                }
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE,TAG_NAME + " : SqlException");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
                e.printStackTrace();
            }
        }
        return result;
    }

    public void logUser(User user,Context context){
        logger.log(Level.INFO,TAG_NAME + " : Inside Register user method");
        User dbuser = null;
        boolean loggedIn = findUser(user.getUserId());
        if(loggedIn) {
            /* User exists in DB..fetch more details although we don't have many now */
            dbuser = fetchUser(user);
            if(dbuser.getUserId()!=null) {
                loggedIn = true;
            } else {
                loggedIn = false;
            }
        } else {
            logger.log(Level.INFO,TAG_NAME + " : User doesn't exist..Registering user");
            Connection conn = DBConnect.getConnection();
            try {
                PreparedStatement stmt = conn.prepareStatement(DB_Constants.REGISTER_USER);
                stmt.setString(1,user.getUserId());
                stmt.setString(2,user.getPassword());
                stmt.setString(3,user.getAddress());
                stmt.setString(4,user.getName());
                stmt.setString(5,user.getEmail());
                stmt.setTimestamp(6,new Timestamp(new Date().getTime()));
                stmt.setTimestamp(7,new Timestamp(new Date().getTime()));
                /*stmt.setString(6,user);*/
                stmt.executeUpdate();
                loggedIn = true;
                dbuser = user;
            } catch(SQLException e) {
                logger.log(Level.INFO,TAG_NAME + " : SQL Exception");
                e.printStackTrace();
            } finally {
                try{
                    conn.close();
                } catch (SQLException e) {
                    logger.log(Level.INFO,TAG_NAME + ": Close connection");
                    e.printStackTrace();
                }
            }
        }
        logger.log(Level.INFO,"is user logged in: " +loggedIn);
        SharedPreferences preferences = context.getSharedPreferences(App_Constants.USER_PREFERENCE_FILE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(loggedIn) {
            editor.putBoolean(App_Constants.IS_USER_LOGGED_IN, loggedIn);
            editor.putString(App_Constants.LOGGED_USER_ID, dbuser.getUserId());
        } else {
            editor.clear();
        }
        editor.commit();
    }

    public boolean updateUser (User user) {
        logger.log(Level.INFO,TAG_NAME + " :update user");
        Boolean result = false;
        Connection conn = DBConnect.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.UPDATE_USER);
            stmt.setString(1,user.getAddress());
            stmt.setString(2,user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUserId());
            int rowCounts = stmt.executeUpdate();
            if (rowCounts >= 1) {
                logger.log(Level.INFO, TAG_NAME + "updated" + rowCounts +"lines");
                result = true;
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE, TAG_NAME + " : SQL Exception");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
                e.printStackTrace();
            }
        }
        return result;
    }

    public User getUser(String userId) {
        logger.log(Level.INFO,TAG_NAME + " :fetching user");
        User userDetails = new User();
        Connection conn = DBConnect.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.GET_USER);
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                userDetails.setUserId(rs.getString(DB_Constants.USER_ID_FIELD));
                userDetails.setEmail(rs.getString(DB_Constants.USER_EMAIL_FIELD));
                userDetails.setName(rs.getString(DB_Constants.USER_NAME_FIELD));
                userDetails.setPassword(rs.getString(DB_Constants.USER_PASSWORD_FIELD));
                userDetails.setAddress(rs.getString(DB_Constants.USER_ADDRESS_FIELD));
            }
        } catch(SQLException e) {
            logger.log(Level.SEVERE, TAG_NAME + " : SQL Exception");
            e.printStackTrace();
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO,TAG_NAME + ": Close connection");
                e.printStackTrace();
            }
        }
        return userDetails;
    }
}