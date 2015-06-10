package com.linuxclub.cdcfan.persist;

/**
 * Created by peace_da on 2015/6/9.
 */
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;


public class GlobalSharedPreferences {

    private static final String KEY = GlobalSharedPreferences.class.getSimpleName();

    private static GlobalSharedPreferences sInstance;

    private SharedPreferences mSharedPref;

    private Context mContext;

    private GlobalSharedPreferences(Context ctx) {
        mContext = ctx.getApplicationContext();
        mSharedPref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public static GlobalSharedPreferences getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized(GlobalSharedPreferences.class) {
                if (sInstance == null) {
                    sInstance = new GlobalSharedPreferences(ctx);
                }
            }
        }
        return sInstance;
    }

    public synchronized void addSkipVersion(String versionCode) {
        Set<String> set = getSkipVersions();
        set.add(versionCode);
        mSharedPref.edit().putStringSet(PersistConst.KEY_SKIP_VERSION_SET, set).commit();
    }

    public synchronized Set<String> getSkipVersions() {
        return mSharedPref.getStringSet(PersistConst.KEY_SKIP_VERSION_SET, new HashSet<String>());
    }

}
