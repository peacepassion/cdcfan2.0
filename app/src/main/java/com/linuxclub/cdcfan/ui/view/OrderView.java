package com.linuxclub.cdcfan.ui.view;

import android.content.Intent;

import com.linuxclub.cdcfan.model.User;
import com.linuxclub.cdcfan.ui.presenter.OrderPresenter;

/**
 * Created by peace_da on 2015/6/11.
 */
public interface OrderView extends LoadingBaseView {

    void showBasiccInfo(User user);

    void showOrderSuccPage(boolean flag);

    void showOrderFailPage(boolean flag, OrderPresenter.OrderSummary orderSummary);

    void gotoLoginPage();

    void gotoCheckOrderPage(User user);

}
