package com.chyss.myapplication.rules.dutychain.organization;

import com.chyss.myapplication.utils.Logg;

/**
 * 经理
 *
 * @author chyss 2017-11-23
 */
public class Manager extends Leader
{
    @Override
    public int limit()
    {
        return 100000; //经理能处理的权限
    }

    @Override
    public void handle(int money)
    {
        Logg.e("iterator","经理处理报销"+money+"元");
    }
}
