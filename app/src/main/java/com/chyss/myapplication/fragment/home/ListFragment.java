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

public class ListFragment extends Fragment implements DifItemListAdapter.OnListItemClick
{
	public static final String TAG = "ListFragment";
	private ExpandListView listView;
	private List<ListData> listData = new ArrayList<>();
	private String big = "http://img1.imgtn.bdimg.com/it/u=1057601304,2646654295&fm=27&gp=0.jpg";
	private String small = "http://img5.imgtn.bdimg.com/it/u=194685546,499818437&fm=27&gp=0.jpg";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.list_fragment_layout, container,
				false);
		init(root);
		initData();

		return root;
	}

	private void initData()
	{
		addDataToList(1,"normal",big,small);
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		addDataToList(1,"normal",big,small);
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		addDataToList(0,"special",small,"");
		
		listView.setAdapter(new DifItemListAdapter(getActivity(), listData,this));
		listView.setFocusable(false);
	}

	private void addDataToList(int type,String name,String imgUrl1,String imgUrl2)
	{
		ListData data = new ListData();
		data.setType(type);
		data.setName(name);
		data.setImgUrl1(imgUrl1);
		data.setImgUrl2(imgUrl2);
		listData.add(data);
	}

	private void init(View root)
	{
		listView = (ExpandListView) root.findViewById(R.id.list_listview);
	}

	@Override
	public void imglayClick(final int position)
	{
		Toast.makeText(getActivity(),"imglay "+position,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void imglay2Click(final int position)
	{
		Toast.makeText(getActivity(),"imglay2 "+position,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void specialClick(final int position)
	{
		Toast.makeText(getActivity(),"special "+position,Toast.LENGTH_SHORT).show();
	}
}
