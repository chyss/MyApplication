package com.chyss.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.adapter.RecyAdapter01;
import com.chyss.myapplication.data.response.RecyData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class RecyclerActivity01 extends BaseActivity
{
    private RecyclerView recyclerView;
    private List<RecyData> datas;
    private RecyAdapter01 adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        setTitle("RecyclerView01");
        initData();
        initView();
    }

    private void initData()
    {
        datas = new ArrayList<>();
        addDatas("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1512613434,148814465&fm=23&gp=0.jpg","name1");
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3169580764,676396808&fm=23&gp=0.jpg","name2");
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1249988107,620775065&fm=23&gp=0.jpg","name3");
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=125580807,845707749&fm=23&gp=0.jpg","name4");
        addDatas("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1887792679,709769868&fm=23&gp=0.jpg","name5");
        addDatas("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4180958988,1444655516&fm=23&gp=0.jpg","name6");
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=34697706,3952588914&fm=23&gp=0.jpg","name7");
        addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2711774624,662743464&fm=23&gp=0.jpg","name8");
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1980068602,128798126&fm=23&gp=0.jpg","name9");
        addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1600397295,882101291&fm=23&gp=0.jpg","name0");
        addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1300919821,2543222485&fm=23&gp=0.jpg","name1");
        addDatas("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=877927539,3829602575&fm=23&gp=0.jpg","name2");
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=903063329,2920727341&fm=23&gp=0.jpg","name3");

    }

    private void addDatas(String url,String name)
    {
        RecyData recyData = new RecyData();
        recyData.setImaUrl(url);
        recyData.setName(name);
        datas.add(recyData);
    }

    private void initView()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview01);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new RecyAdapter01(RecyclerActivity01.this,datas);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);

    }

    RecyAdapter01.OnItemClickListener onItemClickListener = new RecyAdapter01.OnItemClickListener()
    {
        @Override
        public void onItemClick(View view, int position)
        {
            Toast.makeText(RecyclerActivity01.this,"position = "+position,Toast.LENGTH_SHORT).show();
        }
    };
}
