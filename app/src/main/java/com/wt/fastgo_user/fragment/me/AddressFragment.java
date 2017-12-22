package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.MainPagerAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class AddressFragment extends BaseFragment {

    @BindView(R.id.text_package_been)
    TextView textPackageBeen;
    @BindView(R.id.text_package_haveto)
    TextView textPackageHaveto;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    Unbinder unbinder;
    @BindView(R.id.linear_back)
    LinearLayout linearBack;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.ui_address, null);
        ButterKnife.bind(this, view);
        setListener();
        isPrepared = true;
        InitViewPager();
        return view;
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable) {
            return;
        }

    }

    private void setListener() {
        textPackageHaveto.setOnClickListener(this);
        textPackageBeen.setOnClickListener(this);
        //填充各控件的数据
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void InitViewPager() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(
                getChildFragmentManager(),"") {
            //获取第position位置的Fragment
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new DirectmailFragment();
                        break;
                    case 1:
                        fragment = new InternationalFragment();
                        break;
                }
                return fragment;
            }

            //该方法的返回值i表明该Adapter总共包括多少个Fragment
            @Override
            public int getCount() {
                return 2;
            }
        };
        //为ViewPager组件设置FragmentPagerAdapter
        viewpager.setAdapter(pagerAdapter);

    }

    @Override
    protected void setActionBar() {
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("我的包裹");
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
        switch (v.getId()) {
            case R.id.text_package_haveto:
                viewpager.setCurrentItem(1);
                textPackageHaveto.setTextColor(getResources().getColor(R.color.white));
                textPackageBeen.setTextColor(getResources().getColor(R.color.address_grey));
                break;
            case R.id.text_package_been:
                viewpager.setCurrentItem(0);
                textPackageHaveto.setTextColor(getResources().getColor(R.color.address_grey));
                textPackageBeen.setTextColor(getResources().getColor(R.color.white));
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
