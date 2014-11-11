package com.maximos.mobile.challengeapp.dao;

import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.model.User;
import com.maximos.mobile.challengeapp.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maximos on 11/10/2014.
 */
public class UserDao {

    private String TAG_NAME = UserDao.class.getName();

    private Logger logger = Logger.getLogger(TAG_NAME);

    public void registerUser(User user){
        logger.log(Level.INFO,TAG_NAME + " : Inside Register user method");
        Connection conn = DBConnect.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.INSERT_INTO_USER);
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());
            stmt.executeUpdate();
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
    }
}