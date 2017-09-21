package com.chyss.myapplication.utils.encode;

import com.chyss.myapplication.utils.Logg;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-1是一种数据加密算法，该算法的思想是接收一段明文，
 * 然后以一种不可逆的方式将它转换成一段（通常更小）密文，
 * 也可以简单的理解为取一串输入码（称为预映射或信息），
 * 并把它们转化为长度较短、位数固定的输出序列即散列值（也称为信息摘要或信息认证代码）的过程。
 *
 * 不可逆
 *
 * @author chyss 2017-05-05
 */
public class SHA1Util
{
    /**
     * SHA1加密
     * @param strSrc       要加密的字符串
     * @param encName      加密类型 ： SHA-1
     * @return              密文
     */
    public static String encode(String strSrc, String encName) {
        String strDes;
        if (encName == null || encName.equals("")) {
            encName = "SHA-1";
        }
        try {
            MessageDigest md = MessageDigest.getInstance(encName);
            md.update(strSrc.getBytes());
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            Logg.e("encode","encode error : "+e);
            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
