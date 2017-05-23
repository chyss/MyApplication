package com.chyss.myapplication.widget.largPicture.compress.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 用于对需要加载的图片进行像素压缩后加载
 * （不进行质量压缩，因为质量压缩不减少内存的读入）
 *
 * @author chyss 2017-05-05
 */

public class PictureUtils
{
    /**
     * 对资源中的大图片进行分辨率大小的压缩，压缩之后才加载到内存中，防止OOM。
     * 同时按照view的大小进行压缩，保证清晰度。
     * @param context
     * @param resourceId   资源的id
     * @param view          显示图片的控件Imageview
     */
    public static void setImageResource(Context context, int resourceId, ImageView view)
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 不读取像素数组到内存中，仅读取图片的信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resourceId,opts);
        setOpts(context,view,opts);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceId,opts);
        view.setImageBitmap(bitmap);
    }

    /**
     * 对本地中的大图片进行分辨率大小的压缩，压缩之后才加载到内存中，防止OOM。
     * 同时按照view的大小进行压缩，保证清晰度。
     * 如果是Assets中的图片 String srcPath = "file:///android_asset/文件名";
     * @param context
     * @param srcPath       本地图片路径，or Assets中的图片
     * @param view          显示图片的控件Imageview
     */
    public static void setImageLocal(Context context, String srcPath, ImageView view)
    {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 不读取像素数组到内存中，仅读取图片的信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath,opts);
        setOpts(context,view,opts);
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,opts);
        view.setImageBitmap(bitmap);
    }

    /**
     * 对Assets中的图片进行分辨率大小的压缩，压缩之后才加载到内存中，防止OOM。
     * 同时按照view的大小进行压缩，保证清晰度。
     * @param context
     * @param imageName     Assets中图片的文件名
     * @param view          显示图片的控件Imageview
     */
    public static void setImageAssets(Context context, String imageName, ImageView view)
    {
        try
        {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 不读取像素数组到内存中，仅读取图片的信息
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getAssets().open(imageName),null,opts);
            setOpts(context,view,opts);
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(imageName),null,opts);
            view.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
        	//Logg.e(Net.TAG, "catch error : " + e);
        }
    }

    /**
     *  采样率的计算，Options的设置
     * @param context
     * @param view
     * @param opts
     */
    private static void setOpts(Context context,ImageView view,BitmapFactory.Options opts)
    {
        // 从Options中获取图片的分辨率
        int imageHeight = opts.outHeight;
        int imageWidth = opts.outWidth;

        int scaleX;
        int scaleY;

        // 计算采样率
        if(view.getWidth() > 0 && view.getHeight() > 0)
        {
            //采用view的尺寸对应得大小进行压缩
            scaleX = imageWidth / view.getWidth();
            scaleY = imageHeight / view.getHeight();
        }
        else
        {
            //view未初始化完成，获得的宽高为0，采用屏幕的分辨率尺寸来进行压缩
            WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE); // 获取Android屏幕的服务
            // 获取屏幕的分辨率，getHeight()、getWidth已经被废弃掉了
            // 应该使用getSize()，但是这里为了向下兼容所以依然使用它们
            int windowHeight = wm.getDefaultDisplay().getHeight();
            int windowWidth = wm.getDefaultDisplay().getWidth();
            scaleX = imageWidth / windowWidth;
            scaleY = imageHeight / windowHeight;
        }
        int scale = 1;
        // 采样率依照最大的方向为准
        if (scaleX > scaleY && scaleY >= 1) {
            scale = scaleX;
        }
        if (scaleX < scaleY && scaleX >= 1) {
            scale = scaleY;
        }
        Log.e("measure","scale = "+scale);
        // false表示读取图片像素数组到内存中，依照设定的采样率
        opts.inJustDecodeBounds = false;
        // 采样率
        opts.inSampleSize = scale;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }
}
