package com.linuxclub.cdcfan.ui.presenter;

import com.linuxclub.cdcfan.ui.view.BaseView;

/**
 * Created by peace_da on 2015/6/11.
 */
public interface BasePresenter {

    void onCreate(BaseView view);

    void onResume(BaseView view);

    void onPause(BaseView view);

    void onDestroy(BaseView view);

}
