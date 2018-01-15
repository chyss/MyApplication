package com.chyss.myapplication.widget.aidl.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.UiThread;
import android.widget.Toast;

import com.chyss.myapplication.utils.Logg;

import java.lang.annotation.Annotation;

public class MessengerService extends Service
{
    Handler mHandler = new Handler();

    public MessengerService()
    {

    }

    private class MessengerHandler extends Handler
    {

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 11:

                    final Bundle bundle = msg.getData();

                    Logg.e("msg","----------------------"+bundle.getString("msg"));

                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {

                            // TODO Auto-generated method stub
                            Toast.makeText(getApplicationContext(), bundle.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    });

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent)
    {
        return mMessenger.getBinder();
    }
}
