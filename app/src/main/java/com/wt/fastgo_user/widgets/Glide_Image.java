package com.wt.fastgo_user.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wt.fastgo_user.R;


public class Glide_Image {

    public static void load(Context context, String img, ImageView image, Transformation<Bitmap> transformation) {
        Glide.with(context).load(img)
                .placeholder(R.drawable.icon_fount) //设置占位图，在加载之前显示
                .error(R.drawable.icon_fount) //在图像加载失败时显示
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) //将显示出来大小的图像缓存在硬盘(默认缓存策略)
                .crossFade(500)
                .bitmapTransform(transformation)
                .into(image);
    }
    public static void load(Context context, String img, ImageView image) {
        Glide.with(context).load(img)
                .placeholder(R.drawable.icon_fount) //设置占位图，在加载之前显示
                .error(R.drawable.icon_fount) //在图像加载失败时显示
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) //将显示出来大小的图像缓存在硬盘(默认缓存策略)
                .crossFade(500)
                .into(image);
    }



    /**
     * 加载 变换图片
     *
     * @param context
     * @param img
     * @param image
     * @param transformation
     */
    public static void loadWithChange(Context context, String img, ImageView image, Transformation<Bitmap> transformation) {

        Glide.with(context).load(img)
                .placeholder(R.drawable.icon_fount) //设置占位图，在加载之前显示
                .error(R.drawable.icon_fount) //在图像加载失败时显示
                .bitmapTransform(transformation)
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //将显示出来大小的图像缓存在硬盘(默认缓存策略)
                .into(image);
    }
}
