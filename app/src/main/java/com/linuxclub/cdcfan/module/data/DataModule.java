package com.linuxclub.cdcfan.module.data;

import com.linuxclub.cdcfan.autoupdater.UpdateManager;
import com.linuxclub.cdcfan.autoupdater.UpdateReceiver;
import com.linuxclub.cdcfan.service.BackgroundNetworkService;
import com.linuxclub.cdcfan.ui.CancelOrderActivity;
import com.linuxclub.cdcfan.ui.LoginActivity;
import com.linuxclub.cdcfan.ui.OrderActivity;
import com.linuxclub.cdcfan.ui.StartActivity;

import dagger.Module;

/**
 * Created by peace_da on 2015/6/10.
 */
@Module(injects = {StartActivity.class,
        LoginActivity.class,
        OrderActivity.class,
        CancelOrderActivity.class,
        UpdateReceiver.class,
        UpdateManager.class,
        BackgroundNetworkService.class},
        includes = {BasicModule.class}, complete = false)
public class DataModule {

}
