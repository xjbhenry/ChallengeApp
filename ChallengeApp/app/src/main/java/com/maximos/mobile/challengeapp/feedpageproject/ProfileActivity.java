package com.maximos.mobile.challengeapp.feedpageproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.adapter.FeedListAdapter;
import com.maximos.mobile.challengeapp.data.FeedItem;
import com.maximos.mobile.challengeapp.data.Profile;
import com.maximos.mobile.challengeapp.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private List<Profile> mProfileSelected;
    private int userId;
    //private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        listView = (ListView) findViewById(R.id.list);
        //databaseHelper = new DatabaseHelper();

        mProfileSelected = new ArrayList<Profile>();
        DatabaseHelper.DatabaseOpenHelper dataBaseOpenHelper = new DatabaseHelper.DatabaseOpenHelper(this);
        mProfileSelected = dataBaseOpenHelper.getAllUserInfo(userId);


        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        for(int i = 0; i < mProfileSelected.size(); i++) {
            Profile profile = new Profile();
            profile.setName(mProfileSelected.get(i).getName());
            profile.setUserId(mProfileSelected.get(i).getUserId());
            profile.setAffiliationId(mProfileSelected.get(i).getAffiliationId());
        }


        // Image might be null sometimes
/*        String image = feedObj.isNull("image") ? null : feedObj
                .getString("image");
        item.setImge(image);
        item.setStatus(feedObj.getString("status"));
        item.setProfilePic(feedObj.getString("profilePic"));
        item.setTimeStamp(feedObj.getString("timeStamp"));

        // url might be null sometimes
        String feedUrl = feedObj.isNull("url") ? null : feedObj
                .getString("url");
        item.setUrl(feedUrl);

        feedItems.add(item);*/
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


}
