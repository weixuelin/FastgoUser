package com.dingwei.pullrefresh_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable {
    /**
     * 是否需要下拉
     */
    private Boolean canPullDown = true;
    /**
     * 是否需要上拉
     */
    private Boolean canPullUp = true;

    public PullableListView(Context context) {
        super(context);
    }

    public PullableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (canPullDown) {
            if (getCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            } else if (getFirstVisiblePosition() == 0
                    && getChildAt(0).getTop() >= 0) {
                // 滑到ListView的顶部了
                return true;
            } else
                return false;
        } else {
            return false;
        }

    }

    @Override
    public boolean canPullUp() {
        if (canPullUp) {
            if (getCount() == 0) {
                // 没有item的时候也可以上拉加载
                return true;
            } else if (getLastVisiblePosition() == (getCount() - 1)) {
                // 滑到底部了
                if (getChildAt(getLastVisiblePosition()
                        - getFirstVisiblePosition()) != null
                        && getChildAt(
                        getLastVisiblePosition()
                                - getFirstVisiblePosition())
                        .getBottom() <= getMeasuredHeight())
                    return true;
            }
        } else {
            return false;
        }
        return false;

    }

    /**
     * 是否需要下拉
     *
     * @param canPullDown
     */
    public void setCanPullDown(Boolean canPullDown) {
        this.canPullDown = canPullDown;
    }

    /**
     * 是否需要上拉
     *
     * @param canPullUp
     */
    public void setCanPullUp(Boolean canPullUp) {
        this.canPullUp = canPullUp;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
