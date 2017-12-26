package com.wt.fastgo_user.fragment.me;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BasePagerFragment;
import com.wt.fastgo_user.fragment.FragmentFactory;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.CommonUtils;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class AddAddressFragment extends BasePagerFragment {

    @Override
    protected Fragment setFragment(int position) {
        return FragmentFactory.createForAddress(position,"");
    }

    @Override
    protected String[] setTitles() {
        return CommonUtils.getStringArray(R.array.address_center);
    }

    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.textTop.setText(R.string.add_add_address);
    }
}
