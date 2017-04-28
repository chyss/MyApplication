package com.chyss.myapplication.utils;

import android.util.Log;

/**
 * Log 打印
 *
 * create by chyss on 2017/4/6
 */
public class Logg
{
	private static boolean isDebug = true;

	/**
	 * 用于打印错误log，不提供isDebug开关，用于出错、异常的打印
	 *
	 * @param tag
	 * @param log
     */
	public static void e(String tag,String log)
	{
		Log.e(tag, log);
	}
	
	public static void d(String tag,String log)
	{
		if (isDebug)
		{
			Log.d(tag, log);
		}
	}
	
	public static void i(String tag,String log)
	{
		if (isDebug)
		{
			Log.i(tag, log);
		}
	}
	
	public static void v(String tag,String log)
	{
		if (isDebug)
		{
			Log.v(tag, log);
		}
	}
	
	public static void w(String tag,String log)
	{
		if (isDebug)
		{
			Log.w(tag, log);
		}
	}
}
