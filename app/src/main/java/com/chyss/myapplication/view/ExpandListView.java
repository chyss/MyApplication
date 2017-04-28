package com.chyss.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 重写Listview类，用于嵌套在Scrollview里面，用于加载有限的item列表，onscrolllistener方法无效。使用请注意！！！
 * 
 * @author chyss 2016.7.29 
 *
 */
public class ExpandListView extends ListView
{

	public ExpandListView(Context context)
	{
		super(context);
	}

	public ExpandListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
