package com.wt.fastgo_user.fragment.me;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
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
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lljjcoder.citypickerview.model.DbInfo;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.PopBuyAdapter;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.widgets.BitmapUtil;
import com.wt.fastgo_user.widgets.BlockDialog;
import com.wt.fastgo_user.widgets.Glide_Image;
import com.wt.fastgo_user.widgets.ProviderUtil;
import com.wt.fastgo_user.widgets.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class AddInternationalFragment extends TakePhotoFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.edit_address_card)
    EditText editAddressCard;
    @BindView(R.id.edit_add_address1)
    EditText editAddAddress1;
    @BindView(R.id.edit_add_address2)
    EditText editAddAddress2;
    @BindView(R.id.text_add3)
    TextView textAdd3;
    @BindView(R.id.text_add_country)
    TextView textAddCountry;
    @BindView(R.id.text_add2)
    TextView textAdd2;
    @BindView(R.id.text_add_state)
    TextView textAddState;
    @BindView(R.id.text_add1)
    TextView textAdd1;
    @BindView(R.id.text_add_city)
    TextView textAddCity;
    @BindView(R.id.text_add4)
    TextView textAdd4;
    @BindView(R.id.edit_add_zipcode)
    EditText editAddZipcode;
    @BindView(R.id.image_add_zheng)
    ImageView imageAddZheng;
    @BindView(R.id.image_add_fan)
    ImageView imageAddFan;
    @BindView(R.id.btn_add_submit)
    Button btnAddSubmit;
    @BindView(R.id.edit_address_name)
    EditText editAddressName;
    @BindView(R.id.edit_address_phone)
    EditText editAddressPhone;
    private int sign = 1;
    private BlockDialog blockDialog;
    private String id = "";
    private String type = "2";
    private ArrayList<TImage> images;
    private boolean flag = true;
    private List<DbInfo> list;
    private String id_city = "";
    PopupWindow popupWindow = null;
    private String name = "", mobile = "", address = "", province = "", city = "", area = "", zip_code = "", code = "", back_code = "", positive_code = "", is_default = "0";
    private String country = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View view = View.inflate(getActivity(), R.layout.fragment_address_add, null);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            sign = getArguments().getInt("num");
            id = getArguments().getString("id");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        blockDialog = new BlockDialog(getActivity());
        images = new ArrayList<>();
        setListener();
    }

    public static AddInternationalFragment newInstance(int num, String id) {
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putString("id", id);
        AddInternationalFragment fragment = new AddInternationalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setListener() {
        if (sign == 1) {
            editAddressCard.setHint(R.string.add_address_code);
            editAddressName.setHint(R.string.add_address_name);
            editAddressPhone.setHint(R.string.add_address_phone);
        } else {
            editAddressCard.setHint(R.string.add_address_code_sender);
            editAddressName.setHint(R.string.add_address_name_sender);
            editAddressPhone.setHint(R.string.add_address_phone_sender);
        }
        btnAddSubmit.setOnClickListener(this);
        imageAddZheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                new PopupWindow_banner(getActivity(), imageAddZheng);
            }
        });
        imageAddFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                new PopupWindow_banner(getActivity(), imageAddFan);
            }
        });
        textAddCountry.setOnClickListener(this);
        textAddState.setOnClickListener(this);
        textAddCity.setOnClickListener(this);
        if (!id.equals("")) {
            blockDialog.show();
            message_get();
        }
    }

    private void message_city(String id, final int num) {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/common/category/get_city")
                .addParams("id", id)
                .addParams("type", type).build();

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
                list = new ArrayList<>();
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String msg = jsonObject.getString("msg");
                    if (status) {
                        String data = jsonObject.getString("data");
                        popwindow(data, num);
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void popwindow(String restles, final int num) {
        View popview = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_list, null);
        popupWindow = new PopupWindow(popview, DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        setBackgroundAlpha(0.5f);
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
                id_city = modelArrayList.get(position).getId();
                if (num == 1) {
                    textAddCountry.setText(modelArrayList.get(position).getName());
                } else if (num == 2) {
                    textAddState.setText(modelArrayList.get(position).getName());
                } else if (num == 3) {
                    textAddCity.setText(modelArrayList.get(position).getName());
                }
            }
        });
        popupWindow.setOnDismissListener(new poponDismissListener());
        if (popupWindow.isShowing()) {
            setBackgroundAlpha(0f);
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(textAddCountry, Gravity.BOTTOM, 0, 0);
//            popupWindow.showAsDropDown(textAddCountry);
        }
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            setBackgroundAlpha(1f);
        }

    }

    /**
     * 设置pop背景透明度
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((getActivity())).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    private void message() {
        RequestCall call = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/user/address/edit")
                .addParams("id", id + "")
                .addParams("type", type)
                .addParams("sign", sign + "")
                .addParams("name", name)
                .addParams("mobile", mobile)
                .addParams("address", address)
                .addParams("province", province)
                .addParams("city", city)
                .addParams("area", area)
                .addParams("country", country)
                .addParams("zip_code", zip_code)
                .addParams("code", code)
                .addFile("back_code", back_code, new File(back_code))
                .addFile("positive_code", positive_code, new File(positive_code))
                .addParams("is_default", is_default).build();
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
                        ToastUtil.show(msg);
                        getActivity().finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void message_get() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/user/address/edit")
                .addParams("id", id)
                .build();
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
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        sign = jsonData.getInt("sign");
                        type = jsonData.getString("type");
                        address = jsonData.getString("address");
                        province = jsonData.getString("province");
                        city = jsonData.getString("city");
                        zip_code = jsonData.getString("zip_code");
                        code = jsonData.getString("code");
                        back_code = jsonData.getString("back_code");
                        country = jsonData.getString("country");
                        area = jsonData.getString("area");
                        positive_code = jsonData.getString("positive_code");
                        is_default = jsonData.getString("is_default");
                        if (!back_code.equals("")) {
                            Glide_Image.load(getActivity(), back_code, imageAddFan);
                        }
                        if (!positive_code.equals("")) {
                            Glide_Image.load(getActivity(), positive_code, imageAddZheng);
                        }
                        textAddCity.setText(city);
                        textAddState.setText(province);
                        textAddCountry.setText(country);
                        editAddressCard.setText(code);
                        editAddAddress1.setText(address);
                        editAddZipcode.setText(zip_code);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_add_country:
                blockDialog.show();
                message_city("8", 1);
                break;
            case R.id.text_add_state:
                blockDialog.show();
                message_city(id_city, 2);
                break;
            case R.id.text_add_city:
                blockDialog.show();
                message_city(id_city, 3);
                break;
            case R.id.btn_add_submit:
                zip_code = editAddZipcode.getText().toString();
                code = editAddressCard.getText().toString();
                address = editAddAddress1.getText().toString() + editAddAddress2.getText().toString();
                province = textAddState.getText().toString();
                city = textAddCity.getText().toString();
                country = textAddCountry.getText().toString();
                name = editAddressName.getText().toString();
                mobile = editAddressPhone.getText().toString();
                blockDialog.show();
                message();
                break;
        }
    }

    public class PopupWindow_banner extends PopupWindow {

        public PopupWindow_banner(Context mContext, View parent) {
            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = view
                    .findViewById(R.id.item_popupwindows_cancel);
            final TakePhoto takePhoto = getTakePhoto();
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            final Uri imageUri;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                imageUri = Uri.fromFile(file);
            } else {
                /**
                 * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                 * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                 */
                imageUri = FileProvider.getUriForFile(getActivity(), ProviderUtil.getFileProviderName(getActivity()), file);
            }
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    takePhoto.onPickFromCapture(imageUri);
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                    int limit = 1;
                    if (limit > 1) {
                        takePhoto.onPickMultiple(limit);
                        return;
                    }
                    takePhoto.onPickFromGallery();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener()

            {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        images = result.getImages();
        compressPhotoSend(images);
    }

    private void compressPhotoSend(ArrayList<TImage> arrayList2) {
        String newPath = "";
        for (int i = 0; i < arrayList2.size(); i++) {
            newPath = BitmapUtil.compressImageAndSave(
                    getActivity().getApplicationContext(), arrayList2.get(i).getOriginalPath(), false);
        }
        if (flag) {
            Glide_Image.load(getActivity(), newPath, imageAddZheng);
            positive_code = newPath;
        } else {
            Glide_Image.load(getActivity(), newPath, imageAddFan);
            back_code = newPath;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
        unbinder.unbind();
    }
}
