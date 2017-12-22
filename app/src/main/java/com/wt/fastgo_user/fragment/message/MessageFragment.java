package com.wt.fastgo_user.fragment.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hyphenate.easeui.EaseConstant;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.ui.ChatActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.linear_message_tianjin)
    LinearLayout linearMessageTianjin;
    @BindView(R.id.linear_message_nanjing)
    LinearLayout linearMessageNanjing;
    @BindView(R.id.linear_message_guangzhou)
    LinearLayout linearMessageGuangzhou;
    Unbinder unbinder;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_message, null);
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

        linearMessageGuangzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, "13896568031");
                startActivity(intent);
            }
        });
        linearMessageNanjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, "13896568031");
                startActivity(intent);
            }
        });
        linearMessageTianjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, "13896568031");
                startActivity(intent);
            }
        });
//        homeOperationCenter.setOnClickListener(this);
//        homeConsumeCenter.setOnClickListener(this);
//        homeManagerCenter.setOnClickListener(this);
//        homeDataCenter.setOnClickListener(this);
    }

    @Override
    protected void setActionBar() {
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("在线咨询");
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
