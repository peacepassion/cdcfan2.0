package com.linuxclub.cdcfan.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.config.Const;
import com.linuxclub.cdcfan.config.PreferenceHelper;
import com.linuxclub.cdcfan.utils.LogHelper;
import com.gc.materialdesign.widgets.SnackBar;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.Log;
import retrofit.RestAdapter.LogLevel;

/**
 * Created by peace_da on 2015/4/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected final String LOG_TAG = LogHelper.getNativeSimpleLogTag(this.getClass(), LogHelper.DEFAULT_LOG_TAG);

    protected Const mConst;
    protected PreferenceHelper mPre;
    protected Resources mRes;
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
        ButterKnife.inject(this);
        mPre = PreferenceHelper.getInstance(this);
        mRes = getResources();
        mConst = Const.getInstance(this);
        mRestBuilder = new Builder();
        mRestBuilder.setEndpoint(mRes.getString(R.string.portal))
        .setLogLevel(LogLevel.FULL);
    }

    protected void initView() {
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
