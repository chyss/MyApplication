package com.chyss.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.UnitUtils;

/**
 * Created by Administrator on 2017/4/12.
 */
public class MaxHeightScrollView extends ScrollView
{
    private Context mContext;
    private int maxHeight;  //.dp

    public MaxHeightScrollView(Context context)
    {
        super(context);
        init(context);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);
        this.maxHeight = (int) array.getDimension(R.styleable.MaxHeightScrollView_maxHeight,238);
        array.recycle();
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        try {
            //设置控件高度为最高 MAX_HEIGHT
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(UnitUtils.dp2px(mContext,maxHeight), MeasureSpec.AT_MOST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

