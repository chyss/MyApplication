package com.chyss.myapplication.utils;

import android.content.Context;
import android.content.Intent;

/**
 * @author chyss 2017-05-05
 */

public class DoAction
{
    /**
     *  普通activity的跳转
     *
     * @param context
     * @param t
     * @param <T>
     */
    public static <T> void startActivity(Context context,Class<T> t)
    {
        Intent intent = new Intent(context, t);
        context.startActivity(intent);
    }

    /**
     *  FLAG_ACTIVITY_NEW_TASK模式activity的跳转
     *
     * @param context
     * @param t
     * @param <T>
     */
    public static <T> void startActivityNewTask(Context context,Class<T> t)
    {
        Intent intent = new Intent(context,t);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //必须是FLAG_ACTIVITY_NEW_TASK模式
        context.startActivity(intent);
    }
}
