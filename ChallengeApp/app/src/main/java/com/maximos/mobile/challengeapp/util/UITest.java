package com.maximos.mobile.challengeapp.util;

import android.test.ActivityInstrumentationTestCase2;

import com.maximos.mobile.challengeapp.profilepage.ProfileActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;
import junit.framework.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Henry on 11/24/2014.
 */
public class UITest extends ActivityInstrumentationTestCase2<ProfileActivity> {
    private Solo solo;
    private static String TAG_NAME = Test.class.getName();
    private static Logger mLogger = Logger.getLogger(TAG_NAME);

public UITest (){
        super(ProfileActivity.class);
    }
public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        }
public void testPreferenceIsSaved() throws Exception {

        solo.sendKey(Solo.MENU);
        solo.clickOnText("More");
        solo.clickOnText("Preferences");
        solo.clickOnText("Edit File Extensions");
        Assert.assertTrue(solo.searchText("rtf"));
        solo.clickOnText("txt");
        solo.clearEditText(2);
        solo.enterText(2, "robotium");
        solo.clickOnButton("Save");
        solo.goBack();
        solo.clickOnText("Edit File Extensions");
        Assert.assertTrue(solo.searchText("application/robotium"));
        }
    public void testUI() throws Exception {
        boolean expected = true;
        mLogger.log(Level.INFO, TAG_NAME + ": Solo starts");
        solo.clickOnRadioButton(0);
        solo.clickOnEditText(0);
        solo.enterText(0, "180");
        solo.clickOnButton("计算");
        boolean actual1 = solo.searchText("70.00");
        assertEquals("This and/or is are not found", expected, actual1);

        //返回清空editText表单
        solo.goBack();
        solo.clearEditText(0);

        solo.clickOnRadioButton(1);
        solo.clickOnEditText(0);
        solo.enterText(0, "160");
        solo.clickOnButton("计算");
        boolean actual2 = solo.searchText("54.00");
        assertEquals("This and/or is are not found", expected, actual2);
    }

    @Override
public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        }
}
