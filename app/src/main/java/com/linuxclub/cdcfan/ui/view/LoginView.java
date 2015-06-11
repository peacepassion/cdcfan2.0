package com.linuxclub.cdcfan.ui.view;

import com.linuxclub.cdcfan.model.User;

/**
 * Created by peace_da on 2015/6/11.
 */
public interface LoginView extends LoadingBaseView {

    String getUserInputUsername();

    void startOrderPage(User user);

}
