package com.maximos.mobile.challengeapp.dao;

import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.data.Challenge;
import com.maximos.mobile.challengeapp.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Henry on 11/16/2014.
 */
public class ProfileSubDao {
    private String TAG_NAME = ProfileSubDao.class.getName();
    private Logger logger = Logger.getLogger(TAG_NAME);
    private Challenge mChallenge = new Challenge();


    public Challenge getChallenge (int challengeId){
        logger.log(Level.INFO,TAG_NAME + " : Inside get challenge method with challenge id:" + challengeId);

        Connection conn = DBConnect.getConnection();
        try {
            logger.log(Level.INFO,TAG_NAME + " :inside db");
            PreparedStatement stmt = conn.prepareStatement(DB_Constants.GET_CHALLENGE);
            stmt.setInt(1, challengeId);
            logger.log(Level.INFO,TAG_NAME + " :ok");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                logger.log(Level.INFO,TAG_NAME + ": Working");
                mChallenge.setChallengeId(rs.getInt("challengeid"));
                mChallenge.setChallengeTitle(rs.getString("title"));
                mChallenge.setChallengeDescription(rs.getString("description"));
                mChallenge.setChallengeCategory(rs.getInt("category"));
                mChallenge.setChallengeDemonstration(rs.getString("demonstration"));
                mChallenge.setChallengeCreatedTimeStamp(rs.getTimestamp("created_timestamp"));
                mChallenge.setChallengeUpdatedTimeStamp(rs.getTimestamp("updated_timestamp"));
                mChallenge.setChallengeCreatorId(rs.getString("creatorid"));
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
        return mChallenge;
    }
}
