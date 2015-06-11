package com.linuxclub.cdcfan.persist;

/**
 * Created by peace_da on 2015/6/9.
 */
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class GlobalSharedPreferences {

    public static final String KEY = GlobalSharedPreferences.class.getSimpleName();

    @Inject
    SharedPreferences mSharedPref;

    @Inject
    Application mApp;

    @Inject
    GlobalSharedPreferences() {
    }

    public synchronized void addSkipVersion(String versionCode) {
        Set<String> set = getSkipVersions();
        set.add(versionCode);
        mSharedPref.edit().putStringSet(PersistConst.KEY_SKIP_VERSION_SET, set).commit();
    }

    public synchronized Set<String> getSkipVersions() {
        return mSharedPref.getStringSet(PersistConst.KEY_SKIP_VERSION_SET, new HashSet<String>());
    }

    public synchronized String getLastUserName() {
        return mSharedPref.getString(PersistConst.KEY_LAST_USER_NAME, "");
    }

    public synchronized void setKeyLastUserName(String userName) {
        mSharedPref.edit().putString(PersistConst.KEY_LAST_USER_NAME, userName).commit();
    }

}
