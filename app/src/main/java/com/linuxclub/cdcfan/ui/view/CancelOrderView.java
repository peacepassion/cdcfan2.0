package com.linuxclub.cdcfan.ui.view;

/**
 * Created by peace_da on 2015/6/11.
 */
public interface CancelOrderView extends LoadingBaseView {

    void showAbleBtnPage(boolean flag);

    void showDisableBtnPage(boolean flag, String content);
}
