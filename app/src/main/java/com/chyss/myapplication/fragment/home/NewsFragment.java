package com.chyss.myapplication.fragment.home;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.UnitUtils;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * viewpager实现的广播循环轮播
 * 
 * @author chyss 2016.7.27
 *
 */
public class NewsFragment extends Fragment
{
	public static final String TAG = "NewsFragment";
	private static final int CYCLE_TIME = 5000;  //轮播时间
	private LinearLayout notsLayout;
	private ViewPager newsPager;
	private List<ImageView> list = new ArrayList<>();
	private int index = 0; //当前滑动到的页面，同时是自动滑动控制的标记位

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.news_fragment_layout, container,
				false);
		init(root);
		initData();
		//handler.sendEmptyMessageDelayed(1, 500);
		return root;
	}

	private void initData()
	{
		addViewToList("http://img5.imgtn.bdimg.com/it/u=2916880539,291677888&fm=27&gp=0.jpg");
		addViewToList("http://img5.imgtn.bdimg.com/it/u=3808425403,2510620310&fm=27&gp=0.jpg");
		addViewToList("http://img5.imgtn.bdimg.com/it/u=3352575184,460439047&fm=27&gp=0.jpg");
		intiPagerData();
	}

	private void addViewToList(String imgUrl)
	{
//		Log.e("chyss", "getActivity() : "+getActivity());
//		ImageView imageView = new ImageView(getActivity());
//		imageView.setScaleType(ScaleType.CENTER_CROP);
//		ImageUtil.displayImage(imgUrl, imageView);
//		list.add(imageView);

		SimpleDraweeView sv = new SimpleDraweeView(getActivity());
		FrescoUtils.loadImage(imgUrl,sv);
		list.add(sv);
	}

	private void init(View root)
	{
		newsPager = (ViewPager) root.findViewById(R.id.home_news_viewpager);
		notsLayout = (LinearLayout) root.findViewById(R.id.news_notes_layout);
	}

	/**
	 * 初始化viewpager和notes，同时开始动态循环播放news view
	 */
	private void intiPagerData()
	{
		//初始化viewpager
		newsPager.setAdapter(pagerAdapter);
		newsPager.addOnPageChangeListener(onPageChangeListener);

		//初始化notes
		for (int i = 0; i < list.size(); i++) {

			ImageView imageView = new ImageView(getActivity());
			if (i == 0) {
				imageView.setBackgroundResource(R.mipmap.note_selected);
			} else {
				imageView.setBackgroundResource(R.mipmap.note_normal);
			}
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.leftMargin = UnitUtils.dp2px(
					getActivity(), 5);
			params.rightMargin = UnitUtils.dp2px(getActivity(), 5);
			imageView.setLayoutParams(params);
			notsLayout.addView(imageView);
		}

		//开始循环播放
		index ++;
		handler.sendEmptyMessageDelayed(0, CYCLE_TIME);
	}

	PagerAdapter pagerAdapter = new PagerAdapter()
	{

		/**
		 * 判断出去的view是否等于进来的view 如果为true直接复用
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public int getCount()
		{
			// 设置成最大，使用户看不到边界
			return Integer.MAX_VALUE;
		}

		/**
		 * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来就是position
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			// Warning：不要在这里调用removeView
			// container.removeView(list.get(position));
		}

		/**
		 * 创建一个view
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			// 对ViewPager页号求模取出View列表中要显示的项
			position %= list.size();
			if (position < 0) {
				position = list.size() + position;
			}
			ImageView view = list.get(position);
			// 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
			ViewParent vp = view.getParent();
			if (vp != null) {
				ViewGroup parent = (ViewGroup) vp;
				parent.removeView(view);
			}
			container.addView(view);
			return list.get(position);
		}
	};

	OnPageChangeListener onPageChangeListener = new OnPageChangeListener()
	{

		@Override
		public void onPageSelected(int position)
		{
			//用于解决用户自己滑动viewpager时，和自动循环的冲突，提高用户体验
			handler.removeMessages(0);
			index = position + 1;
			handler.sendEmptyMessageDelayed(0, CYCLE_TIME);
			
			//更改notes的状态
			position %= list.size();
			clearAllNotesState();
			ImageView imageView = (ImageView) notsLayout.getChildAt(position);
			imageView.setBackgroundResource(R.mipmap.note_selected);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{

		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{

		}
	};

	/**
	 * 清空notes的状态
	 */
	private void clearAllNotesState()
	{
		for (int i = 0; i < notsLayout.getChildCount(); i++)
		{
			ImageView imageView = (ImageView) notsLayout.getChildAt(i);
			imageView.setBackgroundResource(R.mipmap.note_normal);
		}
	}
	
	/**
	 * 循环延时切换view
	 */
	Handler mhandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case 0:
				newsPager.setCurrentItem(index);
				break;
			default:
				break;
			}
		}
	};

	private final MyHandler handler = new MyHandler(this);

	static class MyHandler extends Handler
	{
		// 使用WeakReference保存当前引用，方便系统GC
		WeakReference<NewsFragment> fragment;

		private MyHandler(NewsFragment fragment)
		{
			this.fragment = new WeakReference<>(fragment);
		}

		@Override
		public void handleMessage(Message msg)
		{
			NewsFragment newsFragment = fragment.get();
			if(newsFragment != null)
			{
				switch (msg.what)
				{
					case 0:
						newsFragment.newsPager.setCurrentItem(newsFragment.index);
						break;
					default:
						break;
				}
			}
		}
	}
}
