package com.chyss.myapplication.utils.encode;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 注意密码的长度使不同的，DES是8个字节的长度的密码，DESede是24个字节的长度的密码，
 * AES 是 16个字节的密码加密全部需要通过byte[]作为数据和密钥进行处理，所以需要获取字节数组。
 *
 * create by chyss 2017-6-15
 */
public class DESedeUtil
{

	//秘钥建议本地分段保存，DESede 密钥必须是24个字节
	private static final String KEY = "abcdefghabcdefghabcdefgh";

	/**
	 * 对字符串进行DES3加密
	 * 
	 * @param str
	 * @return 加密后的字符串，若失败则返回null
	 */
	public static String encode(String str)
	{
		try {
			byte[] key = KEY.getBytes("UTF-8");
			byte[] data = str.getBytes("UTF-8");
			byte[] encodedData = des3EncodeECB(key, data);
			return byte2HexString(encodedData);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decode(String str)
	{
		try {
			byte[] key = KEY.getBytes("UTF-8");
			byte[] data = hexString2Byte(str);
			byte[] decodedData = des3DecodeECB(key, data);
			return new String(decodedData, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ECB加密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	private static byte[] des3EncodeECB(byte[] key, byte[] data)
			throws Exception
	{
		Key deskey;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		//desede是加密方式 ECB是工作模式 PKCS5Padding是填充模式
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * ECB解密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	private static byte[] ees3DecodeECB(byte[] key, byte[] data)
			throws Exception
	{
		Key deskey;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	private static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception
	{
		Key deskey;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	private static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception
	{
		Key deskey;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	private static byte[] des3DecodeECB(byte[] key, byte[] data)
			throws Exception
	{
		Key deskey;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	private static String byte2HexString(byte[] b)
	{
		String a = "";
		for (byte aB : b)
		{
			String hex = Integer.toHexString(aB & 0xFF).toUpperCase(Locale.US);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			a = a + hex;
		}
		return a;
	}

	private static byte[] hexString2Byte(String str)
	{
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}
}
