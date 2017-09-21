package com.chyss.myapplication.utils.safe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 防ACTIVITY劫持工具类，主要针对账号密码登录、修改界面，当界面启动过程中被覆盖，则需提醒用户检查环境，提醒存在风险
 *
 *  Activity中调用如下：
 *	@Override
 *	protected void onStop()
 *	{
 *	boolean bool = HijackingUtil.checkActivity(LoginActivity.this);
 *	if (!bool)
 *	{
 *	Toast.makeText(getApplicationContext(), "当前非华人****APP登录界面，请注意账号密码信息安全！", Toast.LENGTH_LONG).show();
 *	}
 *	super.onStop();
 *	}
 * 
 * @author chyss 2016/11/15
 * 
 */
public class HijackingUtil
{

	public static final String TAG = "HijackingUtil";

	/**
	 * 检测当前Activity是否安全
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkActivity(Context context)
	{
		boolean safe = false;
		
		// 查询所有已经安装的应用程序
		PackageManager pm = context.getPackageManager();
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// 排序

		// 白名单列表
		List<String> safePackages = new ArrayList<String>();

		// 得到所有的系统程序包名放进白名单里面.
		for (ApplicationInfo app : listAppcations)
		{
			//系统预装应用
			if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
			{
				safePackages.add(app.packageName);
			}
		}
		

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivityPackageName;
		
		// 取当前运行的包名
		
		if (Build.VERSION.SDK_INT >= 21)
		{
	        runningActivityPackageName = getCurrentPkgName(context);
		}
		else
		{
			runningActivityPackageName = activityManager.getRunningTasks(1).get(0).topActivity
					.getPackageName(); 
		}
		
		if (runningActivityPackageName != null)
		{
			//过滤当前应用
			if (runningActivityPackageName.equals(context.getPackageName()))
			{
				safe = true;
			}

			// 白名单比对，过滤系统应用
			for (String safePack : safePackages)
			{
				if (safePack.equals(runningActivityPackageName))
				{
					safe = true;
				}
			}
		}
		else
		{
			safe = true;
		}

		return safe;
	}

	public static String getCurrentPkgName(Context context)
	{
		// 5x系统以后利用反射获取当前栈顶activity的包名.
		RunningAppProcessInfo currentInfo = null;
		Field field = null;
		int START_TASK_TO_FRONT = 2;
		String pkgName = null;

		try
		{
			// 通过反射获取进程状态字段.
			field = RunningAppProcessInfo.class
					.getDeclaredField("processState");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List appList = am.getRunningAppProcesses();
		RunningAppProcessInfo app;

		for (int i = 0; i < appList.size(); i++)
		{
			// ActivityManager.RunningAppProcessInfo app : appList
			app = (RunningAppProcessInfo) appList.get(i);
			if (app.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
			{
				// 表示前台运行进程.
				Integer state = null;
				try
				{
					// 反射调用字段值的方法,获取该进程的状态.
					state = field.getInt(app);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				if (state != null && state == START_TASK_TO_FRONT)
				{
					// 根据这个判断条件从前台中获取当前切换的进程对象.
					currentInfo = app;
					break;
				}
			}
		}

		if (currentInfo != null)
		{
			pkgName = currentInfo.processName;
		}
		return pkgName;
	}
}