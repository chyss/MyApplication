package com.chyss.myapplication.utils.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageUtil {

	public static void displayImage(String imageUrl, ImageView imView) {
		// 显示图片的配置
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.ARGB_8888).build();

		ImageLoader.getInstance().displayImage(imageUrl, imView, options);
	}

	public static void displayImage(String imageUrl, ImageView imView,
			ImageLoadingProgressListener imageLoadingProgressListener) {
		// 显示图片的配置
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.ARGB_8888).build();

		ImageLoader.getInstance().displayImage(imageUrl, imView, options,
				new SimpleImageLoadingListener(), imageLoadingProgressListener);
	}
}
