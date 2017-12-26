package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.ui.ClickButtonActivity;
import com.wt.fastgo_user.widgets.BlockDialog;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.CropCircleTransformation;
import com.wt.fastgo_user.widgets.Glide_Image;
import com.wt.fastgo_user.widgets.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class SettingFragment extends BaseFragment {

    @BindView(R.id.linear_setting_back)
    LinearLayout linearSettingBack;
    Unbinder unbinder;
    @BindView(R.id.image_setting_head)
    ImageView imageSettingHead;
    @BindView(R.id.edit_setting_nickname)
    EditText editSettingNickname;
    @BindView(R.id.text_setting_sex)
    TextView textSettingSex;
    @BindView(R.id.text_setting_country)
    TextView textSettingCountry;
    @BindView(R.id.relative_setting_country)
    RelativeLayout relativeSettingCountry;
    @BindView(R.id.edit_setting_address)
    EditText editSettingAddress;
    private BlockDialog blockDialog;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.ftagment_setting, null);
        ButterKnife.bind(this, view);
        setListener();
        blockDialog = new BlockDialog(getActivity());
        blockDialog.show();
        message();
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

    private void message() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/user/index/edit").build();
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
                        String avatar = jsonData.getString("avatar");
                        String nickname = jsonData.getString("nickname");
                        String integral = jsonData.getString("integral");
                        String moeny = jsonData.getString("moeny");
                        String coupon_num = jsonData.getString("coupon_num");
//                        textMeBalance.setText(moeny);
//                        textMeIntegral.setText(integral);
//                        textMeCount.setText(coupon_num);
                        editSettingNickname.setText(nickname);
                        Glide_Image.load(getActivity(), avatar, imageSettingHead, new CropCircleTransformation(getActivity()));

                    } else {
                        ToastUtil.show(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                }
            }
        });
    }

    private void setListener() {
        linearSettingBack.setOnClickListener(this);
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
            case R.id.linear_setting_back:
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
