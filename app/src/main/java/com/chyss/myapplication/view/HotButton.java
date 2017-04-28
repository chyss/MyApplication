package com.chyss.myapplication.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.UnitUtils;
import com.chyss.myapplication.utils.image.ImageUtil;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

public class HotButton extends LinearLayout
{
	private Context context;
	private String hotName;
	private String imgUrl;
	private int type;
	private OnClickListener onClickListener;

	public HotButton(Context context, String hotName, String imgUrl, int type,
			OnClickListener onClickListener)
	{
		super(context);
		this.context = context;
		this.hotName = hotName;
		this.imgUrl = imgUrl;
		this.type = type;
		this.onClickListener = onClickListener;
		init();
	}

	public HotButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		// init();
	}

	private void init()
	{

		View view;
		LayoutParams params;
		if (type == 1) {
			view = inflate(context, R.layout.search_hot_button, null);
			TextView hotNameText = (TextView) view
					.findViewById(R.id.search_hot_button_tex);
			hotNameText.setText(hotName);
			params = new LayoutParams(LayoutParams.MATCH_PARENT, UnitUtils.dp2px(context, 50));
		} else {
			view = inflate(context, R.layout.search_hot_button2, null);
			TextView hotNameText = (TextView) view
					.findViewById(R.id.search_hot_button2_tex);
			ImageView hotImg = (ImageView) view
					.findViewById(R.id.search_hot_button2_img);

			hotNameText.setText(hotName);
			if (!"".equals(imgUrl)) {

				ImageUtil.displayImage(imgUrl, hotImg,
						new ImageLoadingProgressListener()
						{

							@Override
							public void onProgressUpdate(String imageUri,
									View view, int curent, int total)
							{

								Log.e("TAG", "imageUri = " + imageUri
										+ ",curent = " + curent + ",total = "
										+ total);
							}
						});
			}
			
			params = new LayoutParams(LayoutParams.MATCH_PARENT,
					UnitUtils.dp2px(context, 104));
		}

		params.topMargin = UnitUtils.dp2px(context, 2);
		params.bottomMargin = UnitUtils.dp2px(context, 2);
		this.addView(view, params);
		this.setOnClickListener(onClickListener);
	}
}
