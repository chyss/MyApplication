package com.chyss.myapplication.widget.bluetooth.phone;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.utils.Logg;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author chyss 2017-05-05
 */

public class BlueServiceActivity extends BaseActivity
{
    BluetoothAdapter bluetoothAdapter;
    //和客户端相同的UUID
    private UUID connUUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");
    private final String NAME = "Bluetooth_Socket";
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private InputStream is;//输入流
    private Message msg;
    private int DEFIND_BLUETOOTH = 10001;

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
        //蓝牙设置蓝牙可见
        Intent blueIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //设置蓝牙可见时间
        blueIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivityForResult(blueIntent, DEFIND_BLUETOOTH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Toast.makeText(this, "蓝牙打开成功："+requestCode+"--------" + resultCode, Toast.LENGTH_SHORT).show();
        //回调蓝牙打开情况
        if (requestCode == DEFIND_BLUETOOTH)
        {
            getData();
        }
        else
        {

        }
    }

    private void getData()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, connUUID);
                    socket = serverSocket.accept();
                    is = socket.getInputStream();
                    while(true) {
                        byte[] buffer =new byte[1024];
                        int count = is.read(buffer);
                        msg = new Message();
                        msg.obj = new String(buffer, 0, count, "utf-8");
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(BlueServiceActivity.this,String.valueOf(msg.obj),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                catch (Exception e) {
                    Logg.e("bluetooth","bluetooth error : "+e);
                }
            }
        }).start();
    }
}
