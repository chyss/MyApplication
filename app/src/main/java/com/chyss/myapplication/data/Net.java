package com.chyss.myapplication.data;

import com.chyss.myapplication.utils.Logg;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

public class Net
{
	// 服务器的根地址
	public static final String domain = "http://api-and.hrjk-p2p.com/v2/";  //test

	// 服务器版本号
	public static final String VERSIONCODE = "37";
	//平台android/ios
	public static final String PLATFORM = "android";

	// 服务器的数据详细地址
	public static final String version = "versions";

	// 数据地址的合成方法
	public static String getUrl(String url)
	{
		StringBuffer sBuffer = new StringBuffer(domain).append(url);
		Logg.e(TAG, "----" + sBuffer.toString());
		return sBuffer.toString();
	}

	public static final String TAG = "NetRequest";
	public static final int CONNECT_TIMEOUT = 20 * 1000;
	public static final int WRITE_TIMEOUT = 20 * 1000;
	public static final int READ_TIMEOUT = 20 * 1000;

	// 保存地址、文件名
	public static final String savePath = "/sdcard/myapplication/";
	public static final String saveFileName = savePath + "MyApplication.apk";

	// 处理网络异常
	public static String ExceptionCode(Exception e)
	{
		if (e instanceof SocketTimeoutException)
		{
			return "响应超时"; // 响应超时

		} else if (e instanceof ConnectTimeoutException)
		{
			return "请求超时"; // 请求超时

		} else if (e instanceof IOException)
		{
			return "网络异常"; // 网络异常

		} else if (e instanceof JSONException)
		{
			return "网络错误"; // json格式转换异常

		} else if (e instanceof com.google.gson.JsonSyntaxException)
		{
			return "网络错误"; // json格式转换异常

		} else
		{
			return "无法连接网络"; // 无法连接网络
		}
	}
}
