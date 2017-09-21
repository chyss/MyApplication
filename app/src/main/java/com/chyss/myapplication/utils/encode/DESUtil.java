package com.chyss.myapplication.utils.encode;

import android.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author chyss 2017-05-05
 */

public class DESUtil
{
    //8位长度的秘钥，最好本地分段保存
    private static final String KEY = "12345678";

    /**
     * DES算法，解密
     *
     * @param str 待解密字符串
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String str) {

        try {
            byte[] data = Base64.decode(str.getBytes("UTF-8"), Base64.DEFAULT);
            DESKeySpec dks = new DESKeySpec(KEY.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
            return "解密异常";
        }
    }

    /**
     * DES算法，加密
     *
     * @param str 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception 异常
     */
    public static String encodeJa(String str) {
        try {
            byte[] data = str.getBytes("UTF-8");
            DESKeySpec dks = new DESKeySpec(KEY.getBytes("UTF-8"));

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data);
            String b64 = Base64.encodeToString(bytes, Base64.DEFAULT);
            return b64;
        } catch (Exception e) {
            e.printStackTrace();
            return "加密异常";
        }
    }
}
