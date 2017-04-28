package com.chyss.myapplication.widget.webview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

/**
 * 横向progressbar在设置progress时增加动画，减少视觉跳跃，提供用户体验
 * 
 * @author chyss 2016.7.6
 *
 */
public class ProgressbarWeb extends ProgressBar{
	
	public static final int TIME = 500; //缓冲动画时间
	private Context context;
	public int currentPoint = 0;
	private ValueAnimator anim;
	
	public ProgressbarWeb(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	@SuppressLint("NewApi") 
	public void setProg(int progress)
	{
		cancelAnim();
		anim = ValueAnimator.ofInt(currentPoint,progress);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  
            @Override  
            public void onAnimationUpdate(ValueAnimator animation) {  
                currentPoint = (Integer) animation.getAnimatedValue();  
                setProgress(currentPoint);
                if (currentPoint >= 100) {
    	        	setVisibility(View.GONE);
    	        	currentPoint = 0;
    			}
            }  
        });  
        anim.setDuration(TIME);
        anim.start(); 
	}
	
	@SuppressLint("NewApi")
	public void cancelAnim()
	{
		if (anim != null && anim.isRunning()) {
			anim.cancel();
			anim = null;
		}
	}
}
