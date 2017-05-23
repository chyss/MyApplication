package com.chyss.myapplication.widget.largPicture.compress;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.largPicture.compress.utils.BitmapUtils;

/**
 * 展示对大图片的像素压缩后加载
 *
 * @author chyss 2017-05-12
 */

public class LocalImageCompressActivity extends BaseActivity
{
    Button compress_img, delecte_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_compress_local);

        compress_img = (Button) findViewById(R.id.compress_img);
        compress_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Log.e("path",Environment.getExternalStorageDirectory().getPath()+"/larg.jpg");
                        BitmapUtils.compressImageUpload(Environment.getExternalStorageDirectory().getPath() + "/flower.png");
                        BitmapUtils.compressImageUpload(Environment.getExternalStorageDirectory().getPath() + "/larg.jpg");
                    }
                }).start();
            }
        });

        delecte_img = (Button) findViewById(R.id.delecte_img);
        delecte_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BitmapUtils.deleteCacheFile();
            }
        });
    }
}
