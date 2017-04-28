package com.chyss.myapplication.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.view.xlist.XListView;

public class KindFragment extends Fragment implements XListView.IXListViewListener
{

	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;

	private ImageView floatingBtn;
	float x, y;
	int mx, my;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.main_kind_fragment, container,
				false);
		Logg.e(Net.TAG, "kindFragment oncreateview !!");
		geneItems();
		mListView = (XListView) root.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item,
				items);
		mListView.setAdapter(mAdapter);
		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		initFloating(root);

		return root;
	}

	/**
	 * 初始化浮标，以及浮标控制
	 * 
	 * @param root
	 */
	private void initFloating(View root)
	{
		floatingBtn = (ImageView) root.findViewById(R.id.wechat_floating_img);
		floatingBtn.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					x = event.getX();
					y = event.getY();

					break;
				case MotionEvent.ACTION_MOVE:

					mx = (int) (event.getRawX() - x);
					my = (int) (event.getRawY() - 50 - y);

					floatingBtn.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
					break;
				default:
					break;
				}
				return true;
			}
		});
	}

	private void geneItems()
	{
		for (int i = 0; i != 20; i++) {
			items.add("refresh cnt " + (++start));
		}
	}

	private void onLoad()
	{
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh()
	{
		mHandler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{

				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(getActivity(),
						R.layout.list_item, items);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);

	}

	@Override
	public void onLoadMore()
	{
		mHandler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}
}
