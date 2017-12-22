package com.dingwei.pullrefresh_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Andy.Li on 2017/3/27.
 */
public class PullableRL extends RelativeLayout implements Pullable {
    public PullableRL(Context context) {
        super(context);
    }

    public PullableRL(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableRL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
    }
}
