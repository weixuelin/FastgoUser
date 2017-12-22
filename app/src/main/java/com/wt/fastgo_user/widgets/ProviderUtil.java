package com.wt.fastgo_user.widgets;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class ProviderUtil {
    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".provider";
    }
}
