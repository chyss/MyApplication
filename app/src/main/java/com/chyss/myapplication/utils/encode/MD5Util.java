package com.chyss.myapplication.utils.encode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5为不可逆加密算法，简单的string做MD5加密很容易被破解，做MD5加密之前通常先对字符串进行加盐处理，再进行一次或多次MD5加密。
 * 
 * @author chyss 2016.8.9
 * 
 */
public class MD5Util
{

	// public static String getMD5(String val) throws NoSuchAlgorithmException {
	// MessageDigest md5 = MessageDigest.getInstance("MD5");
	// md5.update(val.getBytes());
	// byte[] m = md5.digest();// 加密
	// return getString(m);
	// }
	//
	// private static String getString(byte[] b) {
	// StringBuffer sb = new StringBuffer();
	// for (int i = 0; i < b.length; i++) {
	// sb.append(b[i]);
	// }
	// return sb.toString();
	// }

	public static String getMD5(String str)
	{
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}

}
