package com.chyss.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseActivity extends Activity {

	private LinearLayout view_container;
	private ImageView rightImg, leftImg;
	private TextView title;
	private RelativeLayout title_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.base_view_layout);

		init();
	}

	private void init() {

		title_view = (RelativeLayout) findViewById(R.id.title_view);
		view_container = (LinearLayout) findViewById(R.id.view_container);
		rightImg = (ImageView) findViewById(R.id.base_right_img);
		leftImg = (ImageView) findViewById(R.id.base_left_img);
		title = (TextView) findViewById(R.id.base_title);
		
		leftImg.setOnClickListener(onClickListener);
		rightImg.setOnClickListener(onClickListener);
	}

	@Override
	public void setContentView(int layoutResID) {

		ViewGroup mainView = (ViewGroup) LayoutInflater.from(this).inflate(
				layoutResID, null);
		setContentView(mainView);
	}

	@Override
	public void setContentView(View view) {
		if (view_container.getChildCount() > 1) {
			view_container.removeViewAt(1);
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, 1);
		view_container.addView(view, lp);
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.base_left_img:

				leftBtnClick();
				break;
			case R.id.base_right_img:

				rightBtnClick();
				break;
			default:
				break;
			}
		}
	};

	public void leftBtnClick() 
	{
		
	}
	
	public void rightBtnClick() 
	{
		
	}
	
	public void setTitle(String strTitle)
	{
		this.title.setText(strTitle);
	}
	
	public void addTitleView()
	{
		title_view.setVisibility(View.VISIBLE);
	}
	
	public void addLeftBtn()
	{
		leftImg.setVisibility(View.VISIBLE);
	}
	
	public void addRightBtn()
	{
		rightImg.setVisibility(View.VISIBLE);
	}
	
	public void missTitleView()
	{
		title_view.setVisibility(View.GONE);
	}
	
	public void missLeftBtn()
	{
		leftImg.setVisibility(View.GONE);
	}
	
	public void missRightBtn()
	{
		rightImg.setVisibility(View.GONE);
	}
	
	public void setRightImg(int imgId)
	{
		rightImg.setImageResource(imgId);
	}
	
	public void setRightImg(Bitmap bm)
	{
		rightImg.setImageBitmap(bm);
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
	}

	@Override
	public void startActivity(Intent intent)
	{
		super.startActivity(intent);
		overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
	}
}
