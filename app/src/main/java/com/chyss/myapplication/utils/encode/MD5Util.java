package com.chyss.myapplication.utils.encode;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5将任意长度的”字节串”变换成一个128bit的大整数,并且它是一个不可逆的字符串变换算法。
 * 简单的string做MD5加密很容易被破解，做MD5加密之前通常先对字符串进行加盐处理，再进行一次或多次MD5加密。
 *
 * 不可逆
 *
 * @author chyss 2016.8.9
 * 
 */
public class MD5Util
{
	public static String getMD5(String str)
	{
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] byteArray = messageDigest.digest(str.getBytes("UTF-8"));
			StringBuffer md5StrBuff = new StringBuffer();
			for (byte aByteArray : byteArray)
			{
				if (Integer.toHexString(0xFF & aByteArray).length() == 1)
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
			}
			//大写输出结果
			return md5StrBuff.toString().toUpperCase();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
