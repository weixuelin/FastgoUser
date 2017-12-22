package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Created by Administrator on 2017/10/19 0019.
 */

public class WithdrawalsFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.linear_with_back)
    LinearLayout linearWithBack;
    @BindView(R.id.image_with_zhifubao)
    ImageView imageWithZhifubao;
    @BindView(R.id.relative_with_zhifubao)
    RelativeLayout relativeWithZhifubao;
    @BindView(R.id.image_with_weixin)
    ImageView imageWithWeixin;
    @BindView(R.id.relative_with_weixin)
    RelativeLayout relativeWithWeixin;
    @BindView(R.id.image_with_paypal)
    ImageView imageWithPaypal;
    @BindView(R.id.relative_with_paypal)
    RelativeLayout relativeWithPaypal;
    @BindView(R.id.edit_with_price)
    EditText editWithPrice;
    @BindView(R.id.text_with_balance)
    TextView textWithBalance;
    @BindView(R.id.text_with_all)
    TextView textWithAll;
    @BindView(R.id.btn_with_sure)
    Button btnWithSure;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_withdrawals, null);
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
        linearWithBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        relativeWithZhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageWithPaypal.setVisibility(View.GONE);
                imageWithWeixin.setVisibility(View.GONE);
                imageWithZhifubao.setVisibility(View.VISIBLE);
            }
        });
        relativeWithPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageWithPaypal.setVisibility(View.VISIBLE);
                imageWithWeixin.setVisibility(View.GONE);
                imageWithZhifubao.setVisibility(View.GONE);
            }
        });
        relativeWithWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageWithPaypal.setVisibility(View.GONE);
                imageWithWeixin.setVisibility(View.VISIBLE);
                imageWithZhifubao.setVisibility(View.GONE);
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
