package com.chyss.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.DoAction;
import com.chyss.myapplication.view.AnimImageView;
import com.chyss.myapplication.widget.dialog.BottomPopDialog;
import com.chyss.myapplication.widget.dialog.CenterPopDialog;
import com.chyss.myapplication.widget.flingswipe.SwipeActivity;
import com.chyss.myapplication.widget.largPicture.bigshow.LargeImageViewActivity;
import com.chyss.myapplication.widget.listview.ScrollListViewActivity;
import com.chyss.myapplication.widget.notice.NotificationHelper;
import com.chyss.myapplication.widget.recycler.Activity1;
import com.chyss.myapplication.widget.recycler.Activity2;
import com.chyss.myapplication.widget.webview.WebView;

/**
 * 展示各类UI的设置
 */
public class AcountFragment extends BaseFragment {

	private AnimImageView anim_imgview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.main_acount_fragment, container,
				false);
		init(root);
		return root;
	}

	private void init(View root) {

		RelativeLayout bottompop = (RelativeLayout) root.findViewById(R.id.bottompop);
		RelativeLayout centerpop_1 = (RelativeLayout) root.findViewById(R.id.centerpop_1);
		RelativeLayout centerpop_2 = (RelativeLayout) root.findViewById(R.id.centerpop_2);
		bottompop.setOnClickListener(onClickListener);
		centerpop_1.setOnClickListener(onClickListener);
		centerpop_2.setOnClickListener(onClickListener);

		RelativeLayout notice_to_activity = (RelativeLayout) root.findViewById(R.id.notice_to_activity);
		RelativeLayout notice_to_useract = (RelativeLayout) root.findViewById(R.id.notice_to_useract);
		notice_to_activity.setOnClickListener(onClickListener);
		notice_to_useract.setOnClickListener(onClickListener);

		RelativeLayout local_webview = (RelativeLayout) root.findViewById(R.id.local_webview);
		local_webview.setOnClickListener(onClickListener);
		RelativeLayout scroll_list = (RelativeLayout) root.findViewById(R.id.scroll_list);
		scroll_list.setOnClickListener(onClickListener);

		anim_imgview = (AnimImageView)root.findViewById(R.id.anim_imgview);
		RelativeLayout set_animview = (RelativeLayout) root.findViewById(R.id.set_animview);
		set_animview.setOnClickListener(onClickListener);

		RelativeLayout large_img = (RelativeLayout) root.findViewById(R.id.large_img);
		large_img.setOnClickListener(onClickListener);

		RelativeLayout to_activity1 = (RelativeLayout) root.findViewById(R.id.recycler_refresh1);
		to_activity1.setOnClickListener(onClickListener);

		RelativeLayout to_activity2 = (RelativeLayout) root.findViewById(R.id.recycler_refresh2);
		to_activity2.setOnClickListener(onClickListener);

		RelativeLayout swip_view = (RelativeLayout) root.findViewById(R.id.swip_view);
		swip_view.setOnClickListener(onClickListener);
	}

	OnClickListener onClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.bottompop:
					showBottomDialog();
				break;
				case R.id.centerpop_1:
					showCenterDialog_1();
					break;
				case R.id.centerpop_2:
					showCenterDialog_2();
					break;
				case R.id.notice_to_activity:
					NotificationHelper.sendNotice(getActivity(),"新时代的来客","你想要的只能自己给！","life like a song!","to_activity","notice","",R.mipmap.ic_launcher,R.mipmap.logo);
					break;
				case R.id.notice_to_useract:
					NotificationHelper.sendNotice(getActivity(),"旧时代的过客","匆匆那年，我们都改变了原来的模样！","life where are you?","to_web","","http://www.baidu.com",R.mipmap.ic_launcher,R.mipmap.logo);
					break;
				case R.id.local_webview:
					WebView.load(getActivity(),"file:///android_asset/index.html","网页");
					break;
				case R.id.scroll_list:
					DoAction.startActivity(getActivity(),ScrollListViewActivity.class);
					break;
				case R.id.set_animview:
					//获取手机分配给app的最大内存
					int memory = (int) Runtime.getRuntime().maxMemory() / 1024 / 1024;
					Toast.makeText(getActivity(),"maxMemory : "+memory+"M",Toast.LENGTH_SHORT).show();

					anim_imgview.setImageResource(R.mipmap.logo);
					break;
				case R.id.large_img:
					DoAction.startActivity(getActivity(),LargeImageViewActivity.class);
					break;
				case R.id.recycler_refresh1:
					DoAction.startActivity(getActivity(),Activity1.class);
					break;
				case R.id.recycler_refresh2:
					DoAction.startActivity(getActivity(),Activity2.class);
					break;
				case R.id.swip_view:
					DoAction.startActivity(getActivity(),SwipeActivity.class);
					break;
				default:
				break;
			}
		}
	};

	protected void showCenterDialog_1() {

		CenterPopDialog dialog = new CenterPopDialog(getActivity(),"标题","人生若只如初见！",1,"确定","")
		{
			@Override
			public void left()
			{
				dismiss();
			}

			@Override
			public void right()
			{
				dismiss();
			}
		};
		dialog.show();
	}

	protected void showCenterDialog_2() {

		CenterPopDialog dialog = new CenterPopDialog(getActivity(),"木兰词"," <font color=\"#FF0000\">人生若只如初见</font>， 何事秋风悲画扇。等闲变却故人心， 却道故人心易变。骊山语罢清宵半， 泪雨零铃终不怨。何如薄幸锦衣郎， 比翼连枝当日愿。"
				+"<br /><font color=\"#999999\">与意中人相处应当总像刚刚相识的时候，是那样地甜蜜，那样地温馨，那样地深情和快乐。但你我本应当相亲相爱，却为何成了今日的相离相弃？如今轻易地变了心，你却反而说情人间就是容易变心的。我与你就像唐明皇与杨玉环那样，在长生殿起过生死不相离的誓言，却又最终作决绝之别，即使如此，也不生怨。但你又怎比得上当年的唐明皇呢，他总还是与杨玉环有过比翼鸟、连理枝的誓愿。</font> ",2,"取消","确定")
		{
			@Override
			public void left()
			{
				dismiss();
			}

			@Override
			public void right()
			{
				dismiss();
			}
		};
		dialog.show();
	}

	protected void showBottomDialog() {

		BottomPopDialog dialog = new BottomPopDialog(getActivity(),
				R.style.SheetDialogStyle);
		dialog.show();
	}
}
