package com.chyss.myapplication;

import com.alipay.euler.andfix.patch.PatchManager;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication
{

	@Override
	public void onCreate() {
		super.onCreate();

		Fresco.initialize(this);

		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				this).writeDebugLogs()
				.memoryCacheSizePercentage(20)//设置占用内存的百分比
				.diskCacheFileCount(100)//设置最大下载图片数
				.diskCacheSize(5 * 1024 * 1024)
				.build();

		ImageLoader.getInstance().init(imageLoaderConfiguration);

		// 热修复 AndFix
		PatchManager patchManager = new PatchManager(this);
		patchManager.init(Net.VERSIONCODE);//current version
		patchManager.loadPatch();

		try
		{
			// 获取内置SD卡路径
			String path = Environment.getExternalStorageDirectory().getPath()+"/chyss/app-andfix";
			patchManager.addPatch(path);
		}
		catch (Exception e)
		{
			Logg.e(Net.TAG, "andfix error : " + e);
		}


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
