package com.maximos.mobile.challengeapp.feedpageproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.maximos.mobile.challengeapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter adapter;
    private static final String TAG_ID = "id";
    private static final String TAG_CHALLENGE = "challenge";
    //private FeedListAdapter listAdapter;
    //private List<FeedItem> feedItems;
    //private List<Profile> mProfileSelected;
    //private int userId;
    //private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        listView = (ListView) findViewById(R.id.list);
        final String[] mValues = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        final String[] mValuesSub = new String[] {"1", "2", "3", "4", "5", "6", "7", "8"};

        for(int i=0; i<mValues.length; i++){
            HashMap<String,String> item = new HashMap<String,String>();
            item.put( TAG_CHALLENGE, mValues[i]);
            item.put( TAG_ID, mValuesSub[i] );
            list.add( item );
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        adapter = new SimpleAdapter(
                this,
                list,
                android.R.layout.simple_list_item_2,
                new String[] { TAG_CHALLENGE,TAG_ID },
                new int[] { android.R.id.text1, android.R.id.text2 } );

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);
         //ListAdapter adapter_sub = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2, new String[] {"Hello", "1"}, new int[] {android.R.id.text1, android.R.id.text2});

         listView.setAdapter(adapter);


        // Assign adapter to ListView
        //listView.setAdapter(adapter);


        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                //String  itemValue    = (String) listView.getItemAtPosition(position);
                HashMap<String,String> map = (HashMap<String,String>)listView.getItemAtPosition(position);
                String itemValue = map.get(TAG_ID);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ID : " + itemValue, Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(ProfileActivity.this,ProfileSubActivity.class);
                startActivity(intent);
            }
        });
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
