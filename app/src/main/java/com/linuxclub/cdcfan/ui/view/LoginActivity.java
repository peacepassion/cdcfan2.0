package com.linuxclub.cdcfan.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.model.User;
import com.linuxclub.cdcfan.ui.presenter.BasePresenter;
import com.linuxclub.cdcfan.ui.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends LoadingBaseActivity implements OnClickListener, LoginView {

    @InjectView(R.id.username)
    EditText mUserNameET;

    @InjectView(R.id.login)
    Button mLoginBtn;

    @Inject
    LoginPresenter mLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter.checkJumpToOrderPage();
    }

    @Override
    protected int getLayout() {
        return R.layout.login;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mLoginPresenter;
    }

    @Override
    protected void initBasicData() {
        super.initBasicData();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.title)).setText(getString(R.string.log_in));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    @OnClick({R.id.login})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login) {
            mLoginPresenter.setUserName(mUserNameET.getText().toString());
            mLoginPresenter.startLogin();
        }
    }

    @Override
    public void startOrderPage(User user) {
        Intent intent;
        intent = new Intent(this, OrderActivity.class);
        intent.putExtra(LoginPresenter.KEY_PSID, user.psid);
        intent.putExtra(LoginPresenter.KEY_NAME, user.name);
        intent.putExtra(LoginPresenter.KEY_DEPCODE, user.depcode);
        startActivity(intent);
    }

}
