package com.wt.fastgo_user.fragment.message;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingwei.pullrefresh_lib.PullToRefreshLayout;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.GridAdapter;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.BitmapUtil;
import com.wt.fastgo_user.widgets.BlockDialog;
import com.wt.fastgo_user.widgets.MyGridview;
import com.wt.fastgo_user.widgets.ProviderUtil;
import com.wt.fastgo_user.widgets.ToastUtil;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class AddBoardFragment extends TakePhotoFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.noScrollgridview)
    MyGridview noScrollgridview;
    @BindView(R.id.linear_back)
    LinearLayout linearBack;
    @BindView(R.id.text_top)
    TextView textTop;
    @BindView(R.id.relative_top)
    RelativeLayout relativeTop;
    @BindView(R.id.edit_add_content)
    EditText editAddContent;
    @BindView(R.id.btn_board_submit)
    Button btnBoardSubmit;
    private ArrayList<TImage> images;
    private GridAdapter adapter;
    private ArrayList<String> image, strings;
    private InputMethodManager inputManager;
    private String description;
    private BlockDialog blockDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View view = View.inflate(getActivity(), R.layout.fragment_add_board, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
        activity.relativeTop.setVisibility(View.GONE);
        blockDialog = new BlockDialog(getActivity());
        images = new ArrayList<>();
        image = new ArrayList<>();
        strings = new ArrayList<>();
        Init(image);
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        linearBack.setOnClickListener(this);
        btnBoardSubmit.setOnClickListener(this);
        textTop.setText(R.string.board_add);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_back:
                getActivity().finish();
                break;
            case R.id.btn_board_submit:
                description = editAddContent.getText().toString();
                blockDialog.show();
                message();
                break;

        }
    }

    public void Init(final ArrayList<String> images) {
        adapter = new GridAdapter(getActivity(), strings, clickListeners);
        noScrollgridview.setAdapter(adapter);
        for (int i = 0; i < images.size(); i++) {
            strings.add(images.get(i));
            adapter.notifyDataSetChanged();
        }
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                hideKeyboard();
                if (arg2 < strings.size()) {
//                    Intent intent = new Intent(AddCarActivity.this, BigPhotoActivity.class);
//                    intent.putExtra("mybigphoto", strings);
//                    intent.putExtra("position", arg2);
//                    intent.putExtra("bigphotoType", 2);
//                    startActivity(intent);
                } else {
                    new PopupWindow_banner(getActivity(), noScrollgridview);
                }
            }
        });
    }

    private void message() {
        PostFormBuilder builder = SYApplication.postFormBuilder()
                .url(SYApplication.path_url + "/common/circle/add")
                .addParams("description", description);
        for (int i = 0; i < strings.size(); i++) {
            builder
                    .addFile("ablum[" + i + "]", strings.get(i), new File(strings.get(i)));
        }
        RequestCall call = builder.build();
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

    private View.OnClickListener clickListeners = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.item_grida_delete:
//                    int posiont = (Integer) view.getTag();
//                    dialog_tip(0, posiont);
//                    break;
            }
        }
    };

    public void hideKeyboard() {
        if (this == null) return;
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
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
                    int limit = 9;
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
        String newPath;
        ArrayList<String> strings2 = new ArrayList<>();
        for (int i = 0; i < arrayList2.size(); i++) {
            newPath = BitmapUtil.compressImageAndSave(
                    getActivity().getApplicationContext(), arrayList2.get(i).getOriginalPath(), false);
            strings2.add(newPath);
        }
        Init(strings2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
