package com.linuxclub.cdcfan.autoupdater;

/**
 * Created by peace_da on 2015/6/8.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.persist.GlobalSharedPreferences;
import com.linuxclub.cdcfan.service.BackgroundNetworkService;
import com.linuxclub.cdcfan.service.ServiceConst;
import com.linuxclub.cdcfan.utils.LogHelper;

import java.io.File;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateManager {

    private static final String LOG_TAG = LogHelper.getNativeSimpleLogTag(UpdateManager.class, LogHelper.DEFAULT_LOG_TAG);
    private static UpdateManager sInstance;

    private Context mContext;
    private GlobalSharedPreferences mGlobalSharedPref;
    private RestAdapter mRestAdapter;
    private String mApkFilePath;
    private boolean mIsCanceled;


    private UpdateManager(Context ctx) {
        mContext = ctx.getApplicationContext();
        mRestAdapter = new RestAdapter.Builder().setEndpoint(mContext.getString(R.string.portal)).setLogLevel(RestAdapter.LogLevel.FULL).build();
        mGlobalSharedPref = GlobalSharedPreferences.getInstance(mContext.getApplicationContext());
        mIsCanceled = false;
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

    public void startUpdate(@NonNull UpdateListener listener, String apkUrl) {
        mApkFilePath = new File(mContext.getExternalCacheDir().getAbsolutePath(), "cdcfan" + System.currentTimeMillis()).getAbsolutePath() + ".apk";
        Log.d(LOG_TAG, "apk file path: " + mApkFilePath);

        Intent intent = new Intent(ServiceConst.KEY_DOWNLOAD_FILE);
        intent.setClass(mContext, BackgroundNetworkService.class);
        intent.putExtra(ServiceConst.KEY_DOWNLOAD_FILE_UPDATE_RECEIVER, new UpdateReceiver(mContext, new Handler(), listener));
        intent.putExtra(ServiceConst.KEY_DOWNLOAD_FILE_PATH, mApkFilePath);
        intent.putExtra(ServiceConst.KEY_DOWNLOAD_FILE_APK_URL, apkUrl);
        mContext.startService(intent);
    }

    public synchronized void stopUpdate() {
        mIsCanceled = true;
    }

    public synchronized boolean isUpdateCanceled() {
        return mIsCanceled;
    }

    public void skipVersion(int versionCode) {
        mGlobalSharedPref.addSkipVersion("" + versionCode);
    }

    public void install() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + mApkFilePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

}
