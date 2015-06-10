package com.linuxclub.cdcfan.autoupdater;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by peace_da on 2015/6/8.
 */
public interface CheckUpdateTask {

    @GET("/media/android/version.json")
    void check(Callback<UpdateCheckResult> callback);

}
