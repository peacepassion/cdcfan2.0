package com.linuxclub.cdcfan.ui.presenter;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.baidu.mobstat.StatService;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.httptask.OrderTask;
import com.linuxclub.cdcfan.model.OrderResult;
import com.linuxclub.cdcfan.model.User;
import com.linuxclub.cdcfan.ui.view.BaseView;
import com.linuxclub.cdcfan.ui.view.OrderView;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by peace_da on 2015/6/11.
 */
@Singleton
public class OrderPresenter extends EmptyPresenter {

    private OrderView mOrderView;

    private OrderSummary mOrderSummary;

    private User mUser;

    @Inject
    OrderPresenter() {}

    @Override
    public void onCreate(BaseView view) {
        super.onCreate(view);
        mOrderView = (OrderView) view;
    }

    @Override
    public void onDestroy(BaseView view) {
        super.onDestroy(view);
        mOrderView = null;
    }

    public void onBasicDataInit(Intent intent) {
        mUser = new User();
        mUser.psid = intent.getStringExtra(LoginPresenter.KEY_PSID);
        mUser.name = intent.getStringExtra(LoginPresenter.KEY_NAME);
        mUser.depcode = intent.getStringExtra(LoginPresenter.KEY_DEPCODE);
    }

    public void onViewInit() {
        mOrderView.showBasiccInfo(mUser);
        mOrderView.showOrderSuccPage(false);
        mOrderView.showOrderFailPage(false, mOrderSummary);
    }

    public void order() {
        StatService.onEvent(mApp, mApp.getString(R.string.event_order), mApp.getString(R.string.event_order));
        RestAdapter ra = mRestBuilder.build();
        OrderTask ot = ra.create(OrderTask.class);
        ot.order(mUser.psid, mUser.depcode, mApp.getString(R.string.order_param_type_def_val), new Callback<OrderResult>() {
            @Override
            public void success(OrderResult orderResult, Response response) {
                mOrderView.showLoadingPage(false);
                if (orderResult.succeed_count > 0) {
                    mOrderSummary = OrderSummary.SUCC;
                    mOrderView.showOrderSuccPage(true);
                } else {
                    if (orderResult.exceed_count > 0) {
                        mOrderSummary = OrderSummary.EXCEED;
                    } else if (orderResult.rejected_count > 0) {
                        mOrderSummary = OrderSummary.OVER_TIME;
                    } else {
                        mOrderSummary = OrderSummary.FAIL;
                    }
                    mOrderView.showOrderFailPage(true, mOrderSummary);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("CDC", "error: " + error);
                mOrderView.showLoadingPage(false);
                mOrderSummary = OrderSummary.FAIL;
                mOrderView.showOrderFailPage(true, mOrderSummary);
            }
        });
        mOrderView.showLoadingPage(true);
    }

    public void logOut() {
        mGlobalSharedPref.setKeyLastUserName("");
        mOrderView.gotoLoginPage();
    }

    public void checkOrder() {
        StatService.onEvent(mApp, mApp.getString(R.string.event_check_order), mApp.getString(R.string.event_check_order));
        mOrderView.gotoCheckOrderPage(mUser);
    }

    public enum OrderSummary {
        SUCC(R.string.succ_des),
        EXCEED(R.string.exceed_des),
        OVER_TIME(R.string.overtime_des),
        FAIL(R.string.fail_des);

        private int mDescriptionID;

        OrderSummary(int des) {
            mDescriptionID = des;
        }

        public String getDescription(Application app) {
            return app.getString(mDescriptionID);
        }
    }

}
