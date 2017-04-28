package com.chyss.myapplication.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * 提供各类通用工具
 *
 * @author chyss 2016-11-30
 */
public class CommUtils
{

    /**
     * apk安装调用的api
     *
     * @param context      上下文
     * @param saveFileName apk文件的保存路径
     */
    public static void installApk(Context context, String saveFileName)
    {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists())
        {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * 收起通知栏
     *
     * @param context
     */
    public static void collapseStatusBar(Context context)
    {
        try
        {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;

            if (Build.VERSION.SDK_INT <= 16)
            {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else
            {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    /**
     * @return 返回当前时间
     */
    public static String getTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm",
                Locale.SIMPLIFIED_CHINESE);
        return format.format(new Date());
    }
}
