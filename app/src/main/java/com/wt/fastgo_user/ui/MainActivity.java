package com.wt.fastgo_user.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.MainPagerAdapter;
import com.wt.fastgo_user.widgets.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.bottom_main)
    RadioButton bottomMain;
    @BindView(R.id.bottom_park)
    RadioButton bottomPark;
    @BindView(R.id.bottom_service)
    RadioButton bottomService;
    @BindView(R.id.bottom_message)
    RadioButton bottomMessage;
    @BindView(R.id.bottom_me)
    RadioButton bottomMe;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private MainPagerAdapter adapter;
    private String type = "";
    private SharedPreferences loginpPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        loginpPreferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        type = loginpPreferences.getString("type", "");
        adapter = new MainPagerAdapter(getSupportFragmentManager(),type);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(5);
        viewpager.setCurrentItem(0);
        bottomMain.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bottom_main:
                        viewpager.setCurrentItem(0, false);
                        break;
                    case R.id.bottom_park:
                        viewpager.setCurrentItem(1, false);
                        break;
                    case R.id.bottom_message:
                        viewpager.setCurrentItem(2, false);
                        break;
                    case R.id.bottom_service:
                        viewpager.setCurrentItem(3, false);
                        break;
                    case R.id.bottom_me:
                        viewpager.setCurrentItem(4, false);
                        break;

                }
            }
        });
    }
}
