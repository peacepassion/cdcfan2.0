package com.linuxclub.cdcfan.ui.view;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gc.materialdesign.views.Button;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.model.User;
import com.linuxclub.cdcfan.ui.presenter.BasePresenter;
import com.linuxclub.cdcfan.ui.presenter.LoginPresenter;
import com.linuxclub.cdcfan.ui.presenter.OrderPresenter;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public class OrderActivity extends LoadingBaseActivity implements OnClickListener, OrderView {

    @Inject
    OrderPresenter mOrderPresenter;

    @InjectView(R.id.title)
    TextView mBasicInfo;

    @InjectView(R.id.order)
    Button mOrderBtn;

    @InjectView(R.id.log_out)
    Button mLogoutBtn;

    @InjectView(R.id.check_order)
    Button mCheckOrderBtn;

    @Override
    protected int getLayout() {
        return R.layout.order;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mOrderPresenter;
    }

    @Override
    protected void initBasicData() {
        super.initBasicData();
        mOrderPresenter.onBasicDataInit(getIntent());
    }

    @Override
    protected void initView() {
        super.initView();
        mOrderPresenter.onViewInit();
    }

    @Override
    public void showBasiccInfo(User user) {
        mBasicInfo.setText(user.name + " / " + user.depcode);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    @OnClick({R.id.order, R.id.log_out, R.id.check_order})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.order) {
            mOrderPresenter.order();
        } else if (id == R.id.log_out) {
            mOrderPresenter.logOut();
        } else if (id == R.id.check_order) {
            mOrderPresenter.checkOrder();
        }
    }

    @Override
    public void showOrderSuccPage(boolean flag) {
        if (flag) {
            showFixToast(getString(R.string.order_succ));
        }
    }

    @Override
    public void gotoCheckOrderPage(User user) {
        Intent intent = new Intent(this, CancelOrderActivity.class);
        intent.putExtra(LoginPresenter.KEY_PSID, user.psid);
        startActivity(intent);
    }


    @Override
    public void showOrderFailPage(boolean flag, OrderPresenter.OrderSummary orderSummary) {
        if (flag) {
            showFixToast(String.format(getString(R.string.order_fail), orderSummary.getDescription(getApplication())));
        }
    }

    @Override
    public void gotoLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}
