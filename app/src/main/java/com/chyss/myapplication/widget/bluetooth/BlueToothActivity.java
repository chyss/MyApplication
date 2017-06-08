package com.chyss.myapplication.widget.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.widget.bluetooth.adapter.BlueAdapter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.attr.value;

/**
 * @author chyss 2017-05-05
 */

public class BlueToothActivity extends BaseActivity
{
    public static final String TAG = "BlueTooth";
    private BluetoothAdapter bluetoothAdapter;
    private int REQUEST_BLUETOOTH = 10000;
    private List<BluetoothDevice> devices = new ArrayList<>();

    private BlueAdapter blueAdapter;
    private RecyclerView bluetooth_recyclerview;
    private LinearLayoutManager manager;
    private BluetoothLeScanner scanner;

    private BluetoothGatt mBluetoothGatt;
    BluetoothDevice device;
    BluetoothSocket clientSocket;
    OutputStream os;

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
        public void onItemClick(View view, int position)
        {
            //连接设备
            //mBluetoothGatt = devices.get(position).connectGatt(BlueToothActivity.this, false, mGattCallback);
            Toast.makeText(BlueToothActivity.this, "蓝牙：" + position, Toast.LENGTH_SHORT).show();

            String address = devices.get(position).getAddress();
            try
            {
                //判断当前是否正在搜索
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                try {
                    if (device == null) {
                        //获得远程设备
                        device = bluetoothAdapter.getRemoteDevice(address);
                    }
                    if (clientSocket == null) {
                        //创建客户端蓝牙Socket
                        clientSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456"));
                        //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                        clientSocket.connect();
                        //获得输出流（客户端指向服务端输出文本）
                        os = clientSocket.getOutputStream();
                    }
                } catch (Exception e) {
                }
                if (os != null) {
                    //往服务端写信息
                    os.write("蓝牙信息来了".getBytes("utf-8"));
                }
            } catch (Exception e) {
            }
        }
    };

    /**
     * 判断手机是否支持蓝牙设备
     */
    private void initData()
    {
        //判断是否支持蓝牙设备
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //以下api需要android4.3（18）以上版本才支持
        //        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);//这里与标准蓝牙略有不同
        //        bluetoothAdapter = bluetoothManager.getAdapter();

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
        IntentFilter blueFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, blueFilter);
        //查找蓝牙设备
        bluetoothAdapter.startDiscovery();


        //以下api为android5.0（21）以上才可用
        //        scanner = bluetoothAdapter.getBluetoothLeScanner();
        //        //如果只是要扫描到特定类型的设备，则使用接口 startLeScan(UUID[], BluetoothAdapter.LeScanCallback)，通过UUID来查找设备。
        //        scanner.startScan(leCallback);
        //        new Handler().postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                scanner.stopScan(leCallback);
        //            }
        //        }, 10*1000); //10秒后停止搜索
    }

    /**
     * 以下api为android5.0（21）以上才可用
     * java.lang.SecurityException: Need ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permission to get scan results
     * 某些手机系统中需要主动开启相应的权限，才可以使用
     */
    //    ScanCallback leCallback = new ScanCallback() {
    //        @Override
    //        public void onScanResult(int callbackType, ScanResult result) {
    //
    //            Logg.e(TAG,"BLE搜索成功: "+result+"---"+callbackType);
    //
    //            super.onScanResult(callbackType, result);
    //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    //                BluetoothDevice device = result.getDevice();
    //                if (!devices.contains(device)) {  //判断是否已经添加
    //                    devices.add(device);
    //                }
    //                blueAdapter.notifyDataSetChanged();
    //            }
    //        }
    //
    //        @Override
    //        public void onScanFailed(int errorCode) {
    //            super.onScanFailed(errorCode);
    //            Logg.e(TAG,"BLE搜索失败: "+errorCode);
    //        }
    //    };
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

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback()
    {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
        {
            //当连接上设备或者失去连接时会回调该函数
            if (newState == BluetoothProfile.STATE_CONNECTED)
            {
                //连接成功
                Logg.e(TAG, "蓝牙连接成功 : " + newState);
                //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
                mBluetoothGatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED)
            {
                //连接失败
                Logg.e(TAG, "蓝牙连接失败 : " + newState);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status)
        {
            //当设备是否找到服务时，会回调该函数
            Logg.e(TAG, "找服务 : " + status);
            if (status == BluetoothGatt.GATT_SUCCESS)
            {
                //找到服务了,对服务进行解析，寻找到你需要的服务

                List<BluetoothGattService> gattServices = gatt.getServices();
                for (BluetoothGattService gattService : gattServices)
                {
                    Logg.e(TAG, "服务1 : " + gattService.getUuid());
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics)
                    {
                        Logg.e(TAG, "服务2 : " + gattCharacteristic.getUuid()+"      "+gattCharacteristic.getDescriptors());
                        //gatt.writeCharacteristic(gattCharacteristic);

                        gatt.setCharacteristicNotification(gattCharacteristic, true);  //设置characteristic的通知，触发bluetoothGatt.onCharacteristicWrite()事件。
                        gattCharacteristic.setValue(new byte[]{0});
                        gatt.writeCharacteristic(gattCharacteristic);

                        List<BluetoothGattDescriptor> descriptors = gattCharacteristic.getDescriptors();
                        for (BluetoothGattDescriptor descriptor : descriptors)
                        {
                            Logg.e(TAG, "服务3 : " + descriptor.getUuid()+"    "+descriptor.getValue());
                        }
                    }
                }
            }
            else
            {

            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
        {
            //当读取设备时会回调该函数
            Logg.e(TAG, "读取设备 : " + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {

                //读取到的数据存在characteristic当中，可以通过characteristic.getValue();函数取出。然后再进行解析操作。
                //int charaProp = characteristic.getProperties();if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0)表示可发出通知。  判断该Characteristic属性
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status)
        {
            //向设备Descriptor中写数据
            Logg.e(TAG, "向设备Descriptor中写数据 : " + status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
        {
            //设备发出通知时会调用
            Logg.e(TAG, "设备发出通知!");
            if (characteristic.getValue() != null)
            {
                Logg.e(TAG, "设备发出通知: "+characteristic.getStringValue(0));
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
        {
            //向Characteristic写数据时会回调
            Logg.e(TAG, "向Characteristic写数据!");
        }
    };

    //返回获取到的服务列表
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;
        return mBluetoothGatt.getServices();
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
