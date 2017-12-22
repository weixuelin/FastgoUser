package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.widgets.StartUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<HomeModel> mList;
    private OnItemClickListener mOnItemClickListener = null;
    SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public TransferAdapter(Context mContext, ArrayList<HomeModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transfer, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.image_select.setChecked(isItemChecked(position));

        //checkBox的监听
        viewHolder.image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
                } else {
                    setItemChecked(position, true);
                }
            }
        });

        //条目view的监听
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
                } else {
                    setItemChecked(position, true);
                }
                notifyItemChanged(position);
            }
        });
        viewHolder.ben_order_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartUtils.startActivityById(mContext,view.getId());
            }
        });
    }

    //获得选中条目的结果
    public ArrayList<String> getSelectedItem() {
        ArrayList<String> selectList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (isItemChecked(i)) {
//                selectList.add(mList.get(i).getId());
            }
        }
        return selectList;
    }

    //获得选中条目的结果
    public ArrayList<String> getSelectedName() {
        ArrayList<String> selectList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (isItemChecked(i)) {
//                selectList.add(mList.get(i).getName());
            }
        }
        return selectList;
    }

    //根据位置判断条目是否选中
    public boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
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
        CheckBox image_select;
        Button ben_order_setup;

        public ViewHolder(View view) {
            super(view);
            ben_order_setup = view.findViewById(R.id.ben_order_setup);
            image_select = view.findViewById(R.id.image_select);
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
