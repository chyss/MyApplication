package com.chyss.myapplication.widget.linearsv;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.chyss.myapplication.utils.Logg;

import java.io.InputStream;

/**
 * @author chyss 2017-11-13
 */

public class LinearDScrollView extends LinearLayout
{
    private int mViewHeight;
    private int mViewWidth;
    private float move;
    private float y0;
    private View childView;
    private static final int k = 2;  //弹性因子
    private static final int time = 10;  //刷新频率 ，ms

    public LinearDScrollView(Context context)
    {
        super(context);
    }

    public LinearDScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LinearDScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                handler.removeMessages(0);
                y0 = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                move = (ev.getY() - y0) / k;
                requestLayout();

                break;
            case MotionEvent.ACTION_UP:

                handler.sendEmptyMessageDelayed(0,time);

                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        childView.layout(0,(int)move,mViewWidth,(int)(mViewHeight+move));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();

        childView = getChildAt(0);
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:

                    //Logg.e("move","--------------------------- move--"+move);

                    if(move > 0)
                    {
                        move  = move - (move / (5 * k) +1);
                    }
                    else
                    {
                        move  = move - (move / (5 * k) -1);
                    }

                    if(move > -1 && move <1)
                    {
                        move = 0;
                        removeMessages(0);
                    }
                    else
                    {
                        handler.sendEmptyMessageDelayed(0,time);
                    }

                    requestLayout();

                    break;
            }
        }
    };
}
