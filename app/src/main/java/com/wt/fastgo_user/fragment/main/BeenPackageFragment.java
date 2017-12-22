package com.wt.fastgo_user.fragment.main;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.fragment.BasePagerFragment;
import com.wt.fastgo_user.fragment.FragmentFactory;
import com.wt.fastgo_user.widgets.CommonUtils;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class BeenPackageFragment extends BasePagerFragment {

    @Override
    protected Fragment setFragment(int position) {
        return FragmentFactory.createForConsume(position);
    }

    @Override
    protected String[] setTitles() {
        return CommonUtils.getStringArray(R.array.consume_center);
    }

    @Override
    protected void setActionBar() {
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("消费中心");
    }

}
