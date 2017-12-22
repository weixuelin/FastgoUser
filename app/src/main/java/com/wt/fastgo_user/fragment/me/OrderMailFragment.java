package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.CentreAdapter;
import com.wt.fastgo_user.adapter.OrderMailAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class OrderMailFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.recycler_transfer)
    RecyclerView recyclerTransfer;
    private ArrayList<HomeModel> arrayList;
    private OrderMailAdapter adapter;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_transfer, null);
        ButterKnife.bind(this, view);
        initView();
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
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.relativeTop.setVisibility(View.GONE);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }
    private void initView() {
        //设置固定大小
        recyclerTransfer.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerTransfer.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new OrderMailAdapter(getActivity(), arrayList);
        recyclerTransfer.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {
            HomeModel model = new HomeModel();
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new OrderMailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                StartUtils.startActivityById(getActivity(), -1);
            }
        });
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
