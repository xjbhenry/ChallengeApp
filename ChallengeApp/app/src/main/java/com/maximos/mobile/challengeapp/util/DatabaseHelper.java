package com.maximos.mobile.challengeapp.util;

/**
 * Created by Henry on 10/4/2014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.maximos.mobile.challengeapp.data.Post;
import com.maximos.mobile.challengeapp.data.Profile;
import com.maximos.mobile.challengeapp.data.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper {
    private static final String DATABASE_NAME = "ChallengeMe.db";
    private static final int DATABASE_VERSION = 1;
    private static final String user = "user";
    private static final String post = "post";
    private static final String profile = "profile";
    private static final String visitedPlace = "visitedPlace";
    private static final String interest = "interest";
    private static final String affiliation = "affiliation";
    private static final String LOG = "DatabaseHelper";

    public static class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE" + user +
                    "userId INT(8) AUTO_INCREMENT," +
                    "name VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "address VARCHAR(255), " +
                    "password VARCHAR(255)" +
                    //"profile TEXT" +
                    "PRIMARY KEY(userId));");
            sqLiteDatabase.execSQL("CREATE_TABLE" + post +
                    "postId INT(8) AUTO_INCREMENT," +
                    "type INT(8) " +
                    "creatorId INT(8)" +
                    "challengeCaseId INY(8)" +
                    "timeStamp VARCHAR" +
                    "PRIMARY KEY(postId));");
            sqLiteDatabase.execSQL("CREATE_TABLE" + profile +
                    "userId INT(8)" +
                    "name VARCHAR(255)" +
                    "challengeIdCompleted TEXT," +
                    "challengeIdOngoing TEXT," +
                    "challengeIdCreated TEXT" +
                    "credits INT(6)" +
                    "friendsId TEXT" +
                    "affiliationId TEXT" +
                    "visitedPlaceId TEXT" +
                    "interestId TEXT" +
                    "description TEXT" +
                    "userId INT(8));" );
            sqLiteDatabase.execSQL("CREATE_TABLE" + visitedPlace +
                    "locationCoordinate TEXT," +
                    "timeStamp VARCHAR(255)," +
                    "accompaniedPeople TEXT" +
                    "description TEXT" +
                    "visitedPlaceId: INT(8)" +
                    "PRIMARY KEY(visitedPlaceId));");
            sqLiteDatabase.execSQL("CREATE_TABLE" + interest +
                    "name VARCHAR(255)," +
                    "interestId INT(8) AUTO_INCREMENT," +
                    "description TEXT," +
                    "PRIMARY KEY(interestId));");
            sqLiteDatabase.execSQL("CREATE_TABLE" + affiliation +
                    "name VARCHAR(255)," +
                    "affiliationId INT(8) AUTO_INCREMENT," +
                    "description TEXT," +
                    "PRIMARY KEY(affiliationId));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + user);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + post);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + profile);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + visitedPlace);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + interest);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + affiliation);
            onCreate(sqLiteDatabase);
        }


        public long insertUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put("name", user.getName());
            cv.put("email",user.getEmail());
            cv.put("address", user.getAddress());
            cv.put("password", user.getPassword());

            //insert
            long userId = db.insert("user", null, cv);

            return userId;
        }

        public long insertPost(Post post) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("type", post.getType());
            cv.put("creator",post.getCreatorId());
            cv.put("challengeId", post.getChallengeId());
            cv.put("timeStamp", getDateTime());

            //insert
            long postId = db.insert("post", null, cv);
            return postId;
        }

        public void insertProfile(Profile profile) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("userId", profile.getUserId());
            cv.put("name", profile.getName());
            cv.put("challengeIdCompleted", profile.getChallengeIdCompletedCat());
            cv.put("challengeIdOngoing",profile.getChallengeIdOngoingCat());
            cv.put("challengeIdCreated", profile.getChallengeIdCreatedCat());
            cv.put("credits", profile.getCredits());
            cv.put("friendsId", profile.getFriendsIdCat());
            cv.put("visitedPlaceId", profile.getVisitedPlaceIdCat());
            cv.put("description", profile.getDescription());

            // return userId;
        }

        //loc, profile remains
        public List getAllUserInfo(int userIdSel) {
            List<User> user = new ArrayList<User>();
            String query = "SELECT * FROM" + "user" + "WHERE user='" + userIdSel + "'" ;

            Log.e(LOG, query);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            //get info from all selected rows
            if (cursor.moveToFirst()) {
                do {
                    User userSelected = new User();
                    userSelected.setUserId(cursor.getInt((cursor.getColumnIndex("userId"))));
                    userSelected.setName((cursor.getString(cursor.getColumnIndex("name"))));
                    userSelected.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    userSelected.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                    userSelected.setPassword(cursor.getString(cursor.getColumnIndex("password")));

                    // adding to todo list
                    user.add(userSelected);
                } while (cursor.moveToNext());
            }
            //String userJson = new Gson().toJson(user);
            ///return userJson;
            return user;
        }

        public List<Post> getAllPostInfo(int postIdSel) {
            List<Post> post = new ArrayList<Post>();
            String query = "SELECT * FROM" + "post" + "WHERE post='" + postIdSel +"'" ;

            Log.e(LOG, query);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            //get info from all selected rows
            if (cursor.moveToFirst()) {
                do {
                    Post postSelected = new Post();
                    postSelected.setPostId(cursor.getInt(cursor.getColumnIndex("postId")));
                    postSelected.setType(cursor.getInt(cursor.getColumnIndex("type")));
                    postSelected.setCreatorId(cursor.getInt(cursor.getColumnIndex("creatorId")));
                    postSelected.setChallengeId(cursor.getInt(cursor.getColumnIndex("challengeId")));
                    postSelected.setTimeStamp(cursor.getString(cursor.getColumnIndex("timStamp")));

                    // adding to todo list
                    post.add(postSelected);
                } while (cursor.moveToNext());
            }
            return post;
        }

        public List<Profile> getAllProfileInfo(int usrIdSel) {
            List<Profile> profile = new ArrayList<Profile>();
            String query = "SELECT * FROM" + "profile" + "WHERE profile='" + usrIdSel +"'";

            Log.e(LOG, query);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            //get info from all selected rows
            if (cursor.moveToFirst()) {
                do {
                    Profile profileSelected = new Profile();
                    profileSelected.setUserId(cursor.getInt(cursor.getColumnIndex("postId")));
                    profileSelected.setName(cursor.getString(cursor.getColumnIndex("name")));
                    profileSelected.setChallengeIdCompletedCat(cursor.getString(cursor.getColumnIndex("challengeIdCompleted")));
                    profileSelected.setChallengeIdOngoingCat(cursor.getString(cursor.getColumnIndex("challengeIdOngoing")));
                    profileSelected.setChallengeIdCreatedCat(cursor.getString(cursor.getColumnIndex("challengeIdCreated")));
                    profileSelected.setCredits(cursor.getInt(cursor.getColumnIndex("credits")));
                    profileSelected.setFriendsIdCat(cursor.getString(cursor.getColumnIndex("friendsId")));
                   // profileSelected.setAffiliationIdCat(cursor.getString(cursor.getColumnIndex("affiliationId")));
                    profileSelected.setVisitedPlaceIdCat(cursor.getString(cursor.getColumnIndex("visitedPlaceId")));
                    profileSelected.setInterestIdCat(cursor.getString(cursor.getColumnIndex("interestId")));
                    profileSelected.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    // adding to todo list
                    profile.add(profileSelected);
                } while (cursor.moveToNext());
            }
            return profile;
        }


        public void closeDB() {
            SQLiteDatabase db = this.getReadableDatabase();
            if (db != null && db.isOpen())
                db.close();
        }

        private String getDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }

    }
}
