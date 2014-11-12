package com.maximos.mobile.challengeapp.constants;

/**
 * Created by Maximos on 11/10/2014.
 */
public class DB_Constants {

    /* Queries for User Table */
    public static final String REGISTER_USER = "INSERT INTO `dogest_challengeapp`.`user` (`"+ DB_Constants.USER_ID_FIELD +"`, `"+ DB_Constants.USER_PASSWORD_FIELD +"`, `"+ DB_Constants.USER_ADDRESS_FIELD +"`, `"+ DB_Constants.USER_NAME_FIELD +"`, `"+ DB_Constants.USER_EMAIL_FIELD +"`, `"+ DB_Constants.USER_CREATED_TIMESTAMP_FIELD +"`, `"+ DB_Constants.USER_UPDATED_TIMESTAMP_FIELD +"`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "SELECT COUNT( * ) as COUNT FROM  `dogest_challengeapp`.`user` WHERE userid =  ?";
    public static final String FETCH_USER = "SELECT * FROM  `user` WHERE  `userid` =  ? AND  `password` =  ?";
    public static final String VALIDATE_USER = "";

    /* Constants for User table */
    public static final String USER_ID_FIELD = "userid";
    public static final String USER_PASSWORD_FIELD = "password";
    public static final String USER_ADDRESS_FIELD = "address";
    public static final String USER_NAME_FIELD = "name";
    public static final String USER_EMAIL_FIELD = "email";
    public static final String USER_CREATED_TIMESTAMP_FIELD = "created_timestamp";
    public static final String USER_UPDATED_TIMESTAMP_FIELD ="updated_timestamp";
    public static final String USER_COUNT_FIELD = "COUNT";

}
