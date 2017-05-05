package com.chyss.myapplication.widget.wifi;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;


public class WifiListActivity extends BaseActivity
{

	private WifiManager wifiManager;
	List<ScanResult> list;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.wifi_list_layout);
		setTitle("wifi");
		addLeftBtn();
		initView();
	}

	@Override
	public void leftBtnClick()
	{
		finish();
	}

	private void initView()
	{
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		openWifi();
		list = wifiManager.getScanResults();
		Log.e("wifilist", "list.size = " + list.size());
		ListView listView = (ListView) findViewById(R.id.wifi_list);
		if (list == null) {
			Toast.makeText(this, "wifi未打开！", Toast.LENGTH_LONG).show();
		} else {
			sortByLevel(list);
			listView.setAdapter(new MyAdapter(this, list));
		}
	}

	/**
	 * 打开WIFI
	 */
	private void openWifi()
	{
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
	}

	// 将搜索到的wifi根据信号强度从强到弱进行排序
	private void sortByLevel(List<ScanResult> list)
	{
		for (int i = 0; i < list.size(); i++)
			for (int j = 1; j < list.size(); j++) {
				if (list.get(i).level < list.get(j).level) // level属性即为强度
				{
					ScanResult temp;
					temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
	}

	public class MyAdapter extends BaseAdapter
	{

		List<ScanResult> list;
		Context context;

		public MyAdapter(Context context, List<ScanResult> list)
		{
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			convertView = View.inflate(context, R.layout.item_wifi_list, null);
			ScanResult scanResult = list.get(position);
			TextView textView = (TextView) convertView
					.findViewById(R.id.textView);
			textView.setText(scanResult.SSID);
			TextView signalStrenth = (TextView) convertView
					.findViewById(R.id.signal_strenth);
			signalStrenth.setText(String.valueOf(Math.abs(scanResult.level)));
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.imageView);
			// 判断信号强度，显示对应的指示图标
			if (Math.abs(scanResult.level) > 90) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.mipmap.connect_enable_wifi_animation_3));
			} else if (Math.abs(scanResult.level) > 70) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.mipmap.connect_enable_wifi_animation_2));
			} else if (Math.abs(scanResult.level) > 40) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.mipmap.connect_enable_wifi_animation_1));
			} else {
				imageView.setImageDrawable(getResources().getDrawable(
						R.mipmap.connect_enable_wifi_animation_0));
			}
			return convertView;
		}

	}

}
