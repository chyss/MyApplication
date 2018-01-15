package com.chyss.myapplication.rules.dutychain.organization;

import com.chyss.myapplication.utils.Logg;

/**
 * BOSS
 *
 * @author chyss 2017-11-23
 */
public class Boss extends Leader
{
    @Override
    public int limit()
    {
        return Integer.MAX_VALUE; //boss能处理的权限
    }

    @Override
    public void handle(int money)
    {
        Logg.e("iterator","BOSS处理报销"+money+"元");
    }
}
