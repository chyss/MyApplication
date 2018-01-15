package com.chyss.myapplication.rules.visitor.staff;

import com.chyss.myapplication.rules.visitor.visitor.Visitor;

import java.util.Random;

/**
 * 具体员工类 ：产品经理
 *
 * @author chyss 2017-11-27
 */
public class Manager extends Staff
{
    public Manager(String name, int kpi)
    {
        super(name,kpi);
    }

    @Override
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    /**
     * 产品经理完成的产品量
     */
    public int getProducts()
    {
        return new Random().nextInt(10);
    }
}
