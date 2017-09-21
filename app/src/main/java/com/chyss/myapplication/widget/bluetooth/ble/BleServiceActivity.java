package com.chyss.myapplication.widget.bluetooth.ble;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author chyss 2017-05-05
 */

public class BleServiceActivity extends BaseActivity
{
    BluetoothAdapter bluetoothAdapter;
    BluetoothGattServer bluetoothGattServer;

    private String serverUUID = "00001801-0000-1000-8000-00805f123456";
    private String charecUUID = "00002a05-0000-1000-8000-00805f123456";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initData();
    }

    /**
     * 判断手机是否支持蓝牙设备
     */
    private void initData()
    {
        //判断是否支持蓝牙设备
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null)
        {
            //不支持蓝牙设备
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }

        //<!--声明你的应用程序只能在支持BLE的设备上运行 蓝牙4.0-->
        //<uses-feature android:name="android.hardware.bluetooth_le" android:required="true">
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(this, "不支持蓝牙4.0", Toast.LENGTH_SHORT).show();
            return;
        } else
        {
            Toast.makeText(this, "支持蓝牙4.0", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        // 打开蓝牙
        // 也可以直接调用bluetoothAdapter.enable(),bluetoothAdapter.disable()来启用/禁用蓝牙,不过这种方式不会弹出询问对话框。
        if (!bluetoothAdapter.isEnabled())
        {
            //打开蓝牙
            bluetoothAdapter.enable();
        }

        connect();
    }

    private void connect()
    {
        BluetoothManager bm = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothGattServer = bm.openGattServer(this,serverCallback);

        BluetoothGattCharacteristic character = new BluetoothGattCharacteristic(UUID.fromString(charecUUID),BluetoothGattCharacteristic.PROPERTY_NOTIFY,BluetoothGattCharacteristic.PERMISSION_READ);
        //BluetoothGattServer service = new BluetoothGattServer(UUID.fromString(serverUUID),BluetoothGattServer.GATT_SERVER,1);
    }

    BluetoothGattServerCallback serverCallback = new BluetoothGattServerCallback()
    {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState)
        {
            Logg.e("ble","onConnectionStateChange newState : "+newState);
            super.onConnectionStateChange(device, status, newState);
        }
    };
}
