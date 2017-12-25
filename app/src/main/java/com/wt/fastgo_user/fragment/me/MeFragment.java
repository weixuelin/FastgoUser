package com.wt.fastgo_user.fragment.me;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.applaction.SYApplication;
import com.wt.fastgo_user.fragment.BaseFragment;
import com.wt.fastgo_user.widgets.BlockDialog;
import com.wt.fastgo_user.widgets.CircleImageView;
import com.wt.fastgo_user.widgets.ConstantUtils;
import com.wt.fastgo_user.widgets.CropCircleTransformation;
import com.wt.fastgo_user.widgets.Glide_Image;
import com.wt.fastgo_user.widgets.StartUtils;
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
 * Created by Administrator on 2017/10/10 0010.
 */

public class MeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.image_me_setting)
    ImageView imageMeSetting;
    @BindView(R.id.linear_me_balance)
    LinearLayout linearMeBalance;
    @BindView(R.id.linear_me_coupon)
    LinearLayout linearMeCoupon;
    @BindView(R.id.linear_my_integral)
    LinearLayout linearMyIntegral;
    @BindView(R.id.relative_me_address)
    RelativeLayout relativeMeAddress;
    @BindView(R.id.relative_my_statistics)
    RelativeLayout relativeMyStatistics;
    @BindView(R.id.relative_my_safe)
    RelativeLayout relativeMySafe;
    @BindView(R.id.relative_my_feedback)
    RelativeLayout relativeMyFeedback;
    @BindView(R.id.relative_my_about)
    RelativeLayout relativeMyAbout;
    @BindView(R.id.image_me_avatar)
    ImageView imageMeAvatar;
    @BindView(R.id.text_me_nickname)
    TextView textMeNickname;
    @BindView(R.id.text_me_balance)
    TextView textMeBalance;
    @BindView(R.id.text_me_count)
    TextView textMeCount;
    @BindView(R.id.text_me_integral)
    TextView textMeIntegral;
    private BlockDialog blockDialog;

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        setListener();
        isPrepared = true;
        return view;
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisable) {
            return;
        }
        //填充各控件的数据
        blockDialog.show();
        message();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void message() {
        RequestCall call = SYApplication.genericClient()
                .url(SYApplication.path_url + "/user/index/index").build();
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
                        textMeBalance.setText(moeny);
                        textMeIntegral.setText(integral);
                        textMeCount.setText(coupon_num);
                        textMeNickname.setText(nickname);
                        Glide_Image.load(getActivity(), avatar, imageMeAvatar,new CropCircleTransformation(getActivity()));

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
        blockDialog = new BlockDialog(getActivity());
        relativeMyStatistics.setOnClickListener(this);
        imageMeSetting.setOnClickListener(this);
        linearMeCoupon.setOnClickListener(this);
        relativeMeAddress.setOnClickListener(this);
        linearMeBalance.setOnClickListener(this);
        linearMyIntegral.setOnClickListener(this);
        relativeMySafe.setOnClickListener(this);
        relativeMyFeedback.setOnClickListener(this);
    }


    @Override
    protected void setActionBar() {
//        ClickButtonActivity activity = (ClickButtonActivity) getActivity();
//        activity.textTop.setText("我的");
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
