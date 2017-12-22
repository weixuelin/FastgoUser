package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.model.HomeModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<HomeModel> mList;
    private OnItemClickListener mOnItemClickListener = null;
    private ArrayList<String> arrayList;
    private ImageAdapter adapter;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public FeedbackAdapter(Context mContext, ArrayList<HomeModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feedback, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        //设置固定大小
        viewHolder.recycler_feedback_image.setHasFixedSize(true);
        //创建线性布局
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext,3);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        viewHolder.recycler_feedback_image.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new ImageAdapter(mContext, arrayList);
        viewHolder.recycler_feedback_image.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513597482189&di=f2130542a9305a5ebd25ddd375d47be8&imgtype=0&src=http%3A%2F%2Fimgtu.5011.net%2Fuploads%2Fcontent%2F20170324%2F9100851490342923.jpg");
            adapter.notifyDataSetChanged();
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recycler_feedback_image;

        public ViewHolder(View view) {
            super(view);
            recycler_feedback_image = view.findViewById(R.id.recycler_feedback_image);

        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }
}
