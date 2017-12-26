package com.wt.fastgo_user.fragment.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.AddressAdapter;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.ui.LoginActivity;
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
 * 国际转运
 */

public class InternationalFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recycler_address)
    RecyclerView recyclerAddress;
    @BindView(R.id.btn_address_add)
    Button btnAddressAdd;
    @BindView(R.id.refresh_btn)
    Button refreshBtn;
    @BindView(R.id.refresh_linear)
    LinearLayout refreshLinear;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    private ArrayList<HomeModel> arrayList;
    private AddressAdapter adapter;
    private BlockDialog blockDialog;
    private int sign = 1;
    private String id;
    private int position;
    private Dialog dialog_tips;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_address, null);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            sign = getArguments().getInt("num");
            id = getArguments().getString("id");
        }
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
        arrayList.clear();
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
        adapter = new AddressAdapter(getActivity(), arrayList, onClickListener);
        recyclerAddress.setAdapter(adapter);
        btnAddressAdd.setOnClickListener(this);
    }

    public static InternationalFragment newInstance(int sign, String id) {

        Bundle args = new Bundle();
        args.putInt("num", sign);
        args.putString("id", id);
        InternationalFragment fragment = new InternationalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void message() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/user/address/lists")
                .addParams("sign", sign + "")
                .addParams("type", "2")
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
                                String name = list.getJSONObject(i).getString("name");
                                String mobile = list.getJSONObject(i).getString("mobile");
                                String address = list.getJSONObject(i).getString("address");
                                String province = list.getJSONObject(i).getString("province");
                                String city = list.getJSONObject(i).getString("city");
                                String is_default = list.getJSONObject(i).getString("is_default");
                                String ids = list.getJSONObject(i).getString("id");
                                HomeModel model = new HomeModel();
                                model.setId(ids);
                                model.setName(name);
                                model.setAddress(province + city + address);
                                model.setMobile(mobile);
                                model.setContent(is_default);
                                arrayList.add(model);
                                adapter.notifyDataSetChanged();
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linear_address_edit:
                    position = (Integer) view.getTag();
                    id = arrayList.get(position).getId();
                    StartUtils.startActivityByIds(getActivity(), view.getId(), id);
                    break;
                case R.id.linear_address_delete://删除
                    position = (Integer) view.getTag();
                    id = arrayList.get(position).getId();
                    dialog_tip(1);
                    break;
                case R.id.linear_address_default:
                    position = (Integer) view.getTag();
                    id = arrayList.get(position).getId();
                    dialog_tip(2);
                    break;


            }
        }
    };

    private void dialog_tip(final int num) {
        LayoutInflater inflater_type = getActivity().getLayoutInflater();
        View layout_type = inflater_type.inflate(R.layout.dialog_login, null);
        AlertDialog.Builder record_type = new AlertDialog.Builder(getActivity());
        dialog_tips = record_type.create();
        dialog_tips.show();
        dialog_tips.setContentView(layout_type);
        dialog_tips.setCanceledOnTouchOutside(false);//
        Button btn_dialog_sure = layout_type
                .findViewById(R.id.btn_dialog_sure);
        Button btn_dialog_cancel = layout_type
                .findViewById(R.id.btn_dialog_cancel);
        TextView text_content = layout_type
                .findViewById(R.id.text_content);
        if (num == 1) {
            text_content.setText(R.string.prompt_delete);
        } else
            text_content.setText(R.string.prompt_default);
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_tips.dismiss();
            }
        });

        btn_dialog_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_tips.dismiss();
                blockDialog.show();
                if (num == 1) {
                    message_delete();
                } else {
                    message_default();
                }

            }
        });
    }

    private void message_delete() {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/user/address/del")
                .addParams("id", id)
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
                        ToastUtil.show(msg);
                        arrayList.remove(position);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void message_default() {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/user/address/set_default")
                .addParams("id", id)
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
                        ToastUtil.show(msg);
                        arrayList.get(position).setContent("1");
                        adapter.notifyDataSetChanged();
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
