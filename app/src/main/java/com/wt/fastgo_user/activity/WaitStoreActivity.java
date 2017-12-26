package com.wt.fastgo_user.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.info.OrderInfo;
import com.wt.fastgo_user.newadapter.AdapterCallBack;
import com.wt.fastgo_user.newadapter.WaitAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 本地直邮代收点
 */

public class WaitStoreActivity extends ProActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.index_address)
    TextView indexAddress;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<OrderInfo> list;
    List<OrderInfo> morelist;
    WaitAdapter adapter;

    @Override
    public int getContextId() {
        return R.layout.wait_store_layout;
    }

    @Override
    public void initAllMembersView(Bundle bundle) {
        list = new ArrayList<>();
        morelist = new ArrayList<>();
        initAdapter();

    }

    @Override
    public void handler(Message msg) {


    }


    public void initAdapter() {
        for (int i = 0; i < 5; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setCheck(false);
            list.add(orderInfo);
        }
        morelist.addAll(list);

        adapter = new WaitAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadData(new WaitAdapter.OnLoadData() {
            @Override
            public void onLoad(int p) {
                // 显示详细信息
                getDetailsFromUrl(p, list.get(p));
            }
        });

    }

    private void getDetailsFromUrl(int p, OrderInfo orderInfo) {

//        SYApplication.postFormBuilder().url(SYApplication.path_url+"").addParams("id",String.valueOf(orderInfo.getId())).build().buildCall(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//
//            }
//        });

        showDataDetails(p);

    }


    private void showDataDetails(int p) {
        int len = morelist.size();
        for (int i = 0; i < len; i++) {
            if (i == p) {
                OrderInfo orderInfo = morelist.get(i);
                orderInfo.setCheck(true);
                orderInfo.setInfo(new OrderInfo.Info());
            }
        }

        adapter.update(morelist, new AdapterCallBack<OrderInfo>(list, morelist) {
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return list.get(oldItemPosition).getId() == morelist.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                Bundle bundle = new Bundle();
                OrderInfo info = morelist.get(newItemPosition);
                bundle.putParcelable("name", info);
                return bundle;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.image_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.image_search:
                String str = etSearch.getText().toString();
                if (str.equals("")) {
                    return;
                }
                search(str);
                break;
        }
    }


    private void search(String str) {


    }

}
