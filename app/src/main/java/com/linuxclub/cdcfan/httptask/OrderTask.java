package com.linuxclub.cdcfan.httptask;

import com.linuxclub.cdcfan.model.OrderResult;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by peace_da on 2015/5/26.
 */
public interface OrderTask {

    @FormUrlEncoded
    @POST("/api/order-new")
    void order(@Field("psid") String psid, @Field("depcode") String depcode, @Field("order") String type, Callback<OrderResult> callback);

}
