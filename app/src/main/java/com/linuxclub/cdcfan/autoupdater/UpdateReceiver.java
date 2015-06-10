package com.linuxclub.cdcfan.autoupdater;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.linuxclub.cdcfan.service.ServiceConst;

/**
 * Created by peace_da on 2015/6/9.
 */
public class UpdateReceiver extends ResultReceiver {

    private UpdateListener mUpdateListener;
    private UpdateManager mUpdateMgr;

    public UpdateReceiver(Context ctx, Handler handler, UpdateListener listener) {
        super(handler);
        mUpdateListener = listener;
        mUpdateMgr = UpdateManager.getInstance(ctx.getApplicationContext());
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == ServiceConst.DOWNLOAD_BEGIN) {
            long size = resultData.getLong(ServiceConst.KEY_DOWNLOAD_FILE_APK_SIZE, 0);
            mUpdateListener.onDownloadBegin(size);
        } else if (resultCode == ServiceConst.DOWNLOAD_RUNNING) {
            int percent = resultData.getInt(ServiceConst.KEY_DOWNLOAD_PERCENT, -1);
            if (percent == -1) {
                mUpdateListener.onDownloadError(new Exception("illegal percent data"));
            } else {
                mUpdateListener.onDownloading(percent);
            }
        } else if (resultCode == ServiceConst.DOWNLOAD_SUCC) {
            mUpdateListener.onDownloadSucc();
            mUpdateMgr.install();
        } else if (resultCode == ServiceConst.DOWNLOAD_CANCELED) {
            mUpdateListener.onDownloadCanceled();
        } else {
            Exception e = (Exception) resultData.getSerializable(ServiceConst.KEY_DOWNLOAD_EXCEPTION);
            mUpdateListener.onDownloadError(e);
        }
    }

}
