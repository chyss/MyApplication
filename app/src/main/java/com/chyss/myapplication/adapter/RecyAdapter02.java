package com.chyss.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.data.response.RecyData02;

import java.util.List;

/**
 * Created by chyss on 2017/4/20.
 */

public class RecyAdapter02 extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<RecyData02> datas;
    private OnItemClickListener onItemClickListener;
    private static final int NORMAL_TYPE = 0;
    private static final int RECY_TYPE = 1;

    public RecyAdapter02(Context context, List<RecyData02> datas)
    {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == RECY_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item02_recy,null);
            RecyHolder recyHolder = new RecyHolder(view);
            return recyHolder;
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item02_normal,null);
            NormalHolder normalHolder = new NormalHolder(view);
            return normalHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if(holder instanceof RecyHolder)
        {
//            LinearLayoutManager manager = new LinearLayoutManager(context);
//            manager.setOrientation(LinearLayoutManager.HORIZONTAL);

            GridLayoutManager manager = new GridLayoutManager(context,3);
            ((RecyHolder)holder).recyclerView.setLayoutManager(manager);
            RecyAdapter01 adapter = new RecyAdapter01(context,datas.get(position).getList());
            ((RecyHolder)holder).recyclerView.setAdapter(adapter);
        }
        else
        {
            ((NormalHolder)holder).textView.setText(datas.get(position).getName());
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return datas.get(position).getType();
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    class RecyHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView recyclerView;

        public RecyHolder(View view)
        {
            super(view);
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview02_recy);
        }
    }

    class NormalHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;

        public NormalHolder(View view)
        {
            super(view);
            textView = (TextView) view.findViewById(R.id.recy_item01_tex);
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

