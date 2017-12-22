package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * Created by Administrator on 2017/10/19 0019.
 */

public class AddInternationalFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.edit_address_card)
    EditText editAddressCard;
    private int num;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_address_add, null);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            num = getArguments().getInt("num");
        }
        setListener();
        isPrepared = true;
        return view;
    }

    public static AddInternationalFragment newInstance(int num) {

        Bundle args = new Bundle();
        args.putInt("num", num);
        AddInternationalFragment fragment = new AddInternationalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable) {
            return;
        }
        //填充各控件的数据
    }

    private void setListener() {
        if (num == 0) {
            editAddressCard.setHint(R.string.add_address_code);
        } else {
            editAddressCard.setHint(R.string.add_address_code_sender);
        }
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
