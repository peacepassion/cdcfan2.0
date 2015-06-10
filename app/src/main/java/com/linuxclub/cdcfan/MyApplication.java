package com.linuxclub.cdcfan;

import android.app.Application;
import android.content.res.Resources;
import com.linuxclub.cdcfan.utils.LogHelper;

/**
 * Created by peace_da on 2015/5/9.
 */
public class MyApplication extends Application {

    private Resources mRes;

    @Override
    public void onCreate() {
        super.onCreate();

        initBasicData();
    }

    private void initBasicData() {
        mRes = getResources();
    }

}
