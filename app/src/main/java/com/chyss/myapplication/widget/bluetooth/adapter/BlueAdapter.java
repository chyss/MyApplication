package com.chyss.myapplication.widget.bluetooth.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chyss.myapplication.R;
import java.util.List;

/**
 * @author chyss 2017-06-05
 */
public class BlueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<BluetoothDevice> devices;
    private OnItemClickListener onItemClickListener;

    public BlueAdapter(Context context,List<BluetoothDevice> devices)
    {
        this.context = context;
        this.devices = devices;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.bluetooth_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if(holder instanceof MyHolder)
        {
            ((MyHolder)holder).blue_device_name.setText(devices.get(position).getName());
            ((MyHolder)holder).blue_device_address.setText(devices.get(position).getAddress());

            if(onItemClickListener != null)
            {
                ((MyHolder)holder).blue_device_item.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        onItemClickListener.onItemClick(v,position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return devices.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout blue_device_item;
        TextView blue_device_name;
        TextView blue_device_address;

        public MyHolder(View itemView)
        {
            super(itemView);
            blue_device_item = (RelativeLayout)itemView.findViewById(R.id.blue_device_item);
            blue_device_name = (TextView)itemView.findViewById(R.id.blue_device_name);
            blue_device_address = (TextView)itemView.findViewById(R.id.blue_device_address);
        }
    }

    public void setDevices(List<BluetoothDevice> devices)
    {
        this.devices = devices;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(View view,int position);
    }
}

