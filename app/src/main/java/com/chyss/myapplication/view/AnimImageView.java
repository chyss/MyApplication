package com.chyss.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chyss.myapplication.R;

/**
 * 自定义imageview，在加载图片时增加动画效果
 * 
 * @author chyss 2016.7.5
 *
 */
public class AnimImageView extends ImageView{
	
	private Context context;
	
	public AnimImageView(Context context) {
		super(context);
		this.context = context;
	}
	
	public AnimImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	
//	public void setImageRes(int resource)
//	{
//		setImageResource(resource);
//		//加载动画时增加动画效果
//		//setAnimation(AnimationUtils.loadAnimation(context, R.anim.moo_in)); //此方法在部分系统中只有第一次调用有动画 。4.3版本每次都出现，5.0版本只有第一次出现
//		startAnimation(AnimationUtils.loadAnimation(context, R.anim.moo_in)); //立即启动动画
//	}
	
	@Override
	public void setImageResource(int resId)
	{
		super.setImageResource(resId);
		startAnimation(AnimationUtils.loadAnimation(context, R.anim.moo_in)); //立即启动动画
	}
	
	@Override
	public void setImageBitmap(Bitmap bm)
	{
		super.setImageBitmap(bm);
		startAnimation(AnimationUtils.loadAnimation(context, R.anim.moo_in)); //立即启动动画
	}
	
	@Override
	public void setImageDrawable(Drawable drawable)
	{
		super.setImageDrawable(drawable);
		//startAnimation(AnimationUtils.loadAnimation(context, R.anim.moo_in)); //不能添加
	}
	
	/**
	 * 该操作读取位图，并在 UI 线程中解码，因此可能导致反应迟缓。
	 * 如果反应迟缓，可以考虑用 setImageDrawable(Drawable)、 setImageBitmap(Bitmap) 或者 BitmapFactory 代替。
	 */
	@Override
	public void setImageURI(Uri uri)
	{
		// TODO Auto-generated method stub
		super.setImageURI(uri);
		startAnimation(AnimationUtils.loadAnimation(context, R.anim.moo_in)); //立即启动动画
	}
}
