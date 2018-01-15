package com.chyss.myapplication.rules.proxy.stati;

import com.chyss.myapplication.rules.proxy.dynami.LawProxy;
import com.chyss.myapplication.rules.proxy.object.Chyss;
import com.chyss.myapplication.rules.proxy.object.ILawsuit;

import java.lang.reflect.Proxy;

/**
 * 访问者模式调用类,静态代理
 *
 * @author chyss 2017-11-29
 */
public class Client
{
    public static void main()
    {
        //创建诉讼人对象
        ILawsuit chyss = new Chyss();
        //构建一个静态代理对象
        ILawsuit lawyer = new Lawyer(chyss);

        lawyer.submit();
        lawyer.burden();
        lawyer.defend();
        lawyer.finish();
    }
}
