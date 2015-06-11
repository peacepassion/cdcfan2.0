package com.linuxclub.cdcfan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import com.gc.materialdesign.views.Button;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.httptask.LoginTask;
import com.linuxclub.cdcfan.model.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends LoadingBaseActivity implements OnClickListener {

    public static final String KEY_PSID = "key_psid";
    public static final String KEY_NAME = "name";
    public static final String KEY_DEPCODE = "depcode";

    @InjectView(R.id.username)
    EditText mUserNameET;

    @InjectView(R.id.login)
    Button mLoginBtn;

    private String mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkJumpToOrderPage();
    }

    private void checkJumpToOrderPage() {
        String userName = mGlobalSharedPref.getLastUserName();
        if (userName.equals("") == false) {
            mUserName = userName;
            startLogin();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.login;
    }

    @Override
    protected void initBasicData() {
        super.initBasicData();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.title)).setText(mRes.getString(R.string.log_in));
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
            mUserName = mUserNameET.getText().toString();
            if (mUserName.equals("")) {
                showFixToast(mRes.getString(R.string.fail_empty));
            } else {
                startLogin();
            }
        }
    }

    private void startLogin() {
        RestAdapter ra = mRestBuilder.build();
        LoginTask loginTask = ra.create(LoginTask.class);
        loginTask.login(mUserName, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d(LOG_TAG, "login succ");
                showLoadingPage(false);
                Log.d("CDC", "get user: " + user);
                startOrderActivity(user);
                saveUserInfo(user);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "login error: " + error);
                showLoadingPage(false);
                showFixToast(String.format(mRes.getString(R.string.fail), mUserName));
            }
        });
        showLoadingPage(true);
    }

    private void startOrderActivity(User user) {
        Intent intent;
        intent = new Intent(this, OrderActivity.class);
        intent.putExtra(KEY_PSID, user.psid);
        intent.putExtra(KEY_NAME, user.name);
        intent.putExtra(KEY_DEPCODE, user.depcode);
        startActivity(intent);
    }

    private void saveUserInfo(User user) {
        mGlobalSharedPref.setKeyLastUserName(user.name);
    }

}
