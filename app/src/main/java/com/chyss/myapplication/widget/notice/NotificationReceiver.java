package com.chyss.myapplication.widget.notice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chyss.myapplication.activity.NoticeActivity;
import com.chyss.myapplication.utils.CommUtils;
import com.chyss.myapplication.utils.DoAction;
import com.chyss.myapplication.widget.webview.WebView;

/**
 * 通知栏的点击事件处理类
 *
 * @author chuo.qin 2016.7.20
 */
public class NotificationReceiver extends BroadcastReceiver
{

    private Context context;
    private String title;
    private String content;
    private String afterOpen;
    private String activity;
    private String url;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        int noticeId = intent.getIntExtra(NotificationHelper.NOTICE_ID_KEY, -1);
        if (noticeId != -1)
        {
            NotificationHelper.clearNotification(context, noticeId);
        }
        title = intent.getStringExtra(NotificationHelper.NOTICE_TITLE);
        content = intent.getStringExtra(NotificationHelper.NOTICE_CONTENT);
        afterOpen = intent.getStringExtra(NotificationHelper.NOTICE_TYPE);
        activity = intent.getStringExtra(NotificationHelper.NOTICE_ACTIVITY);
        url = intent.getStringExtra(NotificationHelper.NOTICE_URL);
        //收起通知栏
        CommUtils.collapseStatusBar(context);
        doAction();
    }

    //点击通知的事件处理
    private void doAction()
    {
        if("to_activity".equals(afterOpen))
        {
            if("notice".equals(activity))
            {
                DoAction.startActivityNewTask(context, NoticeActivity.class);
            }
        }
        else if("to_web".equals(afterOpen))
        {
            WebView.loadFromNotice(context,url,title);
        }
        else if("to_app".equals(afterOpen))
        {

        }
        else if("to_custom".equals(afterOpen))
        {

        }
    }
}
