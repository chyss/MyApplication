package com.chyss.myapplication.fragment.home.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.response.ListData;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class DifItemListAdapter extends BaseAdapter
{
	public static final int TYPE_COUNT = 2;
	private Context context;
	private List<ListData> listData;
	private OnListItemClick onListItemClick;

	public DifItemListAdapter(Context context, List<ListData> listData,OnListItemClick onListItemClick)
	{
		this.context = context;
		this.listData = listData;
		this.onListItemClick = onListItemClick;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{

		int currentType = getItemViewType(position);
		switch (currentType)
		{
		case 0:
			SpecialHolder holder;
			if (convertView == null) {
				
				convertView = LayoutInflater.from(context).inflate(R.layout.list_special_item, null);
				holder = new SpecialHolder();
				holder.name = (TextView) convertView.findViewById(R.id.list_special_item_name);
				holder.img = (SimpleDraweeView) convertView.findViewById(R.id.list_special_item_img);

				convertView.setTag(holder);
			}
			else
			{
				holder = (SpecialHolder) convertView.getTag();
			}

			convertView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					onListItemClick.specialClick(position);
				}
			});

			holder.name.setText(listData.get(position).getName());
			FrescoUtils.loadImage(listData.get(position).getImgUrl1(),holder.img);

			//ImageUtil.displayImage(listData.get(position).getImgUrl(), holder.img);
			break;
		case 1:
			NormalHolder normalHolder;
			if (convertView == null) {
				
				convertView = LayoutInflater.from(context).inflate(R.layout.list_normal_item, null);
				normalHolder = new NormalHolder();
				normalHolder.img = (SimpleDraweeView) convertView.findViewById(R.id.list_normal_item_img);
				normalHolder.img2 = (SimpleDraweeView) convertView.findViewById(R.id.list_normal_item_img2);
				normalHolder.imglay = (RelativeLayout) convertView.findViewById(R.id.list_normal_item_imglay);
				normalHolder.imglay2 = (RelativeLayout) convertView.findViewById(R.id.list_normal_item_imglay2);
				normalHolder.imglay.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						//					Toast.makeText(context,"imglay",Toast.LENGTH_SHORT).show();
						onListItemClick.imglayClick(position);
					}
				});

				normalHolder.imglay2.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						//					Toast.makeText(context,"imglay2",Toast.LENGTH_SHORT).show();
						onListItemClick.imglay2Click(position);
					}
				});
				convertView.setTag(normalHolder);
			}
			else
			{
				normalHolder = (NormalHolder) convertView.getTag();
			}

			FrescoUtils.loadImage(listData.get(position).getImgUrl1(),normalHolder.img);
			FrescoUtils.loadImage(listData.get(position).getImgUrl2(),normalHolder.img2);
//			ImageUtil.displayImage(listData.get(position).getImgUrl(), normalHolder.img);
			break;

		default:
			break;
		}

		return convertView;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public Object getItem(int position)
	{
		return listData.get(position);
	}

	@Override
	public int getCount()
	{
		return listData.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		return listData.get(position).getType();
	}

	@Override
	public int getViewTypeCount()
	{
		return TYPE_COUNT;
	}
	
	private static class NormalHolder
	{
		SimpleDraweeView img;
		SimpleDraweeView img2;
		RelativeLayout imglay;
		RelativeLayout imglay2;
	}
	
	private static class SpecialHolder
	{
		TextView name;
		SimpleDraweeView img;
	}

	public interface OnListItemClick{
		void imglayClick(final int position);
		void imglay2Click(final int position);
		void specialClick(final int position);
	}
}
