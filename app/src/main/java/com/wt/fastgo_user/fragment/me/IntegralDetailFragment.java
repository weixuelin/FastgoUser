package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.BalanceDetailAdapter;
import com.wt.fastgo_user.adapter.IntegralDetailAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class IntegralDetailFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.linear_detail_back)
    LinearLayout linearDetailBack;
    @BindView(R.id.recycler_detail)
    RecyclerView recyclerDetail;
    private ArrayList<HomeModel> arrayList;
    private IntegralDetailAdapter adapter;


    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.integral_detail, null);
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
        //设置固定大小
        recyclerDetail.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerDetail.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new IntegralDetailAdapter(getActivity(), arrayList);
        recyclerDetail.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {
            HomeModel model = new HomeModel();
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }
        linearDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
//        linearMeCoupon.setOnClickListener(this);
//        relativeMeAddress.setOnClickListener(this);
//        homeDataCenter.setOnClickListener(this);
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
