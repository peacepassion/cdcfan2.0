package com.linuxclub.cdcfan;

import android.app.Application;

import com.linuxclub.cdcfan.module.AppModule;

import dagger.ObjectGraph;

/**
 * Created by peace_da on 2015/5/9.
 */
public class MyApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        initBasicData();
    }

    private void initBasicData() {
        mObjectGraph = ObjectGraph.create(getModules());
    }

    private Object[] getModules() {
        return new Object[]{
                new AppModule(this)
        };
    }

    public void inject(Object obj) {
        mObjectGraph.inject(obj);
    }

}
