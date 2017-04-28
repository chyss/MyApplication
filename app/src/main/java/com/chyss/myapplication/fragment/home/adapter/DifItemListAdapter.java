package com.chyss.myapplication.fragment.home.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.response.ListData;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.chyss.myapplication.utils.image.ImageUtil;
import com.facebook.drawee.view.SimpleDraweeView;

public class DifItemListAdapter extends BaseAdapter
{
	public static final int TYPE_COUNT = 2;
	private Context context;
	private List<ListData> listData;

	public DifItemListAdapter(Context context, List<ListData> listData)
	{
		this.context = context;
		this.listData = listData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		int currentType = getItemViewType(position);
		switch (currentType)
		{
		case 0:
			SpecialHolder holder = null;
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
			
			holder.name.setText(listData.get(position).getName());
			FrescoUtils.loadImage(listData.get(position).getImgUrl(),holder.img);

			//ImageUtil.displayImage(listData.get(position).getImgUrl(), holder.img);
			break;
		case 1:
			NormalHolder normalHolder = null;
			if (convertView == null) {
				
				convertView = LayoutInflater.from(context).inflate(R.layout.list_normal_item, null);
				normalHolder = new NormalHolder();
				normalHolder.img = (SimpleDraweeView) convertView.findViewById(R.id.list_normal_item_img);
				convertView.setTag(normalHolder);
			}
			else
			{
				normalHolder = (NormalHolder) convertView.getTag();
			}
			FrescoUtils.loadImage(listData.get(position).getImgUrl(),normalHolder.img);
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
	}
	
	private static class SpecialHolder
	{
		TextView name;
		SimpleDraweeView img;
	}
}
