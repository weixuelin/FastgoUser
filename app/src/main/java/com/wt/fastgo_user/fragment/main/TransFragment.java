package com.wt.fastgo_user.fragment.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.TranisAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class TransFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.recycler_address)
    RecyclerView recyclerAddress;
    @BindView(R.id.text_tranis_copy_name)
    TextView textTranisCopyName;
    @BindView(R.id.text_address_copy)
    TextView textAddressCopy;
    @BindView(R.id.text_center_name)
    TextView textCenterName;
    @BindView(R.id.text_center_phone)
    TextView textCenterPhone;
    @BindView(R.id.text_center_address)
    TextView textCenterAddress;
    @BindView(R.id.text_center_zip_code)
    TextView textCenterZipCode;
    private ArrayList<HomeModel> arrayList;
    private TranisAdapter adapter;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_tranis, null);
        unbinder = ButterKnife.bind(this, view);
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
        recyclerAddress.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new TranisAdapter(getActivity(), arrayList);
        recyclerAddress.setAdapter(adapter);
        TranisAdapter.num = 0;
        for (int i = 0; i < 3; i++) {
            HomeModel model = new HomeModel();
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }
        //创建线性布局
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), arrayList.size());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerAddress.setLayoutManager(mLayoutManager);
        adapter.setOnItemClickListener(new TranisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("toby", "onItemClick: " + position);
                TranisAdapter.num = position;
                adapter.notifyDataSetChanged();
            }
        });
        textTranisCopyName.setOnClickListener(this);
    }


    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.home_transfer_address);
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
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_tranis_copy_name://复制名称
                copy(getResources()
                        .getString(R.string.add_address_name_enter) +
                        "\n" + textCenterName.getText().toString());
                break;
        }
//        StartUtils.startActivityById(getActivity(), v.getId());
    }

    private void copy(String copy) {
        ClipboardManager cm = (ClipboardManager) getActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip;
        myClip = ClipData.newPlainText("text", copy);
        cm.setPrimaryClip(myClip);
        ToastUtil.show("复制成功，可以发给朋友们了。");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
