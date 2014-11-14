package com.maximos.mobile.challengeapp.dao;

import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.data.Profile;
import com.maximos.mobile.challengeapp.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Henry on 11/11/2014.
 */
public class ProfileDao {
    private String TAG_NAME = ProfileDao.class.getName();
    private Logger logger = Logger.getLogger(TAG_NAME);
    Profile profile = new Profile();


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
            conn.commit();

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
}
