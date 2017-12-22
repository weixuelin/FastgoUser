package com.wt.fastgo_user.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.wt.fastgo_user.applaction.SYApplication;


public class CommonUtils {
    /**
     * dip转化成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转化成dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转化成sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转化成px
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 将自己从父容器中移除
     */
    public static void removeSelfFromParent(View child) {
        // 获取父view
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                // 将自己移除
                viewGroup.removeView(child);
            }
        }
    }

    /**
     * 获取Resource对象
     */
    public static Resources getResources() {
        return SYApplication.getContext().getResources();
    }

    /**
     * 获取字符串数组资源
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }
}


