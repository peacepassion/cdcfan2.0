package com.linuxclub.cdcfan.ui.presenter;

import com.linuxclub.cdcfan.config.Const;
import com.linuxclub.cdcfan.persist.GlobalSharedPreferences;
import com.linuxclub.cdcfan.ui.view.BaseView;
import com.linuxclub.cdcfan.utils.LogHelper;

import javax.inject.Inject;

/**
 * Created by peace_da on 2015/6/11.
 */
public class EmptyPresenter implements BasePresenter {

    protected final String LOG_TAG = LogHelper.getNativeSimpleLogTag(this.getClass(), LogHelper.DEFAULT_LOG_TAG);

    @Inject
    protected Const mConst;
    @Inject
    protected GlobalSharedPreferences mGlobalSharedPref;

    @Override
    public void onCreate(BaseView view) {

    }

    @Override
    public void onResume(BaseView view) {

    }

    @Override
    public void onPause(BaseView view) {

    }

    @Override
    public void onDestroy(BaseView view) {

    }
}
