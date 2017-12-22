package com.wt.fastgo_user.widgets;

import android.content.Context;
import android.content.Intent;

import com.wt.fastgo_user.ui.ClickButtonActivity;


/**
 * Created by zhaoshuo on 2016/3/17.
 */
public class StartUtils {
    public static void startActivityById(Context context, int resId){
        Intent intent = new Intent(context, ClickButtonActivity.class);
        intent.putExtra("resId",resId);
        context.startActivity(intent);
    }
//    public static void startActivityByIdForResult(Fragment activity, int resId , int requestCode){
//        Intent intent = new Intent(activity, ClickButtonActivity.class);
//        intent.putExtra("resId",resId);
//        activity.startActivityForResult(intent,requestCode);
//    }

}
