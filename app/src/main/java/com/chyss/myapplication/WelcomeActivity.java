package com.chyss.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import com.chyss.myapplication.utils.image.ImageUtil;

/**
 * app 的首页
 */
public class WelcomeActivity extends Activity
{
    private Button welcome_btn;
    private ImageView welcome_img;
    private int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_activity);

        welcome_img = (ImageView)findViewById(R.id.welcome_img);
        welcome_btn = (Button)findViewById(R.id.welcome_btn);
        welcome_btn.setOnClickListener(onClickListener);

        handler.sendEmptyMessageDelayed(0,1000);
        initView();
    }

    private void initView()
    {
        ImageUtil.displayImage("http://imgm.cnmo-img.com.cn/appimg/screenpic/middle/788/787610.jpg",welcome_img);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
            	case R.id.welcome_btn:
                    handler.removeMessages(0);
                    stepToNext();
            	break;
            	default:
            	break;
            }
        }
    };

    private void stepToNext()
    {
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
        finish();
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            count--;
            if(count > 0)
            {
                welcome_btn.setText(count+"");
                handler.sendEmptyMessageDelayed(0,1000);
            }
            else
            {
                stepToNext();
            }
        }
    };

    @Override
    protected void onDestroy()
    {
        handler.removeMessages(0);
        super.onDestroy();
    }
}
