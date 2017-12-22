package com.wt.fastgo_user.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.ConsumeAdapter;
import com.wt.fastgo_user.adapter.JoinFlowAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.FlowTrace;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class LogisticsResultFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recycler_result)
    RecyclerView recyclerResult;
    @BindView(R.id.btn_result_details)
    Button btnResultDetails;
    private ArrayList<FlowTrace> arrayList;
    private JoinFlowAdapter adapter;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_result, null);
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
        activity.textTop.setText(R.string.home_logistics_result);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void setListener() {
        //设置固定大小
        recyclerResult.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerResult.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        initData();
        btnResultDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUtils.startActivityById(getActivity(), -1);
            }
        });
    }

    private void initData() {
        // 模拟一些假的数据
        arrayList.add(new FlowTrace(" "," ","收件地址"));
        arrayList.add(new FlowTrace("今天","12:30","收件地址收件地址收件地址"));
        arrayList.add(new FlowTrace("昨天","11:30","收件地址收件地址"));
        arrayList.add(new FlowTrace("10-01","00:30","收件地址收件地址收件地址"));
        arrayList.add(new FlowTrace("09-28","17:25","收件地址"));
        arrayList.add(new FlowTrace("09-26","13:54","发件地址"));
        adapter = new JoinFlowAdapter(getActivity(), arrayList);
        recyclerResult.setAdapter(adapter);
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
