package com.chyss.myapplication.widget.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chyss.myapplication.R;

import java.util.List;

/**
 * Created by chyss 2017-04-26
 */
public class TextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<Object> listDatas;

    public TextAdapter(Context context, List<Object> listDatas)
    {
        this.context = context;
        this.listDatas = listDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new TextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof TextViewHolder)
        {
            String text = (String) listDatas.get(position);//转换
            ((TextViewHolder)holder).tv.setText(text);//填充数据
        }
    }

    @Override
    public int getItemCount()
    {
        return listDatas.size();
    }

    //    public TextAdapter(Context context, List<Object> listDatas) {
    //        super(context, listDatas);
    //    }
    //
    //    @Override
    //    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //        return new MyViewHolder(mInflater.inflate(R.layout.item_text, parent, false));
    //    }
    //
    //    @Override
    //    public void onBindViewHolder(MyViewHolder holder, final int position) {
    //        super.onBindViewHolder(holder, position);
    //        String text = (String) listDatas.get(position);//转换
    //        holder.tv.setText(text);//填充数据
    //    }
    //
    //    @Override
    //    public int getItemCount() {
    //        return listDatas.size();
    //    }
    //
        class TextViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public TextViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv);
            }
        }
}
