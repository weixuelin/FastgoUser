package com.wt.fastgo_user.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.widgets.BackHandledInterface;


public class BaseActivity extends AppCompatActivity implements BackHandledInterface {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }


    private BaseFragment mBackHandedFragment;

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

}
