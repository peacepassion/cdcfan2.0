package com.linuxclub.cdcfan.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.baidu.mobstat.StatService;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.autoupdater.UpdateCheckResult;
import com.linuxclub.cdcfan.autoupdater.UpdateListener;
import com.linuxclub.cdcfan.autoupdater.UpdateManager;

import de.greenrobot.event.EventBus;

/**
 * Created by peace_da on 2015/5/8.
 */
public class StartActivity extends BaseActivity implements UpdateListener {

    private UpdateManager mUpdateMgr;

    @Override
    protected int getLayout() {
        return R.layout.start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mUpdateMgr = UpdateManager.getInstance(this.getApplicationContext());
        StatService.onEvent(this, mRes.getString(R.string.event_enter_start_acti), mRes.getString(R.string.event_enter_start_acti));

        findViewById(R.id.logo).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAppAvailability();
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void checkAppAvailability() {
        mUpdateMgr.startCheckUpdate();
    }

    public void onEventMainThread(UpdateCheckResult updateCheckResult) {
        Log.d(LOG_TAG, "check update returns");
        Log.d(LOG_TAG, "result: " + updateCheckResult);
        if (updateCheckResult.isCheckSucc()) {
            Log.d(LOG_TAG, "check update succ");
            if (updateCheckResult.isForceUpdate()) {
                showForceUpdateDialog(updateCheckResult);
            } else {
                try {
                    int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                    Log.d(LOG_TAG, "self version code: " + versionCode + ", get version: " + updateCheckResult.getVersionCode());
                    if (mGlobalSharedPref.getSkipVersions().contains("" + versionCode) == false
                            && versionCode < updateCheckResult.getVersionCode()) {
                        showUpdateDialog(updateCheckResult);
                    } else {
                        goToLoginPage();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    goToLoginPage();
                }
            }
        } else {
            Log.w(LOG_TAG, "check update fail");
            showRealToast(mRes.getString(R.string.conn_fail));
            finish();
        }
    }

    public void showForceUpdateDialog(final UpdateCheckResult updateCheckResult) {
        new MaterialDialog.Builder(StartActivity.this)
                .title(mRes.getString(R.string.update_title))
                .content(updateCheckResult.getUpdateTips())
                .positiveText(mRes.getString(R.string.update_ok))
                .negativeText(mRes.getString(R.string.update_no))
                .callback(new ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Log.d(LOG_TAG, "start upgrade");
                        mUpdateMgr.startUpdate(StartActivity.this, mConst.getFullDownloadUrl(updateCheckResult.getApkUrl()));
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }

    public void showUpdateDialog(final UpdateCheckResult updateCheckResult) {
        new MaterialDialog.Builder(StartActivity.this)
                .title(mRes.getString(R.string.update_title2))
                .content(updateCheckResult.getUpdateTips())
                .positiveText(mRes.getString(R.string.update_ok))
                .negativeText(mRes.getString(R.string.update_no2))
                .neutralText(mRes.getString(R.string.update_skip))
                .callback(new ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Log.d(LOG_TAG, "start upgrade");
                        mUpdateMgr.startUpdate(StartActivity.this, mConst.getFullDownloadUrl(updateCheckResult.getApkUrl()));
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        Log.d(LOG_TAG, "cancel upgrade");
                        dialog.dismiss();
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {
                        Log.d(LOG_TAG, "skip this version");
                        mUpdateMgr.skipVersion(updateCheckResult.getVersionCode());
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        finish();
                    }
                }).show();
    }

    private void goToLoginPage() {
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onDownloadBegin() {
        Log.d(LOG_TAG, "download begin");
    }

    @Override
    public void onDownloading(int percent) {
        Log.d(LOG_TAG, "downloading: " + percent);
    }

    @Override
    public void onDownloadSucc() {
        Log.d(LOG_TAG, "download succ");
    }

    @Override
    public void onDownloadError(Throwable err) {
        Log.d(LOG_TAG, "download error" + err);
    }
}
