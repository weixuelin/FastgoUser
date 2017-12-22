package com.wt.fastgo_user.fragment.service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ServiceFragment extends BaseFragment {

    @BindView(R.id.relative_service_common)
    RelativeLayout relativeServiceCommon;
    @BindView(R.id.relative_service_introduction)
    RelativeLayout relativeServiceIntroduction;
    @BindView(R.id.relative_service_offer)
    RelativeLayout relativeServiceOffer;
    @BindView(R.id.relative_service_rate)
    RelativeLayout relativeServiceRate;
    Unbinder unbinder;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_service, null);
        unbinder = ButterKnife.bind(this, view);
        setListener();
        isPrepared = true;
        Log.i("toby", "getSuccessView: ");
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
        relativeServiceCommon.setOnClickListener(this);
//        homeConsumeCenter.setOnClickListener(this);
//        homeManagerCenter.setOnClickListener(this);
//        homeDataCenter.setOnClickListener(this);
    }


    @Override
    protected void setActionBar() {
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("服务");
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
