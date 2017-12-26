package com.wt.fastgo_user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.FragmentFactory;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 所有能点击的按钮全部跳转到这个页面
 * Created by zs on 2015/11/3.
 */
public class ClickButtonActivity extends BaseActivity {

    FragmentManager fm;
    public Intent intent;
    public FragmentTransaction ft;
    public int resId;
    public String id;
    @BindView(R.id.fl_click_button)
    public FrameLayout flClickButton;
    @BindView(R.id.linear_back)
    public LinearLayout linearBack;
    @BindView(R.id.text_top)
    public TextView textTop;
    @BindView(R.id.relative_top)
    public RelativeLayout relativeTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_click_button);
        ButterKnife.bind(this);
        // 获取传递过来的资源id值
        intent = getIntent();

        resId = intent.getIntExtra("resId", 0);
        if (intent.getExtras() != null) {
            resId = intent.getExtras().getInt("resId");
        }
        // 这里需要传递其他值可以自己定义
        id = intent.getStringExtra("id");
        /**
         * 根据传递过来的不同的资源id值设置不同的fragment
         */
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_click_button, FragmentFactory.createByLogin(resId,id));
        ft.commit();

        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickButtonActivity.this.finish();
            }
        });
    }
}
