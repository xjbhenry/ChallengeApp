/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maximos.mobile.challengeapp.FetchFriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.maximos.mobile.challengeapp.R;
import com.maximos.mobile.challengeapp.app.AppController;
import com.maximos.mobile.challengeapp.data.FriendInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FriendPickerSampleActivity extends FragmentActivity {

    /*public class FriendInfo{

        private String name;
        private String id;
        private String email;
        private String user_about_me;
        private String imgUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_about_me() {
            return user_about_me;
        }

        public void setUser_about_me(String user_about_me) {
            this.user_about_me = user_about_me;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }*/

    private static List<String> PERMISSIONS = new ArrayList<String>() {
        {
            add("user_friends");
            add("public_profile");
            add("email");
            add("user_likes");
            add("user_about_me");

            add("user_birthday");
        }
    };
    private static final int PICK_FRIENDS_ACTIVITY = 1;
    private Button pickFriendsButton;
    private TextView resultsTextView;
    private UiLifecycleHelper lifecycleHelper;
    boolean pickFriendsWhenSessionOpened;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        pickFriendsButton = (Button) findViewById(R.id.pickFriendsButton);
        pickFriendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPickFriends();
            }
        });

        lifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChanged(session, state, exception);
            }
        });
        lifecycleHelper.onCreate(savedInstanceState);

        ensureOpenSession();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Update the display every time we are started.
        displaySelectedFriends(RESULT_OK);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be launched into.
        AppEventsLogger.deactivateApp(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FRIENDS_ACTIVITY:
                displaySelectedFriends(resultCode);
                break;
            default:
                Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
                break;
        }
    }

    private boolean ensureOpenSession() {
        if (Session.getActiveSession() == null ||
                !Session.getActiveSession().isOpened()) {
            Session.openActiveSession(
                    this, 
                    true, 
                    PERMISSIONS,
                    new Session.StatusCallback() {
                        @Override
                        public void call(Session session, SessionState state, Exception exception) {
                            onSessionStateChanged(session, state, exception);
                        }
                    });
            return false;
        }
        return true;
    }
    
    private boolean sessionHasNecessaryPerms(Session session) {
        if (session != null && session.getPermissions() != null) {
            for (String requestedPerm : PERMISSIONS) {
                if (!session.getPermissions().contains(requestedPerm)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    private List<String> getMissingPermissions(Session session) {
        List<String> missingPerms = new ArrayList<String>(PERMISSIONS);
        if (session != null && session.getPermissions() != null) {
            for (String requestedPerm : PERMISSIONS) {
                if (session.getPermissions().contains(requestedPerm)) {
                    missingPerms.remove(requestedPerm);
                }
            }
        }
        return missingPerms;
    }

    private void onSessionStateChanged(final Session session, SessionState state, Exception exception) {
        if (state.isOpened() && !sessionHasNecessaryPerms(session)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.need_perms_alert_text);
            builder.setPositiveButton(
                    R.string.need_perms_alert_button_ok, 
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            session.requestNewReadPermissions(
                                    new NewPermissionsRequest(
                                            FriendPickerSampleActivity.this, 
                                            getMissingPermissions(session)));
                        }
                    });
            builder.setNegativeButton(
                    R.string.need_perms_alert_button_quit,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.show();
        } else if (pickFriendsWhenSessionOpened && state.isOpened()) {
            pickFriendsWhenSessionOpened = false;

            startPickFriendsActivity();
        }
    }

    private void displaySelectedFriends(int resultCode) {
        String results = "";
        AppController application = (AppController) getApplication();
        ArrayList<FriendInfo> friendList = new ArrayList();
        Collection<GraphUser> selection = application.getSelectedUsers();
        if (selection != null && selection.size() > 0) {
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<FriendInfo> users = new ArrayList<FriendInfo>();
            for (GraphUser user : selection) {
                names.add(user.getName());
                FriendInfo friend = new FriendInfo();
                friend.setName(user.getName());
                friend.setId(user.getId());
                String stringURL = null;
                try {
                    stringURL = "http://graph.facebook.com/"+ URLEncoder.encode(user.getId(), "UTF-8")+"/"+ URLEncoder.encode("picture", "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                Log.d("Url", "getProfilePicture final url is : " + stringURL);
                friend.setImgUrl(stringURL);
                Log.d("name", user.getName());
                Log.d("id", user.getId());
                Log.d("Url", "getProfilePicture final url is : " + stringURL);
                //Log.d("email", (String) user.getProperty("email"));
                Log.d("email", String.valueOf(user.getProperty("email")));

                Log.d("user_about_me", String.valueOf(user.getProperty("user_about_me")));
                Log.d("user_birthday", String.valueOf(user.getProperty("user_birthday")));

                friendList.add(friend);

            }
            results = TextUtils.join(", ", names);
        } else {
            results = "<No friends selected>";
        }

        resultsTextView.setText(results);
    }

    private void onClickPickFriends() {
        startPickFriendsActivity();
    }

    private void startPickFriendsActivity() {
        if (ensureOpenSession()) {
            Intent intent = new Intent(this, PickFriendsActivity.class);
            // Note: The following line is optional, as multi-select behavior is the default for
            // FriendPickerFragment. It is here to demonstrate how parameters could be passed to the
            // friend picker if single-select functionality was desired, or if a different user ID was
            // desired (for instance, to see friends of a friend).
            PickFriendsActivity.populateParameters(intent, null, true, true);
            startActivityForResult(intent, PICK_FRIENDS_ACTIVITY);
        } else {
            pickFriendsWhenSessionOpened = true;
        }
    }
}
