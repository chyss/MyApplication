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
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
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
    BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> devices = new ArrayList<>();
    //和客户端相同的UUID
    private UUID connUUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");
    private BluetoothDevice device;
    private BluetoothSocket clientSocket;
    private OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initData();
    }

    /**
     * 判断手机是否支持蓝牙设备
     */
    private void initData()
    {
        //获得已配对的远程蓝牙设备的集合
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice bondedDevice : bondedDevices)
        {
            Logg.e("device","bondedDevice : "+bondedDevice.getAddress());
            if("A4:3D:78:7C:AC:4A".equals(bondedDevice.getAddress()))
            {

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String address = "A4:3D:78:7C:AC:4A";
                        Logg.e(Net.TAG, "address : " + address);
                        try
                        {
                            if (device == null)
                            {
                                //获得远程设备
                                device = bluetoothAdapter.getRemoteDevice(address);
                            }
                            if (clientSocket == null)
                            {
                                //创建客户端蓝牙Socket
                                clientSocket = device.createRfcommSocketToServiceRecord(connUUID);
                                //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                                Logg.e(Net.TAG, "如果没有配对则弹出对话框提示我们进行配对");
                                clientSocket.connect();
                                //获得输出流（客户端指向服务端输出文本）
                                os = clientSocket.getOutputStream();
                            }

                            if (os != null)
                            {
                                //往服务端写信息
                                os.write("life like a song！".getBytes("utf-8"));
                            }
                        } catch (Exception e)
                        {
                            Logg.e(Net.TAG, "bluetooth error : " + e);
                        }
                    }
                }).start();
            }
        }
    }
}
