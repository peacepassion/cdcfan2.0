package com.linuxclub.cdcfan.ui.view;

import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by peace_da on 2015/6/11.
 */
public interface StartView extends BaseView {

    void showForceUpdateDialog(String updateTips, MaterialDialog.ButtonCallback callback);

    void showUpdateDialog(String updateTips, MaterialDialog.ButtonCallback callback);

    void goToLoginPage();

    void showDownloadDialog(MaterialDialog.ButtonCallback callback, DialogInterface.OnShowListener showListener);
}
