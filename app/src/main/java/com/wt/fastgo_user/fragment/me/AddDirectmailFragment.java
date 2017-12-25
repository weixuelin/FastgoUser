package com.wt.fastgo_user.fragment.me;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lljjcoder.citypickerview.utils.Key;
import com.lljjcoder.citypickerview.widget.PickerFromUrl;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.applaction.SYApplication;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class AddDirectmailFragment extends TakePhotoFragment {
    Unbinder unbinder;
    @BindView(R.id.edit_address_name)
    EditText editAddressName;
    @BindView(R.id.edit_address_phone)
    EditText editAddressPhone;
    @BindView(R.id.text_add1)
    TextView textAdd1;
    @BindView(R.id.edit_address_card)
    EditText editAddressCard;
    @BindView(R.id.text_address_city)
    TextView textAddressCity;
    @BindView(R.id.edit_address_address)
    EditText editAddressAddress;
    @BindView(R.id.edit_address_zipcode)
    EditText editAddressZipcode;
    @BindView(R.id.image_address_zheng)
    ImageView imageAddressZheng;
    @BindView(R.id.image_address_fan)
    ImageView imageAddressFan;
    @BindView(R.id.btn_address_add)
    Button btnAddressAdd;
    private int sign = 1;
    private BlockDialog blockDialog;
    private String id = "";
    private String type = "1";
    private ArrayList<TImage> images;
    private boolean flag = true;
    private List<DbInfo> list;
    private Map<Integer, List<DbInfo>> map;
    private String id_city = "";
    private String name = "", mobile = "", address = "", province = "", city = "", area = "", zip_code = "", code = "", back_code = "", positive_code = "", is_default = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View view = View.inflate(getActivity(), R.layout.fragment_add_address, null);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            sign = getArguments().getInt("num");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        images = new ArrayList<>();
        setListener();
        blockDialog = new BlockDialog(getActivity());
    }

    public static AddDirectmailFragment newInstance(int num) {
        Bundle args = new Bundle();
        args.putInt("num", num);
        AddDirectmailFragment fragment = new AddDirectmailFragment();
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
        btnAddressAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editAddressName.getText().toString();
                zip_code = editAddressZipcode.getText().toString();
                mobile = editAddressPhone.getText().toString();
                code = editAddressCard.getText().toString();
                address = editAddressAddress.getText().toString();
            }
        });
        imageAddressZheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                new PopupWindow_banner(getActivity(), imageAddressZheng);
            }
        });
        imageAddressFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                new PopupWindow_banner(getActivity(), imageAddressFan);
            }
        });
        textAddressCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockDialog.show();
                message_city();
            }
        });
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

    private void message_city() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/common/category/get_city")
                .addParams("id", id_city)
                .addParams("type", type).build();
//        try {
//            Response response =  call.execute();
//            response.isSuccessful()
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            DbInfo info = new DbInfo();
                            info.setId(data.getJSONObject(i).getInt("id"));
                            info.setText(data.getJSONObject(i).getString("value"));
                            list.add(info);
                            selectAddress();
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


    private void selectAddress() {
        map = new HashMap<>();
        map.put(3, list);
        PickerFromUrl cityPicker = new PickerFromUrl.Builder(getActivity())
                .textSize(14)
                .title("地址选择")
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build(1, list, map);
        cityPicker.show();
        PickerFromUrl.setGetDataFromUrl(new PickerFromUrl.GetDataFromUrl() {
            @Override
            public Key getFromUrlTwo(int keyId) {
                return null;
            }

            @Override
            public Key getFromUrlThree(int oneId, int twoId) {
                return null;
            }
        });
        cityPicker.setOnCityItemClickListener(new PickerFromUrl.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                textAddressCity.setText(map.get(0) + "-" + map.get(1) + "-" + map.get(2));
            }

            @Override
            public void onSelectedId(Integer... sel) {

            }

            @Override
            public void onCancel() {

            }
        });
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
            Glide_Image.load(getActivity(), newPath, imageAddressZheng);
            positive_code = newPath;
        } else {
            Glide_Image.load(getActivity(), newPath, imageAddressFan);
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
