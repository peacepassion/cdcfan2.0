package com.linuxclub.cdcfan;

import android.app.Application;
import android.content.res.Resources;
import com.flurry.android.FlurryAgent;
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

        com.github.snowdream.android.util.Log.setTag(LogHelper.DEFAULT_LOG_TAG);

        initFlurry();
    }

    private void initBasicData() {
        mRes = getResources();
    }

    private void initFlurry() {
        FlurryAgent.setLogEnabled(true);
        FlurryAgent.setLogEvents(true);
        FlurryAgent.setReportLocation(true);

        FlurryAgent.init(this, mRes.getString(R.string.flurry_key));
    }
}
