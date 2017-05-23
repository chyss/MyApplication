package com.chyss.myapplication.widget.listview;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.view.xlist.XListView;

import java.util.ArrayList;

/**
 * @author chyss 2017-05-05
 */

public class ScrollListViewActivity extends BaseActivity implements XListView.IXListViewListener
{

    private XListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> items = new ArrayList<>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;

    private ImageView floatingBtn;
    float x, y;
    int mx, my;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_listview_activity);
        setTitle("scroll listview");
        geneItems();
        mListView = (XListView) findViewById(R.id.xListView);
        mListView.setPullLoadEnable(true);
        mAdapter = new ArrayAdapter<>(this, R.layout.list_item,items);
        mListView.setAdapter(mAdapter);
        // mListView.setPullLoadEnable(false);
        // mListView.setPullRefreshEnable(false);
        mListView.setXListViewListener(this);
        mHandler = new Handler();

        initFloating();
    }

    /**
     * 初始化浮标，以及浮标控制
     */
    private void initFloating()
    {
        floatingBtn = (ImageView) findViewById(R.id.floating_img);
        floatingBtn.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        mx = (int) (event.getRawX() - x);
                        my = (int) (event.getRawY() - 50 - y);

                        floatingBtn.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void geneItems()
    {
        for (int i = 0; i != 20; i++) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad()
    {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh()
    {
        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                start = ++refreshCnt;
                items.clear();
                geneItems();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new ArrayAdapter<>(ScrollListViewActivity.this,
                        R.layout.list_item, items);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);

    }

    @Override
    public void onLoadMore()
    {
        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }
}
