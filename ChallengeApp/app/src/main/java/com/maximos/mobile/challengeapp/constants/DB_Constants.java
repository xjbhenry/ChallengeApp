package com.maximos.mobile.challengeapp.constants;

/**
 * Created by Maximos on 11/10/2014.
 */
public class DB_Constants {
    public static final String TAG_USERID = "x@x.com";

    /* Queries for User Table */
    public static final String REGISTER_USER = "INSERT INTO `dogest_challengeapp`.`user` (`"+ DB_Constants.USER_ID_FIELD +"`, `"+ DB_Constants.USER_PASSWORD_FIELD +"`, `"+ DB_Constants.USER_ADDRESS_FIELD +"`, `"+ DB_Constants.USER_NAME_FIELD +"`, `"+ DB_Constants.USER_EMAIL_FIELD +"`, `"+ DB_Constants.USER_CREATED_TIMESTAMP_FIELD +"`, `"+ DB_Constants.USER_UPDATED_TIMESTAMP_FIELD +"`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "SELECT COUNT( * ) as COUNT FROM  `dogest_challengeapp`.`user` WHERE userid =  ?";
    public static final String FETCH_USER = "SELECT * FROM  `user` WHERE  `userid` =  ? AND  `password` =  ?";
    public static final String VALIDATE_USER = "";
    public static final String UPDATE_USER = "UPDATE `user` SET `address` = ?, `name` = ?, `email` = ? WHERE userid = ?";
    public static final String GET_USER = "SELECT * FROM `user` WHERE `userid` = ?";

    /* Queries for Challenge Table */
    public static final String INSERT_CHALLENGE = "INSERT INTO `challenge`(`title`, `description`, `category`, `demonstration`, `created_timestamp`, `updated_timestamp`, `creatorid`) VALUES (?,?,?,?,?,?,?)";

    /* Queries for Notification Service */
    public static final String INSERT_NOTIFICATION = "INSERT INTO ";

    /* Constants for User table */
    public static final String USER_ID_FIELD = "userid";
    public static final String USER_PASSWORD_FIELD = "password";
    public static final String USER_ADDRESS_FIELD = "address";
    public static final String USER_NAME_FIELD = "name";
    public static final String USER_EMAIL_FIELD = "email";
    public static final String USER_CREATED_TIMESTAMP_FIELD = "created_timestamp";
    public static final String USER_UPDATED_TIMESTAMP_FIELD ="updated_timestamp";
    public static final String USER_COUNT_FIELD = "COUNT";


    public static final String INSERT_INTO_USER = "INSERT INTO  `dogest_challengeapp`.`User` (`UserName` , `Password`) VALUES (?,  ?);";
    /*Queries for profile table*/
    public static final String GET_PROFILE = "SELECT * FROM `profile` WHERE `userid` = ?";
    /*Queries for user_activity table*/
    public static final String GET_USER_ACTIVITY = "SELECT * FROM `user_activity` WHERE `userid` = ? AND `status` = 1";
    public static final String GET_USER_ACTIVITY_TODO = "SELECT * FROM `user_activity` WHERE `userid` = ? AND `status` = 0";
    /*Queries for challenge table*/
    public static final String GET_CHALLENGE = "SELECT * FROM `challenge` WHERE `challengeid` = ?";
    //public static final String GET_CHALLENGE = "SELECT * FROM `challenge` WHERE `challengeid` IN (SELECT `challengeid` from user_activity where `status`= 1 AND `challengeid` = ?)";
    //public static final String GET_CHALLENGE_TODO = "SELECT * FROM `challenge` WHERE `challengeid` IN (SELECT `challengeid` from user_activity where `status`= 0 AND `challengeid` = ?)";
    public static final String GET_CHALLENGE_TODO = "SELECT * FROM `challenge` WHERE `challengeid` = ?";

    /*Queries for friend table*/
    public static final String GET_FRIEND = "SELECT * FROM `friends WHERE `userid` = ?`";
    public static final String STORE_FRIEND = "UPDATE `friends` SET `friendid` = ? WHERE `userid` = ?";

    /* Constant for friend table */
    public static final String TAG_FRIENDID = "friendid";
}
