package com.chyss.myapplication.widget.flingswipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class SwipeAdapter extends BaseAdapter
{
    Context context;
    List<String> urls;

    public SwipeAdapter(Context context,List<String> urls)
    {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount()
    {
        return urls.size();
    }

    @Override
    public Object getItem(int position)
    {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        SwipeHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.flingswipe_item_img, null);
            holder = new SwipeHolder();
            holder.swip_img = (SimpleDraweeView) convertView.findViewById(R.id.swip_img);
//            holder.swip_img = (ImageView) convertView.findViewById(R.id.swip_img);
            convertView.setTag(holder);
        }
        else
        {
            holder = (SwipeHolder) convertView.getTag();
        }

//        ImageUtil.displayImage(urls.get(position),holder.swip_img);
        FrescoUtils.loadImage(urls.get(position),holder.swip_img);
        return convertView;
    }

    private static class SwipeHolder
    {
        SimpleDraweeView swip_img;
//        ImageView swip_img;
    }
}
