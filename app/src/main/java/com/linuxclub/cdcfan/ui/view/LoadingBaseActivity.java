package com.linuxclub.cdcfan.ui.view;

import android.view.View;
import butterknife.InjectView;
import com.linuxclub.cdcfan.R;

/**
 * Created by peace_da on 2015/5/8.
 */
abstract class LoadingBaseActivity extends BaseActivity implements LoadingBaseView {

    @InjectView(R.id.loading)
    View mLoadingView;

    @Override
    protected void initView() {
        super.initView();
        showLoadingPage(false);
    }

    @Override
    public void showLoadingPage(boolean flag) {
        mLoadingView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

}
