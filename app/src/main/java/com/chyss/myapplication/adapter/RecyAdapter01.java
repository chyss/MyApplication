package com.chyss.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.data.response.RecyData;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by chyss on 2017/4/20.
 */

public class RecyAdapter01 extends RecyclerView.Adapter<RecyAdapter01.RecyHolder>
{
    private Context context;
    private List<RecyData> datas;
    private OnItemClickListener onItemClickListener;

    public RecyAdapter01(Context context, List<RecyData> datas)
    {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyAdapter01.RecyHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,null);
        RecyHolder recyHolder = new RecyHolder(view);
        return recyHolder;
    }

    @Override
    public void onBindViewHolder(RecyAdapter01.RecyHolder holder, final int position)
    {
        Logg.e(Net.TAG,"position ------- "+position);
        FrescoUtils.loadImage(datas.get(position).getImaUrl(),holder.imageView);
        holder.textView.setText(datas.get(position).getName());
        if(onItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    class RecyHolder extends RecyclerView.ViewHolder
    {
        private SimpleDraweeView imageView;
        private TextView textView;

        public RecyHolder(View view)
        {
            super(view);
            imageView = (SimpleDraweeView) view.findViewById(R.id.recy_item01_img);
            textView = (TextView) view.findViewById(R.id.recy_item01_tex);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClick (View view,int position);
    }
}

