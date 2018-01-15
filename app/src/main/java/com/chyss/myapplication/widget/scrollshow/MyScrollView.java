package com.chyss.myapplication.widget.scrollshow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义ScrollView，根据拖动位置做相应UI处理
 *
 * @author chyss 2017-10-12
 */
public class MyScrollView extends ScrollView
{
    private OnScrollListener onScrollListener;

    public MyScrollView(Context context)
    {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);

        if(onScrollListener != null)
        {
            onScrollListener.onScroll(l, t, oldl, oldt,getScrollY(),getHeight());
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener)
    {
        this.onScrollListener = onScrollListener;
    }

    public interface OnScrollListener
    {
        void onScroll(int l, int t, int oldl, int oldt,int scrollY,int height);
    }
}
