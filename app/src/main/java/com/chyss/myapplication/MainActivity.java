package com.chyss.myapplication;

import java.io.IOException;

import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.fragment.AcountFragment;
import com.chyss.myapplication.fragment.FriendzoneFragment;
import com.chyss.myapplication.fragment.HomeFragment;
import com.chyss.myapplication.fragment.KindFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener {

	public static final String TAG = "MainActivity";
	private RelativeLayout home_frag, king_frag, zone_frag, acount_frag;
	private Fragment homeFragment, kindFragment, zoneFragment, acountFragment;
	private FragmentTransaction fragmentTransaction;
	private ImageView homeImage,kindImage,zoneImage,acountImage;
	private TextView homeText,kindText,zoneText,acountText;
	private Fragment mContent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		missTitleView();
		initView();

	}

	private void initView() {

		home_frag = (RelativeLayout) findViewById(R.id.main_home);
		king_frag = (RelativeLayout) findViewById(R.id.main_kind);
		zone_frag = (RelativeLayout) findViewById(R.id.main_zone);
		acount_frag = (RelativeLayout) findViewById(R.id.main_acount);
		
		homeImage = (ImageView) findViewById(R.id.main_icon_home);
		kindImage = (ImageView) findViewById(R.id.main_icon_kind);
		zoneImage = (ImageView) findViewById(R.id.main_icon_zone);
		acountImage = (ImageView) findViewById(R.id.main_icon_acount);
		
		homeText = (TextView) findViewById(R.id.main_text_home);
		kindText = (TextView) findViewById(R.id.main_text_kind);
		zoneText = (TextView) findViewById(R.id.main_text_zone);
		acountText = (TextView) findViewById(R.id.main_text_acount);

		home_frag.setOnClickListener(this);
		king_frag.setOnClickListener(this);
		zone_frag.setOnClickListener(this);
		acount_frag.setOnClickListener(this);
		
		initFragment();
	}

	private void initFragment() {
		
		homeFragment = new HomeFragment();
		//mContent = homeFragment;
		switchContent(mContent,homeFragment,"home");
		//setFragment(homeFragment,"qq");
		setallDark();
		homeImage.setImageResource(R.mipmap.ico_home2);
		homeText.setTextColor(Color.RED);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.main_home:
			
			homeFragment = getFragmentManager().findFragmentByTag("home");
			Log.e(Net.TAG, "home CLICK !!" + homeFragment);
			if (homeFragment == null) {

				homeFragment = new HomeFragment();
			}
			//setFragment(homeFragment,"qq");
			switchContent(mContent,homeFragment,"home");
			setallDark();
			homeImage.setImageResource(R.mipmap.ico_home2);
			homeText.setTextColor(Color.RED);
			break;
		case R.id.main_kind:

			kindFragment = getFragmentManager().findFragmentByTag("kind");
			Log.e(Net.TAG, "kind CLICK !!"+kindFragment);
			if (kindFragment == null) {

				kindFragment = new KindFragment();
			}
			//setFragment(kindFragment,"wechat");
			switchContent(mContent,kindFragment,"kind");
			setallDark();
			kindImage.setImageResource(R.mipmap.ico_financial2);
			kindText.setTextColor(Color.RED);
			break;
		case R.id.main_zone:

			zoneFragment = getFragmentManager().findFragmentByTag("zone");
			Log.e(Net.TAG, "zone CLICK !!"+zoneFragment);
			if (zoneFragment == null) {

				zoneFragment = new FriendzoneFragment();
			}
			//setFragment(zoneFragment,"weibo");
			switchContent(mContent,zoneFragment,"zone");
			setallDark();
			zoneImage.setImageResource(R.mipmap.ico_more2);
			zoneText.setTextColor(Color.RED);
			break;
		case R.id.main_acount:

			acountFragment = getFragmentManager().findFragmentByTag("acount");
			Log.e(Net.TAG, "acount CLICK !!"+acountFragment);
			if (acountFragment == null) {

				acountFragment = new AcountFragment();
			}
			//setFragment(acountFragment,"friend");
			switchContent(mContent,acountFragment,"acount");
			setallDark();
			acountImage.setImageResource(R.mipmap.ico_account2);
			acountText.setTextColor(Color.RED);
			break;
		default:
			break;
		}
	}

	private void setallDark() {
		
		homeImage.setImageResource(R.mipmap.ico_home1);
		kindImage.setImageResource(R.mipmap.ico_financial1);
		zoneImage.setImageResource(R.mipmap.ico_more1);
		acountImage.setImageResource(R.mipmap.ico_account1);
		
		homeText.setTextColor(Color.parseColor("#333333"));
		kindText.setTextColor(Color.parseColor("#333333"));
		zoneText.setTextColor(Color.parseColor("#333333"));
		acountText.setTextColor(Color.parseColor("#333333"));
	}

	private void setFragment(Fragment fragment,String tag) {

		fragmentTransaction = getFragmentManager().beginTransaction();
		//替换containerViewId中的fragment实例，注意，它首先把containerViewId中所有fragment删除，然后再add进去当前的fragment
		fragmentTransaction.replace(R.id.main_container, fragment,tag);
		fragmentTransaction.commit();
	}

	public void switchContent(Fragment from, Fragment to,String tag) {
		if (mContent != to) {
			mContent = to;
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			if(from == null)
			{
				transaction.add(R.id.main_container, to,tag).commit(); // add一个到Activity中
			}
			else if (!to.isAdded()) {    // 先判断是否被add过
				transaction.hide(from).add(R.id.main_container, to,tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}
}
