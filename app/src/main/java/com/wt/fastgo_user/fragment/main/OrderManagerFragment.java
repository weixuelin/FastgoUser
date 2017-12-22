package com.wt.fastgo_user.fragment.main;

import android.support.v4.app.Fragment;
import android.view.View;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BasePagerFragment;
import com.wt.fastgo_user.fragment.FragmentFactory;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.CommonUtils;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class OrderManagerFragment extends BasePagerFragment {

    @Override
    protected Fragment setFragment(int position) {
        return FragmentFactory.createForManager(position);
    }

    @Override
    protected String[] setTitles() {
        return CommonUtils.getStringArray(R.array.manager_center);
    }

    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.relativeTop.setVisibility(View.GONE);
    }

}
