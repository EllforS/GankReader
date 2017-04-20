package com.ellfors.gankreader.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * 解决5.0以上RecyclerView滑动卡顿的ScrollView
 */
public class ExScrollView extends ScrollView
{
    private int downX;
    private int downY;
    private int mTouchSlop;

    public ExScrollView(Context context)
    {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ExScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ExScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e)
    {
        int action = e.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop)
                {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}