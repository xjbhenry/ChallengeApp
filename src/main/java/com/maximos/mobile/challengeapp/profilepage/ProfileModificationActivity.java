package com.maximos.mobile.challengeapp.profilepage;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.constants.App_Constants;
import com.maximos.mobile.challengeapp.constants.DB_Constants;
import com.maximos.mobile.challengeapp.dao.UserDao;
import com.maximos.mobile.challengeapp.model.User;
import com.maximos.mobile.challengeapp.util.ConnectivityTest;

/**
 * Created by Henry on 11/17/2014.
 */
public class ProfileModificationActivity extends Activity {
    private EditText mName, mEmail, mAddress;
    private User user;
    private ProfileModificationTask mProfileModificationTask;
    private ProfileModSubmitTask mProfileModSubmitTask;
    private String userId;
    private Button mButtonOk;
    private User mUserMod;
    private static final String TAG_NAME = "mNane";
    private static final String TAG_EMAIL = "mEmail";
    private static final String TAG_ADDRESS = "mAddress";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_modification);

        mName = (EditText)findViewById(R.id.profile_mod_name);
        mEmail = (EditText)findViewById(R.id.profile_mod_email);
        mAddress = (EditText)findViewById(R.id.profile_mod_address);

        if (savedInstanceState != null) {
            String mNameText = savedInstanceState.getString(TAG_NAME);
            String mEmailText = savedInstanceState.getString(TAG_EMAIL);
            String mAddressText = savedInstanceState.getString(TAG_ADDRESS);
            mName.setText(mNameText);
            mEmail.setText(mEmailText);
            mAddress.setText(mAddressText);
        } else if (new ConnectivityTest(this).isNetConnected()) {
            userId = getIntent().getExtras().getString(DB_Constants.TAG_USERID);
            mProfileModificationTask = new ProfileModificationTask(userId);
            mProfileModificationTask.execute((Void) null);
        } else {
            Toast.makeText(this,App_Constants.NETWORK_FAILURE,Toast.LENGTH_SHORT).show();
        }

        mButtonOk = (Button)findViewById(R.id.profile_mod_ok);

            mButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUserMod = new User();
                    mUserMod.setName(mName.getText().toString());
                    mUserMod.setEmail(mEmail.getText().toString());
                    mUserMod.setAddress(mAddress.getText().toString());
                    mUserMod.setUserId(DB_Constants.TAG_USERID);
                    if (new ConnectivityTest(ProfileModificationActivity.this).isNetConnected()) {
                        mProfileModSubmitTask = new ProfileModSubmitTask(mUserMod);
                        mProfileModSubmitTask.execute((Void) null);
                        Intent intent = new Intent(ProfileModificationActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ProfileModificationActivity.this, App_Constants.NETWORK_FAILURE, Toast.LENGTH_SHORT).show();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(ProfileModificationActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG_NAME, mName.getText().toString());
        outState.putString(TAG_EMAIL, mEmail.getText().toString());
        outState.putString(TAG_ADDRESS, mAddress.getText().toString());
    }

    public class ProfileModificationTask extends AsyncTask <Void, Void, Boolean> {
        private final String userId;

        ProfileModificationTask (String userId) {
            this.userId = userId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            user = new UserDao().getUser(userId);
            return true;
        }

/*        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mName = (EditText)findViewById(R.id.profile_mod_name);
            mEmail = (EditText)findViewById(R.id.profile_mod_email);
            mAddress = (EditText)findViewById(R.id.profile_mod_address);
        }*/

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
                mName.setText(user.getName());
                mEmail.setText(user.getEmail());
                mAddress.setText(user.getAddress());
            }
    }

    public class ProfileModSubmitTask extends AsyncTask <Void, Void, Boolean> {
        private final User mUserMod;
        private  Boolean result;

        ProfileModSubmitTask (User mUserMod) {
            this.mUserMod = mUserMod;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            result = new UserDao().updateUser(mUserMod);
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (result) {
                Toast.makeText(ProfileModificationActivity.this, App_Constants.UPDATED, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileModificationActivity.this, App_Constants.UPDATE_FAILED, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
