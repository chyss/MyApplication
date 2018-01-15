package com.chyss.myapplication.widget.aidl.process;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.chyss.myapplication.utils.Logg;

public class RemoteService extends Service
{
    MyBinder binder;
    MyConn conn;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logg.e("aidl","onCreate ----------------------- Remote");
        binder = new MyBinder();
        conn = new MyConn();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Logg.e("aidl","onStartCommand ----------------------- Remote");
        //startLocalService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Logg.e("aidl","onBind ----------------------- Remote");
        //startLocalService();
        return binder;
    }

    class MyBinder extends IProcessAidlInterface.Stub
    {
        @Override
        public String getServiceName() throws RemoteException
        {
            return LocalService.class.getSimpleName();
        }
    }

    class MyConn implements ServiceConnection
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            //绑定了远程服务

            Logg.e("aidl","connect RemoteService!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Logg.e("aidl","disconnect RemoteService!");
            // 与远程服务断开
            startLocalService();
        }
    }

    private void startLocalService()
    {
        Logg.e("aidl","-----------------startLocalService---------------------");

        Intent intent = new Intent(RemoteService.this,LocalService.class);
        startService(intent);
        bindService(intent,conn, Context.BIND_IMPORTANT);

//        intent.setPackage("com.chyss.myapplication");
//        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Logg.e("aidl","-----------------onDestroy--------remote-------------");

        startLocalService();
    }
}
