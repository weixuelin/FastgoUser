package com.wt.fastgo_user.fragment.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingwei.pullrefresh_lib.PullToRefreshLayout;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.BoardAdapter;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.LoginActivity;
import com.wt.fastgo_user.ui.MainActivity;
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
 */

public class BoardFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.recycler_board)
    RecyclerView recyclerBoard;
    @BindView(R.id.text_board_add)
    TextView textBoardAdd;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @BindView(R.id.refresh_btn)
    Button refreshBtn;
    @BindView(R.id.refresh_linear)
    LinearLayout refreshLinear;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    private ArrayList<HomeModel> arrayList;
    private BoardAdapter adapter;
    private BlockDialog blockDialog;
    private String pageSize = "10";
    private int pageNo = 1, position;
    private String id;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_board, null);
        ButterKnife.bind(this, view);
        setListener();
        isPrepared = true;
        loadData();
        return view;
    }


    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable) {
            return;
        }
        //填充各控件的数据
        arrayList.clear();
        blockDialog.show();
        message();
    }

    private void setListener() {
        //设置固定大小
        recyclerBoard.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerBoard.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new BoardAdapter(getActivity(), arrayList, clickListener);
        recyclerBoard.setAdapter(adapter);
        textBoardAdd.setOnClickListener(this);
        blockDialog = new BlockDialog(getActivity());
        refreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pageNo = 1;
                // 下拉刷新操作
                arrayList.clear();
                message();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pageNo++;
                message();
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linear_board_like:
                    position = (Integer) view.getTag();
                    id = arrayList.get(position).getId();
                    blockDialog.show();
                    if (arrayList.get(position).is_like()) {
                        message_cancel();
                    } else {
                        message_like();
                    }
                    break;
            }
        }
    };

    private void message_like() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/common/circle/like")
                .addParams("id", id).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                ToastUtil.show(e + "");
            }

            @Override
            public void onResponse(String response, int id) {
                blockDialog.dismiss();
                Log.i("toby", "onResponse: "+response);
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String msg = jsonObject.getString("msg");
                    if (status) {
                        int num;
                        num = arrayList.get(position).getNum() + 1;
                        arrayList.get(position).setIs_like(true);
                        arrayList.get(position).setNum(num);
                        adapter.notifyDataSetChanged();
                        ToastUtil.show(msg);
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void message_cancel() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/common/circle/del_like")
                .addParams("id", id).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                ToastUtil.show(e + "");
            }

            @Override
            public void onResponse(String response, int id) {
                blockDialog.dismiss();
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String msg = jsonObject.getString("msg");
                    if (status) {
                        int num;
                        num = arrayList.get(position).getNum() - 1;
                        arrayList.get(position).setIs_like(false);
                        arrayList.get(position).setNum(num);
                        adapter.notifyDataSetChanged();
                        ToastUtil.show(msg);
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void message() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/index/circle/lists")
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", pageSize).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                refreshView.refreshFinish(PullToRefreshLayout.FAIL);
                refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
                refreshView.setVisibility(View.GONE);
                refreshLinear.setVisibility(View.VISIBLE);
                ToastUtil.show(e + "");
            }

            @Override
            public void onResponse(String response, int id) {
                blockDialog.dismiss();
                Log.d("toby", "onResponse: " + response);
                refreshView.setVisibility(View.VISIBLE);
                refreshLinear.setVisibility(View.GONE);
                refreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String msg = jsonObject.getString("msg");
                    if (status) {
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        JSONArray list = jsonData.getJSONArray("list");
                        if (list.length() == 0){
                            if (pageNo == 1){
                                refreshView.setVisibility(View.GONE);
                                linearNoData.setVisibility(View.VISIBLE);
                            }
                        }
                        for (int i = 0; i < list.length(); i++) {
                            HomeModel model = new HomeModel();
                            String description = list.getJSONObject(i).getString("description");
                            String ids = list.getJSONObject(i).getString("id");
                            String created = list.getJSONObject(i).getString("created");
                            boolean is_like = list.getJSONObject(i).getBoolean("is_like");
                            int like = list.getJSONObject(i).getInt("like");
                            JSONObject user = list.getJSONObject(i).getJSONObject("user");
                            String nickname = user.getString("nickname");
                            String avatar = user.getString("avatar");
                            model.setAvatar(avatar);
                            model.setContent(description);
                            model.setId(ids);
                            model.setIs_like(is_like);
                            model.setNickname(nickname);
                            model.setTime(created);
                            model.setNum(like);
                            String ablum = list.getJSONObject(i).getString("ablum");
                            model.setAblum(ablum);
                            arrayList.add(model);
                            adapter.notifyDataSetChanged();
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
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("在线咨询");
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
