package com.linuxclub.cdcfan.module.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.persist.GlobalSharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by peace_da on 2015/6/10.
 */
@Module(injects = {GlobalSharedPreferences.class}, library = true, complete = false)
public class BasicModule {

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences(GlobalSharedPreferences.KEY, Context.MODE_PRIVATE);
    }

    @Provides
    RestAdapter provideRestAdapter(Application app) {
        return new RestAdapter.Builder().setEndpoint(app.getString(R.string.portal)).setLogLevel(RestAdapter.LogLevel.FULL).build();
    }

    @Provides
    RestAdapter.Builder provideRestAdapterBulder(Application app) {
        return new RestAdapter.Builder().setEndpoint(app.getString(R.string.portal)).setLogLevel(RestAdapter.LogLevel.FULL);
    }

    @Provides @Singleton
    Resources provideResource(Application app) {
        return app.getResources();
    }

}
