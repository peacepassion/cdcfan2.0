package com.linuxclub.cdcfan.ui.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.ui.presenter.StartPresenter;

import javax.inject.Inject;

/**
 * Created by peace_da on 2015/5/8.
 */
public class StartActivity extends BaseActivity implements StartView {

    @Inject
    StartPresenter mPresenter;

    private MaterialDialog mDownloadingDlg;

    @Override
    protected int getLayout() {
        return R.layout.start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.onCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showForceUpdateDialog(String updateTips, ButtonCallback callback) {
        new MaterialDialog.Builder(StartActivity.this)
                .title(mRes.getString(R.string.update_title))
                .content(updateTips)
                .positiveText(mRes.getString(R.string.update_ok))
                .negativeText(mRes.getString(R.string.update_no))
                .callback(new ButtonCallback() {
                }).show();
    }

    @Override
    public void showUpdateDialog(String updateTips, MaterialDialog.ButtonCallback callback) {
        new MaterialDialog.Builder(StartActivity.this)
                .title(mRes.getString(R.string.update_title2))
                .content(updateTips)
                .positiveText(mRes.getString(R.string.update_ok))
                .negativeText(mRes.getString(R.string.update_no2))
                .neutralText(mRes.getString(R.string.update_skip))
                .callback(callback).show();
    }

    @Override
    public void goToLoginPage() {
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showDownloadDialog(ButtonCallback callback, DialogInterface.OnShowListener showListener) {
        new MaterialDialog.Builder(this)
                .title(R.string.update_title3)
                .content(R.string.update_content)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 100, false)
                .cancelable(false)
                .negativeText(R.string.update_cancel)
                .callback(callback)
                .showListener(showListener)
                .show();
    }

    @Override
    public void exit() {
        finish();
    }

}
