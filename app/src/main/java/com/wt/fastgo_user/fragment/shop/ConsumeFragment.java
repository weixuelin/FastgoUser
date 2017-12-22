package com.wt.fastgo_user.fragment.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.ConsumeAdapter;
import com.wt.fastgo_user.adapter.StatisticsAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/18 0018.
 * 包裹统计
 */

public class ConsumeFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recycler_statistics)
    RecyclerView recyclerStatistics;
    @BindView(R.id.linear_statistics_time)
    LinearLayout linearStatisticsTime;
    @BindView(R.id.text_statistics_time)
    TextView textStatisticsTime;
    private ArrayList<HomeModel> arrayList;
    private ConsumeAdapter adapter;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_consume, null);
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
        recyclerStatistics.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerStatistics.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new ConsumeAdapter(getActivity(), arrayList);
        recyclerStatistics.setAdapter(adapter);
        for (int i = 0; i < 7; i++) {
            HomeModel model = new HomeModel();
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }
        linearStatisticsTime.setOnClickListener(this);
    }


    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.my_consume);
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
        switch (v.getId()) {
            case R.id.linear_statistics_time:
                Util.alertTimerPicker(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY, "yyyy-MM-dd", new Util.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        textStatisticsTime.setText(date);
                    }
                });
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
