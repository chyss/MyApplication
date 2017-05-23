package com.chyss.myapplication.widget.largPicture.compress;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.largPicture.compress.utils.PictureUtils;

/**
 * 展示对大图片的像素压缩后加载
 *
 * @author chyss 2017-05-12
 */

public class BigImageCompressActivity extends BaseActivity
{
    private ImageView img_compress_big;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_compress);

        img_compress_big = (ImageView)findViewById(R.id.img_compress_big);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //获取资源中图片
                PictureUtils.setImageResource(BigImageCompressActivity.this,R.mipmap.larg,img_compress_big);

                //本地图片--- 需先判断存储权限--可参考动态获取权限模块
                //PictureUtils.setImageLocal(BigImageCompressActivity.this, Environment.getExternalStorageDirectory().getPath() + "/larg.jpg", img_compress_big);

                //assets图片
                //PictureUtils.setImageAssets(BigImageCompressActivity.this,"larg.jpg",img_compress_big);
            }
        }, 1000);
    }
}
