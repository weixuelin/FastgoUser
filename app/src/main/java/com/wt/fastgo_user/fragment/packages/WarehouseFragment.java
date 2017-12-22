package com.wt.fastgo_user.fragment.packages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.WarehouseAdapter;
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
 * Created by Administrator on 2017/12/21 0021.
 */

public class WarehouseFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.text_board_add)
    TextView textBoardAdd;
    @BindView(R.id.recycler_transfer)
    RecyclerView recyclerTransfer;
    private ArrayList<HomeModel> arrayList;
    private WarehouseAdapter adapter;
    private Dialog dialog_tips,dialog_success;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_house, null);
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
        activity.textTop.setText(R.string.package_warehouse);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void initView() {
        //设置固定大小
        recyclerTransfer.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerTransfer.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new WarehouseAdapter(getActivity(), arrayList);
        recyclerTransfer.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {
            HomeModel model = new HomeModel();
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }
        textBoardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_tip();
            }
        });
    }

    private void dialog_tip() {
        LayoutInflater inflater_type = getActivity().getLayoutInflater();
        View layout_type = inflater_type.inflate(R.layout.dialog_add_goods, null);
        AlertDialog.Builder record_type = new AlertDialog.Builder(getActivity());
        dialog_tips = record_type.create();
        dialog_tips.show();
        dialog_tips.setContentView(layout_type);
        dialog_tips.setCanceledOnTouchOutside(false);//
        Button btn_add_sure = layout_type
                .findViewById(R.id.btn_add_sure);//
        ImageView image_dialog_close = layout_type
                .findViewById(R.id.image_dialog_close);//
        dialog_tips.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        image_dialog_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_tips.dismiss();
            }
        });

        btn_add_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_success();
                dialog_tips.dismiss();
            }
        });
    }

    private void dialog_success() {
        LayoutInflater inflater_type = getActivity().getLayoutInflater();
        View layout_type = inflater_type.inflate(R.layout.dialog_add_success, null);
        AlertDialog.Builder record_type = new AlertDialog.Builder(getActivity());
        dialog_success = record_type.create();
        dialog_success.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        dialog_success.show();
        dialog_success.setContentView(layout_type);
        dialog_success.setCanceledOnTouchOutside(true);//
        Button btn_dialog_add = layout_type
                .findViewById(R.id.btn_dialog_add);//
        Button btn_dialog_cancel = layout_type
                .findViewById(R.id.btn_dialog_cancel);

        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_success.dismiss();
            }
        });

        btn_dialog_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_success.dismiss();
                dialog_tip();
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
