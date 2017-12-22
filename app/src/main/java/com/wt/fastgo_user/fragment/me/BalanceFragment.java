package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class BalanceFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.linear_balance_back)
    LinearLayout linearBalanceBack;
    @BindView(R.id.text_balance_detail)
    TextView textBalanceDetail;
    @BindView(R.id.btn_balance_recharge)
    Button btnBalanceRecharge;
    @BindView(R.id.btn_balance_withdrawals)
    Button btnBalanceWithdrawals;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_balance, null);
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
        linearBalanceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        textBalanceDetail.setOnClickListener(this);
        btnBalanceRecharge.setOnClickListener(this);
        btnBalanceWithdrawals.setOnClickListener(this);
    }


    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.relativeTop.setVisibility(View.GONE);
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
