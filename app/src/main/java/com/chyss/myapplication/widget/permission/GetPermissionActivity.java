package com.chyss.myapplication.widget.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.largPicture.compress.BigImageCompressActivity;
import com.chyss.myapplication.widget.largPicture.compress.utils.PictureUtils;

import java.util.Stack;

/**
 * @author chyss 2017-05-05
 */

public class GetPermissionActivity extends BaseActivity
{
    private ImageView img_compress_big;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_compress);

        img_compress_big = (ImageView)findViewById(R.id.img_compress_big);

        // 6.0 版本以上
        if (Build.VERSION.SDK_INT >= 23)
        {
            // 返回 0 则存储权限已获取
            int permi = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permi != PackageManager.PERMISSION_GRANTED)
            {
                //requestCode = 10; 可随意设置，匹配onRequestPermissionsResult返回的requestCode
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
            else
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //本地图片
                        PictureUtils.setImageLocal(GetPermissionActivity.this, Environment.getExternalStorageDirectory().getPath() + "/larg.jpg", img_compress_big);
                    }
                }, 200);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("permission", "requestCode" + requestCode);
        if (Build.VERSION.SDK_INT >= 23)
        {
            for (int i = 0; i < grantResults.length; i++)
            {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                {
                    boolean isTip = shouldShowRequestPermissionRationale(permissions[i]);
                    if (isTip)
                    {
                        Log.e("permission", "禁止权限请求！");
                    } else
                    {
                        Log.e("permission", "彻底禁止权限请求！");
                    }
                    return;
                } else
                {
                    PictureUtils.setImageLocal(GetPermissionActivity.this, Environment.getExternalStorageDirectory().getPath() + "/larg.jpg", img_compress_big);
                    Log.e("permission", "允许权限请求！");
                }
            }
        }
    }
}
