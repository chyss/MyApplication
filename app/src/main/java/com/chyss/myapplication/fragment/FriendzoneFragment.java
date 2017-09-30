package com.chyss.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.retrofit.RetrofitActivity;
import com.chyss.myapplication.widget.rxjava.RxActivity;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.widget.bluetooth.BlueSeletorActivity;
import com.chyss.myapplication.widget.largPicture.compress.BigImageCompressActivity;
import com.chyss.myapplication.widget.largPicture.compress.LocalImageCompressActivity;
import com.chyss.myapplication.widget.messagePack.MessagePackActivity;
import com.chyss.myapplication.widget.opengl.OpenglSelectActivity;
import com.chyss.myapplication.widget.permission.GetPermissionActivity;
import com.chyss.myapplication.widget.traces.TracesActivity;

public class FriendzoneFragment extends BaseFragment {

	private RelativeLayout retrofit,rxjava,traces,opengl,bluetooth,msgpack;
	private RelativeLayout bigimage_compress,localimage_compress,permission_get;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.main_zone_fragment, container,
				false);
		Logg.e(Net.TAG, "zoneFragment oncreateview !!");
		init(root);

		return root;
	}

	private void init(View root) {

		retrofit = (RelativeLayout)root.findViewById(R.id.zone_retrofit);
		retrofit.setOnClickListener(onClickListener);
		rxjava = (RelativeLayout)root.findViewById(R.id.zone_rxjava);
		rxjava.setOnClickListener(onClickListener);
		traces = (RelativeLayout)root.findViewById(R.id.zone_traces);
		traces.setOnClickListener(onClickListener);

		opengl = (RelativeLayout)root.findViewById(R.id.zone_opengl);
		opengl.setOnClickListener(onClickListener);
		bluetooth = (RelativeLayout)root.findViewById(R.id.zone_bluetooth);
		bluetooth.setOnClickListener(onClickListener);
		msgpack = (RelativeLayout)root.findViewById(R.id.zone_msgpack);
		msgpack.setOnClickListener(onClickListener);

		bigimage_compress = (RelativeLayout)root.findViewById(R.id.bigimage_compress);
		bigimage_compress.setOnClickListener(onClickListener);
		localimage_compress = (RelativeLayout)root.findViewById(R.id.localimage_compress);
		localimage_compress.setOnClickListener(onClickListener);
		permission_get = (RelativeLayout)root.findViewById(R.id.permission_get);
		permission_get.setOnClickListener(onClickListener);
	}

	View.OnClickListener onClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.zone_retrofit:
					stepNext(RetrofitActivity.class);
					break;
				case R.id.zone_rxjava:
					stepNext(RxActivity.class);
					break;
				case R.id.zone_traces:
					stepNext(TracesActivity.class);
					break;
				case R.id.zone_opengl:
					stepNext(OpenglSelectActivity.class);
					break;
				case R.id.zone_bluetooth:
					stepNext(BlueSeletorActivity.class);
					break;
				case R.id.zone_msgpack:
					stepNext(MessagePackActivity.class);
					break;
				case R.id.bigimage_compress:
					stepNext(BigImageCompressActivity.class);
					break;
				case R.id.localimage_compress:
					stepNext(LocalImageCompressActivity.class);
					break;
				case R.id.permission_get:
					stepNext(GetPermissionActivity.class);
					break;
				default:
					break;
			}
		}
	};

	private <T> void stepNext(Class<T> t)
	{
		Intent intent = new Intent(getActivity(), t);
		startActivity(intent);
	}
}
