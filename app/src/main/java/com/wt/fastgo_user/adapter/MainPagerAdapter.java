package com.wt.fastgo_user.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wt.fastgo_user.fragment.FragmentFactory;


/**
 * Created by zs on 2016/5/9.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private String type;

    public MainPagerAdapter(FragmentManager fm,String type) {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createForMain(position,type);
    }

    @Override
    public int getCount() {
        return 5;
    }

}
