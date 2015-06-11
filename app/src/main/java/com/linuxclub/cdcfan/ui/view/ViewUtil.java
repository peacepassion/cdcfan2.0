package com.linuxclub.cdcfan.ui.view;

import android.view.View;

import com.linuxclub.cdcfan.MyApplication;

import javax.inject.Inject;

/**
 * Created by peace_da on 2015/6/11.
 */
public final class ViewUtil {

    @Inject
    MyApplication mApp;

    @Inject
    ViewUtil() {}

    public ViewUtil visible(View v) {
        v.setVisibility(View.VISIBLE);
        return this;
    }

    public ViewUtil invisible(View v) {
        v.setVisibility(View.INVISIBLE);
        return this;
    }

    public ViewUtil gone(View v) {
        v.setVisibility(View.GONE);
        return this;
    }

}
