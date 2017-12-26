package com.wt.fastgo_user.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.widgets.BlockDialog;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/10/10 0010.
 * 注册
 */

public class RegisterActivity extends BaseFragment {

    @BindView(R.id.edit_login_account)
    EditText editLoginAccount;
    @BindView(R.id.image_register_code)
    ImageView imageRegisterCode;
    @BindView(R.id.edit_register_code)
    EditText editRegisterCode;
    @BindView(R.id.btn_register_code)
    Button btnRegisterCode;
    @BindView(R.id.edit_login_password)
    EditText editLoginPassword;
    @BindView(R.id.text_login_forget)
    TextView textLoginForget;
    @BindView(R.id.btn_login_login)
    Button btnLoginLogin;
    @BindView(R.id.linear_login_content)
    LinearLayout linearLoginContent;
    @BindView(R.id.text_login_register)
    TextView textLoginRegister;
    Unbinder unbinder;
    @BindView(R.id.image_register_address)
    ImageView imageRegisterAddress;
    @BindView(R.id.text_register_address)
    TextView textRegisterAddress;
    @BindView(R.id.image_register_gou)
    ImageView imageRegisterGou;
    private String username, password, password2, is_agree = "1", code, country, mobile, weinx_id;
    private BlockDialog blockDialog;
    private Timer timer;
    private Handler handler_new;
    int i = 60;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.ui_register, null);
        ButterKnife.bind(this, view);
        blockDialog = new BlockDialog(getActivity());
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


    private void message() {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/common/user/register")
                .addParams("type", "1")
                .addParams("username", username)
                .addParams("password2", password2)
                .addParams("password", password)
                .addParams("is_agree", is_agree)
                .addParams("code", code)
                .addParams("country", country)
                .addParams("mobile", mobile).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                ToastUtil.show(e + "");
//                refreshLinear.setVisibility(View.VISIBLE);
//                scrollView.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                blockDialog.dismiss();
//                refreshLinear.setVisibility(View.GONE);
//
                Log.d("toby", "onResponse: " + response);
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String msg = jsonObject.getString("msg");
                    if (status) {
                        ToastUtil.show(msg);
                        getActivity().finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.register_register);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void setListener() {
        btnLoginLogin.setOnClickListener(this);
        textLoginRegister.setOnClickListener(this);
        imageRegisterGou.setOnClickListener(this);
//        homeDataCenter.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.text_login_register:
                getActivity().finish();
                break;

            case R.id.btn_login_login:  //注册
                username = editLoginAccount.getText().toString();
                password = editLoginPassword.getText().toString();
                password2 = editLoginPassword.getText().toString();
                code = editRegisterCode.getText().toString();
                country = textRegisterAddress.getText().toString();
                mobile = editLoginAccount.getText().toString();
                blockDialog.show();
                message();
                break;
            case R.id.image_register_gou:
                if (is_agree.equals("1")) {
                    is_agree = "0";
                    imageRegisterGou.setImageResource(R.drawable.register_not);
                } else {
                    is_agree = "1";
                    imageRegisterGou.setImageResource(R.drawable.register_gou);
                }
                break;
            case R.id.btn_register_code:
                mobile = editLoginAccount.getText().toString();

//                blockDialog.show();
//                runnable_code();
//
                break;

        }
    }

    private void runnable_code() {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/common/user/register")
                .addParams("type", "1")
                .addParams("username", "username")
                .addParams("password2", "password2")
                .addParams("is_agree", "is_agree")
                .addParams("code", "code")
                .addParams("country", "country")
                .addParams("mobile", "mobile").build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String message = jsonObject.getString("msg");
                    if (status == true) {
                        blockDialog.dismiss();
                        JSONObject object = jsonObject.getJSONObject("data");
                        i = object.getInt("time");
                        final int time = object.getInt("time");
                        handler_new = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.what == 1
                                        && Integer.parseInt(msg.obj.toString()) >= 0) {
                                    btnRegisterCode.setText(msg.obj.toString() + "s");
                                    btnRegisterCode.setTextColor(getResources().getColor(R.color.white));
                                    btnRegisterCode.setBackgroundResource(R.drawable.btn_greyss);
                                    btnRegisterCode.setClickable(false);
                                } else {
                                    timer.cancel();
                                    btnRegisterCode.setClickable(true);
                                    btnRegisterCode.setText("重新发送");
                                    btnRegisterCode.setTextColor(getResources().getColor(R.color.green));
                                    btnRegisterCode.setBackgroundResource(R.drawable.btn_white);
                                    i = time;
                                }
                            }
                        };
                        TimerTask task_old = new TimerTask() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                Message message = new Message();
                                message.what = 1;
                                message.obj = i;
                                i--;
                                handler_new.sendMessage(message);
                            }
                        };
                        timer = new Timer(true);
                        timer.schedule(task_old, 0, 1000);
                    } else {
                        blockDialog.dismiss();
                        ToastUtil.show(message);
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
//                                e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
