package com.linuxclub.cdcfan.httptask;

import com.linuxclub.cdcfan.model.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by peace_da on 2015/5/26.
 */
public interface LoginTask {

    @GET("/api/user-search")
    void login(@Query("identity") String username, Callback<User> cb);

}
