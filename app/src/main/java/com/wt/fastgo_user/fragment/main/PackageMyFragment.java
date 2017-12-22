package com.wt.fastgo_user.fragment.main;

import android.support.v4.app.Fragment;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BasePagerFragment;
import com.wt.fastgo_user.fragment.FragmentFactory;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.CommonUtils;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class PackageMyFragment extends BasePagerFragment {

    @Override
    protected Fragment setFragment(int position) {
        return FragmentFactory.createForPackage(position);
    }

    @Override
    protected String[] setTitles() {
        return CommonUtils.getStringArray(R.array.package_center);
    }

    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.my_order);
    }
}
