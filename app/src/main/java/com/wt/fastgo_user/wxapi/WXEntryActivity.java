package com.wt.fastgo_user.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.ui.BaseActivity;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 微信登录相关回调
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String WEIXIN_ID = "";
    private static final String WEIXIN_APPSECRET = "";
    private IWXAPI api;


    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null) {
            Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
            startActivity(iLaunchMyself);
        }
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "", false);
        api.handleIntent(getIntent(), this);


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    String openId;
    String accessToken;
    String refreshToken;


    private static OnBackText onBackText;

    public OnBackText getOnBackText() {
        return onBackText;
    }

    public static void setOnBackText(OnBackText onBackText) {
        WXEntryActivity.onBackText = onBackText;
    }

    /**
     * 获取数据回调
     */
    public interface OnBackText {
        void get(int code, String openId, String sex, String name, String icon);
    }


    /**
     * 获取用户信息
     *
     * @param accessToken token
     */
    private void getUserInfo(String accessToken, String openID) {
        String uri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openID;
        RequestCall call = SYApplication.genericClient().url(uri).build();

        call.buildCall(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json = new JSONObject((String) response);
                    String openId = json.optString("openid");
                    String name = json.optString("nickname");
                    int sexId = json.optInt("sex");
                    String icon = json.optString("headimgurl");      // 获取到微信头像数据
                    String sexName;
                    if (sexId == 1) {
                        sexName = "男";
                    } else {
                        sexName = "女";
                    }

                    if (onBackText != null) {
                        onBackText.get(0, openId, sexName, name, icon);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    @Override
    public void onReq(BaseReq baseReq) {


    }


    @Override
    public void onResp(BaseResp baseResp) {
        int errorCode = baseResp.errCode;
        switch (errorCode) {
            case BaseResp.ErrCode.ERR_OK:
                // 用户同意
                String code = ((SendAuth.Resp) baseResp).code;

                getWeiAccessToken(code);

                break;

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                // 用户拒绝
                if (onBackText != null) {
                    onBackText.get(BaseResp.ErrCode.ERR_AUTH_DENIED, "", "", "", "");
                }

                finish();

                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:

                if (onBackText != null) {
                    onBackText.get(BaseResp.ErrCode.ERR_USER_CANCEL, "", "", "", "");
                }

                // 用户取消
                finish();

                break;

            case BaseResp.ErrCode.ERR_COMM:

                finish();

                break;

            case BaseResp.ErrCode.ERR_BAN:
                if (onBackText != null) {
                    onBackText.get(BaseResp.ErrCode.ERR_BAN, "", "", "", "");
                }

                finish();

                break;


        }


    }


    /**
     * 获取 access_token
     *
     * @param code code
     */
    private void getWeiAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + WEIXIN_ID +
                "&secret=" + WEIXIN_APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";

        RequestCall call = SYApplication.genericClient().url(url).build();
        call.buildCall(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject((String) response);
                    accessToken = object.getString("access_token");
                    openId = object.getString("openid");
                    refreshToken = object.getString("refresh_token");
                    long expires_in = object.getLong("expires_in");
                    getUserInfo(accessToken, openId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
