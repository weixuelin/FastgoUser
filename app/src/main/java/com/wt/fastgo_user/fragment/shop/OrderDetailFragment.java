package com.wt.fastgo_user.fragment.shop;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.ImageAdapter;
import com.wt.fastgo_user.adapter.OrderAdapter;
import com.wt.fastgo_user.adapter.OrderMailAdapter;
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
 * Created by Administrator on 2017/12/19 0019.
 */

public class OrderDetailFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.recycler_order_detail)
    RecyclerView recyclerOrderDetail;
    @BindView(R.id.recycler_order_image)
    RecyclerView recyclerOrderImage;
    private ImageAdapter imageAdapter;
    private ArrayList<String> stringArrayList;
    private ArrayList<HomeModel> arrayList;
    private OrderAdapter adapter;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.order_detail, null);
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
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.add_add_detail);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void initView() {
        //设置固定大小
        recyclerOrderDetail.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerOrderDetail.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new OrderAdapter(getActivity(), arrayList);
        recyclerOrderDetail.setAdapter(adapter);
        for (int i = 0; i < 2; i++) {
            HomeModel model = new HomeModel();
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }

        stringArrayList = new ArrayList<>();
        //设置固定大小
        recyclerOrderImage.setHasFixedSize(true);
        //创建线性布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerOrderImage.setLayoutManager(gridLayoutManager);
        imageAdapter = new ImageAdapter(getActivity(), stringArrayList);
        recyclerOrderImage.setAdapter(imageAdapter);
        for (int i = 0; i < 3; i++) {
            stringArrayList.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1513662774&di=f1c95b03e732918f96056a3910a6e0ed&src=http://imgsrc.baidu.com/imgad/pic/item/3801213fb80e7beca9004ec5252eb9389b506b38.jpg");

        }
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
