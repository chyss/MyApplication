package com.chyss.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.Net;
import com.chyss.myapplication.data.response.KindData;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by chyss on 2017/4/20.
 */

public class KindListAdapter extends RecyclerView.Adapter<KindListAdapter.RecyHolder>
{
    private Context context;
    private List<KindData> datas;
    private OnItemClickListener onItemClickListener;

    public KindListAdapter(Context context, List<KindData> datas)
    {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public KindListAdapter.RecyHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.kind_list_item,null);
        RecyHolder recyHolder = new RecyHolder(view);
        return recyHolder;
    }

    @Override
    public void onBindViewHolder(KindListAdapter.RecyHolder holder, final int position)
    {
        Logg.e(Net.TAG,"position ------- "+position);
        FrescoUtils.loadImage(datas.get(position).getImg(),holder.img);
        holder.title.setText(datas.get(position).getTitle());
        holder.comment.setText(datas.get(position).getComment());
        holder.date.setText(datas.get(position).getDate());
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
        private SimpleDraweeView img;
        private TextView title;
        private TextView comment;
        private TextView date;


        public RecyHolder(View view)
        {
            super(view);
            img = (SimpleDraweeView) view.findViewById(R.id.kind_item_img);
            title = (TextView) view.findViewById(R.id.kind_item_title);
            comment = (TextView) view.findViewById(R.id.kind_item_comment);
            date = (TextView) view.findViewById(R.id.kind_item_date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }
}

