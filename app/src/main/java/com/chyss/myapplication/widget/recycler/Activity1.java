package com.chyss.myapplication.widget.recycler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.recycler.adapter.TextAdapter;
import com.chyss.myapplication.widget.recycler.view.PullBaseView;
import com.chyss.myapplication.widget.recycler.view.PullRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Activity1 extends AppCompatActivity implements PullBaseView.OnRefreshListener {

    PullRecyclerView recyclerView;
    List<Object> mDatas;
    TextAdapter textAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        initData();
        initRecyclerView();
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            mDatas.add("TEXT" + i);
        }
    }

    private void initRecyclerView() {
        recyclerView = (PullRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textAdapter = new TextAdapter(this, mDatas);
//        textAdapter.setOnItemClickListener(this);
//        textAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(textAdapter);
    }


//    @Override
//    public void onItemClick(int position) {
//        Toast.makeText(Activity1.this, "点击-position>>" + position, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onItemLongClick(int position) {
//        Toast.makeText(Activity1.this, "长按-position>>" + position, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onFooterRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.add("TEXT更多");
                textAdapter.notifyDataSetChanged();
                recyclerView.onFooterRefreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.add(0, "TEXT新增");
                textAdapter.notifyDataSetChanged();
                recyclerView.onHeaderRefreshComplete();
            }
        }, 1500);
    }

}
