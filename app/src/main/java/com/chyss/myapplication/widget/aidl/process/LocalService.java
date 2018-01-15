package com.chyss.myapplication.widget.aidl.process;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.chyss.myapplication.utils.Logg;

public class LocalService extends Service
{
    MyBinder binder;
    MyConn conn;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logg.e("aidl","onCreate ----------------------- local");
        binder = new MyBinder();
        conn = new MyConn();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Logg.e("aidl","onStartCommand ----------------------- local");

        startRemoteService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Logg.e("aidl","onBind ----------------------- local");

        // 增加权限验证
        int check = checkCallingOrSelfPermission("com.chyss.myapplication.permission.ACCESS_LOCALSERVICE");
        if(check == PackageManager.PERMISSION_DENIED)
        {
            return null;
        }

        startRemoteService();
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
            Logg.e("aidl","connect LocalService!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            // 与远程服务断开

            Logg.e("aidl","disconnect LocalService!");
            startRemoteService();
        }
    }

    private void startRemoteService()
    {
        Logg.e("aidl","-----------------startRemoteService---------------------");

        Intent intent = new Intent(LocalService.this,RemoteService.class);
        startService(intent);
        bindService(intent,conn, Context.BIND_IMPORTANT);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Logg.e("aidl","-----------------onDestroy--------local-------------");

        startRemoteService();
    }
}
