package com.linuxclub.cdcfan.ui;

import android.view.View;
import butterknife.InjectView;
import com.linuxclub.cdcfan.R;

/**
 * Created by peace_da on 2015/5/8.
 */
abstract class LoadingBaseActivity extends BaseActivity {

    @InjectView(R.id.loading)
    View mLoadingView;

    @Override
    protected void initView() {
        super.initView();
        showLoadingPage(false);
    }

    protected void showLoadingPage(boolean flag) {
        mLoadingView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

}
