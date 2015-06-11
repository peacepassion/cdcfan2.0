package com.linuxclub.cdcfan.ui.presenter;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mobstat.StatService;
import com.linuxclub.cdcfan.MyApplication;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.autoupdater.UpdateCheckResult;
import com.linuxclub.cdcfan.autoupdater.UpdateListener;
import com.linuxclub.cdcfan.autoupdater.UpdateManager;
import com.linuxclub.cdcfan.ui.view.BaseView;
import com.linuxclub.cdcfan.ui.view.StartView;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.event.EventBus;

/**
 * Created by peace_da on 2015/6/11.
 */
@Singleton
public class StartPresenter extends EmptyPresenter implements UpdateListener {

    @Inject
    UpdateManager mUpdateMgr;
    @Inject
    MyApplication mApp;

    private boolean mIsForceUpdate = false;

    private StartView mStartView;
    private MaterialDialog mDownloadingDlg;

    @Inject
    public StartPresenter() {}

    @Override
    public void onCreate(BaseView view) {
        super.onCreate(view);
        mStartView = (StartView) view;
        EventBus.getDefault().register(this);
        StatService.onEvent(mApp, mApp.getString(R.string.event_enter_start_acti), mApp.getString(R.string.event_enter_start_acti));
        mIsForceUpdate = false;
        mApp.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                checkAppAvailability();
            }
        }, 3000);
    }

    @Override
    public void onDestroy(BaseView view) {
        super.onDestroy(view);
        EventBus.getDefault().unregister(this);
        mStartView = null;
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
                mIsForceUpdate = true;
                mStartView.showForceUpdateDialog(updateCheckResult.getUpdateTips(), createForceUpdateDialogCallback(updateCheckResult));
            } else {
                try {
                    int versionCode = mApp.getPackageManager().getPackageInfo(mApp.getPackageName(), 0).versionCode;
                    Log.d(LOG_TAG, "self version code: " + versionCode + ", get version: " + updateCheckResult.getVersionCode());
                    if (mGlobalSharedPref.getSkipVersions().contains("" + versionCode) == false
                            && versionCode < updateCheckResult.getVersionCode()) {
                        mStartView.showUpdateDialog(updateCheckResult.getUpdateTips(), createUpdateDialogCallback(updateCheckResult));
                    } else {
                        mStartView.goToLoginPage();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    mStartView.goToLoginPage();
                }
            }
        } else {
            Log.w(LOG_TAG, "check update fail");
            mStartView.showRealToast(mApp.getString(R.string.conn_fail));
            mStartView.exit();
        }
    }

    private MaterialDialog.ButtonCallback createForceUpdateDialogCallback(final UpdateCheckResult updateCheckResult) {
        return new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                Log.d(LOG_TAG, "start upgrade");
                dialog.dismiss();
                mUpdateMgr.startUpdate(StartPresenter.this, mConst.getFullDownloadUrl(updateCheckResult.getApkUrl()));
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
                mStartView.exit();
            }
        };
    }

    private MaterialDialog.ButtonCallback createUpdateDialogCallback(final UpdateCheckResult updateCheckResult) {
        return new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                Log.d(LOG_TAG, "start upgrade");
                dialog.dismiss();
                mUpdateMgr.startUpdate(StartPresenter.this, mConst.getFullDownloadUrl(updateCheckResult.getApkUrl()));
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                Log.d(LOG_TAG, "cancel upgrade");
                dialog.dismiss();
                mStartView.goToLoginPage();
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                Log.d(LOG_TAG, "skip this version");
                dialog.dismiss();
                mUpdateMgr.skipVersion(updateCheckResult.getVersionCode());
                mStartView.goToLoginPage();
            }
        };
    }

    private MaterialDialog.ButtonCallback createDownloadingDialogCallback() {
        return new MaterialDialog.ButtonCallback() {
            @Override
            public void onNegative(MaterialDialog dialog) {
                Log.w(LOG_TAG, "user cancel update");
                mUpdateMgr.stopUpdate();
            }
        };
    }

    private DialogInterface.OnShowListener createDownloadingShowListener() {
        return new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mDownloadingDlg = (MaterialDialog) dialog;
            }
        };
    }

    @Override
    public void onDownloadBegin(long size) {
        Log.d(LOG_TAG, "download begin");
        mStartView.showDownloadDialog(createDownloadingDialogCallback(), createDownloadingShowListener());
    }

    @Override
    public void onDownloading(int percent) {
        Log.d(LOG_TAG, "downloading: " + percent);
        if (mDownloadingDlg != null) {
            mDownloadingDlg.setProgress(percent);
        }
    }

    @Override
    public void onDownloadSucc() {
        Log.d(LOG_TAG, "download succ");
        if (mDownloadingDlg != null) {
            mDownloadingDlg.setContent(mApp.getString(R.string.download_succ));
        }
        mStartView.exit();
    }

    @Override
    public void onDownloadError(Throwable err) {
        Log.d(LOG_TAG, "download error" + err);
    }

    @Override
    public void onDownloadCanceled() {
        Log.d(LOG_TAG, "update cancel succ");
        if (mIsForceUpdate) {
            mStartView.showRealToast(mApp.getString(R.string.force_update_fail));
        } else {
            mStartView.goToLoginPage();
        }
    }

}
