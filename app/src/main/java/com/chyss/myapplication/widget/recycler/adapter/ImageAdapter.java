package com.chyss.myapplication.widget.recycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.UnitUtils;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by chyss on 2017-04-26
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private DisplayMetrics dm;
    private Context context;
    private List<Object> listDatas;

    public ImageAdapter(Context context, List<Object> listDatas)
    {
        this.context = context;
        this.listDatas = listDatas;
        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid, parent, false);
        //动态设置ImageView的宽高，根据自己每行item数量计算
        //dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=20dp再转换为px的宽度，最后/3得到每个item的宽高
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((dm.widthPixels - UnitUtils.dp2px(context, 20)) / 3, (dm.widthPixels - UnitUtils.dp2px(context, 20)) / 3);
        view.setLayoutParams(lp);
        return new ImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ImgViewHolder)
        {
            String url = (String) listDatas.get(position);//转换
            FrescoUtils.loadImage(url,((ImgViewHolder)holder).iv);
        }
    }

    @Override
    public int getItemCount()
    {
        return listDatas.size();
    }

    class ImgViewHolder extends RecyclerView.ViewHolder
    {

        SimpleDraweeView iv;

        public ImgViewHolder(View view)
        {
            super(view);
            iv = (SimpleDraweeView) view.findViewById(R.id.iv);
        }
    }
}
