package com.chyss.myapplication.widget.recycler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.data.ResultDesc;
import com.chyss.myapplication.utils.analysis.DataImpl;
import com.chyss.myapplication.widget.recycler.adapter.InfoAdapter;
import com.chyss.myapplication.widget.recycler.bean.Contant;
import com.chyss.myapplication.widget.recycler.bean.InfoBean;
import com.chyss.myapplication.widget.recycler.bean.InfoData;
import com.chyss.myapplication.widget.recycler.view.PullBaseView;
import com.chyss.myapplication.widget.recycler.view.PullRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Activity2 extends BaseActivity implements PullBaseView.OnRefreshListener
{

    PullRecyclerView recyclerView;
    List<InfoBean> mDatas;
    InfoAdapter infoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        missTitleView();
        initData();
        initRecyclerView();
    }

    protected void initData()
    {
        ResultDesc<InfoData> resultDesc = DataImpl.getData(Contant.JSON, InfoData.class);
        InfoData infoData = resultDesc.getData();
        mDatas = infoData.getInfoList();
    }

    private List<Object> getImageList()
    {
        List<Object> imageList = new ArrayList<>();
        imageList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1249988107,620775065&fm=23&gp=0.jpg");
        imageList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=125580807,845707749&fm=23&gp=0.jpg");
        imageList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1887792679,709769868&fm=23&gp=0.jpg");
        imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4180958988,1444655516&fm=23&gp=0.jpg");
        imageList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=34697706,3952588914&fm=23&gp=0.jpg");
        imageList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2711774624,662743464&fm=23&gp=0.jpg");

        return imageList;
    }

    private void initRecyclerView()
    {
        recyclerView = (PullRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new InfoAdapter(this, mDatas);
        recyclerView.setAdapter(infoAdapter);
    }

    /**
     * 上拉加载
     *
     * @param view
     */
    @Override
    public void onFooterRefresh(PullBaseView view)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                InfoBean info = new InfoBean();
                info.setContents("人生若只如初见，何事秋风悲画扇。等闲变却故人心，却道故人心易变。骊山语罢清宵半，泪雨零铃终不怨。何如薄幸锦衣郎，比翼连枝当日愿。");
                info.setImgList(getImageList());
                info.setTitle("hello");
                info.setUpDateTime("15:45");
                info.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493203014945&di=157f75d058dc206e5adb36e950a2ddcf&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F44%2F22%2F15b1OOOPIC2a.jpg");
                mDatas.add(info);
                infoAdapter.notifyDataSetChanged();
                recyclerView.onFooterRefreshComplete();
            }
        }, 1500);
    }

    /**
     * 下拉刷新
     *
     * @param view
     */
    @Override
    public void onHeaderRefresh(PullBaseView view)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                InfoBean info = new InfoBean();
                info.setContents("人生若只如初见，何事秋风悲画扇。等闲变却故人心，却道故人心易变。骊山语罢清宵半，泪雨零铃终不怨。何如薄幸锦衣郎，比翼连枝当日愿。");
                info.setImgList(getImageList());
                info.setTitle("hello");
                info.setUpDateTime("15:45");
                info.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493203014945&di=157f75d058dc206e5adb36e950a2ddcf&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F44%2F22%2F15b1OOOPIC2a.jpg");
                mDatas.add(0, info);
                infoAdapter.notifyDataSetChanged();
                recyclerView.onHeaderRefreshComplete();
            }
        }, 1500);
    }
}
