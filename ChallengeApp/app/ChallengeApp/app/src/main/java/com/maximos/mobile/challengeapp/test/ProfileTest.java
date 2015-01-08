package com.maximos.mobile.challengeapp.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.profilepage.ProfileActivity;
import com.robotium.solo.Solo;

import junit.framework.Test;

import java.util.logging.Logger;

/**
 * Created by Henry on 11/24/2014.
 */
public class ProfileTest extends ActivityInstrumentationTestCase2<ProfileActivity> {
    private Solo solo;
    private static String TAG_NAME = Test.class.getName();
    private static Logger mLogger = Logger.getLogger(TAG_NAME);

public ProfileTest(){
        super(ProfileActivity.class);
    }
public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        }
public void testPreferenceIsSaved() throws Exception {
    solo.sendKey(Solo.MENU);
    solo.goBack();
    solo.waitForActivity(ProfileActivity.class);
    solo.waitForView(R.id.user_avatar);
    solo.clickOnButton("Update profile");

    EditText text1 = (EditText)solo.getView(R.id.profile_mod_name);
    solo.waitForView(R.id.profile_mod_name);
    solo.waitForView(R.id.profile_mod_email);
    solo.waitForView(R.id.profile_mod_address);
    solo.clearEditText(text1);
    solo.enterText(text1, "Martin");
    EditText text2 = (EditText)solo.getView(R.id.profile_mod_email);
    solo.clearEditText(text2);
    solo.enterText(text2, "kuanwenlo@gmail.com");

    EditText text3 = (EditText)solo.getView(R.id.profile_mod_address);
    solo.clearEditText(text3);
    solo.enterText(text3, "Stadium Dr");

    solo.clickOnButton("OK");
    solo.goBack();
        }
    public void testUI() throws Exception {

    }

    @Override
public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        }
}
