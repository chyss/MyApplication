package com.chyss.myapplication.widget.messagePack;

import android.os.Bundle;
import android.util.Log;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.messagePack.bean.Info;

import org.msgpack.MessagePack;

/**
 * MessagePack的序列化与反序列化过程
 * MessagePack是一种高效的二进制序列化格式。它允许您像JSON一样在多个语言之间交换数据。
 * 但是，它更快并且更小。小整数被编码为一个字节,和典型的短字符串只需要除了字符串本身的一个额外字节。
 */
public class MessagePackActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagepack_activity);
        setTitle("MsgPack");

        try
        {
            MessagePack messagePack = new MessagePack();

            //序列化过程
            Info info = new Info();
            info.setId("11111");
            info.setName("chyss");
            byte[] bs = messagePack.write(info);

            //return EntityUtils.toByteArray(response.getEntity());
            //反序列化
            Info infoOut = messagePack.read(bs, Info.class);
            Log.e("msgpack", "infoOut : " + infoOut);
        }
        catch (Exception e)
        {
        	Log.e("msgpack", "catch error : " + e);
        }
    }
}
