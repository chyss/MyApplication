package com.chyss.myapplication.widget.bluetooth.phone;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.widget.bluetooth.adapter.BlueAdapter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author chyss 2017-05-05
 */

public class BlueConnActivity extends BaseActivity
{
    public static final String TAG = "BlueTooth";
    private BluetoothAdapter bluetoothAdapter;
    private int REQUEST_BLUETOOTH = 10000;
    private List<BluetoothDevice> devices = new ArrayList<>();
    //UUID需要同service端一致，才能进行连接通信
    private UUID connUUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");
    private BlueAdapter blueAdapter;
    private RecyclerView bluetooth_recyclerview;
    private LinearLayoutManager manager;
    private BluetoothDevice device;
    private BluetoothSocket clientSocket;
    private OutputStream os;

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction()))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
                blueAdapter.setDevices(devices);
                Logg.e("blue", device.getName() + "----" + device.getAddress());
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction()))
            {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState())
                {
                    case BluetoothDevice.BOND_BONDING:
                        Logg.e("BlueToothTestActivity", "正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Logg.e("BlueToothTestActivity", "完成配对");
                        //connect(device);//连接设备
                        break;
                    case BluetoothDevice.BOND_NONE:
                        Logg.e("BlueToothTestActivity", "取消配对");
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_activity);
        setTitle("BlueTooth");

        bluetooth_recyclerview = (RecyclerView) findViewById(R.id.bluetooth_recyclerview);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        bluetooth_recyclerview.setLayoutManager(manager);
        blueAdapter = new BlueAdapter(this, devices);
        bluetooth_recyclerview.setAdapter(blueAdapter);
        blueAdapter.setOnItemClickListener(onItemClickListener);

        initData();
    }

    BlueAdapter.OnItemClickListener onItemClickListener = new BlueAdapter.OnItemClickListener()
    {
        @Override
        public void onItemClick(View view, final int position)
        {

            Toast.makeText(BlueConnActivity.this, "蓝牙：" + position, Toast.LENGTH_SHORT).show();

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        //判断当前是否正在搜索
                        if (bluetoothAdapter.isDiscovering())
                        {
                            bluetoothAdapter.cancelDiscovery();
                        }

                        String address = devices.get(position).getAddress();
                        //获得远程设备
                        device = bluetoothAdapter.getRemoteDevice(address);

                        //获得已配对的远程蓝牙设备的集合
                        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

                        Logg.e(TAG, "开始连接...");
                        if (bondedDevices.contains(device))
                        {
                            Logg.e(TAG, "已经配对...");
                            if (clientSocket == null)
                            {
                                //创建客户端蓝牙Socket
                                clientSocket = device.createRfcommSocketToServiceRecord(connUUID);
                                //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                                clientSocket.connect();
                                //获得输出流（客户端指向服务端输出文本）
                                os = clientSocket.getOutputStream();
                            }
                            if(os != null)
                            {
                                os.write("life like a song！".getBytes("utf-8"));
                            }
                        } else
                        {
                            //创建客户端蓝牙Socket
                            clientSocket = device.createRfcommSocketToServiceRecord(connUUID);
                            //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                            clientSocket.connect();

                        }
                    } catch (Exception e)
                    {
                        Logg.e(Net.TAG, "catch error : " + e);
                    }
                }
            }).start();

            //            new Thread(new Runnable()
            //            {
            //                @Override
            //                public void run()
            //                {
            //                    String address = devices.get(position).getAddress();
            //                    Logg.e(TAG,"address : "+address);
            //                    try
            //                    {
            //                        //判断当前是否正在搜索
            //                        if (bluetoothAdapter.isDiscovering()) {
            //                            bluetoothAdapter.cancelDiscovery();
            //                        }
            //
            //                        if (device == null) {
            //                            //获得远程设备
            //                            device = bluetoothAdapter.getRemoteDevice(address);
            //                        }
            //                        if (clientSocket == null) {
            //                            //创建客户端蓝牙Socket
            //                            clientSocket = device.createRfcommSocketToServiceRecord(connUUID);
            //                            //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
            //                            Logg.e(TAG,"如果没有配对则弹出对话框提示我们进行配对");
            //                            clientSocket.connect();
            //                            //获得输出流（客户端指向服务端输出文本）
            //                            os = clientSocket.getOutputStream();
            //                        }
            //
            //                        if (os != null) {
            //                            //往服务端写信息
            //                            os.write("life like a song！".getBytes("utf-8"));
            //                        }
            //                    } catch (Exception e) {
            //                        Logg.e(TAG,"bluetooth error : "+e);
            //                    }
            //                }
            //            }).start();
        }
    };

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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        // 打开蓝牙
        // 也可以直接调用bluetoothAdapter.enable(),bluetoothAdapter.disable()来启用/禁用蓝牙,不过这种方式不会弹出询问对话框。
        if (!bluetoothAdapter.isEnabled())
        {
            //弹出打开蓝牙对话框
            Intent blueIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //设置蓝牙可见性
            blueIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
            startActivityForResult(blueIntent, REQUEST_BLUETOOTH);
        } else
        {
            getBluetoothDevices();
        }
    }

    /**
     * 获取蓝牙设备信息
     */
    private void getBluetoothDevices()
    {
        IntentFilter blueFilter = new IntentFilter();
        blueFilter.addAction(BluetoothDevice.ACTION_FOUND);
        blueFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        blueFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        blueFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, blueFilter);
        //查找蓝牙设备
        bluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //回调蓝牙打开情况
        if (requestCode == REQUEST_BLUETOOTH)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this, "蓝牙打开成功：" + resultCode, Toast.LENGTH_SHORT).show();
                getBluetoothDevices();
            } else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "取消打开蓝牙：" + resultCode, Toast.LENGTH_SHORT).show();
            }
        } else
        {
            Toast.makeText(this, "打开蓝牙异常：" + resultCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy()
    {
        try
        {
            unregisterReceiver(receiver);
        } catch (Exception e)
        {
            Logg.e(Net.TAG, "catch unregisterReceiver error : " + e);
        }
        super.onDestroy();
    }
}
