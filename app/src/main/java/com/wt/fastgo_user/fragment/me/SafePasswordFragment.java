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
import android.widget.Toast;

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
 * Created by Administrator on 2017/10/19 0019.
 */

public class SafePasswordFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_yan_num)
    EditText etYanNum;
    @BindView(R.id.send_yan)
    TextView sendYan;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_new_num)
    EditText etNewNum;
    @BindView(R.id.button_qr)
    Button buttonQr;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_safe_password, null);
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
        activity.textTop.setText(R.string.safe_change_password);
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

    @OnClick({R.id.send_yan, R.id.button_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_yan:
                String phone = etPhoneNum.getText().toString();
                if (!phone.equals("")) {
                    sendYanZhengMa(phone);
                    sendYan.setClickable(false);
                }

                break;

            case R.id.button_qr:
                String phoneNum = etPhoneNum.getText().toString();
                String pwd = etPwd.getText().toString();
                String newPwd = etNewNum.getText().toString();
                String yan = etYanNum.getText().toString();
                if (!phoneNum.equals("") && !pwd.equals("") && !newPwd.equals("") && !yan.equals("")) {
                    savePassword(phoneNum, pwd, newPwd, yan);
                } else {
                    Toast.makeText(getActivity(), "请填写相关信息", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    private void savePassword(String phoneNum, String pwd, String newPwd, String yan) {
        if (pwd.equals(newPwd)) {
            RequestCall call = SYApplication.postFormBuilder()
                    .addParams("mobile", phoneNum)
                    .addParams("password", pwd)
                    .addParams("password2", newPwd)
                    .addParams("code", yan)
                    .url(SYApplication.path_url + "/common/user/find_password")
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
                            showShortToast("修改密码成功");
                            getActivity().finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


    private void sendYanZhengMa(String phone) {
        RequestCall call = SYApplication.postFormBuilder().addParams("", phone).url(SYApplication.path_url + "").build();
        call.buildCall(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });

        new Time(60, 123, handler).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 123:
                    int num = msg.arg1;
                    if (num != 0) {
                        sendYan.setText(num + "S");
                    } else {
                        sendYan.setText("获取验证码");
                        sendYan.setClickable(true);
                    }
                    break;
            }
        }
    };

}
