package com.linuxclub.cdcfan.ui.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.httptask.LoginTask;
import com.linuxclub.cdcfan.model.User;
import com.linuxclub.cdcfan.ui.view.BaseView;
import com.linuxclub.cdcfan.ui.view.LoginView;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by peace_da on 2015/6/11.
 */
@Singleton
public class LoginPresenter extends EmptyPresenter {

    public static final String KEY_PSID = "key_psid";
    public static final String KEY_NAME = "name";
    public static final String KEY_DEPCODE = "depcode";

    private LoginView mLoginView;

    private String mUserName;

    @Inject
    LoginPresenter() {
    }

    @Override
    public void onCreate(BaseView view) {
        super.onCreate(view);
        mLoginView = (LoginView) view;
    }

    @Override
    public void onDestroy(BaseView view) {
        super.onDestroy(view);
        mLoginView = null;
    }

    public void checkJumpToOrderPage() {
        String userName = mGlobalSharedPref.getLastUserName();
        if (userName.equals("") == false) {
            mUserName = userName;
            startLogin();
        }
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public void startLogin() {
        if (TextUtils.isEmpty(mUserName)) {
            mLoginView.showFixToast(mApp.getString(R.string.fail_empty));
        } else {
            RestAdapter ra = mRestBuilder.build();
            LoginTask loginTask = ra.create(LoginTask.class);
            loginTask.login(mUserName, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    Log.d(LOG_TAG, "login succ");
                    mLoginView.showLoadingPage(false);
                    Log.d("CDC", "get user: " + user);
                    mLoginView.startOrderPage(user);
                    saveUserInfo(user);
                    mLoginView.exit();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(LOG_TAG, "login error: " + error);
                    mLoginView.showLoadingPage(false);
                    mLoginView.showFixToast(String.format(mApp.getString(R.string.fail), mUserName));
                }
            });
            mLoginView.showLoadingPage(true);
        }
    }

    private void saveUserInfo(User user) {
        mGlobalSharedPref.setKeyLastUserName(user.name);
    }

}
