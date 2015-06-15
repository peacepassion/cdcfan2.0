package com.linuxclub.cdcfan.config;

/**
 * Created by peace_da on 2015/5/8.
 */

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.linuxclub.cdcfan.R;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class Const {

    @Inject
    Application mContext;
    @Inject
    Resources mRes;

    @Inject
    Const() {
    }

    public String getDomain() {
        return mContext.getResources().getString(R.string.portal);
    }

    public String getUpdatePath() {
        return mRes.getString(R.string.update_path);
    }

    public String getUpdateInfoUrl() {
        return getDomain() + "/" + getUpdatePath();
    }

    public String getLoginPath() {
        return mContext.getResources().getString(R.string.login_path);
    }

    public Map<String, String> getLoginParams(String username) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put(mContext.getResources().getString(R.string.login_param), username);
        return map;
    }

    public String getOrderPath() {
        return mContext.getResources().getString(R.string.order_path);
    }

    public Map<String, String> getOrderParams(String psid, String depcode, String type) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put(mRes.getString(R.string.order_param_type), type);
        map.put(mRes.getString(R.string.order_param_psid), psid);
        map.put(mRes.getString(R.string.order_param_depcode), depcode);
        return map;
    }

    public String getCheckOrderPath() {
        return mRes.getString(R.string.check_order_path);
    }

    public Map<String, String> getCheckOrderParams(String psid) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put(mRes.getString(R.string.check_order_param_psid), psid);
        return map;
    }

    public String getCancelOrderPath() {
        return mRes.getString(R.string.cancel_order_path);
    }

    public Map<String, String> getCancelOrderParams(String orderID) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put(mRes.getString(R.string.cancel_order_param_orderid), orderID);
        return map;
    }

    public String getFullDownloadUrl(String path) {
        return getDomain() + path;
    }

}
