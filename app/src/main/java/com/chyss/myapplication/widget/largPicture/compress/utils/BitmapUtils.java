package com.chyss.myapplication.widget.largPicture.compress.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片压缩后本地保存，用于图片上传前的处理
 *
 * @author chyss 2017-05-08
 */
public class BitmapUtils
{
    // 现在主流手机已经达到1920*1080分辨率
    private static final float WIDTH = 1080f;
    private static final float HEIGHT = 1920f;

    //压缩后的本地存储路径
    private static final String PATH = "/chyss/";

    /**
     * 压缩上传路径
     * @param path
     * @return
     */
    public static String compressImageUpload(String path) {
        //获取文件名
        String filename = path.substring(path.lastIndexOf("/") + 1);
        File file = new File(path);
        file.isFile();

        Bitmap image = getImage(path);
        return saveMyBitmap(filename, image);
    }

    /**
     * 清除缓存文件
     */
    public static void deleteCacheFile(){
        File file = new File(Environment.getExternalStorageDirectory().getPath()+PATH);
        RecursionDeleteFile(file);
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100 && options > 50) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);

        return bitmap;
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath
     * @return
     */
    private static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int scale = 1;// be=1表示不缩放
        if (w > h && w > WIDTH) {// 如果宽度大的话根据宽度固定大小缩放
            scale = (int) (newOpts.outWidth / WIDTH);
        } else if (w < h && h > HEIGHT) {// 如果高度高的话根据宽度固定大小缩放
            scale = (int) (newOpts.outHeight / HEIGHT);
        }
        if (scale <= 0)
            scale = 1;
        Log.e("measure","be = "+scale+",newOpts.outWidth = "+newOpts.outWidth+",newOpts.outHeight = "+newOpts.outHeight);
        newOpts.inSampleSize = scale;// 设置缩放比例
        newOpts.inJustDecodeBounds = false;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 将压缩的bitmap保存到SDCard卡临时文件夹，用于上传
     *
     * @param filename
     * @param bit
     * @return
     */
    private static String saveMyBitmap(String filename, Bitmap bit) {
        String baseDir = Environment.getExternalStorageDirectory().getPath()+PATH;

        String filePath = baseDir + filename;
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File f = new File(filePath);
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bit.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        finally
        {
            bit.recycle();
        }

        return filePath;
    }

    /**
     * 递归删除
     */
    private static void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}