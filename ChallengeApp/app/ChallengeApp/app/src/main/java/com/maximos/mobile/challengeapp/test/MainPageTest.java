package com.maximos.mobile.challengeapp.test;

/**
 * Created by Martin Lo on 12/1/2014.
 */

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.feedpageproject.MainActivity;
import com.robotium.solo.Solo;

import junit.framework.Test;

import java.util.logging.Logger;

/**
 * Created by Henry on 11/24/2014.
 */
public class MainPageTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;
    private static String TAG_NAME = Test.class.getName();
    private static Logger mLogger = Logger.getLogger(TAG_NAME);

    public MainPageTest(){
        super(MainActivity.class);
    }
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }
    public void testPreferenceIsSaved() throws Exception {

        solo.sendKey(Solo.MENU);
        solo.clickOnMenuItem("My Profile");
        solo.goBack();
        solo.scrollDown();
        solo.scrollDown();
        solo.scrollDown();
        solo.scrollDown();
        solo.scrollDown();
        solo.scrollToBottom();
        solo.scrollToTop();
        solo.clickOnMenuItem("Add Challenge");
        EditText text1 = (EditText)solo.getView(R.id.challenge_title);
        solo.enterText(text1, "Mirror Lake Jump");

        EditText text2 = (EditText)solo.getView(R.id.challenge_desc);
        solo.enterText(text2, "Jump to the Mirror Lake to Fxxx Xichigan");
    }
    public void testUI() throws Exception {
        boolean expected = true;

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
