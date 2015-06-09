package com.linuxclub.cdcfan.autoupdater;

/**
 * Created by peace_da on 2015/6/8.
 */

import android.content.Context;
import android.util.Log;

import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.utils.LogHelper;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateManager {

    private static final String LOG_TAG = LogHelper.getNativeSimpleLogTag(UpdateManager.class, LogHelper.DEFAULT_LOG_TAG);

    private static UpdateManager sInstance;

    private Context mContext;

    private RestAdapter mRestAdapter;

    private UpdateManager(Context ctx) {
        mContext = ctx.getApplicationContext();
        mRestAdapter = new RestAdapter.Builder().setEndpoint(mContext.getString(R.string.portal)).setLogLevel(RestAdapter.LogLevel.FULL).build();
    }

    public static UpdateManager getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (UpdateManager.class) {
                if (sInstance == null) {
                    sInstance = new UpdateManager(ctx);
                }
            }
        }
        return sInstance;
    }

    public void startCheckUpdate() {
        CheckUpdateTask task = mRestAdapter.create(CheckUpdateTask.class);
        task.check(new Callback<UpdateCheckResult>() {
            @Override
            public void success(UpdateCheckResult result, Response response) {
                Log.d(LOG_TAG, "check update task succ");
                result.setCheckSucc(true);
                EventBus.getDefault().post(result);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                UpdateCheckResult result = new UpdateCheckResult();
                result.setCheckSucc(false);
                EventBus.getDefault().post(result);
            }
        });
    }

    public void startUpdate() {

    }

    public void skipVersion(int versionCode) {

    }
}
