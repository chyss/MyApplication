package com.chyss.myapplication.rules.dutychain.organization;

import com.chyss.myapplication.utils.Logg;

/**
 * 主管
 *
 * @author chyss 2017-05-05
 */
public class Director extends Leader
{
    @Override
    public int limit()
    {
        return 10000; //主管能处理的权限
    }

    @Override
    public void handle(int money)
    {
        Logg.e("iterator","主管处理报销"+money+"元");
    }
}
