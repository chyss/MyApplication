package com.chyss.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.adapter.RecyAdapter02;
import com.chyss.myapplication.data.response.RecyData;
import com.chyss.myapplication.data.response.RecyData02;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */

public class RecyclerActivity02 extends BaseActivity
{
    private RecyclerView recyclerView;
    private List<RecyData02> datas = new ArrayList<RecyData02>();;
    private RecyAdapter02 adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler02);
        setTitle("RecyclerView02");
        initData(0);
        initData(1);
        initData(0);
        initData(1);
        initData(0);
        initData(1);
        initData(0);
        initData(1);
        initData(0);
        initData(1);
        initData(0);
        initData(1);
        initData(0);
        initData(1);
        initView();
    }

    private void initData(int type)
    {

        List<RecyData> data = new ArrayList<RecyData>();
        addDatas("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1512613434,148814465&fm=23&gp=0.jpg","name1",data);
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3169580764,676396808&fm=23&gp=0.jpg","name2",data);
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1249988107,620775065&fm=23&gp=0.jpg","name3",data);
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=125580807,845707749&fm=23&gp=0.jpg","name4",data);
        addDatas("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1887792679,709769868&fm=23&gp=0.jpg","name5",data);
        addDatas("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4180958988,1444655516&fm=23&gp=0.jpg","name6",data);
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=34697706,3952588914&fm=23&gp=0.jpg","name7",data);
        addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2711774624,662743464&fm=23&gp=0.jpg","name8",data);
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1980068602,128798126&fm=23&gp=0.jpg","name9",data);
        addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1600397295,882101291&fm=23&gp=0.jpg","name0",data);
        addDatas("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1300919821,2543222485&fm=23&gp=0.jpg","name1",data);
        addDatas("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=877927539,3829602575&fm=23&gp=0.jpg","name2",data);
        addDatas("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=903063329,2920727341&fm=23&gp=0.jpg","name3",data);

        addRecyData(data,type);

    }

    private void addDatas(String url,String name,List<RecyData> data)
    {
        RecyData recyData = new RecyData();
        recyData.setImaUrl(url);
        recyData.setName(name);
        data.add(recyData);
    }

    private void addRecyData(List<RecyData> data,int type)
    {
        RecyData02 recyData02 = new RecyData02();
        recyData02.setList(data);
        recyData02.setName("recylerview 02");
        recyData02.setType(type);
        datas.add(recyData02);
    }

    private void initView()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview02);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new RecyAdapter02(RecyclerActivity02.this,datas);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);

    }

    RecyAdapter02.OnItemClickListener onItemClickListener = new RecyAdapter02.OnItemClickListener()
    {
        @Override
        public void onItemClick(View view, int position)
        {
            Toast.makeText(RecyclerActivity02.this,"position = "+position,Toast.LENGTH_SHORT).show();
        }
    };
}
