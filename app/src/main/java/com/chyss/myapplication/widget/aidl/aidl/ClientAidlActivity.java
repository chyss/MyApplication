package com.chyss.myapplication.widget.aidl.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.CalculAidlInterface;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;

/**
 * @author chyss 2017-12-06
 */
public class ClientAidlActivity extends BaseActivity
{
    private CalculAidlInterface service;

    /**
     * 当我们请求服务端的方法时，客户端的线程会被挂起，直到服务端完成处理。
     * 服务端的方法运行在binder线程池。
     *
     * onServiceConnected, onServiceDisconnected方法都是运行在UI线程，如果调用的服务端的方法是耗时的，
     * 我们需要新开线程，在新线程中调用
     */
    private ServiceConnection conn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder ibinder)
        {
            service = CalculAidlInterface.Stub.asInterface(ibinder);

            try
            {
                // 核心思想在于将原本需要在A进程中计算的任务移到B进程的后台service中去执行。
                int calcul = service.calculation(10,1000);

                Toast.makeText(ClientAidlActivity.this,"calcul = "+calcul,Toast.LENGTH_SHORT).show();
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

        Intent intent = new Intent(this, MyService.class);
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
