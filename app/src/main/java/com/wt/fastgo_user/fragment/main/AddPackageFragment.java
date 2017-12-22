package com.wt.fastgo_user.fragment.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
 * Created by Administrator on 2017/10/12 0012.
 * 添加转运包裹
 */

public class AddPackageFragment extends BaseFragment {

    @BindView(R.id.text_add_cost)
    TextView textAddCost;
    @BindView(R.id.edit_add_number)
    EditText editAddNumber;
    @BindView(R.id.edit_add_content)
    EditText editAddContent;
    @BindView(R.id.linear_back)
    LinearLayout linearBack;
    @BindView(R.id.text_top)
    TextView textTop;
    @BindView(R.id.relative_top)
    RelativeLayout relativeTop;
    @BindView(R.id.btn_add_sure)
    Button btnAddSure;
    private Unbinder unbinder;
    private Dialog dialog_tips;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_add_package, null);
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
        relativeTop.setBackgroundColor(getResources().getColor(R.color.transparent));
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        btnAddSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_tip();
            }
        });
    }

    private void dialog_tip() {
        LayoutInflater inflater_type = getActivity().getLayoutInflater();
        View layout_type = inflater_type.inflate(R.layout.dialog_add_success, null);
        AlertDialog.Builder record_type = new AlertDialog.Builder(getActivity());
        dialog_tips = record_type.create();
        dialog_tips.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        dialog_tips.show();
        dialog_tips.setContentView(layout_type);
        dialog_tips.setCanceledOnTouchOutside(true);//
        Button btn_dialog_add = layout_type
                .findViewById(R.id.btn_dialog_add);//
        Button btn_dialog_cancel = layout_type
                .findViewById(R.id.btn_dialog_cancel);

        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_tips.dismiss();
                getActivity().finish();
            }
        });

        btn_dialog_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editAddContent.setText("");
                editAddNumber.setText("");
                dialog_tips.dismiss();
            }
        });
    }


    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.relativeTop.setVisibility(View.GONE);
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
