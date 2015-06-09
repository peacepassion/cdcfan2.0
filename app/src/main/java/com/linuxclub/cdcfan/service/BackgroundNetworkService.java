package com.linuxclub.cdcfan.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.linuxclub.cdcfan.utils.LogHelper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;

/**
 * Created by peace_da on 2015/6/8.
 */
public class BackgroundNetworkService extends IntentService {

    public static final String LOG_TAG = LogHelper.getNativeSimpleLogTag(BackgroundNetworkService.class, LogHelper.DEFAULT_LOG_TAG);

    public static final String SERVICE_KEY = BackgroundNetworkService.class.getSimpleName();

    public BackgroundNetworkService() {
        super(SERVICE_KEY);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        Log.d(LOG_TAG, "service intent: " + intent.getAction());
        if (action.equals(ServiceConst.KEY_DOWNLOAD_FILE)) {
            final String apkurl = intent.getStringExtra(ServiceConst.KEY_DOWNLOAD_FILE_APK_URL);
            final ResultReceiver receiver = intent.getParcelableExtra(ServiceConst.KEY_DOWNLOAD_FILE_UPDATE_RECEIVER);
            final String apkPath = intent.getStringExtra(ServiceConst.KEY_DOWNLOAD_FILE_PATH);
            if (TextUtils.isEmpty(apkurl) == false) {
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "download task, apk url: " + apkurl + ", apk path: " + apkPath);
                        doStartDownload(apkurl, receiver, apkPath);
                    }
                });
            }
        }
    }

    private void doStartDownload(String apkurl, ResultReceiver receiver, String apkPath) {
        OkHttpClient httpClient = new OkHttpClient();
        Call call = httpClient.newCall(new Request.Builder().url(apkurl).get().build());
        try {
            receiver.send(ServiceConst.DOWNLOAD_BEGIN, null);
            sendDownloadPercent(receiver, 0);

            Response response = call.execute();
            if (response.isSuccessful() == false) {
                Log.e(LOG_TAG, "fail to get apk, error code: " + response.code());
                sendDownloadError(receiver, new Exception("error code: " + response.code()));
                return;
            }

            BufferedInputStream is = new BufferedInputStream(response.body().byteStream());
            RandomAccessFile file = new RandomAccessFile(new File(apkPath), "rw");
            byte[] buff = new byte[1024 * 4];
            long downloaded = 0;
            long target = response.body().contentLength();

            Log.d(LOG_TAG, "body length: " + target);

            while (downloaded < target) {
                int readed = is.read(buff);
                if (readed <= 0) {
                    break;
                }
                file.write(buff, 0, readed);
                downloaded += readed;

                sendDownloadPercent(receiver, (int) (downloaded * 100 / target));
            }

            is.close();
            file.close();

            receiver.send(ServiceConst.DOWNLOAD_SUCC, null);

        } catch (IOException e) {
            e.printStackTrace();
            sendDownloadError(receiver, e);
        }
    }

    private void sendDownloadPercent(ResultReceiver receiver, int percent) {
        Bundle bundle = new Bundle();
        bundle.putInt(ServiceConst.KEY_DOWNLOAD_PERCENT, percent);
        receiver.send(ServiceConst.DOWNLOAD_RUNNING, bundle);
    }

    private void sendDownloadError(ResultReceiver receiver, Throwable t) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ServiceConst.KEY_DOWNLOAD_EXCEPTION, t);
        receiver.send(ServiceConst.DOWNLOAD_ERROR, bundle);
    }

}
