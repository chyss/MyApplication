package com.chyss.myapplication.rules.dutychain.organization;

import com.chyss.myapplication.utils.Logg;

/**
 * 组长
 *
 * @author chyss 2017-11-23
 */
public class Grouper extends Leader
{
    @Override
    public int limit()
    {
        return 1000; //组长能处理的权限
    }

    @Override
    public void handle(int money)
    {
        Logg.e("iterator","组长处理报销"+money+"元");
    }
}
