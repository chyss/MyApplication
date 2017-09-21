package com.chyss.myapplication.utils.encode;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA 非对称加密密钥算法
 *
 * 通过OpenSSl工具生成密钥对
 * OpenSSl工具下载：OpenSSl工具 （64位的也可使用）使用OpenSSl工具生成密钥对的过程如下：
 * 首先双击打开bin文件夹下的openssl.exe，打开之后是一个命令行窗口：
 *
 * 通过如下命令生成私钥：
 * genrsa -out rsa_private_key.pem 1024
 * 这条命令是让openssl随机生成了一份私钥，加密长度是1024位， 密钥长度，范围：512～2048。
 * 执行完命令后就可在bin文件夹下看到rsa_private_key.pem文件了。
 * 用文本类工具打开可看到里面的内容：
 *  -----BEGIN RSA PRIVATE KEY-----
 * MIICXQIBAAKBgQCfRTdcPIH10gT9f31rQuIInLwe7fl2dtEJ93gTmjE9c2H+kLVE
 * NWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThraz/L3nYJYlbqjHC3jTjUnZc0l
 * uumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG9aYqgE7zyTRZYX9byQIDAQAB
 * AoGAO9+sYRtKC9xJDfcocfMxv+UT/1ic6EDgcqu6Uzwq+Jvwod9KlXqyQJqCr6T7
 * pjfodc3RAZOTx4gCZJverBvz053RH5GawCdocEgaqbXAAWJOhA+9IEU0NUud7ckF
 * yDko0QXLoGP9tanrMEt5zMqt8QxDyl6Xcij3mk8rivOgBJECQQDNTO6dZX8xCozc
 * Ne0gzC53Gv/KQXANBBHMr7WkKUb2i5+tXkEJ5z3abx2ppEQXDr4AgJH8Gtbm6K7t
 * EHV4ov4FAkEAxppD/iiT1/SVQq20be8CsiHpsjTPiestWQWdm1Qn/Y2nAkGkpCFp
 * yEdUvVDPtQhRN9EqNggNAnwg5kMvsuwN9QJAfHBhQe4/hk5Kyz+0l+irUW6AFOxN
 * KtaIo3TtuK98X/yJsOAstAACMeCgLi9vRjqdWFiWJCVwlU38mZ0cVx8UsQJBALzt
 * M5Er+LiPKw5rQCD0JZRfPnkQU/3XgyQUe4Gv5PsHLcCvwXeBcafcc3hEz9JfPyPi
 * Dk2oCvg6LPHfKBkFBaECQQCODcKX6DBWiyVxmPaJOOcF63KpCYDPkjeovIUHro1x
 * ElR2GrQCC/9Q4C4vruOhBQ+vX8NMPnO6NBy5TLGDwMyc
 * -----END RSA PRIVATE KEY-----
 * 这里面的内容是标准的ASCII字符，中间的一大串字符就是私钥数据了。
 *
 * 通过如下命令生成公钥：
 * rsa -in rsa_private_key.pem -out rsa_public_key.pem -pubout
 *
 * 打开文件看下里面的内容:
 * -----BEGIN PUBLIC KEY-----
 * MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe
 * 7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra
 * z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG
 * 9aYqgE7zyTRZYX9byQIDAQAB
 * -----END PUBLIC KEY-----
 *
 * 这样密钥就基本生成了，不过这样密钥对的私钥是无法在代码中直接使用的，
 * 要想使用它需要借助RSAPrivateKeyStructure这个类，Java是不自带的。
 * 所以为了方便使用，我们需要对私钥进行PKCS#8编码，命令如下：
 * pkcs8 -topk8 -in rsa_private_key.pem -out pkcs8_rsa_private_key.pem -nocrypt
 *
 * 这条命令的结果依然是在bin文件夹生成了pkcs8_rsa_private_key.pem文件，打开内容如下：
 *
 * -----BEGIN PRIVATE KEY-----
 * MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ9FN1w8gfXSBP1/
 * fWtC4gicvB7t+XZ20Qn3eBOaMT1zYf6QtUQ1aAQKIlVDmyidA1/BOgwp07Rvc6V/
 * imAEp4tOGtrP8vedgliVuqMcLeNONSdlzSW66alcayjHrb4+5IYGV9vzMk7qGLHg
 * ZX++HJBUKkb1piqATvPJNFlhf1vJAgMBAAECgYA736xhG0oL3EkN9yhx8zG/5RP/
 * WJzoQOByq7pTPCr4m/Ch30qVerJAmoKvpPumN+h1zdEBk5PHiAJkm96sG/PTndEf
 * kZrAJ2hwSBqptcABYk6ED70gRTQ1S53tyQXIOSjRBcugY/21qeswS3nMyq3xDEPK
 * XpdyKPeaTyuK86AEkQJBAM1M7p1lfzEKjNw17SDMLnca/8pBcA0EEcyvtaQpRvaL
 * n61eQQnnPdpvHamkRBcOvgCAkfwa1uboru0QdXii/gUCQQDGmkP+KJPX9JVCrbRt
 * 7wKyIemyNM+J6y1ZBZ2bVCf9jacCQaSkIWnIR1S9UM+1CFE30So2CA0CfCDmQy+y
 * 7A31AkB8cGFB7j+GTkrLP7SX6KtRboAU7E0q1oijdO24r3xf/Imw4Cy0AAIx4KAu
 * L29GOp1YWJYkJXCVTfyZnRxXHxSxAkEAvO0zkSv4uI8rDmtAIPQllF8+eRBT/deD
 * JBR7ga/k+wctwK/Bd4Fxp9xzeETP0l8/I+IOTagK+Dos8d8oGQUFoQJBAI4Nwpfo
 * MFaLJXGY9ok45wXrcqkJgM+SN6i8hQeujXESVHYatAIL/1DgLi+u46EFD69fw0w+
 * c7o0HLlMsYPAzJw=
 * -----END PRIVATE KEY-----
 *
 * 可以看到中间的私钥内容有所变化了，这样的私钥我们在代码里就方便使用了。
 * 以上的密钥文件使用时需要注意吧头和尾的字符串去掉，我们只取中间的内容。
 *
 * @author chyss 2017-06-16
 */
public class RSAUtil
{
    /* 密钥内容 base64 code */
    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe"
            + "\r" + "7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra" + "\r"
            + "z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG" + "\r"
            + "9aYqgE7zyTRZYX9byQIDAQAB" + "\r";
    private static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ9FN1w8gfXSBP1/"
            + "\r" + "fWtC4gicvB7t+XZ20Qn3eBOaMT1zYf6QtUQ1aAQKIlVDmyidA1/BOgwp07Rvc6V/" + "\r"
            + "imAEp4tOGtrP8vedgliVuqMcLeNONSdlzSW66alcayjHrb4+5IYGV9vzMk7qGLHg" + "\r"
            + "ZX++HJBUKkb1piqATvPJNFlhf1vJAgMBAAECgYA736xhG0oL3EkN9yhx8zG/5RP/" + "\r"
            + "WJzoQOByq7pTPCr4m/Ch30qVerJAmoKvpPumN+h1zdEBk5PHiAJkm96sG/PTndEf" + "\r"
            + "kZrAJ2hwSBqptcABYk6ED70gRTQ1S53tyQXIOSjRBcugY/21qeswS3nMyq3xDEPK" + "\r"
            + "XpdyKPeaTyuK86AEkQJBAM1M7p1lfzEKjNw17SDMLnca/8pBcA0EEcyvtaQpRvaL" + "\r"
            + "n61eQQnnPdpvHamkRBcOvgCAkfwa1uboru0QdXii/gUCQQDGmkP+KJPX9JVCrbRt" + "\r"
            + "7wKyIemyNM+J6y1ZBZ2bVCf9jacCQaSkIWnIR1S9UM+1CFE30So2CA0CfCDmQy+y" + "\r"
            + "7A31AkB8cGFB7j+GTkrLP7SX6KtRboAU7E0q1oijdO24r3xf/Imw4Cy0AAIx4KAu" + "\r"
            + "L29GOp1YWJYkJXCVTfyZnRxXHxSxAkEAvO0zkSv4uI8rDmtAIPQllF8+eRBT/deD" + "\r"
            + "JBR7ga/k+wctwK/Bd4Fxp9xzeETP0l8/I+IOTagK+Dos8d8oGQUFoQJBAI4Nwpfo" + "\r"
            + "MFaLJXGY9ok45wXrcqkJgM+SN6i8hQeujXESVHYatAIL/1DgLi+u46EFD69fw0w+" + "\r" + "c7o0HLlMsYPAzJw="
            + "\r";

    public static final String RSA = "RSA";// 非对称加密密钥算法
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    public static final int DEFAULT_KEY_SIZE = 2048;//秘钥默认长度
    public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
    public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用公钥对字符串进行加密
     *
     * @param data 原文
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);
        return cp.doFinal(data);
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);
        // 数据加密
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data      待解密数据
     * @param publicKey 密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 数据解密
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, keyPublic);
        return cipher.doFinal(data);
    }

    /**
     * 使用私钥进行解密
     */
    public static byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey) throws Exception {
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);

        // 解密数据
        Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);
        byte[] arr = cp.doFinal(encrypted);
        return arr;
    }
}
