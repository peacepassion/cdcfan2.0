package com.linuxclub.cdcfan.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by peace_da on 2015/6/8.
 */
public class BackgroundNetworkService extends IntentService {

    public static final String SERVICE_KEY = BackgroundNetworkService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundNetworkService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
