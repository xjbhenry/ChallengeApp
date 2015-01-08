package com.maximos.mobile.challengeapp.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * Created by Henry on 11/30/2014.
 */
public class ConnectivityTest {
    Context mContext;
    public ConnectivityTest (Context mContext) {
        this.mContext = mContext;
    }
    public boolean isNetConnected() {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo ni : infos) {
                    if (ni.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    public boolean is3gConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    public boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        List<String> accessibleProviders = lm.getProviders(true);
        for (String name : accessibleProviders) {
            if ("gps".equals(name)) {
                return true;
            }
        }
        return false;
    }
}
