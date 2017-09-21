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
import android.widget.EditText;
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

//    private String serverUUID = "00001801-0000-1000-8000-00805f9b34fb";
//    private String charecUUID = "00002a05-0000-1000-8000-00805f9b34fb";

    private String serverUUID = "00001801-0000-1000-8000-00805f123456";
    private String charecUUID = "00002a05-0000-1000-8000-00805f123456";
    private BluetoothDevice device;

    private String name;
    private String address;
    private BluetoothGatt mBluetoothGatt;

    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ble_main_activity);

        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        setTitle(name);

        findViewById(R.id.ble_send_news).setOnClickListener(onClickListener);
        findViewById(R.id.ble_recieve_news).setOnClickListener(onClickListener);
        input = (EditText) findViewById(R.id.ble_input_news);

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

                    writeCharacteristic(input.getText().toString().getBytes(),serverUUID,charecUUID);
            	break;
                case R.id.ble_recieve_news:

                    byte[] result = readCharacteristic(serverUUID,charecUUID);
                    Toast.makeText(BleMainActivity.this,new String(result),Toast.LENGTH_SHORT).show();
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

//                        gatt.setCharacteristicNotification(gattCharacteristic, true);  //设置characteristic的通知，触发bluetoothGatt.onCharacteristicWrite()事件。
//                        gattCharacteristic.setValue(new byte[]{0});
//                        gattCharacteristic.setValue("hello world!");
//                        gatt.writeCharacteristic(gattCharacteristic);

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

    /**
     * 从Characteristic获取数据
     *
     * @param serviceUUID
     * @param charecUUID
     */
    public byte[] readCharacteristic(String serviceUUID,String charecUUID) {
        //得到服务对象
        BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString(serviceUUID));  //调用上面获取服务的方法

        if (service == null) {
            Logg.e(TAG, "Can not find 'BluetoothGattService'");
            return null;
        }

        //得到服务结点下Characteristic
        BluetoothGattCharacteristic readCharacteristic = service.getCharacteristic(UUID.fromString(charecUUID));
        if (readCharacteristic == null) {
            Logg.e(TAG, "Can not find 'BluetoothGattCharacteristic'");
            return null;
        }
        boolean isBoolean = mBluetoothGatt.readCharacteristic(readCharacteristic);
        Logg.e(TAG, "readCharacteristic = " +isBoolean);
        return readCharacteristic.getValue();
    }

    /**
     * 写入Characteristic
     *
     * @param data              要写入的数据
     * @param serviceUUID      服务UUID
     * @param charecUUID       Characteristic UUID
     */
    public void writeCharacteristic(byte[] data,String serviceUUID,String charecUUID) {

        //得到服务对象
        BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString(serviceUUID));  //调用上面获取服务的方法

        if (service == null) {
            Logg.e(TAG, "Can not find 'BluetoothGattService'");
            return;
        }

        //得到服务结点下Characteristic
        BluetoothGattCharacteristic writeCharacteristic = service.getCharacteristic(UUID.fromString(charecUUID));
        if (writeCharacteristic == null) {
            Logg.e(TAG, "Can not find 'BluetoothGattCharacteristic'");
            return;
        }
        //设置characteristic的通知，触发bluetoothGatt.onCharacteristicWrite()事件。
        mBluetoothGatt.setCharacteristicNotification(writeCharacteristic, true);
        //为characteristic赋值
        writeCharacteristic.setValue(data);
        //如果isBoolean返回的是true则写入成功
        boolean isBoolean = mBluetoothGatt.writeCharacteristic(writeCharacteristic);

        Logg.e(TAG, "writeCharacteristic = " +isBoolean);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
