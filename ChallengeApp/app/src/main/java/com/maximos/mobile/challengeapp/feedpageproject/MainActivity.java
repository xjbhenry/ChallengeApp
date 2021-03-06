package com.maximos.mobile.challengeapp.feedpageproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.adapter.FeedListAdapter;
import com.maximos.mobile.challengeapp.app.AppController;
import com.maximos.mobile.challengeapp.constants.App_Constants;
import com.maximos.mobile.challengeapp.data.FeedItem;
import com.maximos.mobile.challengeapp.profilepage.ProfileActivity;
import com.maximos.mobile.challengeapp.util.ConnectivityTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://codexu.com/ChallengeMe/feed.json";

    public Logger logger = Logger.getLogger(MainActivity.class.getName());

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        /*getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));*/
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }

    @Override
    protected void onPause() {
        logger.log(Level.INFO, "Inside onPause of Main Activity");
        super.onPause();
    }

    @Override
    protected void onStop() {
        logger.log(Level.INFO, "Inside OnStop in Main Activity ");
        super.onStop();
    }

    @Override
    protected void onStart() {
        logger.log(Level.INFO, "Inside Onstart in Main activity" );
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        logger.log(Level.INFO, "Inside OnDestroy in Main activity" );
        super.onDestroy();
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_challenge) {
            Intent intent = new Intent(this, CreateChallengeActivity.class);
            startActivity(intent);
        }
        if (new ConnectivityTest(this).isNetConnected()) {
            if (item.getItemId() == R.id.profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            }
            if (item.getItemId() == R.id.to_do_challenges) {
                Intent intent = new Intent(this, ToDoChallengesActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, App_Constants.NETWORK_FAILURE,Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
}