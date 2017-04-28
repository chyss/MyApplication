package com.chyss.myapplication.utils.encode;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

/**
 * Base64编码的作用：由于某些系统中只能使用ASCII字符。Base64就是用来将非ASCII字符的数据转换成ASCII字符的一种方法。
 * base64其实不是安全领域下的加密解密算法。虽然有时候经常看到所谓的base64加密解密。其实base64只能算是一个编码算法，对数据内容进行编码来适合传输。
 * 而且base64特别适合在http，mime协议下快速传输数据。
 * 
 * @author chyss 2016.6.22
 *
 */
public class Base64Util {

	/**
	 * 对图片进行base64编码，返回编码后的字符串
	 * 
	 * @param path
	 *            图片的路径
	 * @return
	 */
	public static String encodePicture(String path) {

		Bitmap bitmap = BitmapFactory.decodeFile(path);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] bytes = baos.toByteArray();
		byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
		String encodeString = new String(encode);

		return encodeString;
	}

	/**
	 * 对图片的base64编码字符串进行解码，返回bitmap
	 * 
	 * @param imgStr
	 *            图片的base64编码字符串
	 * @return
	 */
	public static Bitmap decode2Bitmap(String imgStr) {

		byte[] decode = Base64.decode(imgStr, Base64.DEFAULT);
		Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);

		return bitmap;
	}

	/**
	 * 对图片的base64编码字符串进行解码,并对图片进行保存
	 * 
	 * @param imgStr 图片的base64编码字符串
	 * @param fileName
	 *            如：decodeImage.jpg
	 */
	public static void decodeAndSave(String imgStr, String fileName) {
		
		try {
			
			byte[] decode = Base64.decode(imgStr, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
			
			String path = Environment.getExternalStorageDirectory().getPath()
					+ "/" + fileName;

			OutputStream stream = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			stream.close();

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("linc", "failed: " + e.getMessage());
		}
	}
	
//	public static String encode(String string)
//	{
//		byte[] encode = Base64.encode(string.getBytes(), Base64.DEFAULT);
//		return null;
//	}
}
