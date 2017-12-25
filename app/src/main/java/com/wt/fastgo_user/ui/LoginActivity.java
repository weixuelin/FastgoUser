package com.wt.fastgo_user.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.widgets.BlockDialog;
import com.wt.fastgo_user.widgets.StartUtils;
import com.wt.fastgo_user.widgets.ToastUtil;
import com.wt.fastgo_user.wxapi.WXEntryActivity;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class LoginActivity extends BaseActivity {
    private static final java.lang.String WEIXIN_ID = "";

    @BindView(R.id.edit_login_account)
    EditText editLoginAccount;
    @BindView(R.id.edit_login_password)
    EditText editLoginPassword;
    @BindView(R.id.text_login_forget)
    TextView textLoginForget;
    @BindView(R.id.btn_login_login)
    Button btnLoginLogin;
    @BindView(R.id.linear_login_content)
    LinearLayout linearLoginContent;
    @BindView(R.id.text_login_quick)
    TextView textLoginQuick;
    @BindView(R.id.linear_login_facebook)
    LinearLayout linearLoginFacebook;
    @BindView(R.id.text_login_register)
    TextView textLoginRegister;
    @BindView(R.id.text_login_type)
    TextView textLoginType;
    @BindView(R.id.linear_login_type)
    LinearLayout linearLoginType;
    @BindView(R.id.weixin_login)
    ImageView weiXinLogin;
    private String account = "", password = "", types = "1";
    private BlockDialog blockDialog;
    private SharedPreferences loginpPreferences;
    private String token = "", str = "", type = "1";
    private Dialog dialog_tips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_login);
        ButterKnife.bind(this);
        loginpPreferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);

        token = loginpPreferences.getString("token", "1234");

        types = loginpPreferences.getString("type", "");
        str = loginpPreferences.getString("str", "");
        blockDialog = new BlockDialog(this);

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockDialog.show();

                account = editLoginAccount.getText().toString();
                password = editLoginPassword.getText().toString();

                message();

//                EMClient.getInstance().login(editLoginAccount.getText().toString(), editLoginPassword.getText().toString(), new EMCallBack() {//回调
//                    @Override
//                    public void onSuccess() {
//                        blockDialog.dismiss();
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                        EMClient.getInstance().chatManager().loadAllConversations();

//                        Log.d("toby", "登录聊天服务器成功！");
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//
//                    @Override
//                    public void onError(int code, String message) {
//                        blockDialog.dismiss();
//                        Log.d("toby", "登录聊天服务器失败！");
//                    }
//                });

            }
        });

        if (!token.equals("")) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        textLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUtils.startActivityById(LoginActivity.this, view.getId());
            }
        });

        linearLoginType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_tip();
            }
        });

        weiXinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinLogin();
            }
        });
    }


    private void message() {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/common/user/login")
                .addParams("type", "1")
                .addParams("account", account)
                .addParams("password", password)
                .addParams("type", type).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                ToastUtil.show(e + "");
            }

            @Override
            public void onResponse(String response, int id) {
                blockDialog.dismiss();
                Log.d("toby", "onResponse: " + response);
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String msg = jsonObject.getString("msg");
                    if (status) {
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        String token = jsonData.getString("token");
                        SharedPreferences.Editor editor_logo = loginpPreferences.edit();
                        editor_logo.putString("token", token);
                        editor_logo.putString("type", type);
                        editor_logo.commit();
                        ToastUtil.show(msg);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void dialog_tip() {
        LayoutInflater inflater_type = getLayoutInflater();
        View layout_type = inflater_type.inflate(R.layout.dialog_type, null);
        layout_type.setMinimumWidth(10000);
        AlertDialog.Builder record_type = new AlertDialog.Builder(this, R.style.dialog);
        dialog_tips = record_type.create();
        dialog_tips.show();
        dialog_tips.setContentView(layout_type);
        dialog_tips.setCanceledOnTouchOutside(true);//
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog_tips.getWindow()
                .getAttributes();
        lp.width = (display.getWidth()); //
        lp.y = display.getHeight();
        dialog_tips.getWindow().setAttributes(lp);
        final TextView text_login_common = layout_type
                .findViewById(R.id.text_login_common);//
        final TextView text_login_shop = layout_type
                .findViewById(R.id.text_login_shop);//
        final TextView text_login_cancel = layout_type
                .findViewById(R.id.text_login_cancel);//
        text_login_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_tips.dismiss();
            }
        });
        text_login_common.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                type = "1";
                textLoginType.setText(text_login_common.getText().toString());
                dialog_tips.dismiss();
            }
        });
        text_login_shop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                type = "2";
                textLoginType.setText(text_login_shop.getText().toString());
                dialog_tips.dismiss();
            }
        });
    }


    IWXAPI iwxapi;


    private void weixinLogin() {
        if (checkApkExist("com.tencent.mm")) {
            iwxapi = WXAPIFactory.createWXAPI(getApplicationContext(), WEIXIN_ID, false);
            iwxapi.registerApp(WEIXIN_ID);
            // 绑定 登录回调信息
            WXEntryActivity.setOnBackText(new WXEntryActivity.OnBackText() {
                @Override
                public void get(int code, String openId, String sex, String name, String icon) {
                    if (code == 0) {
                        if (!openId.equals("") && !sex.equals("") && !name.equals("")) {
                            // 绑定登录的信息
                        }
                    } else if (code == BaseResp.ErrCode.ERR_USER_CANCEL) {
                        showToastShort("用户取消微信登录");
                    } else if (code == BaseResp.ErrCode.ERR_BAN) {

                        showToastShort("微信授权失败，请使用其他方式登录");
                    } else if (code == BaseResp.ErrCode.ERR_AUTH_DENIED) {

                        showToastShort("用户拒绝登录，请重新登录");
                    }
                }
            });

            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = getPackageName();
            iwxapi.sendReq(req);

        } else {
            showToastShort("请安装微信app");
        }
    }

}
