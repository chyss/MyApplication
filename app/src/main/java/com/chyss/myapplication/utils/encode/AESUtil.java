package com.chyss.myapplication.utils.encode;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 注意密码的长度使不同的，DES是8个字节的长度的密码，DESede是24个字节的长度的密码，
 * AES 是 16个字节的密码加密全部需要通过byte[]作为数据和密钥进行处理，所以需要获取字节数组。
 *
 * create by chyss 2017-6-15
 */
public class AESUtil
{
	/**
	 * AES 加密
	 * @param seed   	加密秘钥，16个字节的密码
	 * @param str		要加密的数据
	 * @return			密文
	 * @throws Exception
	 */
	public static String encrypt(String seed, String str)
			throws Exception
	{
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, str.getBytes());
		return toHex(result);
	}

	/**
	 * AES 加密
	 * @param seed   	解密秘钥，16个字节的密码
	 * @param str		要解密的数据
	 * @return			明文
	 * @throws Exception
	 */
	public static String decrypt(String seed, String str)
			throws Exception
	{
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] enc = toByte(str);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	private static byte[] getRawKey(byte[] seed) throws Exception
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception
	{
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception
	{
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	private static String toHex(String txt)
	{
		return toHex(txt.getBytes());
	}

	private static String fromHex(String hex)
	{
		return new String(toByte(hex));
	}

	private static byte[] toByte(String hexString)
	{
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	private static String toHex(byte[] buf)
	{
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (byte aBuf : buf)
		{
			appendHex(result, aBuf);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b)
	{
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}
