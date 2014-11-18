package com.maximos.mobile.challengeapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maximos on 11/10/2014.
 */
public class DBConnect {

    private static String TAG_NAME = DBConnect.class.getName();

    private static Logger logger = Logger.getLogger(TAG_NAME);

    /**
     * Credentials to be moved to some secure location
     */
    private final static String USERNAME = "dogest_Admin";
    private final static String PASSWORD = "password12345";
    private final static String HOSTNAME = "gator4135.hostgator.com:3306";
    private final static String DB_NAME = "dogest_challengeapp";

    public static void main(String args[]) {
        Connection conn = getConnection();
        logger.log(Level.INFO,TAG_NAME + " : " + conn);
    }

    /**
     * Connection class
     * @return connection object to database
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String differentWay = "jdbc:mysql://"+HOSTNAME+"/"+DB_NAME+"?zeroDateTimeBehavior=convertToNull" + "&user="+USERNAME+"&password="+PASSWORD;
            conn = DriverManager.getConnection(differentWay);
            if (conn != null) {
                System.out.println("You made it, take control your database now!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO, TAG_NAME + " : ClassNotFoundException");
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.log(Level.INFO, TAG_NAME + " : InstantiationException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.log(Level.INFO, TAG_NAME + " : IllegalAccessException");
            e.printStackTrace();
        } catch (SQLException e) {
            logger.log(Level.INFO, TAG_NAME + " : SQLException");
            e.printStackTrace();
        } /*finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO, TAG_NAME + " : SQLException");
                e.printStackTrace();
            }
        }*/
        return conn;
    }
}
