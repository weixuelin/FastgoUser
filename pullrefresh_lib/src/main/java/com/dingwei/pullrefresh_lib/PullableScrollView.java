package com.dingwei.pullrefresh_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.dingwei.pullrefresh_lib.MyOnTouchLisener;
import com.dingwei.pullrefresh_lib.Pullable;


public class PullableScrollView extends ScrollView implements Pullable {
    Scroller mScroller;

    /**
     * 是否需要下拉
     */
    private Boolean canPullDown = true;
    /**
     * 是否需要上拉
     */
    private Boolean canPullUp = true;

    public PullableScrollView(Context context) {
        super(context);
        mScroller = new Scroller(context);
        init();
    }

    public PullableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        init();
    }

    public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean canPullDown() {
        if (canPullDown) {
            if (getScrollY() == 0)
                return true;
            else
                return false;
        } else {
            return false;
        }

    }

    @Override
    public boolean canPullUp() {
        if (canPullUp) {
            if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
                return true;
            else
                return false;
        } else {
            return false;
        }

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

    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
    private int mTouchSlop; // 一个距离 如果小于 不触发效果 大于 触发移动
    private OnScrollListener onScrollListener;

    /**
     * 与ViewPager 滑动冲突处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                } else if (yDistance > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public MyOnTouchLisener listener;

    public void setOnMyTouchListener(MyOnTouchLisener listener) {
        this.listener = listener;
    }


    //调用此方法滚动到目标位置  duration滚动时间
    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();  普通view使用这种方法
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBySlow(int dx, int dy, int duration) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);//scrollView使用的方法（因为可以触摸拖动）
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }


    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (onScrollListener != null) {
//            onScrollListener.onScroll(t);
//        }
//    }

    /**
     * 滚动的回调接口
     *
     * @author xiaanming
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         *
         * @param scrollY 、
         */
        public void onScroll(int scrollY);
    }

    /**
     * @author Cyril Mottier
     */
    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
//            onScrollListener.onScroll(t);
        }
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }
}
