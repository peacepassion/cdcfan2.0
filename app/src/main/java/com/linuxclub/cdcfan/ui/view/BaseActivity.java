package com.linuxclub.cdcfan.ui.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.gc.materialdesign.widgets.SnackBar;
import com.linuxclub.cdcfan.MyApplication;
import com.linuxclub.cdcfan.ui.presenter.BasePresenter;
import com.linuxclub.cdcfan.utils.LogHelper;

import butterknife.ButterKnife;

/**
 * Created by peace_da on 2015/4/15.
 */
public abstract class BaseActivity extends FragmentActivity implements BaseView {

    protected final String LOG_TAG = LogHelper.getNativeSimpleLogTag(this.getClass(), LogHelper.DEFAULT_LOG_TAG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initBasicData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onDestroy(this);
        }
    }

    protected abstract int getLayout();

    protected BasePresenter getPresenter() {
        return null;
    }

    protected void initBasicData() {
        ((MyApplication) getApplication()).inject(this);
        BasePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onCreate(this);
        }
        ButterKnife.inject(this);
        StatService.setDebugOn(true);
    }

    protected void initView() {}

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    public void showFixToast(String content) {
        new SnackBar(this, content).show();
    }

    @Override
    public void showRealToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void exit() {
        finish();
    }

}