package com.chyss.myapplication.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * 为RecyclerView类增加横向回弹效果,要求PullRecyclerViewGroup中必须有且只有一个RecyclerView
 * manager.setOrientation(LinearLayoutManager.HORIZONTAL);
 *
 * @author  chyss 2017/04/24
 */
public class PullRecyclerViewGroupX extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener
{
    // 回滚时间
    private static final long ANIM_TIME = 300;

    // recyclerview
    private View childView;

    // 用于记录正常的布局位置
    private Rect originalRect = new Rect();

    // 在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;

    // 如果按下时不能上拉和下拉， 会在手指移动时更新为当前手指的Y值
    private float startX;

    //阻尼系数
    private static final float OFFSET_RADIO = 0.3f;

    //事件的分发
    private boolean isRecyclerReuslt = false;


    public PullRecyclerViewGroupX(Context context)
    {
        this(context, null);
        init();
    }

    public PullRecyclerViewGroupX(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
        init();
    }

    public PullRecyclerViewGroupX(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        //关闭右侧滚动条
        this.setVerticalScrollBarEnabled(false);
    }

    /**
     * 加载布局后初始化,这个方法会在加载完布局后调用
     */
    @Override
    protected void onFinishInflate()
    {
        //此处为容器中的子view
        if (getChildCount() > 0)
        {
            for (int i = 0; i < getChildCount(); i++)
            {
                if (getChildAt(i) instanceof RecyclerView)
                {
                        childView = getChildAt(i);
                }
            }
        }

        if (childView == null)
        {
            throw new RuntimeException("PullRecyclerViewGroup 中必须有一个RecyclerView");
        }
        //布局重绘监听，比如华为屏幕键盘可以弹出和隐藏，改变布局，加监听就可以虽键盘弹出关闭的变化而变化
        getViewTreeObserver().addOnGlobalLayoutListener(this);

        super.onFinishInflate();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        //ScrollView中唯一的子控件的位置信息，这个位置在整个控件的生命周期中保持不变
        originalRect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
    }

    /**
     * 事件分发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (childView == null)
        {
            return super.dispatchTouchEvent(ev);
        }

        boolean isTouchOutOfScrollView = ev.getX() >= originalRect.right || ev.getX() <= originalRect.left; //如果当前view的Y上的位置
        if (isTouchOutOfScrollView)
        {//如果不在view的范围内
            if (isMoved)
            {      //当前容器已经被移动
                recoverLayout();
            }
            return true;
        }

        int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                //记录按下时的Y
                startX = ev.getX();
            case MotionEvent.ACTION_MOVE:

                float nowX = ev.getX();
                int scrollX = (int) (nowX - startX);
                if ((isCanPullDown() && scrollX > 0) || (isCanPullUp() && scrollX < 0) || (isCanPullDown() && isCanPullUp()))
                {
                    int offset = (int) (scrollX * OFFSET_RADIO);
                    childView.layout(originalRect.left + offset, originalRect.top, originalRect.right + offset, originalRect.bottom);
                    isMoved = true;
                    isRecyclerReuslt = false;
                    return true;
                } else
                {
                    startX = ev.getX();
                    isMoved = false;
                    isRecyclerReuslt = true;
                    recoverLayout();
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:

                if (isMoved)
                {
                    recoverLayout();
                }

                if (isRecyclerReuslt)
                {
                    return super.dispatchTouchEvent(ev);
                } else
                {
                    return true;
                }
            default:
                return true;
        }
    }

    /**
     * 位置还原
     */
    private void recoverLayout()
    {
        if (!isMoved)
        {
            return;//如果没有移动布局，则跳过执行
        }
        TranslateAnimation anim = new TranslateAnimation(childView.getLeft() - originalRect.left, 0, 0, 0);
        anim.setDuration(ANIM_TIME);
        childView.startAnimation(anim);
        childView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);
        isMoved = false;
    }

    /**
     * 容器的的事件都在事件分发中处理，这里处理的是事件分发传递过来的事件，
     * 传递过来的为RecyclerVIew的事件  不拦截，直接交给reyclerview处理
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return false;  //不拦截  直接传递给子的view
    }

    /**
     * 判断是否可以下拉
     *
     * @return
     */
    private boolean isCanPullDown()
    {
        if(((RecyclerView) childView).computeHorizontalScrollOffset() <= 0)
        {
            return true;
        }
        return false;
    }


    /**
     * 判断是否可以上拉
     *
     * @return
     */
    private boolean isCanPullUp()
    {
        if(((RecyclerView) childView).computeHorizontalScrollOffset() + ((RecyclerView) childView).computeHorizontalScrollExtent() >= ((RecyclerView) childView).computeHorizontalScrollRange())
        {
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onGlobalLayout()
    {
        //华为手机屏幕下方的返回、home键显示隐藏改变布局
        requestLayout();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
}
