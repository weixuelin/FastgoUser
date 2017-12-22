package com.wt.fastgo_user.ui;

import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseBaseActivity;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class ChatBaseActivity extends EaseBaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
//        MobclickAgent.onPause(this);
    }
}
