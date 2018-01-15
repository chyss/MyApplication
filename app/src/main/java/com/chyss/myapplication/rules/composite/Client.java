package com.chyss.myapplication.rules.composite;

import com.chyss.myapplication.rules.dutychain.organization.Boss;
import com.chyss.myapplication.rules.dutychain.organization.Director;
import com.chyss.myapplication.rules.dutychain.organization.Grouper;
import com.chyss.myapplication.rules.dutychain.organization.Manager;

/**
 * 组合模式，也称部分-整体模式。
 *
 * @author chyss 2017-11-30
 */
public class Client
{
    public static void useComposite()
    {
        //创建根节点对象
        Component root = new Composite("root");

        //构建分支节点
        Component branch1 = new Composite("branch1");
        Component branch2 = new Composite("branch2");

        //构建叶子节点
        Component leaf1 = new Leaf("leaf1");
        Component leaf2 = new Leaf("leaf2");
        Component leaf3 = new Leaf("leaf3");
        Component leaf4 = new Leaf("leaf4");

        //节点添加
        branch1.addChild(leaf1);
        branch1.addChild(leaf2);
        branch2.addChild(leaf3);
        branch2.addChild(leaf4);
        root.addChild(branch1);
        root.addChild(branch2);

        //执行方法
        root.doSomething();
    }
}
