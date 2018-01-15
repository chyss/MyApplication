package com.chyss.myapplication.rules.proxy.dynami;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理实现
 *
 * @author chyss 2017-11-29
 */
public class LawProxy implements InvocationHandler
{
    private Object obj;

    public LawProxy(Object obj)
    {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        Object result = method.invoke(obj,args);
        return result;
    }
}
