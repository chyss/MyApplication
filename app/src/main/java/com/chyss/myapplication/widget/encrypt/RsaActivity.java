package com.chyss.myapplication.widget.encrypt;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.encode.RSAUtil;

import java.security.Key;
import java.security.KeyPair;

/**
 * @author chyss
 */

public class RsaActivity extends BaseActivity
{
    private byte[] result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);

        findViewById(R.id.rsa_key).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                KeyPair keyPair = RSAUtil.generateRSAKeyPair(RSAUtil.DEFAULT_KEY_SIZE);
                //得到私钥
                Key privateKey = keyPair.getPrivate();
                String privateKeyStr = Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);
                Logg.e("keypair", "privateKeyStr : " + privateKeyStr);
                //得到公钥
                Key publicKey = keyPair.getPublic();
                String publicKeyStr = Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP);
                Logg.e("keypair", "publicKeyStr : " + publicKeyStr);
            }
        });

        findViewById(R.id.rsa_entrypt).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 加密，PUBLIC_KEY 客户端保存
                try
                {
                    result = RSAUtil.encryptByPublicKey("123456789".getBytes(), Base64.decode(RSA.PUBLIC_KEY, Base64.NO_WRAP));
                    String srt2 = new String(result, "UTF-8");
                    Logg.e("keypair", "加密 : " + srt2);
                }
                catch (Exception e)
                {
                    Logg.e("keypair", "加密 : error  " + e.toString());
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.rsa_detrypt).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    // 解密，PRIVATE_KEY 服务器保存
                    byte[] rusul = RSAUtil.decryptByPrivateKey(result, Base64.decode(RSA.PRIVATE_KEY, Base64.NO_WRAP));
                    String ss = new String(rusul, "UTF-8");
                    Logg.e("keypair", "解密 : " + ss);
                } catch (Exception e)
                {
                    Logg.e("keypair", "解密 : error  " + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }
}
