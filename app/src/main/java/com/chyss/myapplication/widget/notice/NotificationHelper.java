package com.chyss.myapplication.widget.notice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

/**
 * 通知工具类,用于展示推送通知
 * 
 * @author chyss 2016.07.20
 */
public class NotificationHelper
{
	public static final String NOTICE_ID_KEY = "NOTICE_ID";
	public static final String NOTICE_CONTENT = "NOTICE_CONTENT";
	public static final String NOTICE_TITLE = "NOTICE_TITLE";
	public static final String NOTICE_TYPE = "NOTICE_TYPE"; // 用于区分不同的notice的key，key对应得value为上面枚举的值
	public static final String NOTICE_URL = "NOTICE_URL"; // 后续打开web的连接key
	public static final String NOTICE_ACTIVITY = "NOTICE_ACTIVITY"; // 后续打开ACTIVITY
	public static final String ACTION_RECEIVE_NOTICE = "com.chyss.myapplication.notice";
	public static int NOTICE_ID = 0;

	/**
	 * 发送通知入口
	 *
	 * @param context
	 * @param title		通知title
	 * @param content     	通知内容
	 * @param res			通知icon，同常为app icon
	 * @param smallIco		通知smallico
	 * @param ticker		通知ticher
     * @param afterOpen  区分notice的类型，为后续处理点击事件提供类型
     */
	public static void sendNotice(Context context, String title,
			String content,String ticker, String afterOpen,String activity,String url,int res, int smallIco)
	{
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);

		int requestCode = (int) SystemClock.uptimeMillis();
		Intent intent = new Intent(ACTION_RECEIVE_NOTICE);
		intent.putExtra(NOTICE_ID_KEY, NOTICE_ID);
		intent.putExtra(NOTICE_TYPE, afterOpen);
		intent.putExtra(NOTICE_TITLE, title);
		intent.putExtra(NOTICE_CONTENT, content);
		intent.putExtra(NOTICE_URL, url);
		intent.putExtra(NOTICE_ACTIVITY, activity);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification notification = builder.setContentTitle(title)
				.setContentText(content)
				.setSmallIcon(res).setSmallIcon(smallIco)
				.setTicker(ticker).setContentIntent(pendingIntent).build();
		// 设置通知的声音、震动
		notification.defaults = Notification.DEFAULT_SOUND;

		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(NOTICE_ID, notification);

		NOTICE_ID++;
	}

	public static void clearNotification(Context context, int noticeId)
	{
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(noticeId);
	}
}
