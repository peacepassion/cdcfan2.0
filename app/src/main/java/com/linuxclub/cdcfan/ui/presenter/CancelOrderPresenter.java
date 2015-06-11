package com.linuxclub.cdcfan.ui.presenter;

import android.content.Intent;
import android.util.Log;

import com.baidu.mobstat.StatService;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.httptask.CancelOrderTask;
import com.linuxclub.cdcfan.httptask.CheckOrderTask;
import com.linuxclub.cdcfan.model.CacelOrderResult;
import com.linuxclub.cdcfan.model.Order;
import com.linuxclub.cdcfan.ui.view.BaseView;
import com.linuxclub.cdcfan.ui.view.CancelOrderView;

import java.util.ArrayList;
import java.util.List;

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
public class CancelOrderPresenter extends EmptyPresenter {

    private CancelOrderView mCancelOrderView;

    private String mPSID;
    private List<Order> mOrderList;

    @Inject
    CancelOrderPresenter() {}

    @Override
    public void onCreate(BaseView view) {
        super.onCreate(view);
        mCancelOrderView = (CancelOrderView) view;
    }

    @Override
    public void onDestroy(BaseView view) {
        super.onDestroy(view);
        mCancelOrderView = null;
    }

    public void onBasicDataInit(Intent intent) {
        mPSID = intent.getStringExtra(LoginPresenter.KEY_PSID);
        mOrderList = new ArrayList<Order>();
    }

    public void onViewInit() {
        mCancelOrderView.showLoadingPage(true);
        mCancelOrderView.showDisableBtnPage(false, "");
        startGetOrderInfo();
    }

    private void startGetOrderInfo() {
        if (mPSID.equals("")) {
            mCancelOrderView.showLoadingPage(false);
            mCancelOrderView.showDisableBtnPage(true, mApp.getString(R.string.check_order_fail));
        } else {
            RestAdapter ra = mRestBuilder.build();
            CheckOrderTask task = ra.create(CheckOrderTask.class);
            task.checkOrder(mPSID, new Callback<List<Order>>() {
                @Override
                public void success(List<com.linuxclub.cdcfan.model.Order> orders, Response response) {
                    mOrderList = orders;
                    mCancelOrderView.showLoadingPage(false);
                    if (orders.size() > 0) {
                        mCancelOrderView.showAbleBtnPage(true);
                    } else {
                        mCancelOrderView.showDisableBtnPage(true, mApp.getString(R.string.check_order_fail2));
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    mCancelOrderView.showLoadingPage(false);
                    mCancelOrderView.showDisableBtnPage(true, mApp.getString(R.string.check_order_fail));
                }
            });
            mCancelOrderView.showLoadingPage(true);
        }
    }

    public void cancelOrder() {
        StatService.onEvent(mApp, mApp.getString(R.string.event_cancel_order), mApp.getString(R.string.event_order));
        Log.d("CDC", "start cancel order");
        RestAdapter ra = mRestBuilder.build();
        CancelOrderTask task = ra.create(CancelOrderTask.class);
        task.cancelOrder(mOrderList.get(0).orderid, new Callback<CacelOrderResult>() {
            @Override
            public void success(CacelOrderResult cacelOrderResult, Response response) {
                mCancelOrderView.showLoadingPage(false);
                mCancelOrderView.showDisableBtnPage(true, mApp.getString(R.string.cancel_order_succ));
                mCancelOrderView.showFixToast(mApp.getString(R.string.cancel_order_succ));
            }

            @Override
            public void failure(RetrofitError error) {
                mCancelOrderView.showLoadingPage(false);
                mCancelOrderView.showDisableBtnPage(true, mApp.getString(R.string.cancel_order_fail));
                mCancelOrderView.showFixToast(mApp.getString(R.string.cancel_order_fail));
            }
        });
        mCancelOrderView.showLoadingPage(true);
    }


}
