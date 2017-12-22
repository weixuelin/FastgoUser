package com.wt.fastgo_user.fragment.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class ShopFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.image_me_setting)
    ImageView imageMeSetting;
    @BindView(R.id.linear_me_balance)
    RelativeLayout linearMeBalance;
    @BindView(R.id.relative_shop_order)
    RelativeLayout relativeShopOrder;
    @BindView(R.id.relative_my_statistics)
    RelativeLayout relativeMyStatistics;
    @BindView(R.id.relative_shop_supplies)
    RelativeLayout relativeShopSupplies;
    @BindView(R.id.relative_shop_consume)
    RelativeLayout relativeShopConsume;
    @BindView(R.id.relative_my_safe)
    RelativeLayout relativeMySafe;
    @BindView(R.id.relative_my_feedback)
    RelativeLayout relativeMyFeedback;
    @BindView(R.id.relative_my_about)
    RelativeLayout relativeMyAbout;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_shop, null);
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
        imageMeSetting.setOnClickListener(this);
        linearMeBalance.setOnClickListener(this);
        relativeMyFeedback.setOnClickListener(this);
        relativeMySafe.setOnClickListener(this);
        relativeMyStatistics.setOnClickListener(this);
        relativeShopOrder.setOnClickListener(this);
        relativeShopSupplies.setOnClickListener(this);
        relativeShopConsume.setOnClickListener(this);
    }

    @Override
    protected void setActionBar() {
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("我的");
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
}
