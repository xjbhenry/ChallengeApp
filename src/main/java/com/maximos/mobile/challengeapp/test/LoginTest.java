package com.maximos.mobile.challengeapp.test;

/**
 * Created by Martin Lo on 12/1/2014.
 */

import android.test.ActivityInstrumentationTestCase2;

import com.maximos.mobile.challengeapp.LoginActivity;
import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.profilepage.ProfileActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;
import junit.framework.Test;

import java.util.logging.Level;
import java.util.logging.Logger;


import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.maximos.mobile.challengeapp.profilepage.ProfileActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;
import junit.framework.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Henry on 11/24/2014.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;
    private static String TAG_NAME = Test.class.getName();
    private static Logger mLogger = Logger.getLogger(TAG_NAME);

    public LoginTest(){
        super(LoginActivity.class);
    }
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testUI() throws Exception {
        boolean expected = true;
        mLogger.log(Level.INFO, TAG_NAME + ": Solo starts");

        EditText text1 = (EditText)solo.getView(R.id.email);
        solo.enterText(text1, "x@x.com");
        //solo.clickOnEditText(2);
        EditText text2 = (EditText)solo.getView(R.id.password);
        solo.enterText(text2, "password");
        solo.clickOnButton("Sign in or register");

        solo.goBack();
        solo.enterText(text1, "x@x.com");
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
