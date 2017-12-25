package com.wt.fastgo_user.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wt.fastgo_user.widgets.BackHandledInterface;
import com.wt.fastgo_user.widgets.CommonUtils;
import com.wt.fastgo_user.widgets.ContentPage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import rx.Subscriber;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public ContentPage contentPage;
    public ProgressDialog pdLoading;
    private ArrayList<Subscriber> subscribers;
    protected BackHandledInterface mBackHandledInterface;
    protected boolean isVisable;
    // 标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;

    public Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gson = new Gson();

        /**
         * 初始化pdLoading
         */
        pdLoading = new ProgressDialog(getActivity());
        pdLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdLoading.setMessage("请稍后");
        pdLoading.setCanceledOnTouchOutside(false);
        pdLoading.setCancelable(true);

        /**
         * 创建Subscriber容器
         */
        subscribers = new ArrayList<>();
        if (contentPage == null) {
            contentPage = new ContentPage(getActivity()) {
                @Override
                protected Object loadData() {
                    return requestData();
                }

                @Override
                protected View createSuccessView() {
                    return getSuccessView();
                }
            };
        } else {
            CommonUtils.removeSelfFromParent(contentPage);
        }
        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }
        setActionBar();

        return contentPage;

    }

    public void showShortToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 实现Fragment数据的缓加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisable = true;
            onVisible();
        } else {
            isVisable = false;
            onInVisible();
        }
    }

    protected void onInVisible() {
    }

    protected void onVisible() {
        //加载数据
        loadData();
    }

    protected abstract void loadData();

    /**
     * 返回据的fragment填充的具体View
     */
    protected abstract View getSuccessView();

    /**
     * 返回请求服务器的数据
     */
    protected abstract Object requestData();

    public void refreshPage(Object o) {
        contentPage.refreshPage(o);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Subscriber subscriber : subscribers) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.unsubscribe();
            }
        }
    }

    public <T> Subscriber<T> addSubscriber(Subscriber<T> subscriber) {
        subscribers.add(subscriber);
        return subscriber;
    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
    }

    protected abstract void setActionBar();// 设置ActionBar

    public abstract boolean onBackPressed();
}



