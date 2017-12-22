package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wt.fastgo_user.R;
import com.wt.fastgo_user.widgets.Glide_Image;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class GridAdapter extends BaseAdapter {
    private LayoutInflater inflater; // 视图容器
    private int selectedPosition = -1;// 选中的位置
    private boolean shape;
    private List<String> images;
    private View.OnClickListener clickListener;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public GridAdapter(Context context, List<String> images, View.OnClickListener clickListener) {
        inflater = LayoutInflater.from(context);
        this.images = images;
        this.clickListener = clickListener;
    }

    public int getCount() {
        return (images.size() + 1);
    }

    public Object getItem(int arg0) {

        return null;
    }

    public long getItemId(int arg0) {

        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida,
                    parent, false);
            holder = new ViewHolder();
            holder.image = convertView
                    .findViewById(R.id.item_grida_image);
//            holder.item_grida_delete = convertView
//                    .findViewById(R.id.item_grida_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == images.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(
                    inflater.getContext().getResources(), R.drawable.feedback_add));
//            holder.item_grida_delete.setVisibility(View.GONE);
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
//                holder.item_grida_delete.setVisibility(View.GONE);
            }
        } else {
//            holder.item_grida_delete.setVisibility(View.VISIBLE);
            if (images.get(position).contains("http://")) {
                Glide_Image.load(inflater.getContext(), images.get(position), holder.image);
//                ImageDownloadUtils.DownloadImage();
            } else {
                Glide.with(inflater.getContext()).load(new File(images.get(position))).into(holder.image);
            }
        }
//        holder.item_grida_delete.setTag(position);
//        holder.item_grida_delete.setOnClickListener(clickListener);
        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }
}
