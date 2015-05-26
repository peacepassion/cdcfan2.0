package com.linuxclub.cdcfan.httptask;

import com.linuxclub.cdcfan.model.CacelOrderResult;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by peace_da on 2015/5/26.
 */
public interface CancelOrderTask {

    @FormUrlEncoded
    @POST("/api/cancel-order")
    void cancelOrder(@Field("orderid") int orderId, Callback<CacelOrderResult> callback);

}
