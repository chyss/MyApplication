package com.chyss.myapplication.fragment.home;

import java.util.ArrayList;
import java.util.List;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.response.ListData;
import com.chyss.myapplication.fragment.home.adapter.DifItemListAdapter;
import com.chyss.myapplication.view.ExpandListView;

public class ListFragment extends Fragment
{
	public static final String TAG = "ListFragment";
	private ExpandListView listView;
	private List<ListData> listData = new ArrayList<ListData>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.list_fragment_layout, container,
				false);
		init(root);
		initData();

		//handler.sendEmptyMessageDelayed(0, 500);
		return root;
	}

	private void initData()
	{
		addDataToList(1,"normal","http://img0.imgtn.bdimg.com/it/u=3246030480,1173771526&fm=206&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(1,"normal","http://img0.imgtn.bdimg.com/it/u=3246030480,1173771526&fm=206&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		addDataToList(0,"special","http://img3.imgtn.bdimg.com/it/u=1100963527,1982157674&fm=21&gp=0.jpg");
		
		listView.setAdapter(new DifItemListAdapter(getActivity(), listData));
		listView.setFocusable(false);
		listView.setOnItemClickListener(onItemClickListener);
	}

	private void addDataToList(int type,String name,String imgUrl)
	{
		ListData data = new ListData();
		data.setType(type);
		data.setName(name);
		data.setImgUrl(imgUrl);
		listData.add(data);
	}

	private void init(View root)
	{
		listView = (ExpandListView) root.findViewById(R.id.list_listview);
	}
	
	OnItemClickListener onItemClickListener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			if (listData.get(position).getType() == 0) {
				
				Toast.makeText(getActivity(), "special", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getActivity(), "normal", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			
			initData();
		}
	};
}
