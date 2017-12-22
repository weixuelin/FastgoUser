package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.widgets.StartUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class OrderLibraryAdapter extends RecyclerView.Adapter<OrderLibraryAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<HomeModel> mList;
    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public OrderLibraryAdapter(Context mContext, ArrayList<HomeModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_library, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.btn_order_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUtils.startActivityById(mContext,view.getId());
            }
        });
        viewHolder.ben_order_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUtils.startActivityById(mContext,view.getId());
            }
        });
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
        Button btn_order_editor;
        Button ben_order_setup;

        public ViewHolder(View view) {
            super(view);
            ben_order_setup = view.findViewById(R.id.ben_order_setup);
            btn_order_editor = view.findViewById(R.id.btn_order_editor);
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
