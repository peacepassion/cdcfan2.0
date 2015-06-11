package com.linuxclub.cdcfan.module;

import android.app.Application;

import com.linuxclub.cdcfan.MyApplication;
import com.linuxclub.cdcfan.module.data.DataModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by peace_da on 2015/6/10.
 */
@Module(includes = {DataModule.class}, library = true)
public class AppModule {
    private MyApplication mApp;

    public AppModule(MyApplication app) {
        mApp = app;
    }

    @Provides @Singleton Application provideApplication() {
        return mApp;
    }
}
