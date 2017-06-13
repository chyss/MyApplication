package com.chyss.myapplication.widget.bluetooth.ble;


import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.chyss.myapplication.utils.Logg;

/**
 * @author chyss 2017-05-05
 */

public class BleService extends Service
{

    private BluetoothAdapter bluetoothAdapter;
    private String bleName;
    private String bleAddress;


    @Override
    public void onCreate()
    {
        Logg.e("bleservice","onCreate");

        BluetoothManager bm = (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
        if(bm != null)
        {
            bluetoothAdapter = bm.getAdapter();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Logg.e("bleservice","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Logg.e("bleservice","onBind");
        return null;
    }
}
