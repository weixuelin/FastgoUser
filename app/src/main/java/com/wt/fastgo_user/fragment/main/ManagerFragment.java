package com.wt.fastgo_user.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.MainPagerAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.SYViewPager;
import com.wt.fastgo_user.widgets.StartUtils;
import com.wt.fastgo_user.widgets.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class ManagerFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.linear_back)
    LinearLayout linearBack;
    @BindView(R.id.viewpager)
    SYViewPager viewpager;
    @BindView(R.id.text_manager_time)
    TextView textManagerTime;
    @BindView(R.id.linear_manager_time)
    LinearLayout linearManagerTime;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_manager, null);
        ButterKnife.bind(this, view);
        setListener();
        InitViewPager();
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

    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.home_logistics_query);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void InitViewPager() {
        viewpager.setCurrentItem(0);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(
                getChildFragmentManager(),"") {
            //获取第position位置的Fragment
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new OrderManagerFragment();
                        break;
                }
                return fragment;
            }

            //该方法的返回值i表明该Adapter总共包括多少个Fragment
            @Override
            public int getCount() {
                return 1;
            }
        };
        //为ViewPager组件设置FragmentPagerAdapter
        viewpager.setAdapter(pagerAdapter);

    }

    private void setListener() {
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        linearManagerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.alertTimerPicker_start(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY, "yyyy-MM-dd", new Util.TimerPickerCallBacks() {
                    @Override
                    public void onTimeSelect(String date) {
                        textManagerTime.setText(date);
                    }
                });
            }
        });
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
}
