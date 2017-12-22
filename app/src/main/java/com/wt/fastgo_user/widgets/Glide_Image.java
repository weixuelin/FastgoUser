package com.wt.fastgo_user.widgets;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wt.fastgo_user.R;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class Glide_Image {

    public static void load(Context context, String img, ImageView image) {
        Glide.with(context).load(img)
                .placeholder(R.drawable.icon_fount) //设置占位图，在加载之前显示
                .error(R.drawable.icon_fount) //在图像加载失败时显示
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //将显示出来大小的图像缓存在硬盘(默认缓存策略)
                .into(image);
    }
}
