package com.maximos.mobile.challengeapp.feedpageproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.constants.App_Constants;
import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.dao.ProfileDao;
import com.maximos.mobile.challengeapp.dao.ProfileSubDao;
import com.maximos.mobile.challengeapp.data.Challenge;
import com.maximos.mobile.challengeapp.data.Profile;
import com.maximos.mobile.challengeapp.data.UserActivity;
import com.maximos.mobile.challengeapp.profilepage.ProfileSubActivity;
import com.maximos.mobile.challengeapp.util.ConnectivityTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToDoChallengesActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_CHALLENGE_ID = "challengeId";
    private ListView mListView;
    private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private SimpleAdapter adapter;
    private static final String TAG_CHALLENGE_CREATED_TIMESTAMP = "time";
    private static final String TAG_CHALLENGE_TITLE = "challenge";
    private String TAG_NAME = ProfileDao.class.getName();
    private Logger logger = Logger.getLogger(TAG_NAME);
    private ProfileTask mProfileTask;
    private Profile profile;
    private List<UserActivity> mUserActivityList;
    private Challenge mChallenge;
    //private FeedListAdapter listAdapter;
    //private List<FeedItem> feedItems;
    //private List<Profile> mProfileSelected;
    //private int userId;
    //private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_challenges);
        mProfileTask = new ProfileTask(DB_Constants.TAG_USERID);
        mProfileTask.execute((Void)null);

        logger.log(Level.INFO, TAG_NAME + ": inside onCreate of profile");
        // ListView Item Click Listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                //int itemPosition = position;

                // ListView Clicked item value
                //String  itemValue    = (String) mListView.getItemAtPosition(position);
                HashMap<String, String> map = (HashMap<String, String>) mListView.getItemAtPosition(position);
                int mId = Integer.parseInt(map.get(TAG_CHALLENGE_ID));

                // Show Alert
                /*Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ID : " + itemValue, Toast.LENGTH_LONG)
                        .show();*/
                if (new ConnectivityTest(ToDoChallengesActivity.this).isNetConnected()) {
                    Intent intent = new Intent(ToDoChallengesActivity.this, ProfileSubActivity.class);
                    intent.putExtra(TAG_CHALLENGE_ID, mId);
                    Toast.makeText(getApplicationContext(), "ID" + mId, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(ToDoChallengesActivity.this, App_Constants.NETWORK_FAILURE,Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ProfileTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserId;

        ProfileTask(String userId) {
            mUserId = userId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            profile = new ProfileDao().getProfile(mUserId);
            mUserActivityList = new ProfileDao().getUserActivityToDo(mUserId);

            for (int i = 0; i < mUserActivityList.size(); i++) {
                HashMap<String, String> item = new HashMap<String, String>();
                int id = mUserActivityList.get(i).getChallengeId();
                mChallenge = new ProfileSubDao().getChallengeToDo(id);
                item.put(TAG_CHALLENGE_TITLE, mChallenge.getChallengeTitle());
                item.put(TAG_CHALLENGE_CREATED_TIMESTAMP, mChallenge.getChallengeCreatedTimeStamp().toString());
                item.put(TAG_CHALLENGE_ID,Integer.toString(mChallenge.getChallengeId()));
                list.add(item);
            }

            adapter = new SimpleAdapter(
                    ToDoChallengesActivity.this,
                    list,
                    android.R.layout.simple_list_item_2,
                    new String[]{TAG_CHALLENGE_TITLE, TAG_CHALLENGE_CREATED_TIMESTAMP},
                    new int[]{android.R.id.text1, android.R.id.text2});

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListView = (ListView) findViewById(R.id.todo_list);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListView.setAdapter(adapter);
        }
    }
}
