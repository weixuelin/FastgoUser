package com.wt.fastgo_user.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.CommonAdapter;
import com.wt.fastgo_user.adapter.MainPagerAdapter;
import com.wt.fastgo_user.adapter.PackageAdapter;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.fragment.packages.HaveToFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.NoScrollViewPager;
import com.wt.fastgo_user.widgets.StartUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class PackageFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.linear_back)
    LinearLayout linearBack;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    String mParam1;
    @BindView(R.id.text_package_add)
    TextView textPackageAdd;
    @BindView(R.id.recycler_package)
    RecyclerView recyclerPackage;
    private PackageAdapter adapter;
    private ArrayList<String> arrayList;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.ui_package, null);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("param");
        }
        setListener();
        InitViewPager();
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

    public static PackageFragment newInstance(String text) {
        PackageFragment fragment = new PackageFragment();
        Bundle args = new Bundle();
        args.putString("param", text);
        fragment.setArguments(args);
        return fragment;
    }

    private void setListener() {
        linearBack.setOnClickListener(this);
        textPackageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUtils.startActivityById(getActivity(), view.getId());
            }
        });
        //设置固定大小
        recyclerPackage.setHasFixedSize(true);
        //创建线性布局
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerPackage.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        arrayList.add("天津仓");
        arrayList.add("南京仓");
        adapter = new PackageAdapter(getActivity(), arrayList);
        recyclerPackage.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new PackageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                PackageAdapter.num = position;
                adapter.notifyDataSetChanged();
                viewpager.setCurrentItem(position);
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void InitViewPager() {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(
                getChildFragmentManager(),"") {
            //获取第position位置的Fragment
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0://已发包裹
                        fragment = new HaveToFragment();
                        break;
                    case 1://已到包裹
                        fragment = new HaveToFragment();
                        break;
                }
                return fragment;
            }

            //该方法的返回值i表明该Adapter总共包括多少个Fragment
            @Override
            public int getCount() {
                return arrayList.size();
            }
        };

        //为ViewPager组件设置FragmentPagerAdapter
        viewpager.setAdapter(pagerAdapter);


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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_back:
                getActivity().finish();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
