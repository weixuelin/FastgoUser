package com.wt.fastgo_user.fragment.packages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.MainPagerAdapter;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.info.BanInfo;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.Glide_Image;
import com.wt.fastgo_user.widgets.ImageCycleView;
import com.wt.fastgo_user.widgets.MyScrollView;
import com.wt.fastgo_user.widgets.ScrollViewPager;
import com.wt.fastgo_user.widgets.StartUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class LocalMainFragment extends BaseFragment implements MyScrollView.OnScrollListener {
    Unbinder unbinder;
    @BindView(R.id.relative_home_message)
    RelativeLayout relativeHomeMessage;
    @BindView(R.id.relative_home_question)
    RelativeLayout relativeHomeQuestion;
    @BindView(R.id.ad_view)
    ImageCycleView adView;
    @BindView(R.id.linear_local_detail)
    LinearLayout linearLocalDetail;
    @BindView(R.id.linear_home_been)
    LinearLayout linearHomeBeen;
    @BindView(R.id.linear_local_point)
    LinearLayout linearLocalPoint;
    @BindView(R.id.linear_local_warehouse)
    LinearLayout linearLocalWarehouse;
    @BindView(R.id.linear_home_have_to)
    LinearLayout linearHomeHaveTo;
    @BindView(R.id.text_home_content)
    TextView textHomeContent;
    @BindView(R.id.linear_home_center)
    LinearLayout linearHomeCenter;
    @BindView(R.id.include_layout)
    LinearLayout includeLayout;
    @BindView(R.id.viewpager)
    ScrollViewPager viewpager;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.view_home_prediction)
    View viewHomePrediction;
    @BindView(R.id.linear_local_prediction)
    LinearLayout linearLocalPrediction;
    @BindView(R.id.view_home_storage)
    View viewHomeStorage;
    @BindView(R.id.linear_local_storage)
    LinearLayout linearLocalStorage;
    @BindView(R.id.view_home_library)
    View viewHomeLibrary;
    @BindView(R.id.linear_local_library)
    LinearLayout linearLocalLibrary;
    @BindView(R.id.view_home_problem)
    View viewHomeProblem;
    @BindView(R.id.linear_local_problem)
    LinearLayout linearLocalProblem;
    @BindView(R.id.include_layoutq)
    LinearLayout includeLayoutq;
    @BindView(R.id.myScrollView)
    MyScrollView myScrollView;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_local, null);
        unbinder = ButterKnife.bind(this, view);
        setListener();
        isPrepared = true;
        InitViewPager();
        getBanFromUrl();
        return view;
    }

    private void getBanFromUrl() {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/index/banner/lists")
                .addParams("id", "1")
                .build();

        call.buildCall(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("wwwwww", "wwwww=====" + response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.optInt("code");
                    if (code == 200) {
                        ArrayList<BanInfo> listBan = gson.fromJson(json.optString("data"), new TypeToken<List<BanInfo>>() {
                        }.getType());

                        adView.setImageResources(listBan, new ImageCycleView.ImageCycleViewListener() {
                            @Override
                            public void displayImage(String imageURL, ImageView imageView) {
                                Glide_Image.load(getActivity(), SYApplication.path_url + imageURL, imageView);
                            }

                            @Override
                            public void onImageClick(BanInfo info, int postion, View imageView) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable) {
            return;
        }
        //填充各控件的数据
    }

    private void InitViewPager() {
        viewpager.setCurrentItem(0);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(
                getChildFragmentManager(),"") {
            //获取第position位置的Fragment
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new LocalFragment();
                        break;
                    case 1:
                        fragment = new LocalFragment();
                        break;
                    case 2:
                        fragment = new LocalFragment();
                        break;
                    case 3:
                        fragment = new LocalFragment();
                        break;
                }
                return fragment;
            }

            //该方法的返回值i表明该Adapter总共包括多少个Fragment
            @Override
            public int getCount() {
                return 4;
            }
        };
        //为ViewPager组件设置FragmentPagerAdapter
        viewpager.setAdapter(pagerAdapter);

    }

    private void setListener() {
        myScrollView.setOnScrollListener(this);
        myScrollView.smoothScrollTo(0, 20);
        //当布局的状态或者控件的可见性发生改变回调的接口
        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScroll(myScrollView.getScrollY());
            }
        });
        linearHomeCenter.setOnClickListener(this);
        linearLocalWarehouse.setOnClickListener(this);
        linearHomeHaveTo.setOnClickListener(this);
        linearHomeBeen.setOnClickListener(this);
//        linearHomeTransfer.setOnClickListener(this);
        relativeHomeQuestion.setOnClickListener(this);
        relativeHomeMessage.setOnClickListener(this);
        linearLocalLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(2);
                textHomeContent.setText(R.string.board_library_btn);
                viewHomeLibrary.setVisibility(View.VISIBLE);
                viewHomeProblem.setVisibility(View.INVISIBLE);
                viewHomeStorage.setVisibility(View.INVISIBLE);
                viewHomePrediction.setVisibility(View.INVISIBLE);
            }
        });
        linearLocalProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(3);
                textHomeContent.setText(R.string.board_problem_btn);
                viewHomeLibrary.setVisibility(View.INVISIBLE);
                viewHomeProblem.setVisibility(View.VISIBLE);
                viewHomeStorage.setVisibility(View.INVISIBLE);
                viewHomePrediction.setVisibility(View.INVISIBLE);
            }
        });
        linearLocalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
                textHomeContent.setText(R.string.board_storage_btn);
                viewHomeLibrary.setVisibility(View.INVISIBLE);
                viewHomeProblem.setVisibility(View.INVISIBLE);
                viewHomeStorage.setVisibility(View.VISIBLE);
                viewHomePrediction.setVisibility(View.INVISIBLE);
            }
        });
        linearLocalPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(0);
                textHomeContent.setText(R.string.home_prediction_btn);
                viewHomeLibrary.setVisibility(View.INVISIBLE);
                viewHomeProblem.setVisibility(View.INVISIBLE);
                viewHomeStorage.setVisibility(View.INVISIBLE);
                viewHomePrediction.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, includeLayout.getTop());
        includeLayoutq.layout(0, mBuyLayout2ParentTop, includeLayoutq.getWidth(), mBuyLayout2ParentTop + includeLayoutq.getHeight());
    }

    @Override
    protected void setActionBar() {
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("我的");
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
