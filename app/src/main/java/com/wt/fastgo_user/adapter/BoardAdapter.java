package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.model.HomeModel;
import com.wt.fastgo_user.widgets.Glide_Image;
import com.wt.fastgo_user.widgets.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<HomeModel> mList;
    private OnItemClickListener mOnItemClickListener = null;
    private ArrayList<String> arrayList;
    private ImageAdapter adapter;
    private View.OnClickListener clickListener;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public BoardAdapter(Context mContext, ArrayList<HomeModel> mList, View.OnClickListener clickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.clickListener = clickListener;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_board, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.text_board_content.setText(mList.get(position).getContent());
        viewHolder.text_board_like.setText(mContext.getResources().getString(R.string.board_zan)
                + mList.get(position).getNum() + ")");
        viewHolder.text_board_nickname.setText(mList.get(position).getNickname());
        viewHolder.text_board_time.setText(mList.get(position).getTime());
        if (mList.get(position).is_like()) {
            viewHolder.text_board_like.setTextColor(mContext.getResources().getColor(R.color.main_red));
            viewHolder.image_board_zan.setImageResource(R.drawable.board_zan_red);
        } else {
            viewHolder.text_board_like.setTextColor(mContext.getResources().getColor(R.color.main_grey73));
            viewHolder.image_board_zan.setImageResource(R.drawable.board_zan);
        }
        Glide_Image.load(mContext, mList.get(position).getAvatar(), viewHolder.image_board_head);
        viewHolder.linear_board_like.setOnClickListener(clickListener);
        viewHolder.linear_board_like.setTag(position);
        viewHolder.recycler_board.setHasFixedSize(true);
        //创建线性布局
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 3);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        viewHolder.recycler_board.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new ImageAdapter(mContext, arrayList);
        viewHolder.recycler_board.setAdapter(adapter);
        try {
            JSONArray list = new JSONArray(mList.get(position).getAblum());
            for (int i = 0; i < list.length(); i++) {
                arrayList.add(list.getString(i));
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
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
        RecyclerView recycler_board;
        ImageView image_board_head;
        TextView text_board_nickname;
        TextView text_board_content;
        TextView text_board_time;
        TextView text_board_like;
        ImageView image_board_zan;
        LinearLayout linear_board_like;

        public ViewHolder(View view) {
            super(view);
            linear_board_like = view.findViewById(R.id.linear_board_like);
            image_board_zan = view.findViewById(R.id.image_board_zan);
            text_board_nickname = view.findViewById(R.id.text_board_nickname);
            text_board_content = view.findViewById(R.id.text_board_content);
            text_board_time = view.findViewById(R.id.text_board_time);
            text_board_like = view.findViewById(R.id.text_board_like);
            image_board_head = view.findViewById(R.id.image_board_head);
            recycler_board = view.findViewById(R.id.recycler_board);
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
