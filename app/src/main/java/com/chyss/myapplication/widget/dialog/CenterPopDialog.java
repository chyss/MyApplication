package com.chyss.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chyss.myapplication.R;

public abstract class CenterPopDialog extends Dialog{

	public static final float WIDTH_SCALE = 0.9f;  //设置dialog的宽度为屏幕的倍数
	public static final float DIMACOUNT = 0.2f;    //设置dialog弹出后背景的明暗度
	private Context context;

	private TextView dialog_left,dialog_right,dialog_only;
	private TextView center_pop_title,center_pop_content;
	private LinearLayout ll_1,ll_2;

	private String title,contents;
	private String left,right;
	private int type;  // 1 : 只显示一个button    2 ： 显示两个button

	public CenterPopDialog(Context context, String title,String contents,int type,String left,String right) {
		super(context, R.style.dialog);
		this.context = context;
		this.title = title;
		this.contents = contents;
		this.type = type;
		this.left = left;
		this.right = right;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.center_pop_dailog);
		setDimm();
		init();
	}

	private void init()
	{
		center_pop_title = (TextView)findViewById(R.id.center_pop_title);
		center_pop_content = (TextView)findViewById(R.id.center_pop_content);
		ll_1 = (LinearLayout)findViewById(R.id.ll_1);
		ll_2 = (LinearLayout)findViewById(R.id.ll_2);
		dialog_left = (TextView)findViewById(R.id.dialog_left);
		dialog_right = (TextView)findViewById(R.id.dialog_right);
		dialog_only = (TextView)findViewById(R.id.dialog_only);
		dialog_left.setOnClickListener(onClickListener);
		dialog_right.setOnClickListener(onClickListener);
		dialog_only.setOnClickListener(onClickListener);

		initData();
	}

	private void initData()
	{
		center_pop_title.setText(title);
		center_pop_content.setText(Html.fromHtml(contents));

		if(type == 1)
		{
			ll_1.setVisibility(View.VISIBLE);
			ll_2.setVisibility(View.GONE);
			dialog_only.setText(left);
		}
		else
		{
			ll_1.setVisibility(View.GONE);
			ll_2.setVisibility(View.VISIBLE);
			dialog_left.setText(left);
			dialog_right.setText(right);
		}
	}

	View.OnClickListener onClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.dialog_left:
					left();
					break;
				case R.id.dialog_right:
					right();
				break;
				case R.id.dialog_only:
					left();
					break;
				default:
				break;
			}
		}
	};

	private void setDimm()
	{
		Window window = this.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		//wl.width = ViewGroup.LayoutParams.MATCH_PARENT; //设置宽度和屏幕一样
		Point point = new Point();
		window.getWindowManager().getDefaultDisplay().getSize(point);
		wl.width = (int) (point.x * WIDTH_SCALE);
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		wl.dimAmount = DIMACOUNT;
		this.onWindowAttributesChanged(wl);
	}

	public abstract void left();
	public abstract void right();
}
