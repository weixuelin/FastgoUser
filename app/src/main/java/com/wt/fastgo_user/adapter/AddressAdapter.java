package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.model.HomeModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<HomeModel> mList;
    private OnItemClickListener mOnItemClickListener = null;
    private View.OnClickListener onClickListener;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public AddressAdapter(Context mContext, ArrayList<HomeModel> mList, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.onClickListener = onClickListener;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.text_address_address.setText(mList.get(position).getAddress());
        viewHolder.text_address_name.setText(mList.get(position).getName());
        viewHolder.text_address_phone.setText(mList.get(position).getMobile());
        if (mList.get(position).getContent().equals("1")) {
            viewHolder.image_address_default.setImageResource(R.drawable.address_default);
            viewHolder.text_address_default.setTextColor(mContext.getResources().getColor(R.color.main_red));
        } else {
            viewHolder.image_address_default.setImageResource(R.drawable.address_notdefault);
            viewHolder.text_address_default.setTextColor(mContext.getResources().getColor(R.color.main_grey6));
        }
        viewHolder.linear_address_default.setOnClickListener(onClickListener);
        viewHolder.linear_address_default.setTag(position);
        viewHolder.linear_address_delete.setOnClickListener(onClickListener);
        viewHolder.linear_address_delete.setTag(position);
        viewHolder.linear_address_edit.setOnClickListener(onClickListener);
        viewHolder.linear_address_edit.setTag(position);

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
        LinearLayout linear_address_default;
        ImageView image_address_default;
        TextView text_address_default;
        LinearLayout linear_address_edit;
        LinearLayout linear_address_delete;
        TextView text_address_phone;
        TextView text_address_name;
        TextView text_address_address;

        public ViewHolder(View view) {
            super(view);
            text_address_address = view.findViewById(R.id.text_address_address);
            text_address_name = view.findViewById(R.id.text_address_name);
            text_address_phone = view.findViewById(R.id.text_address_phone);
            linear_address_edit = view.findViewById(R.id.linear_address_edit);
            linear_address_delete = view.findViewById(R.id.linear_address_delete);
            linear_address_default = view.findViewById(R.id.linear_address_default);
            image_address_default = view.findViewById(R.id.image_address_default);
            text_address_default = view.findViewById(R.id.text_address_default);

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
