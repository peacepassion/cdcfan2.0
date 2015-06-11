package com.linuxclub.cdcfan.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.gc.materialdesign.widgets.SnackBar;
import com.linuxclub.cdcfan.MyApplication;
import com.linuxclub.cdcfan.config.Const;
import com.linuxclub.cdcfan.persist.GlobalSharedPreferences;
import com.linuxclub.cdcfan.utils.LogHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.RestAdapter;

/**
 * Created by peace_da on 2015/4/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected final String LOG_TAG = LogHelper.getNativeSimpleLogTag(this.getClass(), LogHelper.DEFAULT_LOG_TAG);

    @Inject
    protected Const mConst;
    @Inject
    protected GlobalSharedPreferences mGlobalSharedPref;
    @Inject
    protected Resources mRes;
    @Inject
    protected RestAdapter.Builder mRestBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initBasicData();
        initView();
    }

    protected abstract int getLayout();

    protected void initBasicData() {
        ((MyApplication) getApplication()).inject(this);
        ButterKnife.inject(this);
        StatService.setDebugOn(true);
    }

    protected void initView() {
    }

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

    protected void showFixToast(String content) {
        new SnackBar(this, content).show();
    }

    protected void showRealToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    public BaseActivity visible(View v) {
        v.setVisibility(View.VISIBLE);
        return this;
    }

    public BaseActivity invisible(View v) {
        v.setVisibility(View.INVISIBLE);
        return this;
    }

    public BaseActivity gone(View v) {
        v.setVisibility(View.GONE);
        return this;
    }

}
