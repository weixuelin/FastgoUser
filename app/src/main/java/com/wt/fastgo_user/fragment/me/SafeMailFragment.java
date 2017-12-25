package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.net.Time;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 绑定邮箱
 */

public class SafeMailFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_email_yan)
    EditText etEmailYan;
    @BindView(R.id.tv_send_email)
    TextView tvSendEmail;
    @BindView(R.id.button)
    Button button;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_safe_mail, null);
        ButterKnife.bind(this, view);
        setListener();
        isPrepared = true;
        return view;
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable) {
            return;
        }
        //填充各控件的数据
    }

    private void setListener() {

    }


    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.safe_binding_email);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected Object requestData() {
        return ConstantUtils.STATE_SUCCESSED;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        StartUtils.startActivityById(getActivity(), v.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_send_email, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_email:
                String email = etEmail.getText().toString();
                if (!email.equals("") && email.contains("@")) {
                    tvSendEmail.setClickable(false);
                    sendYan(email);

                }

                break;

            case R.id.button:
                String emails = etEmail.getText().toString();
                String emailNum = etEmailYan.getText().toString();
                if (!emailNum.equals("")) {
                    save(emails, emailNum);
                }
                break;
        }
    }


    private void save(String email, String emailNum) {
        RequestCall call = SYApplication.postFormBuilder().url(SYApplication.path_url + "/user/index/bind_mail")
                .addParams("mail", email).addParams("code", emailNum)
                .build();
        call.buildCall(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.optInt("code");
                    if (code == 200) {
                        showShortToast("修改成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void sendYan(String email) {
        RequestCall call = SYApplication.postFormBuilder().url(SYApplication.path_url + "/common/user/send_mail_code").addParams("mail", email).build();
        call.buildCall(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.optInt("code");
                    if (code == 200) {
                        showShortToast("验证码发送成功，请注意查收");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        new Time(60, 234, handler);
        tvSendEmail.setClickable(false);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 234:
                    int num = msg.arg1;
                    if (num != 0) {
                        tvSendEmail.setText(num + "S");
                    } else {
                        tvSendEmail.setText("获取验证码");
                        tvSendEmail.setClickable(true);
                    }
                    break;
            }
        }
    };
}
