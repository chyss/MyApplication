package com.chyss.myapplication.rules.dutychain;

import com.chyss.myapplication.rules.dutychain.organization.Boss;
import com.chyss.myapplication.rules.dutychain.organization.Director;
import com.chyss.myapplication.rules.dutychain.organization.Grouper;
import com.chyss.myapplication.rules.dutychain.organization.Manager;

/**
 * 职责链模式，调用类。
 *
 * android中使用场景：view事件的分发处理
 *
 * @author chyss 2017-11-23
 */
public class Client
{
    public static void useIterator()
    {
        //创建各leader对象
        Grouper grouper = new Grouper();
        Director director = new Director();
        Manager manager = new Manager();
        Boss boss = new Boss();

        //设置职责链
        grouper.nextHandle = director;
        director.nextHandle = manager;
        manager.nextHandle = boss;

        //发起报账申请
        grouper.handleRequest(20000);

    }
}
