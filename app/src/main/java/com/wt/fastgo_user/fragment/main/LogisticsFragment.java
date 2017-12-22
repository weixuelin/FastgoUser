package com.wt.fastgo_user.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Administrator on 2017/10/11 0011.
 * 物流查询
 */

public class LogisticsFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.edit_logistics_number)
    EditText editLogisticsNumber;
    @BindView(R.id.text_logistics_business)
    TextView textLogisticsBusiness;
    @BindView(R.id.relative_logistics_business)
    RelativeLayout relativeLogisticsBusiness;
    @BindView(R.id.btn_logistics_query)
    Button btnLogisticsQuery;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_logistics, null);
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
        activity.textTop.setText(R.string.home_logistics_query);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }
    private void setListener() {
        btnLogisticsQuery.setOnClickListener(this);
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
