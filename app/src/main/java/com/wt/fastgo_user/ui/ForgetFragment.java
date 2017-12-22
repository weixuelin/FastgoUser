package com.wt.fastgo_user.ui;

import android.support.v4.app.Fragment;
import android.view.View;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.fragment.FragmentFactory;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class ForgetFragment extends BaseFragment {

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.ui_register, null);
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

    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.login_find_password);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void setListener() {
//        homeOperationCenter.setOnClickListener(this);
//        homeConsumeCenter.setOnClickListener(this);
//        homeManagerCenter.setOnClickListener(this);
//        homeDataCenter.setOnClickListener(this);
    }

    @Override
    protected Object requestData() {
        return ConstantUtils.STATE_SUCCESSED;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        StartUtils.startActivityById(getActivity(), v.getId());
    }
}
