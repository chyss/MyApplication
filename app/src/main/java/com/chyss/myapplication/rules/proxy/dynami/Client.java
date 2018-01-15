package com.chyss.myapplication.rules.proxy.dynami;

import com.chyss.myapplication.rules.proxy.object.Chyss;
import com.chyss.myapplication.rules.proxy.object.ILawsuit;

import java.lang.reflect.Proxy;

/**
 * 访问者模式调用类,动态代理
 *
 * @author chyss 2017-11-29
 */
public class Client
{
    public static void main()
    {
        //创建诉讼人对象
        ILawsuit chyss = new Chyss();
        //构建一个动态代理
        LawProxy proxy = new LawProxy(chyss);
        //获取被代理类Chyss的ClassLoader
        ClassLoader loader = chyss.getClass().getClassLoader();
        //动态构造一个代理律师
        ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(loader,new Class[]{ILawsuit.class},proxy);

        lawyer.submit();
        lawyer.burden();
        lawyer.defend();
        lawyer.finish();
    }
}
