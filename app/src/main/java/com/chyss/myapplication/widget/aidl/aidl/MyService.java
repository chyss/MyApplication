package com.chyss.myapplication.widget.aidl.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.chyss.myapplication.CalculAidlInterface;

/**
 * 运行下独立进程下的Service
 *
 * android:process=":myservice"
 */
public class MyService extends Service
{
    public MyService()
    {

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return nBinder;
    }

    CalculAidlInterface.Stub nBinder = new CalculAidlInterface.Stub()
    {
        /**
         * 服务中的方法运行在binder线程池中
         *
         * @param anInt
         * @param bnInt
         * @return
         * @throws RemoteException
         */
        @Override
        public int calculation(int anInt, int bnInt) throws RemoteException
        {
            return anInt + bnInt;
        }
    };
}
