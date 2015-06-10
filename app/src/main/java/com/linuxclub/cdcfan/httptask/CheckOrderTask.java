package com.linuxclub.cdcfan.httptask;

import com.linuxclub.cdcfan.model.Order;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by peace_da on 2015/5/26.
 */
public interface CheckOrderTask {

    @GET("/api/my-orders")
    void checkOrder(@Query("psid") String psid, Callback<List<Order>> callback);

}
