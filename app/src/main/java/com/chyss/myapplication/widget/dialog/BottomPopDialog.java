package com.chyss.myapplication.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.chyss.myapplication.R;

public class BottomPopDialog extends Dialog{

	private Context context;
	
	public BottomPopDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom_pop_dailog);
		
		Window window = this.getWindow();
		// 设置显示动画
		window.setGravity(Gravity.LEFT|Gravity.BOTTOM);
		//window.setWindowAnimations(R.style.AnimBottom);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = 0;
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置背景透明度0---1.0f
		wl.dimAmount = 0.1f;
		window.setAttributes(wl);

		// 设置显示位置
		//this.onWindowAttributesChanged(wl);

	}
	
}
