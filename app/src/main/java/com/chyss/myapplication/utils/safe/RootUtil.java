package com.chyss.myapplication.utils.safe;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 	检查手机系统的root权限工具类
 * 	private void checkRoot()
 *  {
 *  	boolean root = RootUtil.isDeviceRooted();
 *  	if (root) {
 *  		Toast.makeText(WelcomeActivity.this, "您的手机root权限已被获取，请注意数据信息安全！", Toast.LENGTH_LONG).show();
 *  	}
 * 		else
 *  	{
 *  		//Toast.makeText(WelcomeActivity.this, "手机未root！", Toast.LENGTH_LONG).show();
 * 		}
 *  }
 *
 *	 @author chyss 2016/11/17
 *
 */
public class RootUtil
{

	/**
	 * 检查手机系统的root权限
	 * 
	 * @return  is rooted or not root
	 */
	public static boolean isDeviceRooted()
	{

		return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
				|| checkRootMethod4();
	}

	private static boolean checkRootMethod1()
	{

		String buildTags = android.os.Build.TAGS;

		return buildTags != null && buildTags.contains("test-keys");
	}

	private static boolean checkRootMethod2()
	{

		return new File("/system/app/Superuser.apk").exists();
	}

	private static boolean checkRootMethod3()
	{

		String[] paths = { "/sbin/su", "/system/bin/su", "/system/xbin/su",
				"/data/local/xbin/su", "/data/local/bin/su",
				"/system/sd/xbin/su",
				"/system/bin/failsafe/su", "/data/local/su" };

		for (String path : paths) {

			if (new File(path).exists())
				return true;

		}

		return false;
	}

	private static boolean checkRootMethod4()
	{
		Process process = null;

		try {

			process = Runtime.getRuntime().exec(
					new String[] { "/system/xbin/which", "su" });

			BufferedReader in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			if (in.readLine() != null)
				return true;

			return false;

		} catch (Throwable t) {

			return false;

		} finally {
			if (process != null)
				process.destroy();
		}
	}
}
