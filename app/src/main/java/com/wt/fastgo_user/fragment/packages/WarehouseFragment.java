package com.wt.fastgo_user.fragment.packages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.PopBuyAdapter;
import com.wt.fastgo_user.adapter.WarehouseAdapter;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.fragment.me.AddInternationalFragment;
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
 * Created by Administrator on 2017/12/21 0021.
 */

public class WarehouseFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.text_board_add)
    TextView textBoardAdd;
    @BindView(R.id.recycler_transfer)
    RecyclerView recyclerTransfer;
    private ArrayList<HomeModel> arrayList;
    private WarehouseAdapter adapter;
    private Dialog dialog_tips, dialog_success;
    private BlockDialog blockDialog;
    PopupWindow popupWindow = null;
    private RelativeLayout relative_goods_type;
    private TextView text_goods_type;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_house, null);
        ButterKnife.bind(this, view);
        blockDialog = new BlockDialog(getActivity());
        initView();
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

    @Override
    protected void setActionBar() {
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.relativeTop.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void initView() {
        //设置固定大小
        recyclerTransfer.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerTransfer.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new WarehouseAdapter(getActivity(), arrayList);
        recyclerTransfer.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {
            HomeModel model = new HomeModel();
            arrayList.add(model);
            adapter.notifyDataSetChanged();
        }
        textBoardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_tip();
            }
        });
    }

    private void dialog_tip() {
        LayoutInflater inflater_type = getActivity().getLayoutInflater();
        View layout_type = inflater_type.inflate(R.layout.dialog_add_goods, null);
        AlertDialog.Builder record_type = new AlertDialog.Builder(getActivity());
        dialog_tips = record_type.create();
        dialog_tips.show();
        dialog_tips.setContentView(layout_type);
        dialog_tips.setCanceledOnTouchOutside(false);//
        Button btn_add_sure = layout_type
                .findViewById(R.id.btn_add_sure);//
        ImageView image_dialog_close = layout_type
                .findViewById(R.id.image_dialog_close);//
        final EditText edit_goods_name = layout_type
                .findViewById(R.id.edit_goods_name);//
        text_goods_type = layout_type
                .findViewById(R.id.text_goods_type);//
        relative_goods_type = layout_type
                .findViewById(R.id.relative_goods_type);//
        final EditText edit_goods_brand = layout_type
                .findViewById(R.id.edit_goods_brand);//
        final EditText edit_goods_spec = layout_type
                .findViewById(R.id.edit_goods_spec);//
        final EditText edit_goods_num = layout_type
                .findViewById(R.id.edit_goods_num);//
        final EditText edit_goods_price = layout_type
                .findViewById(R.id.edit_goods_price);//
        EditText edit_goods_total = layout_type
                .findViewById(R.id.edit_goods_total);//
        edit_goods_total.setVisibility(View.GONE);
        dialog_tips.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        image_dialog_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_tips.dismiss();
            }
        });

        btn_add_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                blockDialog.show();
                message_success(edit_goods_name.getText().toString(),
                        text_goods_type.getText().toString(),
                        edit_goods_brand.getText().toString(),
                        edit_goods_spec.getText().toString(),
                        edit_goods_num.getText().toString(),
                        edit_goods_price.getText().toString());
                dialog_tips.dismiss();
            }
        });
        relative_goods_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockDialog.show();
                message_type();
            }
        });
    }

    private void message_success(String name, String category, String brand
            , String spec, String num, String price) {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/user/warehouse/edit")
                .addParams("name", name)
                .addParams("category", category)
                .addParams("brand", brand)
                .addParams("spec", spec)
                .addParams("price", price)
                .addParams("num", num).build();

        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                ToastUtil.show(e + "");
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
                        dialog_success();
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void message_type() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/common/category/get_list")
                .addParams("id", "587").build();

        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                blockDialog.dismiss();
                ToastUtil.show(e + "");
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
                        String data = jsonObject.getString("data");
                        popwindow(data);
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void popwindow(String restles) {
        View popview = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_list, null);
        popupWindow = new PopupWindow(popview, DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        RecyclerView recycle_city = popview
                .findViewById(R.id.recycle_city);
        final ArrayList<HomeModel> modelArrayList;
        PopBuyAdapter adapter;
        LinearLayoutManager linearLayoutManager;
        modelArrayList = new ArrayList<>();
        adapter = new PopBuyAdapter(getActivity(), modelArrayList);
        //设置固定大小
        recycle_city.setHasFixedSize(true);
        //创建线性布局
        linearLayoutManager = new LinearLayoutManager(getActivity());
        //垂直方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        recycle_city.setLayoutManager(linearLayoutManager);
        recycle_city.setAdapter(adapter);
        try {
            JSONArray jsonData = new JSONArray(restles);
            for (int i = 0; i < jsonData.length(); i++) {
                HomeModel homeModel = new HomeModel();
                String id = jsonData.getJSONObject(i).getString("id");
                String value = jsonData.getJSONObject(i).getString("value");
                homeModel.setId(id);
                homeModel.setName(value);
                modelArrayList.add(homeModel);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
//                                e.printStackTrace();
        }
        adapter.setOnItemClickListener(new PopBuyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                popupWindow.dismiss();
                text_goods_type.setText(modelArrayList.get(position).getName());
            }
        });
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(relative_goods_type, Gravity.CENTER, 0, 0);
//            popupWindow.showAsDropDown(textAddCountry);
        }
    }

    private void dialog_success() {
        LayoutInflater inflater_type = getActivity().getLayoutInflater();
        View layout_type = inflater_type.inflate(R.layout.dialog_add_success, null);
        AlertDialog.Builder record_type = new AlertDialog.Builder(getActivity());
        dialog_success = record_type.create();
        dialog_success.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        dialog_success.show();
        dialog_success.setContentView(layout_type);
        dialog_success.setCanceledOnTouchOutside(true);//
        Button btn_dialog_add = layout_type
                .findViewById(R.id.btn_dialog_add);//
        Button btn_dialog_cancel = layout_type
                .findViewById(R.id.btn_dialog_cancel);

        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_success.dismiss();
            }
        });

        btn_dialog_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog_success.dismiss();
                dialog_tip();
            }
        });
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
