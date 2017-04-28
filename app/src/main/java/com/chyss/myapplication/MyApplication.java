package com.chyss.myapplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		Fresco.initialize(this);

		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				this).writeDebugLogs()
				.memoryCacheSizePercentage(20)//设置占用内存的百分比
				.diskCacheFileCount(100)//设置最大下载图片数
				.diskCacheSize(5 * 1024 * 1024)
				.build();

		ImageLoader.getInstance().init(imageLoaderConfiguration);

		// File cacheDir = StorageUtils.getCacheDirectory(context);
		// ImageLoaderConfiguration config = new
		// ImageLoaderConfiguration.Builder(context)
		// .memoryCacheExtraOptions(480, 800) // default = device screen
		// dimensions
		// .diskCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
		// .taskExecutor(...)
		// .taskExecutorForCachedImages(...)
		// .threadPoolSize(3) // default
		// .threadPriority(Thread.NORM_PRIORITY - 1) // default
		// .tasksProcessingOrder(QueueProcessingType.FIFO) // default
		// .denyCacheImageMultipleSizesInMemory()
		// .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		// .memoryCacheSize(2 * 1024 * 1024)
		// .memoryCacheSizePercentage(13) // default
		// .diskCache(new UnlimitedDiscCache(cacheDir)) // default
		// .diskCacheSize(50 * 1024 * 1024)
		// .diskCacheFileCount(100)
		// .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) //
		// default
		// .imageDownloader(new BaseImageDownloader(context)) // default
		// .imageDecoder(new BaseImageDecoder()) // default
		// .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) //
		// default
		// .writeDebugLogs()
		// .build();
	}
}
