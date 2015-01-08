package com.maximos.mobile.challengeapp.dao;

import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.model.Challenge;
import com.maximos.mobile.challengeapp.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maximos on 11/17/2014.
 */
public class ChallengeDao {

    private static String TAG_NAME = ChallengeDao.class.getName();

    private static Logger logger = Logger.getLogger(TAG_NAME);

    public static void createChallenge(Challenge challenge) {
        logger.log(Level.INFO,TAG_NAME+": Inside createChallengeId table with creator Id :" + challenge.getCreatorId());
        Connection conn = DBConnect.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.INSERT_CHALLENGE);
            stmt.setString(1,challenge.getTitle());
            stmt.setString(2,challenge.getDescription());
            stmt.setInt(3,challenge.getCategory());
            stmt.setString(4,challenge.getDemonstration());
            stmt.setTimestamp(5,new Timestamp(new Date().getTime()));
            stmt.setTimestamp(6,new Timestamp(new Date().getTime()));
            stmt.setString(7,challenge.getCreatorId());
            stmt.executeUpdate();
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
}
