package com.chyss.myapplication.rules.proxy.stati;

import android.app.ActivityManager;

import com.chyss.myapplication.rules.proxy.object.ILawsuit;

/**
 * 静态代理实现
 *
 * @author chyss 2017-11-29
 */
public class Lawyer implements ILawsuit
{
    private ILawsuit lawsuit;

    public Lawyer(ILawsuit lawsuit)
    {
        this.lawsuit = lawsuit;
    }

    @Override
    public void submit()
    {
        lawsuit.submit();
    }

    @Override
    public void burden()
    {
        lawsuit.burden();
    }

    @Override
    public void defend()
    {
        lawsuit.defend();
    }

    @Override
    public void finish()
    {
        lawsuit.finish();
    }
}
