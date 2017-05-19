package com.chyss.myapplication.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.activity.RecyclerActivity01;
import com.chyss.myapplication.adapter.KindListAdapter;
import com.chyss.myapplication.adapter.RecyAdapter01;
import com.chyss.myapplication.adapter.RecyAdapter02;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.data.response.KindData;
import com.chyss.myapplication.data.response.RecyData;
import com.chyss.myapplication.data.response.RecyData02;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.view.xlist.XListView;
import com.chyss.myapplication.widget.recycler.view.PullBaseView;

public class KindFragment extends Fragment
{
	private RecyclerView recyclerView;
	private List<KindData> datas = new ArrayList<>();
	private KindListAdapter adapter;
	private LinearLayoutManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.main_kind_fragment, container,
				false);
		recyclerView = (RecyclerView) root.findViewById(R.id.kind_recycler_view);
		manager = new LinearLayoutManager(getActivity());
		manager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(manager);
		adapter = new KindListAdapter(getActivity(),datas);
		adapter.setOnItemClickListener(onItemClickListener);
		recyclerView.setAdapter(adapter);

		return root;
	}

	KindListAdapter.OnItemClickListener onItemClickListener = new KindListAdapter.OnItemClickListener()
	{
		@Override
		public void onItemClick(View view, int position)
		{
			Toast.makeText(getActivity(),"position = "+position,Toast.LENGTH_SHORT).show();
		}
	};

	private void initData()
	{
		datas = new ArrayList<>();
		addDatas("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1512613434,148814465&fm=23&gp=0.jpg","name1","hello world 1","16:25");
		addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3169580764,676396808&fm=23&gp=0.jpg","name2","hello world 2","15:25");
		addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1249988107,620775065&fm=23&gp=0.jpg","name3","hello world 3","14:00");
		addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=125580807,845707749&fm=23&gp=0.jpg","name444444444444444444444444444444444444444","hello world 4444444444444444444444444444444444444444444444","13:25");
		addDatas("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1887792679,709769868&fm=23&gp=0.jpg","name5","hello world 5","12:02");
		addDatas("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4180958988,1444655516&fm=23&gp=0.jpg","name6","hello world 6","10:25");
		addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=34697706,3952588914&fm=23&gp=0.jpg","name7","hello world 7","08:25");
		addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2711774624,662743464&fm=23&gp=0.jpg","name8","hello world 8","昨天");
		addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1980068602,128798126&fm=23&gp=0.jpg","name9","hello world 9","昨天");
		addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1600397295,882101291&fm=23&gp=0.jpg","name0","hello world 10","昨天");
		addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1300919821,2543222485&fm=23&gp=0.jpg","name1","hello world 11","周一");
		addDatas("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=877927539,3829602575&fm=23&gp=0.jpg","name2","hello world 22","周一");
		addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=903063329,2920727341&fm=23&gp=0.jpg","name3","hello world 33","5月12日");

	}

	private void addDatas(String img,String title,String comment,String date)
	{
		KindData kindData = new KindData();
		kindData.setImg(img);
		kindData.setTitle(title);
		kindData.setComment(comment);
		kindData.setDate(date);
		datas.add(kindData);
	}
}
