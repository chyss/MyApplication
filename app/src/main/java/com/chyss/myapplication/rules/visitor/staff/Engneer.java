package com.chyss.myapplication.rules.visitor.staff;

import com.chyss.myapplication.rules.visitor.visitor.Visitor;

import java.util.Random;

/**
 * 具体员工类 ：工程师
 *
 * @author chyss 2017-11-27
 */
public class Engneer extends Staff
{
    public Engneer(String name,int kpi)
    {
        super(name,kpi);
    }

    @Override
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }

    /**
     * 工程师完成的代码量
     */
    public int getCodeLines()
    {
        return new Random().nextInt(10 * 10000);
    }
}
