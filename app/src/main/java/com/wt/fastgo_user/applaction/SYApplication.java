package com.wt.fastgo_user.applaction;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.wt.fastgo_user.ui.LoginActivity;
import com.wt.fastgo_user.widgets.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class SYApplication extends Application {
    private static Context mContext;
    public static String path_url = "http://192.168.0.254:8092/v1";
    private static Handler mHandler;
    private static SharedPreferences sharedPreferences;
    public static String token = "", imei = "", lg = "zh";
    public static SYApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("toby", "onCreate: "+"sssssss");
        mContext = this;
        mHandler = new Handler();
        application = this;
        sharedPreferences = getSharedPreferences("userLogin",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        EMOptions options = new EMOptions();
        EMClient.getInstance().init(mContext, options);
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(this, null);
        imei = getAndroidId(this);
        setLanguage();

    }
    public static void loginOut() {
        SharedPreferences.Editor editor_logo = sharedPreferences.edit();
        editor_logo.putString("token", "");
        editor_logo.putString("time_out", "");
        editor_logo.putString("types", "");
        editor_logo.putString("type", "");
        editor_logo.commit();

    }
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void setLanguage() {
        String strs = Locale.getDefault().getLanguage();
        Locale locale = new Locale(strs);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        application.getBaseContext().getResources().updateConfiguration(config, null);
//        SharedPreferences.Editor editor_logo = sharedPreferences.edit();
//        editor_logo.putString("str", strs);
//        editor_logo.commit();
    }


    /**
     * 获取全局的context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取全局的主线程的handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

    public static PostFormBuilder postFormBuilder() {
        PostFormBuilder builder = OkHttpUtils.post();
        builder.addHeader("token", token);
        builder.addHeader("lg", lg);
        builder.addHeader("version", "1.0");
        builder.addHeader("imei", imei);
        return builder;
    }

    public static GetBuilder genericClient() {
        GetBuilder builder = OkHttpUtils.get();
        builder.addHeader("token", token);
        builder.addHeader("lg", lg);
        builder.addHeader("version", "1.0");
        builder.addHeader("imei", imei);
        return builder;
    }
}
