package com.chyss.myapplication.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chyss.myapplication.R;
import com.chyss.myapplication.activity.RetrofitActivity;
import com.chyss.myapplication.activity.RxActivity;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.utils.Logg;

public class FriendzoneFragment extends Fragment {

	private RelativeLayout retrofit,rxjava;
	

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
