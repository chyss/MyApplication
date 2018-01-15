package com.chyss.myapplication.widget.scrollshow;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.Logg;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ScrollShowActivity extends Activity implements MyScrollView.OnScrollListener
{
    MyScrollView myScrollView;
    TextView view1,view2;
    LinearLayout linear;
    int h;
    TextView show1,show2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_show_layout);

        myScrollView = (MyScrollView)findViewById(R.id.myscroll_view);
        view1 = (TextView)findViewById(R.id.view1);
        view2 = (TextView)findViewById(R.id.view2);
        linear = (LinearLayout)findViewById(R.id.linear);

        show1 = (TextView)findViewById(R.id.show1);
        show2 = (TextView)findViewById(R.id.show2);

        myScrollView.setOnScrollListener(this);

        // 获取屏幕密度（方法2）
        DisplayMetrics dm = getResources().getDisplayMetrics();

        Logg.e("scroll","density = "+dm.density);

        int screenWidth  = getWindowManager().getDefaultDisplay().getWidth();       // 屏幕宽（像素，如：480px）
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();      // 屏幕高（像素，如：800p）

        Logg.e("scroll","screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        Logg.e("scroll","-------------------- view1=" + view1.getTop()  + "; view1b=" + view1.getBottom()+ "; view2=" + view2.getTop());

        linear.setVisibility(View.GONE);
    }

    @Override
    public void onScroll(int l, int t, int oldl, int oldt,int scrollY,int height)
    {
        Logg.e("scroll","l="+l+",t="+t+",oldl="+oldl+",oldt="+oldt+",scrollY"+scrollY+",height"+height);

        if(height + scrollY >= view1.getTop())
        {
            show1.setVisibility(View.GONE);
        }
        else
        {
            show1.setVisibility(View.VISIBLE);
        }

        if(height + scrollY >= view2.getTop())
        {
            show2.setVisibility(View.GONE);
        }
        else
        {
            show2.setVisibility(View.VISIBLE);
        }
    }
}
