package com.linuxclub.cdcfan.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.gc.materialdesign.views.Button;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.httptask.CancelOrderTask;
import com.linuxclub.cdcfan.httptask.CheckOrderTask;
import com.linuxclub.cdcfan.model.CacelOrderResult;
import com.linuxclub.cdcfan.model.Order;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peace_da on 2015/4/15.
 */
public class CancelOrderActivity extends LoadingBaseActivity implements OnClickListener {

    public Button mCancelBtn;
    private String mPSID;
    private List<Order> mOrderList;
    private TextView mHasOrderTV;
    private View mCancelView;

    @Override
    protected int getLayout() {
        return R.layout.cancel_order;
    }

    @Override
    protected void initBasicData() {
        super.initBasicData();
        Intent intent = getIntent();
        mPSID = intent.getStringExtra(LoginActivity.KEY_PSID);
        mOrderList = new ArrayList<Order>();
    }

    @Override
    protected void initView() {
        super.initView();
        mCancelBtn = (Button) findViewById(R.id.cancel);
        mCancelBtn.setOnClickListener(this);
        mHasOrderTV = (TextView) findViewById(R.id.has_order);
        mCancelView = findViewById(R.id.cancel_view);

        ((TextView) findViewById(R.id.title)).setText(mRes.getString(R.string.check_order));

        showLoadingPage(true);
        showDisableBtnPage(false, "");

        startGetOrderInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startGetOrderInfo() {
        if (mPSID.equals("")) {
            showLoadingPage(false);
            showDisableBtnPage(true, mRes.getString(R.string.check_order_fail));
        } else {
            RestAdapter ra = mRestBuilder.build();
            CheckOrderTask task = ra.create(CheckOrderTask.class);
            task.checkOrder(mPSID, new Callback<List<com.linuxclub.cdcfan.model.Order>>() {
                @Override
                public void success(List<com.linuxclub.cdcfan.model.Order> orders, Response response) {
                    mOrderList = orders;
                    showLoadingPage(false);
                    showAbleBtnPage(true);
                }

                @Override
                public void failure(RetrofitError error) {
                    showLoadingPage(false);
                    showDisableBtnPage(true, mRes.getString(R.string.check_order_fail2));
                }
            });
            showLoadingPage(true);
        }
    }

    private void showAbleBtnPage(boolean flag) {
        if (flag) {
            mHasOrderTV.setText(String.format(mRes.getString(R.string.has_order), 1));
        }
    }

    private void showDisableBtnPage(boolean flag, String content) {
        if (flag) {
            showFixToast(content);
            mCancelBtn.setEnabled(false);
            mHasOrderTV.setText(content);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel) {
            Log.d("CDC", "start cancel order");
            RestAdapter ra = mRestBuilder.build();
            CancelOrderTask task = ra.create(CancelOrderTask.class);
            task.cancelOrder(mOrderList.get(0).orderid, new Callback<CacelOrderResult>() {
                @Override
                public void success(CacelOrderResult cacelOrderResult, Response response) {
                    showLoadingPage(false);
                    showDisableBtnPage(true, mRes.getString(R.string.cancel_order_succ));
                    showFixToast(mRes.getString(R.string.cancel_order_succ));
                }

                @Override
                public void failure(RetrofitError error) {
                    showLoadingPage(false);
                    showDisableBtnPage(true, mRes.getString(R.string.cancel_order_fail));
                }
            });
            showLoadingPage(true);
        }
    }

}
