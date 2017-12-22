package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.model.FlowTrace;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class JoinFlowAdapter extends RecyclerView.Adapter<JoinFlowAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<FlowTrace> mList;
    private OnItemClickListener mOnItemClickListener = null;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public JoinFlowAdapter(Context mContext, List<FlowTrace> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_join_flow, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            viewHolder.tvTopLine.setVisibility(View.INVISIBLE);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            viewHolder.text_flow_content.setText(mList.get(position).getAcceptStation());
            viewHolder.text_time.setText(mList.get(position).getNum());
            viewHolder.text_flow_title.setText(mList.get(position).getTitle());
        }

    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
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
        TextView text_flow_content;
        TextView text_flow_title;
        TextView text_time;
        TextView tvTopLine;

        public ViewHolder(View view) {
            super(view);
            tvTopLine = view.findViewById(R.id.tvTopLine);
            text_flow_title = view.findViewById(R.id.text_flow_title);
            text_time = view.findViewById(R.id.text_time);
            text_flow_content = view.findViewById(R.id.text_flow_content);
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
