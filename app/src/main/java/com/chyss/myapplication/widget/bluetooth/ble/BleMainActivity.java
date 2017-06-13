package com.chyss.myapplication.widget.bluetooth.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author chyss 2017-05-05
 */
public class BleMainActivity extends BaseActivity
{
    public static final String TAG = "BleMain";
    BluetoothAdapter bluetoothAdapter;
    //和客户端相同的UUID
    private UUID connUUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");
    private BluetoothDevice device;
    private BluetoothSocket clientSocket;
    private OutputStream os;

    private String name;
    private String address;
    private BluetoothGatt mBluetoothGatt;

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
        //获得远程设备
        device = bluetoothAdapter.getRemoteDevice(address);

        mBluetoothGatt = device.connectGatt(BleMainActivity.this, false, mGattCallback);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
            	case R.id.ble_send_news:

                    //write("hello world".getBytes(),"00001801-0000-1000-8000-00805f9b34fb","00002a05-0000-1000-8000-00805f9b34fb");

                    BluetoothGattCharacteristic bgc = getCharcteristic("00001801-0000-1000-8000-00805f9b34fb","00002a05-0000-1000-8000-00805f9b34fb");
                    Toast.makeText(BleMainActivity.this,bgc.getStringValue(0),Toast.LENGTH_SHORT).show();
            	break;
            }
        }
    };


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
                    Logg.e(TAG, "Service : " + gattService.getUuid());
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics)
                    {
                        Logg.e(TAG, "Characteristic : " + gattCharacteristic.getUuid()+"      "+gattCharacteristic.getDescriptors());
                        //gatt.writeCharacteristic(gattCharacteristic);

                        gatt.setCharacteristicNotification(gattCharacteristic, true);  //设置characteristic的通知，触发bluetoothGatt.onCharacteristicWrite()事件。
                        gattCharacteristic.setValue(new byte[]{0});
                        gattCharacteristic.setValue("hello world!");
                        gatt.writeCharacteristic(gattCharacteristic);

                        List<BluetoothGattDescriptor> descriptors = gattCharacteristic.getDescriptors();
                        for (BluetoothGattDescriptor descriptor : descriptors)
                        {
                            Logg.e(TAG, "服务3 : " + descriptor.getUuid()+"    "+descriptor.getValue());
                        }
                    }
                }
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

    //获取服务
    public BluetoothGattService getService(UUID uuid) {
        if (bluetoothAdapter == null || mBluetoothGatt == null) {
            Logg.e(TAG, "BluetoothAdapter not initialized");
            return null;
        }
        return mBluetoothGatt.getService(uuid);
    }

    //获取特征
    private BluetoothGattCharacteristic getCharcteristic(String serviceUUID, String characteristicUUID) {

        //得到服务对象
        BluetoothGattService service = getService(UUID.fromString(serviceUUID));  //调用上面获取服务的方法

        if (service == null) {
            Logg.e(TAG, "Can not find 'BluetoothGattService'");
            return null;
        }

        //得到此服务结点下Characteristic对象
        final BluetoothGattCharacteristic gattCharacteristic = service.getCharacteristic(UUID.fromString(characteristicUUID));
        if (gattCharacteristic != null) {
            return gattCharacteristic;
        } else {
            Logg.e(TAG, "Can not find 'BluetoothGattCharacteristic'");
            return null;
        }
    }

    //获取数据
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (bluetoothAdapter == null || mBluetoothGatt == null) {
            Logg.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (bluetoothAdapter == null || mBluetoothGatt == null) {
            Logg.e(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        return mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
    }

    public void write(byte[] data,String serviceUUID,String charecUUID) {   //一般都是传byte
        //得到可写入的characteristic Utils.isAIRPLANE(mContext) &&
//        if(!mBleManager.isEnabled()){
//            TLog.e(TAG, "writeCharacteristic 开启飞行模式");
//            //closeBluetoothGatt();
//            isGattConnected = false;
//            broadcastUpdate(Config.ACTION_GATT_DISCONNECTED);
//            return;
//        }
        BluetoothGattCharacteristic writeCharacteristic = getCharcteristic(serviceUUID, charecUUID);  //这个UUID都是根据协议号的UUID
        if (writeCharacteristic == null) {
            Logg.e(TAG, "Write failed. GattCharacteristic is null.");
            return;
        }
        writeCharacteristic.setValue(data); //为characteristic赋值
        writeCharacteristicWrite(writeCharacteristic);

    }

    public void writeCharacteristicWrite(BluetoothGattCharacteristic characteristic) {
        if (bluetoothAdapter == null || mBluetoothGatt == null) {
            Logg.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        Logg.e(TAG, "BluetoothAdapter 写入数据");
        boolean isBoolean = false;
        isBoolean = mBluetoothGatt.writeCharacteristic(characteristic);
        Logg.e(TAG, "BluetoothAdapter_writeCharacteristic = " +isBoolean);  //如果isBoolean返回的是true则写入成功
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
