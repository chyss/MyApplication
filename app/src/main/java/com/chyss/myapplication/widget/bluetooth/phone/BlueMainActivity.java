package com.chyss.myapplication.widget.bluetooth.phone;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author chyss 2017-05-05
 */

public class BlueMainActivity extends BaseActivity
{
    public static final String TAG = "BlueMain";
    BluetoothAdapter bluetoothAdapter;
    //和客户端相同的UUID
    private UUID connUUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");
    private BluetoothDevice device;
    private BluetoothSocket clientSocket;
    private OutputStream os;

    private String name;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ble_main_activity);

        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        setTitle(name);

        findViewById(R.id.ble_send_news).setOnClickListener(onClickListener);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initData();
    }

    /**
     * 判断手机是否支持蓝牙设备
     */
    private void initData()
    {
        try
        {
            //获得远程设备
            device = bluetoothAdapter.getRemoteDevice(address);

            //获得已配对的远程蓝牙设备的集合
            Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

            Logg.e(TAG, "开始连接...");
            if (!bondedDevices.contains(device))
            {
                //创建客户端蓝牙Socket
                clientSocket = device.createRfcommSocketToServiceRecord(connUUID);
                //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                clientSocket.connect();
            }
        }
        catch (Exception e)
        {
            Logg.e(Net.TAG, "catch error : " + e);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ble_send_news:

                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Logg.e(Net.TAG, "clientSocket : " + clientSocket);
                                if (clientSocket == null)
                                {
                                    //创建客户端蓝牙Socket
                                    clientSocket = device.createRfcommSocketToServiceRecord(connUUID);
                                    //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                                    clientSocket.connect();
                                    //获得输出流（客户端指向服务端输出文本）
                                    os = clientSocket.getOutputStream();
                                }

                                Logg.e(Net.TAG, "os : " + os);
                                if(os != null)
                                {
                                    os.write("life like a song！".getBytes("utf-8"));
                                }
                            }
                            catch (Exception e)
                            {
                                Logg.e(Net.TAG, "catch error : " + e);
                            }
                        }
                    }).start();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy()
    {
        try
        {
            if(os != null)
            {
                os.close();
            }
            if(clientSocket != null)
            {
                clientSocket.close();
            }
        }
        catch (Exception e)
        {
            Logg.e(Net.TAG, "catch error : " + e);
        }
        super.onDestroy();
    }
}
