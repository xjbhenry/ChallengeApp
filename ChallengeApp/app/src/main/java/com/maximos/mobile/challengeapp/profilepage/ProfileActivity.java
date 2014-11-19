package com.maximos.mobile.challengeapp.profilepage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.dao.ProfileDao;
import com.maximos.mobile.challengeapp.dao.ProfileSubDao;
import com.maximos.mobile.challengeapp.data.Challenge;
import com.maximos.mobile.challengeapp.data.Profile;
import com.maximos.mobile.challengeapp.data.UserActivity;
import com.maximos.mobile.challengeapp.feedpageproject.MainActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileActivity extends Activity {
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
    private TextView mName;
    private ImageView mAvatar;
    private List<UserActivity> mUserActivityList;
    private Challenge mChallenge;
    private Button mButtonUpdateProfile;
    //private FeedListAdapter listAdapter;
    //private List<FeedItem> feedItems;
    //private List<Profile> mProfileSelected;
    //private int userId;
    //private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mButtonUpdateProfile = (Button)findViewById(R.id.profile_mod);
        mProfileTask = new ProfileTask(DB_Constants.TAG_USERID);
        mProfileTask.execute((Void)null);

        logger.log(Level.INFO, TAG_NAME + ": inside onCreate of profile");
        //listView = (ListView) findViewById(R.id.list);

        /*final String[] mValues = new String[]{"Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        final String[] mValuesSub = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};

        for (int i = 0; i < mValues.length; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put(TAG_CHALLENGE, mValues[i]);
            item.put(TAG_ID, mValuesSub[i]);
            list.add(item);
        }
*/
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
       /* adapter = new SimpleAdapter(
                this,
                list,
                android.R.layout.simple_list_item_2,
                new String[]{TAG_CHALLENGE, TAG_ID},
                new int[]{android.R.id.text1, android.R.id.text2});*/

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);
        //ListAdapter adapter_sub = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2, new String[] {"Hello", "1"}, new int[] {android.R.id.text1, android.R.id.text2});

        //mListView.setAdapter(adapter);


        // Assign adapter to ListView
        //listView.setAdapter(adapter);


        // ListView Item Click Listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                //String  itemValue    = (String) mListView.getItemAtPosition(position);
                HashMap<String, String> map = (HashMap<String, String>) mListView.getItemAtPosition(position);
                int mId = Integer.parseInt(map.get(TAG_CHALLENGE_ID));

                // Show Alert
                /*Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ID : " + itemValue, Toast.LENGTH_LONG)
                        .show();*/
                Intent intent = new Intent(ProfileActivity.this, ProfileSubActivity.class);
                intent.putExtra(TAG_CHALLENGE_ID, mId);
                Toast.makeText(getApplicationContext(), "ID" + mId, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        mButtonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileModificationActivity.class);
                intent.putExtra(DB_Constants.TAG_USERID, DB_Constants.TAG_USERID);
                //Toast.makeText(getApplicationContext(), "ID" + mId, Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
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
        Bitmap bm;

        ProfileTask(String userId) {
            mUserId = userId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            profile = new ProfileDao().getProfile(mUserId);
            mUserActivityList = new ProfileDao().getUserActivity(mUserId);

            try {
                InputStream in = new java.net.URL(profile.getUserAvatar()).openStream();
                logger.log(Level.INFO, TAG_NAME + ": Trying");
                bm = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            for (int i = 0; i < mUserActivityList.size(); i++) {
                HashMap<String, String> item = new HashMap<String, String>();
                int id = mUserActivityList.get(i).getChallengeId();
                mChallenge = new ProfileSubDao().getChallenge(id);
                item.put(TAG_CHALLENGE_TITLE, mChallenge.getChallengeTitle());
                item.put(TAG_CHALLENGE_CREATED_TIMESTAMP, mChallenge.getChallengeCreatedTimeStamp().toString());
                item.put(TAG_CHALLENGE_ID,Integer.toString(mChallenge.getChallengeId()));
                list.add(item);
            }

            adapter = new SimpleAdapter(
                    ProfileActivity.this,
                    list,
                    android.R.layout.simple_list_item_2,
                    new String[]{TAG_CHALLENGE_TITLE, TAG_CHALLENGE_CREATED_TIMESTAMP},
                    new int[]{android.R.id.text1, android.R.id.text2});

            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mName = (TextView) findViewById(R.id.user_name);
            mAvatar = (ImageView)findViewById(R.id.user_avatar);
            mListView = (ListView) findViewById(R.id.list);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mName.setText(profile.getName());
            mAvatar.setImageBitmap(bm);
            mListView.setAdapter(adapter);

/*            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(profile.getUserAvatar()).openStream();
                logger.log(Level.INFO, TAG_NAME + ": Trying");
                mIcon11 = BitmapFactory.decodeStream(in);
                mAvatar.setImageBitmap(mIcon11);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }*/
        }
    }
}
