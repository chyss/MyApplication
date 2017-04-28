package com.chyss.myapplication.fragment;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.activity.SearchActivity;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.fragment.home.ListFragment;
import com.chyss.myapplication.fragment.home.NewsFragment;
import com.chyss.myapplication.utils.Logg;

public class HomeFragment extends Fragment
{
	public static final String TAG = "HomeFragment";
	private ImageView scan, acount;
	private Button search;
	public static final String NEWS_FRAG_TAG = "newsfragment";
	public static final String LIST_FRAG_TAG = "listfragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.main_home_fragment, container,
				false);
		Log.e(Net.TAG, "homeFragment oncreateview !!");
		initSearchView(root);
		initNewsFragment();
		initListFragment();
		return root;
	}

	/**
	 * 当我们调用transaction.hide(fragment).commit()和transaction.show(fragment).commit()时会调用到onHiddenChanged(boolean hidden)
	 *
	 * @param hidden  隐藏和显示fragment的参数
     */
	@SuppressLint("NewApi")
	@Override
	public void onHiddenChanged(boolean hidden)
	{
		Log.e(Net.TAG, "homeFragment onHiddenChanged !!");
//		if(!hidden)
//		{
//			initNewsFragment(hidden);
//			initListFragment(hidden);
//		}
	}

	@SuppressLint("NewApi")
	private void initListFragment()
	{
		Fragment listFragment = getChildFragmentManager().findFragmentByTag(
				LIST_FRAG_TAG);
		FragmentTransaction fragmentTransaction = getChildFragmentManager()
				.beginTransaction();

		Log.e(TAG, "listFragment : "+listFragment);
		if (listFragment == null)
		{
			listFragment = new ListFragment();
			fragmentTransaction.add(R.id.listfragment_container, listFragment,LIST_FRAG_TAG).commit();
		}
		else if(!listFragment.isAdded())
		{
			fragmentTransaction.add(R.id.listfragment_container, listFragment,LIST_FRAG_TAG).commit();
		}
	}

	@SuppressLint("NewApi")
	private void initNewsFragment()
	{
		Fragment newsFragment = getChildFragmentManager().findFragmentByTag(
				NEWS_FRAG_TAG);
		FragmentTransaction fragmentTransaction = getChildFragmentManager()
				.beginTransaction();

		Logg.e(Net.TAG,"newsFragment"+newsFragment);
		if (newsFragment == null)
		{
			newsFragment = new NewsFragment();
			fragmentTransaction.add(R.id.newsfragment_container, newsFragment,NEWS_FRAG_TAG).commit();
		}
		else if(!newsFragment.isAdded())
		{
			fragmentTransaction.add(R.id.newsfragment_container, newsFragment,NEWS_FRAG_TAG).commit();
		}
	}

	private void initSearchView(View root)
	{
		scan = (ImageView) root.findViewById(R.id.main_scan_img);
		acount = (ImageView) root.findViewById(R.id.main_account_ico_img);
		search = (Button) root.findViewById(R.id.main_search_button);

		scan.setOnClickListener(onClickListener);
		acount.setOnClickListener(onClickListener);
		search.setOnClickListener(onClickListener);
	}

	OnClickListener onClickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.main_scan_img:

				break;
			case R.id.main_account_ico_img:

				break;
			case R.id.main_search_button:
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				getActivity().startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		// 用于处理fragment中嵌套fragment时引起的崩溃问题。 这是旧版本support库的一个bug
		// Fragment 重新 attach 时，它 (fragment) 没有重新 attachm childFragmentManager
		// ，从而 引发了异常
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
