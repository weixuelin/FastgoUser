package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class RechargeFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.linear_recharge_back)
    LinearLayout linearRechargeBack;
    @BindView(R.id.image_recharge_zhifubao)
    ImageView imageRechargeZhifubao;
    @BindView(R.id.relative_recharge_zhifubao)
    RelativeLayout relativeRechargeZhifubao;
    @BindView(R.id.image_recharge_weixin)
    ImageView imageRechargeWeixin;
    @BindView(R.id.relative_recharge_weixin)
    RelativeLayout relativeRechargeWeixin;
    @BindView(R.id.image_recharge_paypal)
    ImageView imageRechargePaypal;
    @BindView(R.id.relative_recharge_paypal)
    RelativeLayout relativeRechargePaypal;
    @BindView(R.id.edit_recharge_price)
    EditText editRechargePrice;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_recharge, null);
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
        linearRechargeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        relativeRechargeZhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageRechargePaypal.setVisibility(View.GONE);
                imageRechargeWeixin.setVisibility(View.GONE);
                imageRechargeZhifubao.setVisibility(View.VISIBLE);
            }
        });
        relativeRechargePaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageRechargePaypal.setVisibility(View.VISIBLE);
                imageRechargeWeixin.setVisibility(View.GONE);
                imageRechargeZhifubao.setVisibility(View.GONE);
            }
        });
        relativeRechargeWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageRechargePaypal.setVisibility(View.GONE);
                imageRechargeWeixin.setVisibility(View.VISIBLE);
                imageRechargeZhifubao.setVisibility(View.GONE);
            }
        });
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
