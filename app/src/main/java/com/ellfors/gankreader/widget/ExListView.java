package com.ellfors.gankreader.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自适应ScrollView的ListView
 */
public class ExListView extends ListView
{
    public ExListView(Context context)
    {
        super(context);
    }

    public ExListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
