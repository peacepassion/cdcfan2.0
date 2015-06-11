package com.linuxclub.cdcfan.module;

import android.app.Application;

import com.linuxclub.cdcfan.MyApplication;
import com.linuxclub.cdcfan.autoupdater.UpdateManager;
import com.linuxclub.cdcfan.autoupdater.UpdateReceiver;
import com.linuxclub.cdcfan.module.data.DataModule;
import com.linuxclub.cdcfan.module.ui.UIModule;
import com.linuxclub.cdcfan.service.BackgroundNetworkService;
import com.linuxclub.cdcfan.ui.presenter.StartPresenter;
import com.linuxclub.cdcfan.ui.view.CancelOrderActivity;
import com.linuxclub.cdcfan.ui.view.LoginActivity;
import com.linuxclub.cdcfan.ui.view.OrderActivity;
import com.linuxclub.cdcfan.ui.view.StartActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by peace_da on 2015/6/10.
 */
@Module(injects = {StartActivity.class,
        LoginActivity.class,
        OrderActivity.class,
        CancelOrderActivity.class,
        StartPresenter.class,
        UpdateReceiver.class,
        UpdateManager.class,
        BackgroundNetworkService.class},
        includes = {DataModule.class,
        UIModule.class},
        library = true)
public class AppModule {
    private MyApplication mApp;

    public AppModule(MyApplication app) {
        mApp = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApp;
    }

    @Provides
    @Singleton
    MyApplication provideMyApplication() {
        return mApp;
    }
}
