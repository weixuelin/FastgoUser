package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dingwei.pullrefresh_lib.PullToRefreshLayout;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.AddressAdapter;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.BlockDialog;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.StartUtils;
import com.wt.fastgo_user.widgets.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/19 0019.
 * 本地直邮
 */

public class DirectmailFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recycler_address)
    RecyclerView recyclerAddress;
    @BindView(R.id.refresh_btn)
    Button refreshBtn;
    @BindView(R.id.refresh_linear)
    LinearLayout refreshLinear;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    @BindView(R.id.btn_directmail_add)
    Button btnDirectmailAdd;

    private ArrayList<HomeModel> arrayList;
    private AddressAdapter adapter;
    private BlockDialog blockDialog;
    private String sign = "1";

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_directmail, null);
        ButterKnife.bind(this, view);
        setListener();
        isPrepared = true;
        blockDialog = new BlockDialog(getActivity());
        refreshBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable) {
            return;
        }
        //填充各控件的数据
        //填充各控件的数据
        blockDialog.show();
        message();
    }

    private void setListener() {
        //设置固定大小
        recyclerAddress.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerAddress.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new AddressAdapter(getActivity(), arrayList);
        recyclerAddress.setAdapter(adapter);
        btnDirectmailAdd.setOnClickListener(this);
    }

    private void message() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/user/address/lists")
                .addParams("sign", sign)
                .addParams("type", "1")
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                ToastUtil.show(e + "");
                refreshLinear.setVisibility(View.VISIBLE);
                recyclerAddress.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                blockDialog.dismiss();
                Log.d("toby", "onResponse: " + response);
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String msg = jsonObject.getString("msg");
                    if (status) {
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        JSONArray list = jsonData.getJSONArray("list");
                        if (list.length() == 0) {
                            linearNoData.setVisibility(View.VISIBLE);
                            recyclerAddress.setVisibility(View.GONE);
                        } else {
                            linearNoData.setVisibility(View.GONE);
                            recyclerAddress.setVisibility(View.VISIBLE);
                            for (int i = 0; i < list.length(); i++) {
                                
                            }
                        }

                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }


    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.relativeTop.setVisibility(View.GONE);
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
        switch (v.getId()) {
            case R.id.refresh_btn:
                blockDialog.show();
                message();
                break;
                default:
                    StartUtils.startActivityById(getActivity(), v.getId());
        }
//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
