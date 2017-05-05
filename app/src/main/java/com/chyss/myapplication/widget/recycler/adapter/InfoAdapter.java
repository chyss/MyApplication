package com.chyss.myapplication.widget.recycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.image.FrescoUtils;
import com.chyss.myapplication.view.CircleMovementMethod;
import com.chyss.myapplication.view.SpannableClickable;
import com.chyss.myapplication.widget.recycler.bean.InfoBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by chyss on 2017-04-26
 */
public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<InfoBean> datas;
    private static final int FRIEND = 1;
    private static final int COMMENT = 2;

    public InfoAdapter(Context context, List<InfoBean> datas)
    {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_info, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof MyViewHolder)
        {
            InfoBean infoBean = datas.get(position);//转换
            ((MyViewHolder)holder).tv.setText(infoBean.getContents());//填充数据
            ((MyViewHolder)holder).update_time.setText(infoBean.getUpDateTime());
            ((MyViewHolder)holder).title_name.setText(infoBean.getTitle());
            FrescoUtils.loadImage(infoBean.getImgUrl(),((MyViewHolder)holder).ic);
            initZanComments(infoBean,(MyViewHolder)holder,position);

            if(infoBean.getImgList()!=null && infoBean.getImgList().size()>0){
                ImageAdapter imageAdapter = new ImageAdapter(context, infoBean.getImgList());
                ((MyViewHolder)holder).rv_grid.setLayoutManager(new GridLayoutManager(context,3));
                ((MyViewHolder)holder).rv_grid.setAdapter(imageAdapter);
                ((MyViewHolder)holder).rv_grid.setVisibility(View.VISIBLE);
            }else{
                ((MyViewHolder)holder).rv_grid.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;//内容
        ImageView iv_z, iv_pl;//赞，评论
        RecyclerView rv_grid;
        SimpleDraweeView ic;
        TextView update_time;
        TextView title_name;
        LinearLayout user_comments;
        TextView zan_guys;
        TextView comments;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            rv_grid = (RecyclerView) view.findViewById(R.id.rv_grid);
            iv_z = (ImageView) view.findViewById(R.id.iv_z);
            iv_pl = (ImageView) view.findViewById(R.id.iv_pl);
            ic = (SimpleDraweeView)view.findViewById(R.id.ic);
            update_time = (TextView) view.findViewById(R.id.update_time);
            title_name = (TextView) view.findViewById(R.id.title_name);
            user_comments = (LinearLayout) view.findViewById(R.id.user_comments);
            zan_guys = (TextView) view.findViewById(R.id.zan_guys);
            comments = (TextView) view.findViewById(R.id.comments);
        }
    }

    private void initZanComments(InfoBean infoBean,MyViewHolder holder, int position)
    {
        if((infoBean.getZans() == null || infoBean.getZans().size() <= 0) && (infoBean.getComments() == null || infoBean.getComments().size() <= 0))
        {
            holder.user_comments.setVisibility(View.GONE);
            return;
        }
        holder.user_comments.setVisibility(View.VISIBLE);

        // zan
        if(infoBean.getZans() != null && infoBean.getZans().size() > 0)
        {
            holder.zan_guys.setVisibility(View.VISIBLE);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            CircleMovementMethod circleMovementMethod = new CircleMovementMethod(context);
            for (int i = 0; i < infoBean.getZans().size(); i++)
            {
                builder.append("  ");
                builder.append(getSpannable(infoBean.getZans().get(i).toString(),"#23bcb9",FRIEND));
            }
            holder.zan_guys.setText(builder);
            holder.zan_guys.setMovementMethod(circleMovementMethod);
        }
        else
        {
            holder.zan_guys.setVisibility(View.GONE);
        }

        // comments
        if(infoBean.getZans() != null && infoBean.getZans().size() > 0)
        {
            holder.comments.setVisibility(View.VISIBLE);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            CircleMovementMethod circleMovementMethod = new CircleMovementMethod(context);
            for (int i = 0; i < infoBean.getComments().size(); i++)
            {
                builder.append(getSpannable(infoBean.getComments().get(i).getName()+ ":  ","#23bcb9",FRIEND));
                builder.append(getSpannable(infoBean.getComments().get(i).getComment(),"#666666",COMMENT));
                if( i != infoBean.getComments().size() - 1)
                {
                    builder.append("\n");
                }
            }
            holder.comments.setText(builder);
            holder.comments.setMovementMethod(circleMovementMethod);
        }
        else
        {
            holder.comments.setVisibility(View.GONE);
        }
    }

    private SpannableString getSpannable(String contents,String color,final int type)
    {
        final SpannableString subjectSpanText = new SpannableString(contents);
        subjectSpanText.setSpan(new SpannableClickable(Color.parseColor(color)) {
                                    @Override
                                    public void onClick(View widget)
                                    {
                                        switch (type)
                                        {
                                            case FRIEND:
                                                Toast.makeText(context,"friend -- "+subjectSpanText,Toast.LENGTH_SHORT).show();
                                                break;
                                            case COMMENT:
                                                Toast.makeText(context,"comment -- "+subjectSpanText,Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }
}
