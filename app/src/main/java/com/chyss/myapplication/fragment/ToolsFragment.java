package com.chyss.myapplication.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.aidl.messenger.MessengerActivity;
import com.chyss.myapplication.utils.DoAction;
import com.chyss.myapplication.widget.aidl.aidl.ClientAidlActivity;
import com.chyss.myapplication.widget.aidl.process.LocalService;
import com.chyss.myapplication.widget.encrypt.RsaActivity;
import com.chyss.myapplication.widget.gis.GisActivity;
import com.chyss.myapplication.widget.jni.MyJni;
import com.chyss.myapplication.widget.linearsv.LinearSVActivity;
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
import com.chyss.myapplication.widget.screenshot.ScreenShotActivity;
import com.chyss.myapplication.widget.scrollshow.ScrollShowActivity;
import com.chyss.myapplication.widget.traces.TracesActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolsFragment extends BaseFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
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

		root.findViewById(R.id.zone_retrofit).setOnClickListener(onClickListener);
		root.findViewById(R.id.zone_rxjava).setOnClickListener(onClickListener);
		root.findViewById(R.id.zone_traces).setOnClickListener(onClickListener);

		root.findViewById(R.id.zone_opengl).setOnClickListener(onClickListener);
		root.findViewById(R.id.zone_bluetooth).setOnClickListener(onClickListener);
		root.findViewById(R.id.zone_msgpack).setOnClickListener(onClickListener);

		root.findViewById(R.id.bigimage_compress).setOnClickListener(onClickListener);
		root.findViewById(R.id.localimage_compress).setOnClickListener(onClickListener);
		root.findViewById(R.id.permission_get).setOnClickListener(onClickListener);

		root.findViewById(R.id.scroll_show).setOnClickListener(onClickListener);
		root.findViewById(R.id.linear_sview).setOnClickListener(onClickListener);
		root.findViewById(R.id.screen_shot).setOnClickListener(onClickListener);

		root.findViewById(R.id.aidl_client).setOnClickListener(onClickListener);
		root.findViewById(R.id.aidl_service).setOnClickListener(onClickListener);

		root.findViewById(R.id.zone_gis).setOnClickListener(onClickListener);
		root.findViewById(R.id.kill_app).setOnClickListener(onClickListener);
		root.findViewById(R.id.kill_process).setOnClickListener(onClickListener);

		root.findViewById(R.id.bind_local).setOnClickListener(onClickListener);
		root.findViewById(R.id.kill_remote).setOnClickListener(onClickListener);
		root.findViewById(R.id.kill_local).setOnClickListener(onClickListener);
		root.findViewById(R.id.tool_rsa).setOnClickListener(onClickListener);
		root.findViewById(R.id.tool_jni).setOnClickListener(onClickListener);
	}

	View.OnClickListener onClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.zone_retrofit:
					DoAction.startActivity(getActivity(),RetrofitActivity.class);
					break;
				case R.id.zone_rxjava:
					DoAction.startActivity(getActivity(),RxActivity.class);
					break;
				case R.id.zone_traces:
					DoAction.startActivity(getActivity(),TracesActivity.class);
					break;
				case R.id.zone_opengl:
					DoAction.startActivity(getActivity(),OpenglSelectActivity.class);
					break;
				case R.id.zone_bluetooth:
					DoAction.startActivity(getActivity(),BlueSeletorActivity.class);
					break;
				case R.id.zone_msgpack:
					DoAction.startActivity(getActivity(),MessagePackActivity.class);
					break;
				case R.id.bigimage_compress:
					DoAction.startActivity(getActivity(),BigImageCompressActivity.class);
					break;
				case R.id.localimage_compress:
					DoAction.startActivity(getActivity(),LocalImageCompressActivity.class);
					break;
				case R.id.permission_get:
					DoAction.startActivity(getActivity(),GetPermissionActivity.class);
					break;
				case R.id.scroll_show:
					DoAction.startActivity(getActivity(),ScrollShowActivity.class);
					break;
				case R.id.linear_sview:
					DoAction.startActivity(getActivity(),LinearSVActivity.class);
					break;
				case R.id.screen_shot:
					DoAction.startActivity(getActivity(),ScreenShotActivity.class);
					break;
				case R.id.aidl_client:
					DoAction.startActivity(getActivity(),ClientAidlActivity.class);
					break;
				case R.id.aidl_service:
					DoAction.startActivity(getActivity(),MessengerActivity.class);
					break;
				case R.id.kill_process:
					// 关闭所在的进程
					Logg.e("pid","pid ------------------------------- "+Process.myPid());
					Process.killProcess(Process.myPid());

					break;
				case R.id.kill_app:

					Map<String,Integer> map = getProcessList();
					Process.killProcess(map.get("com.chyss.myapplication:myservice"));
					//Process.killProcess(Process.myPid());

					break;
				case R.id.bind_local:

					Intent intent = new Intent(getActivity(), LocalService.class);
					getActivity().startService(intent);

					break;
				case R.id.kill_remote:

//					Map<String,Integer> map2 = getProcessList();
//					Process.killProcess(map2.get("com.chyss.myapplication:remote"));
//					Process.killProcess(map2.get("com.chyss.myapplication:local"));
//					Process.killProcess(Process.myPid());

					System.exit(0);

					break;
				case R.id.kill_local:

					Map<String,Integer> map3 = getProcessList();
					Process.killProcess(map3.get("com.chyss.myapplication:local"));

					break;
				case R.id.zone_gis:
					DoAction.startActivity(getActivity(),GisActivity  .class);
					break;
				case R.id.tool_rsa:
					DoAction.startActivity(getActivity(),RsaActivity.class);
					break;
				case R.id.tool_jni:
					Toast.makeText(getActivity(), MyJni.fromNative(),Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
		}
	};

	private Map<String,Integer> getProcessList()
	{
		Map<String,Integer> map = new HashMap<>();

		ActivityManager mActivityManager = (ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo>
				mRunningProcess = mActivityManager.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess)
		{
			map.put(amProcess.processName,amProcess.pid);
		}

		return map;
	}
}
