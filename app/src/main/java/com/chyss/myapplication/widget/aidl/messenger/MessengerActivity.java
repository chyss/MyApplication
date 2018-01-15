package com.chyss.myapplication.widget.aidl.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;

/**
 * 使用Messenger进行进程间的通信。
 *  Messenger 是以串行的方式处理客户端发来的消息，如果大量的消息同时发送到服务端，Messenger则不适合
 *
 * @author chyss 2017-12-06
 */
public class MessengerActivity extends BaseActivity
{
    private Messenger service;

    private ServiceConnection conn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder ibinder)
        {
            service = new Messenger(ibinder);

            Message msg = Message.obtain(null,11);

            Bundle data = new Bundle();

            data.putString("msg","hello chyss ！");

            msg.setData(data);

            try
            {
                service.send(msg);
            }
            catch (Exception e)
            {
                Logg.e(Net.TAG, "catch error : " + e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setTitle("aidl");

        Intent intent = new Intent(this, MessengerService.class);
        //intent.setAction("com.chyss.myapplication.widget.aidl.aidl.MyService");
        //从 Android 5.0开始 隐式Intent绑定服务的方式已不能使用,所以这里需要设置Service所在服务端的包名
        intent.setPackage("com.chyss.myapplication");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (conn != null) {
            unbindService(conn);
        }
    }
}
