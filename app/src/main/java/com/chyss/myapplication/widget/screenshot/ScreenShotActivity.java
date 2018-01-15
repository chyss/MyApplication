package com.chyss.myapplication.widget.screenshot;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.CommUtils;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.ScreenShotUtils;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenShotActivity extends BaseActivity
{
    ImageView screenshot_img;
    Button screenshot_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);

        screenshot_btn = (Button)findViewById(R.id.screenshot_btn);
        screenshot_img = (ImageView)findViewById(R.id.screenshot_img);

        screenshot_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bitmap bmp = ScreenShotUtils.screenshot(ScreenShotActivity.this);

                if(bmp != null)
                {
                    screenshot_img.setImageBitmap(bmp);
                    ScreenShotUtils.saveBitmap(bmp);
                }
            }
        });
    }
}
