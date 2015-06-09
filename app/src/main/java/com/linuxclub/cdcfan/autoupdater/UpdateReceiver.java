package com.linuxclub.cdcfan.autoupdater;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.linuxclub.cdcfan.service.ServiceConst;

/**
 * Created by peace_da on 2015/6/9.
 */
public class UpdateReceiver extends ResultReceiver {

    private UpdateListener mUpdateListener;

    public UpdateReceiver(Handler handler, UpdateListener listener) {
        super(handler);
        mUpdateListener = listener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == ServiceConst.DOWNLOAD_BEGIN) {
            mUpdateListener.onDownloadBegin();
        } else if (resultCode == ServiceConst.DOWNLOAD_RUNNING) {
            int percent = resultData.getInt(ServiceConst.KEY_DOWNLOAD_PERCENT, -1);
            if (percent == -1) {
                mUpdateListener.onDownloadError(new Exception("illegal percent data"));
            } else {
                mUpdateListener.onDownloading(percent);
            }
        } else if (resultCode == ServiceConst.DOWNLOAD_SUCC) {
            mUpdateListener.onDownloadSucc();
        } else {
            Exception e = (Exception) resultData.getSerializable(ServiceConst.KEY_DOWNLOAD_EXCEPTION);
            mUpdateListener.onDownloadError(e);
        }
    }

}
