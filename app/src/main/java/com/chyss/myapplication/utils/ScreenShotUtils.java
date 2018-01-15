package com.chyss.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author chyss 2017-05-05
 */

public class ScreenShotUtils
{
    private static final String SAVE_PATH = "/chyss/";

    /**
     * 获取屏幕截图
     *
     * @param mActivity
     * @return
     */
    public static Bitmap screenshot(Activity mActivity)
    {
        // 获取屏幕
        View dView = mActivity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();

        return bmp;
    }

    /**
     *
     * @param bmp
     */
    public static void saveBitmap(Bitmap bmp)
    {
        if (bmp != null)
        {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath()+SAVE_PATH;

                //如果路径不存在则创建路径
                File dir = new File(sdCardPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                // 组装图片文件路径
                StringBuilder builder = new StringBuilder();
                builder.append(sdCardPath);
                builder.append("screenshot");
                builder.append(CommUtils.getTimeForPicture());
                builder.append(".png");

                //保存图片
                File file = new File(builder.toString());
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            }
            catch (Exception e) {
            }
        }
    }
}
