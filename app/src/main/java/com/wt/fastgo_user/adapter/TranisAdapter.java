package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.model.HomeModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class TranisAdapter extends RecyclerView.Adapter<TranisAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<HomeModel> mList;
    private OnItemClickListener mOnItemClickListener = null;
    public static int num;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public TranisAdapter(Context mContext, ArrayList<HomeModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tranis, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        int number = position + 1;
        if (num == position) {
            viewHolder.linear_tranis_all.setBackgroundResource(R.drawable.tranis_select);
            viewHolder.text_tranis_number.setVisibility(View.VISIBLE);
            viewHolder.text_tranis_address.setTextColor(mContext.getResources().getColor(R.color.main_red));
            viewHolder.text_tranis_number.setText("0" + number);
        } else {
            viewHolder.linear_tranis_all.setBackgroundResource(R.drawable.tranis_not);
            viewHolder.text_tranis_number.setVisibility(View.GONE);
            viewHolder.text_tranis_address.setTextColor(mContext.getResources().getColor(R.color.main_grey6));
            viewHolder.text_tranis_number.setText("0" + number);
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
        LinearLayout linear_tranis_all;
        TextView text_tranis_number, text_tranis_address;

        public ViewHolder(View view) {
            super(view);
            linear_tranis_all = view.findViewById(R.id.linear_tranis_all);
            text_tranis_number = view.findViewById(R.id.text_tranis_number);
            text_tranis_address = view.findViewById(R.id.text_tranis_address);
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
