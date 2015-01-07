package com.maximos.mobile.challengeapp.test;

/**
 * Created by Martin Lo on 12/1/2014.
 */

import android.test.ActivityInstrumentationTestCase2;
import android.widget.RadioButton;

import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.testActivity;
import com.robotium.solo.Solo;

import junit.framework.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Henry on 11/24/2014.
 */
public class TestTest extends ActivityInstrumentationTestCase2<testActivity> {
    private Solo solo;
    private static String TAG_NAME = Test.class.getName();
    private static Logger mLogger = Logger.getLogger(TAG_NAME);

    public TestTest(){
        super(testActivity.class);
    }
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testUI() throws Exception {
        boolean expected = true;
        mLogger.log(Level.INFO, TAG_NAME + ": Solo starts");


        RadioButton radio1=(RadioButton)solo.getView(R.id.radio1);
        solo.clickOnRadioButton(1);
        //RadioButton radio2=(RadioButton)solo.getView(R.id.radio2);
        solo.clickOnRadioButton(2);
        solo.clickOnRadioButton(0);
        solo.clickOnRadioButton(3);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}

