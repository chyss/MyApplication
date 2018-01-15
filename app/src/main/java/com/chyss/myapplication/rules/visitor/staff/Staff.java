package com.chyss.myapplication.rules.visitor.staff;

import com.chyss.myapplication.rules.visitor.visitor.Visitor;

/**
 * staff抽象类
 *
 * @author chyss 2017-11-27
 */
public abstract class Staff
{
    public String name;
    public int kpi;

    public Staff(String name,int kpi)
    {
        this.name = name;
        this.kpi = kpi;
    }

    // 接受Visitor的访问
    public abstract void accept(Visitor visitor);
}
