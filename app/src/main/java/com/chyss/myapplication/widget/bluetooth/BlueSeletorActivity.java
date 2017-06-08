package com.chyss.myapplication.widget.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.bluetooth.phone.BlueConnActivity;
import com.chyss.myapplication.widget.bluetooth.phone.BlueMainActivity;
import com.chyss.myapplication.widget.bluetooth.phone.BlueServiceActivity;

/**
 * @author chyss 2017-06-7
 */
public class BlueSeletorActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_selector);
        setTitle("BlueTooth");

        initView();
    }

    private void initView()
    {
        findViewById(R.id.bluetooth_conn).setOnClickListener(onClickListener);
        findViewById(R.id.bluetooth_main).setOnClickListener(onClickListener);
        findViewById(R.id.bluetooth_service).setOnClickListener(onClickListener);
        findViewById(R.id.bluetooth_blemode).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId()){
                case R.id.bluetooth_conn:
                    stepNext(BlueConnActivity.class); //主连接（手机）
                    break;
                case R.id.bluetooth_main:
                    stepNext(BlueMainActivity.class); //主（手机）
                    break;
                case R.id.bluetooth_service:
                    stepNext(BlueServiceActivity.class); //从（手机）
                    break;
                case R.id.bluetooth_blemode:
                    stepNext(BlueToothActivity.class); //蓝牙模块通信
                    break;
            }
        }
    };

    private void stepNext(Class mClass)
    {
        Intent intent = new Intent(this,mClass);
        startActivity(intent);
    }
}