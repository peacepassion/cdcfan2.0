package com.linuxclub.cdcfan.ui.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.ui.presenter.BasePresenter;
import com.linuxclub.cdcfan.ui.presenter.CancelOrderPresenter;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by peace_da on 2015/4/15.
 */
public class CancelOrderActivity extends LoadingBaseActivity implements OnClickListener, CancelOrderView {

    @Inject
    CancelOrderPresenter mCancelOrderPresenter;

    @InjectView(R.id.cancel)
    Button mCancelBtn;

    @InjectView(R.id.has_order)
    TextView mHasOrderTV;

    @InjectView(R.id.cancel_view)
    View mCancelView;

    @Override
    protected int getLayout() {
        return R.layout.cancel_order;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mCancelOrderPresenter;
    }

    @Override
    protected void initBasicData() {
        super.initBasicData();
        mCancelOrderPresenter.onBasicDataInit(getIntent());
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.title)).setText(getString(R.string.check_order));
        mCancelOrderPresenter.onViewInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showAbleBtnPage(boolean flag) {
        if (flag) {
            mHasOrderTV.setText(String.format(getString(R.string.has_order), 1));
        }
    }

    @Override
    public void showDisableBtnPage(boolean flag, String content) {
        if (flag) {
            mCancelBtn.setEnabled(false);
            mHasOrderTV.setText(content);
        }
    }

    @Override
    @OnClick({R.id.cancel})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel) {
            mCancelOrderPresenter.cancelOrder();
        }
    }

}
