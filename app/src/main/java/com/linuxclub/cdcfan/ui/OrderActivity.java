package com.linuxclub.cdcfan.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.telephony.ServiceState;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import com.baidu.mobstat.StatService;
import com.gc.materialdesign.views.Button;
import com.linuxclub.cdcfan.R;
import com.linuxclub.cdcfan.httptask.HttpTaskCallback;
import com.linuxclub.cdcfan.httptask.OrderTask;
import com.linuxclub.cdcfan.model.OrderResult;
import com.linuxclub.cdcfan.model.User;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrderActivity extends LoadingBaseActivity implements OnClickListener, HttpTaskCallback {

    @InjectView(R.id.title)
    TextView mBasicInfo;

    @InjectView(R.id.order)
    Button mOrderBtn;

    @InjectView(R.id.log_out)
    Button mLogoutBtn;

    @InjectView(R.id.check_order)
    Button mCheckOrderBtn;

    private OrderSummary mOrderSummary;

    User mUser;

    @Override
    protected int getLayout() {
        return R.layout.order;
    }

    @Override
    protected void initBasicData() {
        super.initBasicData();

        Intent intent = getIntent();
        mUser = new User();
        mUser.psid = intent.getStringExtra(LoginActivity.KEY_PSID);
        mUser.name = intent.getStringExtra(LoginActivity.KEY_NAME);
        mUser.depcode = intent.getStringExtra(LoginActivity.KEY_DEPCODE);
    }

    @Override
    protected void initView() {
        super.initView();
        mBasicInfo.setText(mUser.name + " / " + mUser.depcode);

        showOrderSuccPage(false);
        showOrderFailPage(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    @OnClick({R.id.order, R.id.log_out, R.id.check_order})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.order) {
            StatService.onEvent(this, mRes.getString(R.string.event_order), mRes.getString(R.string.event_order));
            RestAdapter ra = mRestBuilder.build();
            OrderTask ot = ra.create(OrderTask.class);
            ot.order(mUser.psid, mUser.depcode, mRes.getString(R.string.order_param_type_def_val), new Callback<OrderResult>() {
                @Override
                public void success(OrderResult orderResult, Response response) {
                    showLoadingPage(false);
                    if (orderResult.succeed_count > 0) {
                        mOrderSummary = OrderSummary.SUCC;
                        showOrderSuccPage(true);
                    } else {
                        if (orderResult.exceed_count > 0) {
                            mOrderSummary = OrderSummary.EXCEED;
                        } else if (orderResult.rejected_count > 0) {
                            mOrderSummary = OrderSummary.OVER_TIME;
                        } else {
                            mOrderSummary = OrderSummary.FAIL;
                        }
                        showOrderFailPage(true);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("CDC", "error: " + error);
                    showLoadingPage(false);
                    mOrderSummary = OrderSummary.FAIL;
                    showOrderFailPage(true);
                }
            });
            showLoadingPage(true);
        } else if (id == R.id.log_out) {
            mPre.setKeyLastUserName("");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.check_order) {
            StatService.onEvent(this, mRes.getString(R.string.event_check_order), mRes.getString(R.string.event_check_order));
            Intent intent = new Intent(this, CancelOrderActivity.class);
            intent.putExtra(LoginActivity.KEY_PSID, mUser.psid);
            startActivity(intent);
        }
    }

    @Override
    public void onSucc(int statusCode, String responseBody) {
        Log.d("CDC", "response: " + responseBody);
        showLoadingPage(false);
        if (parseResult(responseBody)) {
            showOrderSuccPage(true);
        } else {
            showOrderFailPage(true);
        }
    }

    @Override
    public void onErr(int responseCode, String responseBody) {
        Log.d("CDC", "response: " + responseBody);
        showLoadingPage(false);
    }

    private boolean parseResult(String jsonObj) {
        try {
            JSONObject obj = new JSONObject(jsonObj);
            int num = obj.getInt("succeed_count");
            if (num > 0) {
                mOrderSummary = OrderSummary.SUCC;
                return true;
            }
            num = obj.getInt("exceed_count");
            if (num > 0) {
                mOrderSummary = OrderSummary.EXCEED;
                return false;
            }
            num = obj.getInt("rejected_count");
            if (num > 0) {
                mOrderSummary = OrderSummary.OVER_TIME;
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mOrderSummary = OrderSummary.FAIL;
        return false;
    }

    private void showOrderSuccPage(boolean flag) {
        if (flag) {
            showFixToast(mRes.getString(R.string.order_succ));
        }
    }

    private void showOrderFailPage(boolean flag) {
        if (flag) {
            showFixToast(String.format(mRes.getString(R.string.order_fail), mOrderSummary.getDescription(mRes)));
        }
    }

    static enum OrderSummary {
        SUCC(R.string.succ_des),
        EXCEED(R.string.exceed_des),
        OVER_TIME(R.string.overtime_des),
        FAIL(R.string.fail_des);

        private int mDescriptionID;

        OrderSummary(int des) {
            mDescriptionID = des;
        }

        public String getDescription(Resources res) {
            return res.getString(mDescriptionID);
        }
    }

    static enum OrderType {
        DINNER(R.string.order_type_dinner),
        LUNCH(R.string.order_type_lunch);

        private int mDesID;

        OrderType(int id) {
            mDesID = id;
        }

        public String getDescription(Resources res) {
            return res.getString(mDesID);
        }
    }

}
